package com.libra.core.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.libra.core.viewmodel.BaseViewModel

/**
 * @author Libra
 * @since 2018/6/13
 */
abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initIntentData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(getLayoutID(), container, false)
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
    abstract fun initViewModel()

    /**
     * 初始化特别试图
     */
    open fun initCustomView() {

    }
}