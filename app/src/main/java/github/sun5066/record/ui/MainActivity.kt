package github.sun5066.record.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import github.sun5066.record.R
import github.sun5066.record.databinding.ActivityMainBinding
import github.sun5066.record.model.RecordData
import github.sun5066.record.ui.adapter.RecordAdapter
import github.sun5066.record.ui.adapter.RecordAdapterViewModel
import github.sun5066.record.ui.record.RecordActivity
import github.sun5066.record.util.IntentKey

class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {

    private lateinit var mRecordRecyclerAdapter: RecordAdapter
    private val mRecordAdapterViewModel: RecordAdapterViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            RecordAdapterViewModel::class.java
        )
    }

    public interface RecordNavigator {
        fun onClickListener(_view: View)
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_main

    override fun initDataBinding() {
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mRecordAdapterViewModel
    }

    override fun initView() {
        mBinding.btnActivityRecord.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_activity_record -> {
                val intent = Intent(this, RecordActivity::class.java)
                startActivityForResult(intent, IntentKey.REQUEST_KEY)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let {
            val recordData: RecordData = it?.getSerializableExtra("record") as RecordData

            if (resultCode == Activity.RESULT_OK) {
                when (requestCode) {
                    IntentKey.REQUEST_KEY -> {
                        mRecordAdapterViewModel.save(recordData)
                    }
                }
            }
        }
    }
}