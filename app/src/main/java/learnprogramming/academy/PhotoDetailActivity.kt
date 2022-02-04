package learnprogramming.academy

import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_photo_detail.*

class PhotoDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        activateToolbar(true)

        val photoItem = intent.getParcelableExtra<PhotoClass>(PHOTO_TRANSFER) as PhotoClass
        photo_title.text = resources.getString(R.string.title_res,photoItem.title)
        photo_detail.text = photoItem.author
        photo_tags.text = resources.getString(R.string.tags_res,photoItem.tags)
        Picasso.get().load(photoItem.link).error(R.drawable.placeholder_image).placeholder(R.drawable.placeholder_image)
            .into(photo_image)

    }
}