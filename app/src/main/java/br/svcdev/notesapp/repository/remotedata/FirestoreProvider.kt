package br.svcdev.notesapp.repository.remotedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.repository.model.NoteResult
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreProvider: RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    private val store = FirebaseFirestore.getInstance()
    private val notesRference = store.collection(NOTES_COLLECTION)

    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesRference.addSnapshotListener { snapShop, error ->
            error?.let {
                result.value = NoteResult.Error(it)
            } ?: snapShop?.let {
                val notes = snapShop.documents.mapNotNull { it.toObject(Note::class.java) }
                result.value = NoteResult.Success(notes)
            }
        }
        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesRference.document(note.mId).set(note)
            .addOnSuccessListener {snapShot ->
                result.value = NoteResult.Success(note)
            }.addOnFailureListener {
                result.value = NoteResult.Error(it)
            }
        return result
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesRference.document(id).get()
            .addOnSuccessListener {snapShot ->
                val note = snapShot.toObject(Note::class.java)
                result.value = NoteResult.Success(note)
            }.addOnFailureListener {
                result.value = NoteResult.Error(it)
            }
        return result
    }
}