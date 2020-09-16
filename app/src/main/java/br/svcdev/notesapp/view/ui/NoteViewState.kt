package br.svcdev.notesapp.view.ui

import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.view.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) :
    BaseViewState<Note?>(note, error) {

}