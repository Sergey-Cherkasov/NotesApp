package br.svcdev.notesapp.repository.data

import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.repository.remotedata.FirestoreProvider
import br.svcdev.notesapp.repository.remotedata.RemoteDataProvider

class NotesData(val remoteDataProvider: RemoteDataProvider) {

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()
    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)
    fun deleteNote(id: String) = remoteDataProvider.deleteNote(id)
    fun getCurrentUser() = remoteDataProvider.getCurrentUser()

}