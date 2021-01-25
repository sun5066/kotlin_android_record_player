package github.sun5066.record.db

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import github.sun5066.record.model.RecordData

class RecordRepository(app: Application) {
    private lateinit var recordDao: RecordDao

    init {
        var db: RecordDataBase? = RecordDataBase.getInstance(app)
        db?.let { recordDao = it.getRecordDao()!! }
    }

    fun selectAll(): LiveData<MutableList<RecordData>> = recordDao.selectAll()

    fun findById(id: Long): RecordData = recordDao.findById(id)

    fun save(recordData: RecordData) =
        RecordDataBase.databaseWriterExecutor.execute { recordDao.save(recordData) }

    fun delete(id: Long) = recordDao.delete(id)
}