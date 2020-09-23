package br.svcdev.notesapp.viewmodels

import br.svcdev.notesapp.repository.data.NotesData
import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.repository.model.NoteResult
import br.svcdev.notesapp.view.ui.note.NoteViewState

class NoteViewModel(val notesData: NotesData): BaseViewModel<NoteViewState.Data, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    fun loadNote(noteId: String){
        notesData.getNoteById(noteId).observeForever { result ->
            result?: return@observeForever
            when(result) {
                is NoteResult.Success<*> -> {
                    pendingNote = result.data as? Note
                    viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = pendingNote))
                }
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
            }
        }
    }

    override fun onCleared() {
        pendingNote?.let {
            notesData.saveNote(it)
        }
    }

    fun deleteNote() {
        pendingNote?.let {
            notesData.deleteNote(it.mId).observeForever { result ->
                result?: return@observeForever
                pendingNote = null
                when(result) {
                    is NoteResult.Success<*> ->
                        viewStateLiveData.value = NoteViewState(NoteViewState.Data(isDeleted = true))
                    is NoteResult.Error -> viewStateLiveData.value =
                        NoteViewState(error = result.error)
                }
            }
        }
    }

}