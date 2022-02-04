package learnprogramming.academy

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.CancellationException

private const val TAG = "parseJSON"
class ParseJSON {
    suspend fun parse(json: String): ArrayList<PhotoClass> = withContext(Dispatchers.Default) {
        Log.d(TAG, "Starting Parsing")
        val photoList = ArrayList<PhotoClass>()
        try{
            val jsonData = JSONObject(json)
            val itemsArray = jsonData.getJSONArray("items")
            for(i in 0 until itemsArray.length()){
                val jsonPhoto = itemsArray.getJSONObject(i)
                val photoTitle = jsonPhoto.getString("title")
                val photoAuthor = jsonPhoto.getString("author")
                val photoAuthorId = jsonPhoto.getString("author_id")
                val photoTags = jsonPhoto.getString("tags")
                val jsonMedia = jsonPhoto.getJSONObject("media")
                val photoImage = jsonMedia.getString("m")
                val photoLink = photoImage.replace("_m.jpg", "_b.jpg")

                val photoObject = PhotoClass(photoTitle, photoAuthor, photoAuthorId, photoTags, photoImage, photoLink)
//                Log.d(TAG, photoObject.toString())
                photoList.add(photoObject)
            }
        }catch (e: JSONException){
            e.printStackTrace()
            cancel(CancellationException())
            Log.e(TAG, "Parse error: ${e.message}")
        }
        Log.d(TAG, "Finished Parsing")
        photoList
    }
}