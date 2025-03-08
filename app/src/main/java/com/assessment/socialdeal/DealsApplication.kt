package com.assessment.socialdeal

import android.app.Application
import com.assessment.socialdeal.data.AppContainer
import com.assessment.socialdeal.data.DefaultAppContainer

class DealsApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}