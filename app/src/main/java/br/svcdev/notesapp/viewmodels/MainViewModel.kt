package br.svcdev.notesapp.viewmodels

import androidx.lifecycle.*
import br.svcdev.notesapp.repository.data.NotesData
import br.svcdev.notesapp.view.ui.MainViewState

class MainViewModel : ViewModel() {

    private val viewStateLiveData = MutableLiveData<MainViewState>()

    init {
        viewStateLiveData.value = MainViewState(NotesData.getNotes())
    }

    fun getViewState(): LiveData<MainViewState> = viewStateLiveData

}