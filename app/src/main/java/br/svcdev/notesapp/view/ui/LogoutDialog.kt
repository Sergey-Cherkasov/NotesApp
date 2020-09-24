package br.svcdev.notesapp.view.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class LogoutDialog() : DialogFragment() {
    companion object {
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun createInstance() = LogoutDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(context!!)
            .setTitle("Logout")
            .setMessage("Are You sure?")
            .setPositiveButton("Ok") { _, _ -> (activity as LogoutListener).onLogout() }
            .setNegativeButton("No") { _, _ -> dismiss() }
            .create()

    interface LogoutListener {
        fun onLogout()
    }
}