package br.svcdev.notesapp.view.ui

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import br.svcdev.notesapp.R
import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.view.adapter.NotesRVAdapter
import br.svcdev.notesapp.view.ui.main.MainActivity
import br.svcdev.notesapp.view.ui.main.MainViewState
import br.svcdev.notesapp.viewmodels.MainViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin

class MainActivityTest {

    @get:Rule
    val activityTestRule = IntentsTestRule(MainActivity::class.java,
        true, false)

    private val model: MainViewModel = mockk(relaxed = true)
    private val viewStateLiveData = MutableLiveData<MainViewState>()

    private val testNotes = listOf(
        Note("id1", "title 1", "text 1"),
        Note("id2", "title 2", "text 2"),
        Note("id3", "title 3", "text 3"),
        Note("id4", "title 4", "text 4"),
        Note("id5", "title 5", "text 5"),
        Note("id6", "title 6", "text 6"),
        Note("id7", "title 7", "text 7")
    )

    @Before
    fun setup() {
        loadKoinModules(
            listOf(
                module {
                    viewModel { model }
                }
            )
        )
        every { model.getViewState() } returns viewStateLiveData
        activityTestRule.launchActivity(null)
        viewStateLiveData.postValue(MainViewState(notes = testNotes))
    }

    @Test
    fun check_data_is_displayed() {
        onView(withId(R.id.notes_recycler_view))
            .perform(scrollToPosition<NotesRVAdapter.ViewHolder>(4))
        onView(withText(testNotes[4].mText)).check(matches(isDisplayed()))
    }

    @After
    fun teardown() {
        stopKoin()
    }

}