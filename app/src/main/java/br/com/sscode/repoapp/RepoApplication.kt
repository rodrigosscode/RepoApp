package br.com.sscode.repoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RepoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RepoServiceLocator.init(this)
    }
}