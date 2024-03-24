package ru.edu.hse.mentalhealthwithgpt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.edu.hse.common.Core
import ru.edu.hse.common.CoreProvider
import ru.edu.hse.data.RemoteConfigManager
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication: Application() {

    @Inject
    lateinit var coreProvider: CoreProvider

    override fun onCreate() {
        super.onCreate()
        Core.init(coreProvider)
        RemoteConfigManager.fetchRemoteConfig()
    }

}