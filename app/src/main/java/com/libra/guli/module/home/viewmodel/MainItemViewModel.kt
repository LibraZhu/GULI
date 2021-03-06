package com.libra.guli.module.home.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.graphics.drawable.Drawable
import android.view.View
import com.libra.core.AppContext
import com.libra.core.viewmodel.BaseViewModel
import com.libra.guli.dao.model.Schedule

/**
 * @author Libra
 * @since 2018/6/13
 */
class MainItemViewModel : BaseViewModel {
    constructor() : super(AppContext.getApplication())

    constructor(application: Application) : super(application)

    var schedule: Schedule? = null
    var content = MutableLiveData<String>()
    var itemClick: View.OnClickListener? = null
}