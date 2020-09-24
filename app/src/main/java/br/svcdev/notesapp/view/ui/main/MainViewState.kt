package br.svcdev.notesapp.view.ui.main

import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.view.ui.base.BaseViewState

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null) :
    BaseViewState<List<Note>?>(notes, error) {
}