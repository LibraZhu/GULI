package com.libra.core.view

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.libra.core.BR
import com.libra.core.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * @author Libra
 * @since 2018/6/13
 */
abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    protected var viewModel: VM? = null
    protected var binding: ViewDataBinding? = null
    private val tClass: Class<VM>
        get() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initIntentData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutID(), container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        initCustomView()
    }

    /**
     * 初始化intent
     */
    open fun initIntentData() {

    }

    /**
     * 布局文件
     */
    abstract fun getLayoutID(): Int


    /**
     * 初始化ViewModel
     */
    open fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(tClass)
        binding?.setVariable(BR.vm, viewModel)
        binding?.setLifecycleOwner(this)
    }

    /**
     * 初始化特别试图
     */
    open fun initCustomView() {

    }
}