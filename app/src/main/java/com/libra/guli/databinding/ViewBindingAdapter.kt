package com.libra.guli.databinding

import android.arch.lifecycle.MutableLiveData
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.libra.core.view.BaseAdapter
import com.libra.core.view.BaseBindingViewHolder
import com.libra.core.viewmodel.BaseViewModel
import com.libra.guli.module.home.viewmodel.MainItemViewModel

/**
 * Created by libra on 2017/8/4.
 */
@BindingAdapter("isSelected")
fun textView(textView: TextView, uri: Any?, isSelected: Boolean) {
    textView.isSelected = isSelected
}

@BindingAdapter("uri", "placeholder")
fun imageUri(imageView: ImageView, uri: Any?, placeholder: Drawable?) {
    if (imageView.layoutParams.width > 0) {
        Glide.with(imageView.context).load(uri).apply(
                RequestOptions().override(imageView.layoutParams.width,
                        imageView.layoutParams.height).placeholder(
                        placeholder)).into(imageView)
    } else {
        Glide.with(imageView.context).load(uri).apply(
                RequestOptions().placeholder(placeholder)).into(imageView)
    }
}

@BindingAdapter("itemLayoutId", "items")
fun recyclerView(recyclerView: RecyclerView, itemLayoutId: Int, items: ArrayList<BaseViewModel>) {
    val adapter = object : BaseAdapter() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
            return object : BaseBindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(recyclerView.context),
                    itemLayoutId, parent, false)) {
            }
        }

    }
    recyclerView.adapter = adapter
    adapter.setData(items)
}