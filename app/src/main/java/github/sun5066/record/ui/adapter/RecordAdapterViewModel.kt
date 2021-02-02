package github.sun5066.record.ui.adapter

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import github.sun5066.record.R
import github.sun5066.record.db.RecordRepository
import github.sun5066.record.model.RecordData
import github.sun5066.record.ui.MainActivity

class RecordAdapterViewModel(app: Application) : AndroidViewModel(app) {
    private val mRecordRepository = RecordRepository(app)
    private lateinit var mListener: MainActivity.RecordNavigator

    val onClickEvent = View.OnClickListener { v ->
        Log.d("onClickEvent", "onClickEvent")
        when (v.id) {
            R.id.layout_record_items -> {
                Log.d("뭐여 쓰발", "뭔데이건")
                mListener.onClickListener(v)
            }
        }
    }

    val mLiveData: LiveData<MutableList<RecordData>> = mRecordRepository.selectAll()

    fun findById(id: Long): RecordData = mRecordRepository.findById(id)

    fun save(recordData: RecordData) = mRecordRepository.save(recordData)

    fun delete(id: Long) = mRecordRepository.delete(id)

    fun setRecordNavigator(_listener: MainActivity.RecordNavigator) {
        Log.d("setRecordNavigator", "setRecordNavigator!!")
        mListener = _listener
    }
}