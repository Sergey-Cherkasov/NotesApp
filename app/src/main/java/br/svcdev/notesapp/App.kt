package br.svcdev.notesapp

import android.app.Application
import br.svcdev.notesapp.di.appModule
import br.svcdev.notesapp.di.mainViewModule
import br.svcdev.notesapp.di.noteViewModule
import br.svcdev.notesapp.di.splashViewModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    companion object {
        lateinit var instance: App
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin(this, listOf(appModule, splashViewModule, mainViewModule, noteViewModule))
    }
}