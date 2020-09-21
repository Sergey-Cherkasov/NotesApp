package br.svcdev.notesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.svcdev.notesapp.view.ui.base.BaseViewState

open class BaseViewModel<T, S: BaseViewState<T>>: ViewModel() {
    open val viewStateLiveData = MutableLiveData<S>()
    open fun getViewState(): LiveData<S> = viewStateLiveData
}