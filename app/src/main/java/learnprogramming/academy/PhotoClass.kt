package learnprogramming.academy

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PhotoClass(
    val title: String,
    val author: String,
    val author_id: String,
    val tags: String,
    val image: String,
    val link: String
) : Parcelable {
    override fun toString(): String {
        return "Photo( title: $title ; author: $author ; author_id: $author_id ; tags: $tags ; image: $image ; link: $link )"
    }
}