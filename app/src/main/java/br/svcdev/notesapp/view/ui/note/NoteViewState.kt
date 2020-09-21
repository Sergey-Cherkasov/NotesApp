package br.svcdev.notesapp.view.ui.note

import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.view.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) :
    BaseViewState<Note?>(note, error) {

}