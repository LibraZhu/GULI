package com.libra.core.view

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.libra.core.viewmodel.BaseViewModel
import com.libra.core.BR

/**
 * Created by libra on 2017/6/15
 */

abstract class BaseBindingViewHolder(
        private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * 绑定数据
     *
     * @param obj obj
     */
    open fun bindViewHolder(obj: Any?, position: Int) {
        binding.setVariable(BR.vm, obj)
        binding.executePendingBindings()
    }
}
