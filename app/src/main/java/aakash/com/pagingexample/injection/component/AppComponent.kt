package aakash.com.pagingexample.injection.component

import aakash.com.pagingexample.ui.repos.view.MainActivity
import aakash.com.pagingexample.injection.module.NetworkModule
import dagger.Component
import aakash.com.pagingexample.injection.module.ViewModelModule
import javax.inject.Singleton

/**
 * Created by stllpt031 on 14/8/18.
 */
@Singleton
@Component(modules = [
    NetworkModule::class,
    ViewModelModule::class
])
interface AppComponent {
    fun inject(activity: MainActivity)
}