package com.reco1l.rimu.ui

import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import com.reco1l.toolkt.kotlin.isLazyInit
import com.reco1l.toolkt.kotlin.isLazyInitialized
import com.reco1l.rimu.IWithContext
import com.reco1l.rimu.MainContext
import com.reco1l.rimu.management.skin.WorkingSkin
import org.andengine.entity.IEntity

/**
 * Indicates that a View or Entity is skinnable.
 *
 * For [ViewGroup] or [IEntity] types the method [onApplySkin] will be called recursively between children.
 */
interface ISkinnable
{

    fun onApplySkin(skin: WorkingSkin)
    {
        if (this is View)
        {
            (background as? ISkinnable)?.onApplySkin(skin)
            (foreground as? ISkinnable)?.onApplySkin(skin)

            if (this is ViewGroup)
                forEach { (it as? ISkinnable)?.onApplySkin(skin) }

            return
        }

        if (this is IEntity)
            callOnChildren { (it as? ISkinnable)?.onApplySkin(skin) }
    }

    /**
     * Calls [onApplySkin] with the context skin, if there's a [MainContext] implementation you
     * don't need to pass the context.
     */
    fun invalidateSkin(ctx: MainContext = (this as IWithContext).ctx)
    {
        // Preventing from race condition when skin isn't loaded yet.
        ctx.skins.current?.also { onApplySkin(it) }
    }
}


open class SkinningRules<T>
{
    open fun onApplySkin(target: T, skin: WorkingSkin) = Unit
}

interface ISkinnableWithRules<T : Any, D : SkinningRules<T>> : ISkinnable
{

    /**
     * The view skinning rules, every rule will be applied once [onApplySkin] is called.
     */
    val rules: D

    @Suppress("UNCHECKED_CAST")
    override fun onApplySkin(skin: WorkingSkin)
    {
        // Preventing unnecessary initialization.
        if (!::rules.isLazyInit || ::rules.isLazyInitialized)
            rules.onApplySkin(this as T, skin)

        super.onApplySkin(skin)
    }

    /**
     * Apply the skinning rules to the view.
     */
    fun setSkinning(block: D.() -> Unit) = rules.apply(block)
}