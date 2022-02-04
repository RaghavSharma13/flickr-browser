package learnprogramming.academy

import android.view.View
import androidx.appcompat.app.AppCompatActivity

internal const val FLICKR_QUERY = "FLICKR_QUERY"
internal const val PHOTO_TRANSFER = "PHOTO_TRANSFER"
open class BaseActivity: AppCompatActivity() {
    internal fun activateToolbar(enableHome: Boolean){
        var toolbar = findViewById<View>(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}