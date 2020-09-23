package br.svcdev.notesapp.di

import br.svcdev.notesapp.repository.data.NotesData
import br.svcdev.notesapp.repository.remotedata.FirestoreProvider
import br.svcdev.notesapp.repository.remotedata.RemoteDataProvider
import br.svcdev.notesapp.viewmodels.MainViewModel
import br.svcdev.notesapp.viewmodels.NoteViewModel
import br.svcdev.notesapp.viewmodels.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<RemoteDataProvider> { FirestoreProvider(get(),get()) }
    single { NotesData(get()) }
}

val splashViewModule = module {
    viewModel { SplashViewModel(get()) }
}

val noteViewModule = module {
    viewModel { NoteViewModel(get()) }
}

val mainViewModule = module {
    viewModel { MainViewModel(get()) }
}