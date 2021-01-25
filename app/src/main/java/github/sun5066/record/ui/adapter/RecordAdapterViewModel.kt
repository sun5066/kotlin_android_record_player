package github.sun5066.record.ui.adapter

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import github.sun5066.record.R
import github.sun5066.record.model.RecordData
import github.sun5066.record.db.RecordRepository

class RecordAdapterViewModel(app: Application) : AndroidViewModel(app) {
    private val mRecordRepository = RecordRepository(app)

    val onClickEvent = View.OnClickListener { v ->
        when (v.id) {
            R.id.layout_record_items -> {

            }
        }
    }

    val mLiveData: LiveData<MutableList<RecordData>> = mRecordRepository.selectAll()

    fun findById(id: Long): RecordData = mRecordRepository.findById(id)

    fun save(recordData: RecordData) = mRecordRepository.save(recordData)

    fun delete(id: Long) = mRecordRepository.delete(id)
}