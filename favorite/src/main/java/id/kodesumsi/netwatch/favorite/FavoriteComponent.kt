package id.kodesumsi.netwatch.favorite

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.kodesumsi.netwatch.core.di.FavoriteModuleDepencencies

@Component(dependencies = [FavoriteModuleDepencencies::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(appDepencencies: FavoriteModuleDepencencies): Builder
        fun build(): FavoriteComponent
    }

}