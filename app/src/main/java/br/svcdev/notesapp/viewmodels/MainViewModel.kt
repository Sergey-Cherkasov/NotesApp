package br.svcdev.notesapp.viewmodels

import androidx.lifecycle.*
import br.svcdev.notesapp.data.MainModel

class MainViewModel : ViewModel() {

    private val viewStateLiveData = MutableLiveData<Int>()
    private val mainModel: MainModel = MainModel()
    private val observer: Observer<Int> = Observer{
        viewStateLiveData.value = it
    }
    private val counterLiveData = mainModel.counterLiveData()

    init {
        counterLiveData.observeForever(observer)
    }

    fun getViewState(): LiveData<Int> = viewStateLiveData

    fun onClickButton() {
        mainModel.increaseCounter()
    }

    override fun onCleared() {
        super.onCleared()
        counterLiveData.removeObserver(observer)
    }
}