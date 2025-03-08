package com.assessment.socialdeal

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.assessment.socialdeal.data.AppContainer
import com.assessment.socialdeal.data.DefaultAppContainer

private const val PREFERENCE_DATASTORE_NAME = "preferencesDatastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCE_DATASTORE_NAME
)

class DealsApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(dataStore = dataStore)
    }
}