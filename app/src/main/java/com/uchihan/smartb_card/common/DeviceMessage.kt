package com.uchihan.smartb_card.common

import android.content.SharedPreferences
import com.google.android.gms.nearby.messages.Message
import com.google.gson.Gson
import com.uchihan.smartb_card.common.DeviceMessage
import java.nio.charset.Charset

/**
 * Used to prepare the payload for a
 * [Nearby Message][com.google.android.gms.nearby.messages.Message]. Adds a unique id
 * to the Message payload, which helps Nearby distinguish between multiple devices with
 * the same model name.
 */
class DeviceMessage private constructor(private val mUUID: String,sharedPreferences: SharedPreferences) {
    val messageBody = sharedPreferences.getString(Constants.REGISTERED_USER,"empty")

    companion object {
        private val gson = Gson()

        /**
         * Builds a new [Message] object using a unique identifier.
         */
        fun newNearbyMessage(instanceId: String,sharedPreferences: SharedPreferences): Message {
            val deviceMessage = DeviceMessage(instanceId,sharedPreferences)
            return Message(gson.toJson(deviceMessage).toByteArray(Charset.forName("UTF-8")))
        }

        /**
         * Creates a `DeviceMessage` object from the string used to construct the payload to a
         * `Nearby` `Message`.
         */
        fun fromNearbyMessage(message: Message): DeviceMessage {
            val nearbyMessageString = String(message.content).trim { it <= ' ' }
            return gson.fromJson(
                String(nearbyMessageString.toByteArray(Charset.forName("UTF-8"))),
                DeviceMessage::class.java
            )
        }
    }
}