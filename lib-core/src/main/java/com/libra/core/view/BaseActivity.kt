package com.libra.core.view

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.transition.Explode
import android.widget.TextView
import com.libra.core.BR
import com.libra.core.R
import com.libra.core.utils.StatusBarUtil

import com.libra.core.viewmodel.BaseViewModel

import java.lang.reflect.ParameterizedType

/**
 * @author Libra
 * @since 2018/6/13
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    protected var viewModel: VM? = null
    protected var toolbar: Toolbar? = null
    protected var toolbarTitle: TextView? = null
    protected var binding: ViewDataBinding? = null

    private val tClass: Class<VM>
        get() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.statusBarLightMode(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = Explode()
            window.exitTransition = Explode()
        }
        viewModel = ViewModelProviders.of(this).get(tClass)
        binding = DataBindingUtil.setContentView(this, getLayoutID())
        binding?.setVariable(BR.vm, viewModel)
        binding?.setLifecycleOwner(this)
        initIntentData()
        initToolBar()
        initViewModel()
        initCustomView()
    }

    /**
     * 布局文件
     */
    abstract fun getLayoutID(): Int

    /**
     * 初始化intent
     */
    open fun initIntentData() {

    }

    /**
     * 初始化toolbar
     */
    open fun initToolBar() {
        toolbar = findViewById(R.id.titlebar)
        if (toolbar != null) {
            this.setSupportActionBar(toolbar)
            toolbar!!.setNavigationOnClickListener({ onBackPressed() })
            toolbarTitle = toolbar!!.findViewById(R.id.titleName)
            if (toolbarTitle != null) {
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }
            showBackButton(true)
        }
    }

    /**
     * 是否显示back图标
     */
    fun showBackButton(visible: Boolean) {
        supportActionBar?.setDisplayShowHomeEnabled(visible)
        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    override fun onTitleChanged(title: CharSequence?, color: Int) {
        super.onTitleChanged(title, color)
        toolbarTitle?.text = title
    }

    /**
     * 初始化ViewModel
     */
    abstract fun initViewModel()

    /**
     * 初始化特别试图
     */
    open fun initCustomView() {

    }
}
