package github.sun5066.record.repo

import android.app.Application
import androidx.lifecycle.LiveData
import github.sun5066.record.model.RecordData

class RecordRepository(app: Application) {
    private lateinit var recordDao: RecordDao

    init {
        var db: RecordDataBase? = RecordDataBase.getInstance(app)
        db?.let { recordDao = db.getRecordDao()!! }
    }

    fun selectAll(): LiveData<MutableList<RecordData>> = recordDao.selectAll()

    fun findById(_id: Long): RecordData = recordDao.findById(_id = _id)

    fun save(_recordData: RecordData) {
        RecordDataBase.databaseWriterExecutor.execute(Runnable { recordDao.save(_recordData = _recordData) })
    }

    fun delete(_id: Long) = recordDao.delete(_id = _id)
}