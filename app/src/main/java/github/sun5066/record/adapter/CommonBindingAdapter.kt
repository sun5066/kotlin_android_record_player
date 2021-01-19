package github.sun5066.record.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import github.sun5066.record.model.RecordData
import github.sun5066.record.ui.adapter.RecordAdapter

object CommonBindingAdapter {

    @BindingAdapter("setItems")
    @JvmStatic
    fun setItems(_recyclerView: RecyclerView, _items: MutableList<RecordData>?) {
        if (_recyclerView.adapter == null) {
            _recyclerView.adapter = RecordAdapter(mutableListOf())
        }

        val recordRecyclerAdapter = _recyclerView.adapter as RecordAdapter
        _items?.let { recordRecyclerAdapter.setList(it) }
        _recyclerView.scrollToPosition(recordRecyclerAdapter.itemCount - 1)
        recordRecyclerAdapter.notifyDataSetChanged()
    }
}