package br.svcdev.notesapp.repository.data

import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.repository.remotedata.FirestoreProvider
import br.svcdev.notesapp.repository.remotedata.RemoteDataProvider

object NotesData {

    private val remoteDataProvider: RemoteDataProvider = FirestoreProvider()

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)
    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
    fun getCurrentUser() = remoteDataProvider.getCurrentUser()

}