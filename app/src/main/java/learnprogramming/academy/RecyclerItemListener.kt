package learnprogramming.academy

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "RItemListener"
class RecyclerItemListener(context: Context, recycler_view: RecyclerView, private val listener: OnRItemClick) :
    RecyclerView.SimpleOnItemTouchListener(){
    interface OnRItemClick{
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    private val gestureDetector = GestureDetectorCompat(context, object: GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.d(TAG, "onSingleTapUp called")
            val childView = recycler_view.findChildViewUnder(e.x, e.y)
            Log.d(TAG, "onSingleTapUp: Calling the listener.onItemClick")
            listener.onItemClick(childView!!, recycler_view.getChildAdapterPosition(childView))
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            Log.d(TAG, "onLongPressCalled")
            val childView = recycler_view.findChildViewUnder(e.x, e.y)
            Log.d(TAG, "onLongPress: Calling the listener.onItemLongClick")
            listener.onItemLongClick(childView!!, recycler_view.getChildAdapterPosition(childView))
        }
    })
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: motion = $e")
        val result = gestureDetector.onTouchEvent(e)
//        return super.onInterceptTouchEvent(rv, e)
        Log.d(TAG, "onInterceptTouchEvent: returning $result")
        return result
    }
}