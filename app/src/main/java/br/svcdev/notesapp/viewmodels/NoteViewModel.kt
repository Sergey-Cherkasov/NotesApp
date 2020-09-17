package br.svcdev.notesapp.viewmodels

import androidx.lifecycle.ViewModel
import br.svcdev.notesapp.repository.data.NotesData
import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.repository.model.NoteResult
import br.svcdev.notesapp.view.base.BaseViewModel
import br.svcdev.notesapp.view.ui.MainViewState
import br.svcdev.notesapp.view.ui.NoteViewState

class NoteViewModel: BaseViewModel<Note?, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var pendingNote: Note? = null
    fun save(note: Note) {
        pendingNote = note
    }

    fun loadNote(noteId: String){
        NotesData.getNoteById(noteId).observeForever {result ->
            result?: return@observeForever
            when(result){
                is NoteResult.Success<*> ->
                    viewStateLiveData.value = NoteViewState(note = result.data as? Note)
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
            }
        }
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesData.saveNote(it)
        }
    }

}