package br.svcdev.notesapp.viewmodels

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import br.svcdev.notesapp.repository.data.NotesData
import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.repository.model.NoteResult
import br.svcdev.notesapp.view.ui.main.MainViewState
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MainViewModel(val notesData: NotesData) : BaseViewModel<List<Note>?>() {

    private val notesChannel = notesData.getNotes()

    init {
        launch {
            notesChannel.consumeEach {
                when(it){
                    is NoteResult.Success<*> ->
                        setData(it.data as? List<Note>)
                    is NoteResult.Error -> setError(it.error)
                }
            }
        }
    }

    @VisibleForTesting
    override public fun onCleared() {
        super.onCleared()
        notesChannel.cancel()
    }

}