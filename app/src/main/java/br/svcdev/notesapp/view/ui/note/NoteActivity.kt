package br.svcdev.notesapp.view.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import br.svcdev.notesapp.R
import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.view.ui.base.BaseActivity
import br.svcdev.notesapp.viewmodels.NoteViewModel
import kotlinx.android.synthetic.main.activity_note.*
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity: BaseActivity<Note?, NoteViewState>() {
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
    override val viewModel by lazy {
        ViewModelProvider(this).get(NoteViewModel::class.java)
    }
    override val layoutResource = R.layout.activity_note

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
        }
        titleEt.addTextChangedListener(textWatcher)
        bodyEt.addTextChangedListener(textWatcher)
    }

    private fun saveNote() {
        titleEt.text?.let {
            if (it.length < 3) return
        }?: return
        note = note?.copy(
            mTitle = titleEt.text.toString(),
            mText = bodyEt.text.toString(),
            mLastChanged = Date()
        )?: Note(UUID.randomUUID().toString(), titleEt.text.toString(), bodyEt.text.toString())
        note?.let { viewModel.save(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun renderData(data: Note?) {
        this.note = data
        supportActionBar?.title = note?.let {
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(it.mLastChanged)
        }?: getString(R.string.new_note)
        initView()
    }
}