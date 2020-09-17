package br.svcdev.notesapp.repository.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    var mId: String = "",
    var mTitle: String = "",
    var mText: String = "",
    var mColor: Color = Color.WHITE,
    var mLastChanged: Date = Date()) : Parcelable {

    enum class Color {
        WHITE,
        RED,
        INDIGO,
        BLUE,
        GREEN,
        YELLOW,
        ORANGE
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Note
        if (mId != other.mId) return false
        return true
    }

    override fun hashCode(): Int {
        var result = mId.hashCode()
        result = 31 * result + mTitle.hashCode()
        result = 31 * result + mText.hashCode()
        result = 31 * result + mColor.hashCode()
        result = 31 * result + mLastChanged.hashCode()
        return result
    }

}