package br.svcdev.notesapp.view.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import br.svcdev.notesapp.R
import br.svcdev.notesapp.common.extensions.getColorInt
import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.view.ui.base.BaseActivity
import br.svcdev.notesapp.viewmodels.NoteViewModel
import kotlinx.android.synthetic.main.activity_note.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity: BaseActivity<NoteViewState.Data, NoteViewState>() {
    companion object{
        private const val NOTE_KEY = "note"
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, noteId: String? = null) =
            Intent(context, NoteActivity::class.java).apply {
                putExtra(NOTE_KEY, noteId)
                context.startActivity(this)
            }
    }
    private var note: Note? = null
    override val viewModel : NoteViewModel by viewModel()
    override val layoutResource = R.layout.activity_note

    var color: Note.Color = Note.Color.WHITE

    val textWatcher = object: TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(NOTE_KEY)
        noteId?.let {
            viewModel.loadNote(it)
        }
    }

    private fun initView() {
        titleEt.removeTextChangedListener(textWatcher)
        bodyEt.removeTextChangedListener(textWatcher)
        note?.let {
            titleEt.setTextKeepState(it.mTitle)
            bodyEt.setTextKeepState(it.mText)
            toolbar.setBackgroundColor(it.mColor.getColorInt(this))
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(it.mLastChanged)
        }
        titleEt.addTextChangedListener(textWatcher)
        bodyEt.addTextChangedListener(textWatcher)
        colorPicker.onColorClickListener = {
            color = it
            toolbar.setBackgroundColor(it.getColorInt(this))
            saveNote()
        }
    }

    private fun saveNote() {
        titleEt.text?.let {
            if (it.length < 3) return
        }?: return
        note = note?.copy(
            mTitle = titleEt.text.toString(),
            mText = bodyEt.text.toString(),
            mLastChanged = Date(),
            mColor = color
        )?: Note(UUID.randomUUID().toString(), titleEt.text.toString(), bodyEt.text.toString(),
            color)
        note?.let { viewModel.save(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu) = menuInflater.inflate(R.menu.note_menu, menu)
        .let { true }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        R.id.palette -> togglePalette().let{ true }
        R.id.delete -> deleteNote().let{ true }
        else -> super.onOptionsItemSelected(item)
    }

    override fun renderData(data: NoteViewState.Data) {
        if (data.isDeleted) {
            finish()
            return
        }
        this.note = data.note
        initView()
    }

    private fun togglePalette() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(this)
            .setTitle("Delete note")
            .setMessage("Are You sure that you want to delete note?")
            .setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }
            .setPositiveButton(R.string.delete) { dialog, which -> viewModel.deleteNote() }
            .show()
    }
}