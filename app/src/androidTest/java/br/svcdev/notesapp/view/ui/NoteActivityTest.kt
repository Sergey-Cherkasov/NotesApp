package br.svcdev.notesapp.view.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import br.svcdev.notesapp.R
import br.svcdev.notesapp.common.extensions.getColorInt
import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.view.ui.note.NoteActivity
import br.svcdev.notesapp.view.ui.note.NoteViewState
import br.svcdev.notesapp.viewmodels.NoteViewModel
import io.mockk.*
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext

class NoteActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(NoteActivity::class.java,
        true, false)

    private val model: NoteViewModel = mockk(relaxed = true)
    private val viewStateLiveData = MutableLiveData<NoteViewState>()

    private val testNote = Note("id1", "title 1", "text 1", Note.Color.GREEN)

    @Before
    fun setup() {
        StandAloneContext.loadKoinModules(
            listOf(
                module {
                    viewModel { model }
                }
            )
        )

        every { model.getViewState() } returns viewStateLiveData
        every { model.loadNote(any()) } just runs
        every { model.save(any()) } just runs
        every { model.deleteNote() } just runs

        activityTestRule.launchActivity(null)
        viewStateLiveData.postValue(NoteViewState())
    }

    @Test
    fun should_show_color_picker() {
        onView(withId(R.id.palette)).perform(click())
        onView(withId(R.id.colorPicker)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun should_close_color_picker() {
        onView(withId(R.id.palette)).perform(click()).perform(click())
        onView(withId(R.id.colorPicker)).check(matches(not(isDisplayed())))
    }

    @Test
    fun should_set_toolbar_color() {
        onView(withId(R.id.palette)).perform(click())
        onView(withTagValue(`is`(Note.Color.GREEN))).perform(click())

        val colorInt = Note.Color.GREEN.getColorInt(activityTestRule.activity)

        onView(withId(R.id.toolbar)).check {view, _ ->
            assertTrue("toolbar background color does not match",
            (view.background as? ColorDrawable)?.color == colorInt)}
    }

    // Данный тест не работает
    @Test
    fun should_call_viewModel_loadNote_once() {
        verify(exactly = 1) { model.loadNote(testNote.mId) }
    }

    @Test
    fun should_show_note() {
        viewStateLiveData.postValue(NoteViewState(NoteViewState.Data(note = testNote)))

        onView(withId(R.id.titleEt)).check(matches(withText(testNote.mTitle)))
        onView(withId(R.id.bodyEt)).check(matches(withText(testNote.mText)))
    }

    @Test
    fun should_call_saveNote() {
        onView(withId(R.id.titleEt)).perform(typeText(testNote.mTitle))
        verify(timeout = 1000) { model.save(any()) }
    }

    @Test
    fun should_call_deleteNote() {
        viewStateLiveData.postValue(NoteViewState(NoteViewState.Data(note = testNote)))
        onView(withId(R.id.delete)).perform(click())
        onView(withText(R.string.delete)).perform(click())
        verify { model.deleteNote() }
    }

    @After
    fun teardown() {
        StandAloneContext.stopKoin()
    }

}