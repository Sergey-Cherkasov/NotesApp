package br.svcdev.notesapp.repository.remotedata

import androidx.lifecycle.LiveData
import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.repository.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
}