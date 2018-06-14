package com.libra.guli.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.libra.core.AppContext
import com.libra.core.viewmodel.BaseViewModel

/**
 * @author Libra
 * @since 2018/6/13
 */
class MainViewModel : BaseViewModel {
    constructor() : super(AppContext.getApplication())

    constructor(application: Application) : super(application)

    var edittext = MutableLiveData<String>()
}