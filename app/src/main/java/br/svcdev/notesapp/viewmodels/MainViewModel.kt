package br.svcdev.notesapp.viewmodels

import androidx.lifecycle.*
import br.svcdev.notesapp.repository.data.NotesData
import br.svcdev.notesapp.view.ui.MainViewState

class MainViewModel : ViewModel() {

    private val viewStateLiveData = MutableLiveData<MainViewState>()

    init {
        NotesData.getNotes().observeForever{
            viewStateLiveData.value = viewStateLiveData.value?.copy(notes = it)?: MainViewState(it)
        }
    }

    fun getViewState(): LiveData<MainViewState> = viewStateLiveData

}