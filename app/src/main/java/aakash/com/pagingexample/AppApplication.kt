package aakash.com.pagingexample

import aakash.com.pagingexample.injection.component.AppComponent
import aakash.com.pagingexample.injection.component.DaggerAppComponent
import aakash.com.pagingexample.injection.module.NetworkModule
import android.app.Application

/**
 * Created by stllpt031 on 14/8/18.
 */
class AppApplication : Application() {
    lateinit var mComponent: AppComponent

    companion object {

        private lateinit var instance: AppApplication

        fun getAppContext(): AppApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDagger()
    }

    private fun initDagger() {
        mComponent = DaggerAppComponent.builder()
                .networkModule(NetworkModule)
                .build()
    }
}