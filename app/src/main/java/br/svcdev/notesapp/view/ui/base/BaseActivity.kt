package br.svcdev.notesapp.view.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.svcdev.notesapp.R
import br.svcdev.notesapp.repository.model.errors.NoAuthException
import br.svcdev.notesapp.viewmodels.BaseViewModel
import com.firebase.ui.auth.AuthUI

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_SIGN_IN = 100
    }

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutResource: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutResource?.let {
            setContentView(it)
        }
        viewModel.getViewState().observe(this, Observer { state ->
            state ?: return@Observer
            state.error?.let {
                renderError(it)
                return@Observer
            }
            renderData(state.data)
        })
    }

    abstract fun renderData(data: T)

    protected fun renderError(error: Throwable) {
        when(error) {
            is NoAuthException -> startLogin()
            else -> error.message?.let { showError(it)
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