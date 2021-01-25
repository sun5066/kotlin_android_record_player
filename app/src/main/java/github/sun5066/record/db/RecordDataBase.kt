package github.sun5066.record.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import github.sun5066.record.model.RecordData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [RecordData::class], version = 1, exportSchema = false)
abstract class RecordDataBase : RoomDatabase() {
    abstract fun getRecordDao(): RecordDao?

    companion object {
        private var sInstance: RecordDataBase? = null
        private const val NUMBER_THREADS = 5;
        public val databaseWriterExecutor: ExecutorService = Executors.newFixedThreadPool(
            NUMBER_THREADS
        )

        fun getInstance(context: Context): RecordDataBase? {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(
                    context.applicationContext,
                    RecordDataBase::class.java,
                    "record_db"
                ).allowMainThreadQueries().build()
            }
            return sInstance
        }
    }
}