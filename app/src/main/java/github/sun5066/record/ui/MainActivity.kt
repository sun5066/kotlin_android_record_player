package github.sun5066.record.ui

import androidx.lifecycle.ViewModelProvider
import github.sun5066.record.R
import github.sun5066.record.databinding.ActivityMainBinding
import github.sun5066.record.ui.adapter.RecordViewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mRecordViewModel: RecordViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            RecordViewModel::class.java
        )
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_main

    override fun initDataBinding() {
        gBinding.lifecycleOwner = this
        gBinding.viewModel = mRecordViewModel
    }

    override fun initView() {
        TODO("Not yet implemented")
    }
}