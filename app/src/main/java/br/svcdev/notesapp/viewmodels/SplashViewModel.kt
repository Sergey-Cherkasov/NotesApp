package br.svcdev.notesapp.viewmodels

import br.svcdev.notesapp.repository.data.NotesData
import br.svcdev.notesapp.repository.model.errors.NoAuthException
import kotlinx.coroutines.launch

class SplashViewModel(val notesData: NotesData) : BaseViewModel<Boolean?>() {
    fun requestUser() = launch {
        notesData.getCurrentUser()?.let {
            setData(true)
        } ?: setError(NoAuthException())
    }
}