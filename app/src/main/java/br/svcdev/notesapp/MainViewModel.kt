package br.svcdev.notesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val viewStateLiveData = MutableLiveData<Int>()
    private val mainModel: MainModel = MainModel()

    init {
        viewStateLiveData.value = mainModel.getCounter()
    }

    fun getViewState(): LiveData<Int> = viewStateLiveData
    fun onClickButton() {
        viewStateLiveData.value = mainModel.getCounter()
    }
}