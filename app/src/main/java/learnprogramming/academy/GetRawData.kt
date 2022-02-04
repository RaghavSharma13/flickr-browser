package learnprogramming.academy

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus{
    OK, IDLE, NOT_INITIALIZED, FAILED_OR_EMPTY, PERMISSION_ERROR, ERROR
}

private const val TAG = "getRawData"

class GetRawData {
    private var downloadStatus = DownloadStatus.IDLE

    suspend fun doInBackground(link: String): Any = withContext(Dispatchers.IO){
        try{
            downloadStatus = DownloadStatus.OK
            val result = URL(link).readText()
            DownloadedData(result, downloadStatus)
        }catch(e: Exception){
            val errorMsg = when(e){
                is MalformedURLException -> {
                    downloadStatus = DownloadStatus.NOT_INITIALIZED
                    "doInBackground: URL not initialized: ${e.message}"
                }
                is IOException -> {
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY
                    "doInBackground: IO exception reading data: ${e.message}"
                }
                is SecurityException -> {
                    downloadStatus = DownloadStatus.PERMISSION_ERROR
                    "doInBackground: Security exception permissions needed ${e.message}"
                }
                else -> {
                    downloadStatus = DownloadStatus.ERROR
                    "doInBackground: Unknown Error: ${e.message}"
                }
            }
            Log.e(TAG, errorMsg)
            DownloadedData(errorMsg, downloadStatus)
        }
    }
}