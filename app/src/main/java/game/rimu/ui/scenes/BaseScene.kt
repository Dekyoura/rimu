package game.rimu.ui.scenes

import android.view.KeyEvent
import game.rimu.IWithContext
import game.rimu.MainContext
import game.rimu.ui.IScalable
import org.andengine.entity.scene.Scene
import android.view.KeyEvent.Callback as KeyEventCallback

abstract class BaseScene(final override val ctx: MainContext) :
    Scene(),
    IScalable,
    IWithContext,
    KeyEventCallback
{

    // Key listening events

    override fun onKeyDown(keyCode: Int, event: KeyEvent?) = true

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?) = false

    override fun onKeyUp(keyCode: Int, event: KeyEvent?) = false

    override fun onKeyMultiple(keyCode: Int, count: Int, event: KeyEvent?) = false
}