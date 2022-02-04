package learnprogramming.academy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
private const val TAG = "mainActivity"

class MainActivity : BaseActivity(), RecyclerItemListener.OnRItemClick{
//    private val flickrUrl = "https://api.flickr.com/services/feeds/photos_public.gne?tags=android,oreo,sdk&tagmode=any&format=json&nojsoncallback=1"
    private val flickrRecyclerViewAdapter = FlickrRecycler(ArrayList())
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate Started")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activateToolbar(false)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addOnItemTouchListener(RecyclerItemListener(this, recycler_view, this))
        recycler_view.adapter = flickrRecyclerViewAdapter

//        val flickrURL = createUri("https://api.flickr.com/services/feeds/photos_public.gne", "android,oreo", "en-us", true)
//        getData(flickrURL)
        Log.d(TAG, "onCreate Ended")
    }

    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG, "onItemClick: Starts")
        val photoItem = flickrRecyclerViewAdapter.getPhoto(position)
        val intent = Intent(this, PhotoDetailActivity::class.java)
        intent.putExtra(PHOTO_TRANSFER, photoItem)
        startActivity(intent)
//        Toast.makeText(this, "Normal tap at $position", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, "onItemLongClick: Start")
//        Toast.makeText(this, "Long tap at $position", Toast.LENGTH_SHORT).show()
    }

    private fun createUri(baseUrl: String, tags: String, lang: String, matchAll: Boolean): String{
        Log.d(TAG, "CreateURI starting")
        return Uri.parse(baseUrl).
                    buildUpon().
                    appendQueryParameter("tags", tags).
                    appendQueryParameter("tagmode", if(matchAll) "ALL" else "ANY").
                    appendQueryParameter("lang", lang).
                    appendQueryParameter("format", "json").
                    appendQueryParameter("nojsoncallback", "1").
                    build().toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.menu_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getData(flickrUrl: String){

        CoroutineScope(Dispatchers.Default).launch {
            val getRawData = GetRawData()
            val response: DownloadedData = getRawData.doInBackground(flickrUrl) as DownloadedData
//            Log.d(TAG, response.showResult())
            val parsedData = ParseJSON()
            var photoList = parsedData.parse(response.result)
            Log.d(TAG, "photoList created with ${photoList.size} elements.")
            withContext(Dispatchers.Main){
                flickrRecyclerViewAdapter.loadNewData(photoList)
            }
            Log.d(TAG, "List was created")
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        var querryTags = sharedPref.getString(FLICKR_QUERY, "nature")
        if(!querryTags.isNullOrEmpty()){
            querryTags = querryTags.split(' ').joinToString(",")
            val flickrURL = createUri("https://api.flickr.com/services/feeds/photos_public.gne", querryTags, "en-us", true)
            getData(flickrURL)
        }
    }
}