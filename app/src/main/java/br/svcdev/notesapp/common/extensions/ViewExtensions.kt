package br.svcdev.notesapp.common.extensions

import android.view.View

inline fun View.dip(value: Int): Float = resources.displayMetrics.density * value