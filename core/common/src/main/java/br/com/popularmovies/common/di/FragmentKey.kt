package br.com.popularmovies.common.di

import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass
import java.lang.annotation.*

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)
