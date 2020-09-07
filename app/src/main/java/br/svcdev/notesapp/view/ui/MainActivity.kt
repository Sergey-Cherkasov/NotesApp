package br.svcdev.notesapp.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.svcdev.notesapp.R
import br.svcdev.notesapp.view.adapter.NotesRVAdapter
import br.svcdev.notesapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        notes_recycler_view.layoutManager = StaggeredGridLayoutManager(2, 1)
        adapter = NotesRVAdapter()
        notes_recycler_view.adapter = adapter

        viewModel.getViewState().observe(this, { value ->
            value?.let { adapter.notes = it.notes }
        })

    }
}