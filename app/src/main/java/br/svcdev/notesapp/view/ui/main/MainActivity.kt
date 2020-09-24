package br.svcdev.notesapp.view.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.svcdev.notesapp.R
import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.view.adapter.NotesRVAdapter
import br.svcdev.notesapp.view.ui.base.BaseActivity
import br.svcdev.notesapp.view.ui.LogoutDialog
import br.svcdev.notesapp.view.ui.note.NoteActivity
import br.svcdev.notesapp.view.ui.splash.SplashActivity
import br.svcdev.notesapp.viewmodels.MainViewModel
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<List<Note>?, MainViewState>(), LogoutDialog.LogoutListener {

    companion object {
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    override val viewModel: MainViewModel by viewModel()
    override val layoutResource: Int = R.layout.activity_main
    private lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        
        notes_recycler_view.layoutManager = StaggeredGridLayoutManager(2, 1)

        adapter = NotesRVAdapter{
            NoteActivity.start(this, it.mId)
        }
        notes_recycler_view.adapter = adapter

        floating_button.setOnClickListener{
            NoteActivity.start(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.logout -> showLogoutDialog().let{true}
            else -> false
        }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }

    private fun showLogoutDialog() {
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?:
                LogoutDialog().show(supportFragmentManager, LogoutDialog.TAG)
    }

    override fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }
    }

}