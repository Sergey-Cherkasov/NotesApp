package br.svcdev.notesapp.view.ui.splash

import br.svcdev.notesapp.view.ui.base.BaseViewState

class SplashViewState(authenticated: Boolean? = null, error: Throwable? = null):
    BaseViewState<Boolean?>(authenticated, error)