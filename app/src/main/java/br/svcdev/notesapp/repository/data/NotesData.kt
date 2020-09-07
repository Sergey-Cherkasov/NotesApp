package br.svcdev.notesapp.repository.data

import br.svcdev.notesapp.R
import br.svcdev.notesapp.repository.model.Note

object NotesData {
    private val notes: List<Note> = listOf(
        Note(
            "Первая заметка",
            "Текст первой заметки, не очень длинный, но интересный",
            R.color.colorRed_500
        ),
        Note(
            "Вторая заметка",
            "Текст третьей заметки. А этот текст немного длинней и все еще интересный",
            R.color.colorIndigo_500
        ),
        Note(
            "Третья заметка",
            "Текст третьей заметки. А этот текст гораздо длинней, чем предудущий и так же все еще интересный",
            R.color.colorBlue_500
        ),
        Note(
            "Четвертая заметка",
            "Текст четвертой заметки короткий, но интересный",
            R.color.colorGreen_500
        ),
        Note(
            "Пятая заметка",
            "Текст пятой заметки, не очень длинный, но интересный",
            R.color.colorYellow_500
        )
    )

    fun getNotes(): List<Note> = notes
}