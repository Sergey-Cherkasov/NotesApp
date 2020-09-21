package br.svcdev.notesapp.viewmodels

import br.svcdev.notesapp.repository.data.NotesData
import br.svcdev.notesapp.repository.model.errors.NoAuthException
import br.svcdev.notesapp.view.ui.splash.SplashViewState

class SplashViewModel(): BaseViewModel<Boolean?, SplashViewState>() {
    fun requestUser(){
        NotesData.getCurrentUser().observeForever {
            viewStateLiveData.value = if (it != null) {
                SplashViewState(authenticated = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}