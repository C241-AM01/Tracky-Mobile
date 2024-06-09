import java.text.SimpleDateFormat
import java.util.*

object DateTimeFormat {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val inputPFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val customPDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    fun formatDate(timestamp: String): String {
        val date = inputFormat.parse(timestamp)
        return dateFormat.format(date!!)
    }

    fun formatTime(timestamp: String): String {
        val date = inputFormat.parse(timestamp)
        return timeFormat.format(date!!)
    }

    fun formatCustomDate(timestamp: String): String {
        val date = inputPFormat.parse(timestamp)
        return customPDateFormat.format(date!!)
    }
}
