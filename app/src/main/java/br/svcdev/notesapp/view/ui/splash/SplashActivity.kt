package br.svcdev.notesapp.view.ui.splash

import br.svcdev.notesapp.view.ui.base.BaseActivity
import br.svcdev.notesapp.view.ui.main.MainActivity
import br.svcdev.notesapp.viewmodels.SplashViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class SplashActivity : BaseActivity<Boolean?>() {

    override val viewModel: SplashViewModel by viewModel()

    override val layoutResource: Int? = null

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        finish()
    }
}