package com.libra.guli.module.countdown.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.libra.core.AppContext
import com.libra.core.viewmodel.BaseViewModel
import com.libra.guli.dao.model.Schedule
import com.libra.guli.module.calendar.utils.DateUtils
import android.media.RingtoneManager.getRingtone
import android.media.RingtoneManager
import android.media.Ringtone
import java.util.ArrayList


/**
 * @author Libra
 * @since 2018/6/21
 */
class CountdownItemViewModel : BaseViewModel {
    constructor() : super(AppContext.getApplication())

    constructor(application: Application) : super(application)

    var title = MutableLiveData<String>()
    var content = MutableLiveData<String>()
    var day = MutableLiveData<String>()
    var itemClick: View.OnClickListener? = null
}