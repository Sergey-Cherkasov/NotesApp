package br.svcdev.notesapp.view.ui

import br.svcdev.notesapp.repository.model.Note

data class MainViewState(val notes: List<Note>) {
}