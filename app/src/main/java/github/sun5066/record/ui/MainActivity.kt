package github.sun5066.record.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import github.sun5066.record.R
import github.sun5066.record.databinding.ActivityMainBinding
import github.sun5066.record.model.RecordData
import github.sun5066.record.ui.adapter.RecordViewModel
import github.sun5066.record.util.IntentKey

class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {

    private val mRecordViewModel: RecordViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            RecordViewModel::class.java
        )
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_main

    override fun initDataBinding() {
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mRecordViewModel
    }

    override fun initView() {
        mBinding.btnActivityRecord.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, RecordActivity::class.java)
        startActivityForResult(intent, IntentKey.REQUEST_KEY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val recordData: RecordData = data?.getSerializableExtra("record") as RecordData

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IntentKey.REQUEST_KEY -> {
                    mRecordViewModel.save(recordData)
                    Log.d("aaaass222", mRecordViewModel.selectAll().value.toString())
                }
            }
        }
    }
}