package br.svcdev.notesapp.repository.data

import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.repository.remotedata.RemoteDataProvider

class NotesData(private val remoteDataProvider: RemoteDataProvider) {

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()
    suspend fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
    suspend fun saveNote(note: Note) = remoteDataProvider.saveNote(note)
    suspend fun deleteNote(id: String) = remoteDataProvider.deleteNote(id)
    suspend fun getCurrentUser() = remoteDataProvider.getCurrentUser()

}