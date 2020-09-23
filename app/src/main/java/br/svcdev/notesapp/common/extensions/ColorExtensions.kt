package br.svcdev.notesapp.common.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import br.svcdev.notesapp.R
import br.svcdev.notesapp.repository.model.Note

inline fun Note.Color.getColorRes(context: Context) = when(this){
    Note.Color.RED -> R.color.colorRed_500
    Note.Color.INDIGO -> R.color.colorIndigo_500
    Note.Color.BLUE -> R.color.colorBlue_500
    Note.Color.GREEN -> R.color.colorGreen_500
    Note.Color.YELLOW -> R.color.colorYellow_500
    Note.Color.ORANGE -> R.color.colorOrange_500
    Note.Color.WHITE -> R.color.colorWhite
}

inline fun Note.Color.getColorInt(context: Context) =
    ContextCompat.getColor(context, getColorRes(context))