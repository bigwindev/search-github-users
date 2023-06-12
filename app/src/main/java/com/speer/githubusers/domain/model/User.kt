package com.speer.githubusers.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class User(
    val login: String,
    val id: Long,
    @SerializedName("avatar_url") val avatarUrl: String,
    val url: String,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val bio: String?,
    val followers: Long,
    val following: Long
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(login)
        parcel.writeLong(id)
        parcel.writeString(avatarUrl)
        parcel.writeString(url)
        parcel.writeString(name)
        parcel.writeString(company)
        parcel.writeString(blog)
        parcel.writeString(location)
        parcel.writeString(email)
        parcel.writeString(bio)
        parcel.writeLong(followers)
        parcel.writeLong(following)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
