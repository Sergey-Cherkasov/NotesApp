package br.svcdev.notesapp.repository.remotedata

import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.repository.model.NoteResult
import br.svcdev.notesapp.repository.model.User
import kotlinx.coroutines.channels.ReceiveChannel

interface RemoteDataProvider {
    fun subscribeToAllNotes(): ReceiveChannel<NoteResult>

    suspend fun getCurrentUser(): User?
    suspend fun getNoteById(id: String): Note?
    suspend fun saveNote(note: Note): Note
    suspend fun deleteNote(id: String)
}