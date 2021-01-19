package github.sun5066.record.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var gBinding: DB

    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    abstract fun initDataBinding()

    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gBinding = DataBindingUtil.setContentView(this, getLayoutResourceId())

        this.initDataBinding()
        this.initView()
    }
}