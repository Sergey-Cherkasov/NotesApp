package br.svcdev.notesapp.view.ui.note

import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.view.ui.base.BaseViewState

class NoteViewState(data: Data = Data(), error: Throwable? = null) :
    BaseViewState<NoteViewState.Data>(data, error) {
    data class Data (val isDeleted:  Boolean = false, val note: Note? = null)
}