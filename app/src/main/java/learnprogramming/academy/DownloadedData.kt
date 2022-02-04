package learnprogramming.academy

class DownloadedData(val result: String, val status: DownloadStatus) {

    fun showResult(): String {
        return result
    }
    fun showStatus(): DownloadStatus{
        return status
    }
}