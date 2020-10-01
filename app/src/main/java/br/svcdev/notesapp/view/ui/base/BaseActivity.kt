package br.svcdev.notesapp.view.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.svcdev.notesapp.R
import br.svcdev.notesapp.repository.model.errors.NoAuthException
import br.svcdev.notesapp.viewmodels.BaseViewModel
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<S> : AppCompatActivity(), CoroutineScope {

    companion object {
        private const val REQUEST_CODE_SIGN_IN = 100
    }

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    abstract val viewModel: BaseViewModel<S>
    abstract val layoutResource: Int?

    private lateinit var dataJob: Job
    private lateinit var errorJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutResource?.let {
            setContentView(it)
        }
    }

    override fun onStart() {
        super.onStart()
        dataJob = launch {
            viewModel.getViewState().consumeEach { renderData(it) }
        }

        errorJob = launch {
            viewModel.getErrorChannel().consumeEach { renderError(it) }
        }
    }

    override fun onStop() {
        super.onStop()
        dataJob.cancel()
        errorJob.cancel()
    }

    abstract fun renderData(data: S)

    protected fun renderError(error: Throwable) {
        when (error) {
            is NoAuthException -> startLogin()
            else -> error.message?.let {
                showError(it)
            }
        }
    }

    protected fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun startLogin() {
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setLogo(R.drawable.ic_launcher_foreground) // TODO: Change logo
            .setAvailableProviders(providers)
            .build()

        startActivityForResult(intent, REQUEST_CODE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN && resultCode != Activity.RESULT_OK) {
            finish()
        }
    }

}