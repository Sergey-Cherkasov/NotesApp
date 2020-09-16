package br.svcdev.notesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import br.svcdev.notesapp.repository.data.NotesData
import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.repository.model.NoteResult
import br.svcdev.notesapp.view.base.BaseViewModel
import br.svcdev.notesapp.view.ui.MainViewState

class MainViewModel : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer<NoteResult>{ result ->
        result ?: return@Observer
        when(result){
            is NoteResult.Success<*> ->
                viewStateLiveData.value = MainViewState(notes = result.data as? List<Note>)
            is NoteResult.Error -> viewStateLiveData.value = MainViewState(error = result.error)
        }
    }
    private val repositoryNotes = NotesData.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        super.onCleared()
        repositoryNotes.removeObserver(notesObserver)
    }

}