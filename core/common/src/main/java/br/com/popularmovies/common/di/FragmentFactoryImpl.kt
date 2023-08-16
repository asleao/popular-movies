package br.com.popularmovies.common.di


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class FragmentFactoryImpl @Inject constructor(
    private val fragmentProviders: Map<Class<out Fragment>,
            @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)
        val creator =
            fragmentProviders[fragmentClass] ?: return super.instantiate(classLoader, className)

        try {
            return creator.get()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}