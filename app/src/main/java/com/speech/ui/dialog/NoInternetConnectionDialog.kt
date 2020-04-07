package com.speech.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.speech.R


class NoInternetConnectionDialog : DialogFragment(), DialogInterface.OnClickListener  {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val adb: AlertDialog.Builder = AlertDialog.Builder(context!!)
            .setTitle(R.string.no_internet)
            .setPositiveButton(R.string.settings, this)
            .setNegativeButton(android.R.string.cancel, this)
            .setMessage(R.string.no_internet_hint)

        return adb.create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
       when(which){
           Dialog.BUTTON_POSITIVE -> startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0);
           Dialog.BUTTON_NEGATIVE -> dismiss()
       }
    }
}