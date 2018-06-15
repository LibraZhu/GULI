package com.libra.guli.module.home.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.graphics.drawable.Drawable
import android.text.TextUtils
import com.libra.core.AppContext
import com.libra.core.viewmodel.BaseViewModel

/**
 * @author Libra
 * @since 2018/6/13
 */
class MainViewModel : BaseViewModel {
    constructor() : super(AppContext.getApplication())

    constructor(application: Application) : super(application)

    var countdown1 = MutableLiveData<String>()
    var countdown2 = MutableLiveData<String>()
    var countdown3 = MutableLiveData<String>()
    var imageBackground = MutableLiveData<Int>()

    var itemLayoutId = MutableLiveData<Int>()
    var itemViewModelList = MutableLiveData<ArrayList<BaseViewModel>>()
}