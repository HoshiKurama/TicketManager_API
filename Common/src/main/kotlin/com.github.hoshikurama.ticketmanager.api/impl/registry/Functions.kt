package com.github.hoshikurama.ticketmanager.api.impl.registry

import com.github.hoshikurama.ticketmanager.api.utilities.Result
import java.lang.Exception
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

internal fun <T: Any> KClass<T>.hasZeroArgConstructor(): Boolean =
    this.primaryConstructor?.parameters?.isEmpty() ?: false

internal inline fun <T, U> T.runCatch(block: T.() -> U): Result<U> {
    return try { Result.Success(block(this)) }
    catch (e: Exception) { Result.Failure(e) }
}