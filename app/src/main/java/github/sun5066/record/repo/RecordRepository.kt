package github.sun5066.record.repo

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import github.sun5066.record.model.RecordData

class RecordRepository(app: Application) {
    private lateinit var recordDao: RecordDao

    init {
        var db: RecordDataBase? = RecordDataBase.getInstance(app)
        if (db != null) {
            recordDao = db.getRecordDao()!!
        }
    }

    fun selectAll(): LiveData<MutableList<RecordData>> = recordDao.selectAll()

    fun findById(_id: Long): RecordData = recordDao.findById(id = _id)

    fun save(_recordData: RecordData) {
        RecordDataBase.databaseWriterExecutor.execute(Runnable { recordDao.save(recordData = _recordData) })
    }

    fun delete(_id: Long) = recordDao.delete(id = _id)
}