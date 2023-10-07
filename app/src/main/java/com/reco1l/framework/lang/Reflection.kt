package com.reco1l.framework.lang

import kotlin.reflect.KClass
import kotlin.reflect.KProperty0
import kotlin.reflect.full.createInstance

/**
 * Equivalent of [Class.getSimpleName]. 
 */
fun Any?.getClassName() = if (this == null) "Null" else this::class.simpleName ?: "Null"

/**
 * Create a new instance with the given parameters.
 */
fun <T : Any> KClass<T>.createInstance(vararg parameters: Any?): T
{
    if (parameters.isEmpty())
        return createInstance()

    val constructor = constructors.find { it.parameters.size == parameters.size }
        ?:
        throw IllegalArgumentException("No constructor matches the given parameters for class: $this")

    return constructor.callBy(constructor.parameters.associateWithIndexed { _, i -> parameters[i] })
}


val KProperty0<*>.isLazyInit: Boolean
    get() = getDelegate() is Lazy<*>

/**
 * Returns `true` if the lazy property was initialized, `false` if it was not or if it's not a lazy
 * property.
 */
val KProperty0<*>.isLazyInitialized: Boolean
    get() = (getDelegate() as? Lazy<*>)?.isInitialized() ?: false
