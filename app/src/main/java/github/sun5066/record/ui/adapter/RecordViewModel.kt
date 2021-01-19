package github.sun5066.record.ui.adapter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import github.sun5066.record.model.RecordData
import github.sun5066.record.repo.RecordRepository

class RecordViewModel(app: Application) : AndroidViewModel(app) {

    private val mRecordRepository = RecordRepository(app)

    fun selectAll(): LiveData<MutableList<RecordData>> = mRecordRepository.selectAll()

    fun findById(_id: Long): RecordData = mRecordRepository.findById(_id = _id)

    fun save(_recordData: RecordData) = mRecordRepository.save(_recordData = _recordData)

    fun delete(_id: Long) = mRecordRepository.delete(_id = _id)
}