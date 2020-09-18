package br.svcdev.notesapp.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.svcdev.notesapp.R
import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.view.adapter.NotesRVAdapter
import br.svcdev.notesapp.view.base.BaseActivity
import br.svcdev.notesapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override val layoutResource: Int = R.layout.activity_main
    private lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notes_recycler_view.layoutManager = StaggeredGridLayoutManager(2, 1)

        adapter = NotesRVAdapter{
            NoteActivity.start(this, it.mId)
        }
        notes_recycler_view.adapter = adapter

        floating_button.setOnClickListener{
            NoteActivity.start(this)
        }
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }
}