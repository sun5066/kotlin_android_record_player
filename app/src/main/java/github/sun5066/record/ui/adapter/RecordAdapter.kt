package github.sun5066.record.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import github.sun5066.record.databinding.RecordItemBinding
import github.sun5066.record.model.RecordData
import github.sun5066.record.ui.MainActivity


class RecordAdapter(private var mRecordList: MutableList<RecordData>) :
    RecyclerView.Adapter<RecordAdapter.RecordHolder?>(), MainActivity.RecordNavigator {
    private val TAG = this::class.java.simpleName

    fun setList(_recordList: MutableList<RecordData>) {
        mRecordList = _recordList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recordClientItemBinding = RecordItemBinding.inflate(layoutInflater, parent, false)
        return RecordHolder(recordClientItemBinding)
    }

    override fun onBindViewHolder(holder: RecordHolder, position: Int) {
        val recordItem = mRecordList[position]
        holder.gRecordItemBinding.recordItem = recordItem
        holder.gRecordItemBinding.viewModel?.let {
            Log.d(TAG, it.toString())
            it.setRecordNavigator(this as MainActivity.RecordNavigator)
        }
        holder.gRecordItemBinding.executePendingBindings()
    }

    override fun getItemCount(): Int = mRecordList.size

    override fun onClickListener(_view: View) {
        Log.d("할말 없다고라니", _view?.toString())
    }

    inner class RecordHolder(_recordItemBinding: RecordItemBinding) :
        RecyclerView.ViewHolder(_recordItemBinding.root) {
        val gRecordItemBinding = _recordItemBinding
    }
}