package br.svcdev.notesapp.view.ui.splash

import br.svcdev.notesapp.view.ui.base.BaseActivity
import br.svcdev.notesapp.view.ui.main.MainActivity
import br.svcdev.notesapp.viewmodels.SplashViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {
    override val viewModel :SplashViewModel by viewModel()
    override val layoutResource : Int? = null

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