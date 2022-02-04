package learnprogramming.academy

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

private const val TAG = "flickrRecycler"
class FlickrViewHolder(private var view: View): RecyclerView.ViewHolder(view){
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var imageTitle: TextView = view.findViewById(R.id.imageTitile)
}
class FlickrRecycler(private var photoList: List<PhotoClass>): RecyclerView.Adapter<FlickrViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrViewHolder {
        Log.d(TAG, "onCreateViewHolder Called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
        return FlickrViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlickrViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder Called")
        if(photoList.isEmpty()){
            holder.thumbnail.setImageResource(R.drawable.placeholder_image)
            holder.imageTitle.setText(R.string.empty_list_msg)
        }else{
            val photoItem = photoList[position]
//        Log.d(TAG, "titile: ${photoItem.title}")
            Picasso.get().load(photoItem.image)
                .error(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.thumbnail)
            holder.imageTitle.text = photoItem.title
        }
    }

    override fun getItemCount(): Int {
//        Log.d(TAG, "getItemCount called")
        return if(photoList.isNotEmpty()) photoList.size else 1
    }

    fun getPhoto(position: Int): PhotoClass?{
        return if(photoList.isNotEmpty()) photoList[position] else null
    }

    fun loadNewData(newPhotoList: List<PhotoClass>){
        photoList = newPhotoList
        notifyDataSetChanged()
    }
}