package br.svcdev.notesapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainModel: ViewModel() {
    private var counter: Int = 0
    private val counterLiveData = MutableLiveData<Int>()

    fun counterLiveData(): MutableLiveData<Int> = counterLiveData

    fun increaseCounter() {
        counter++
        counterLiveData.value = counter
    }
}