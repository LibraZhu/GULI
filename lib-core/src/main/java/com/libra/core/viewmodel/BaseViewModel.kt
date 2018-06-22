package com.libra.core.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

import com.libra.core.AppContext

/**
 * @author Libra
 * @since 2018/6/13
 */
abstract class BaseViewModel : AndroidViewModel {
    constructor() : super(AppContext.getApplication())

    constructor(application: Application) : super(application)

}
