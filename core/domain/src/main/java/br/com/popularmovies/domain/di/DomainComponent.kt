package br.com.popularmovies.domain.di

import br.com.popularmovies.data.di.DataComponent
import dagger.Component

@Component(
    dependencies = [DataComponent::class]
)
interface DomainComponent : DomainComponentProvider {
    @Component.Factory
    interface Factory {
        fun create(dataComponent: DataComponent): DomainComponent
    }
}
