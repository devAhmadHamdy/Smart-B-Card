package com.uchihan.smartb_card.common

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.data.models.UserModel

object Utils {
    fun shareApp(context: Context, appURL: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Share Your B-Card with me :) \n $appURL")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

    fun addToContacts(context: Context, userModel: RoomUserModel) {
        val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
            // Sets the MIME type to match the Contacts Provider
            type = ContactsContract.RawContacts.CONTENT_TYPE
        }

        intent.apply {
            // Inserts Name
            putExtra(ContactsContract.Intents.Insert.NAME, userModel.name)

            // Inserts an email address
            putExtra(ContactsContract.Intents.Insert.EMAIL, userModel.email)

            // Inserts a phone number
            putExtra(ContactsContract.Intents.Insert.PHONE, userModel.mobile)

            // Inserts job
            putExtra(ContactsContract.Intents.Insert.JOB_TITLE, userModel.work)

        }
        context.startActivity(intent)
    }
}