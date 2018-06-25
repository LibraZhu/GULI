package com.libra.guli.module.countdown.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.libra.core.AppContext
import com.libra.core.viewmodel.BaseViewModel
import com.libra.guli.dao.DaoManager
import com.libra.guli.dao.model.Countdown
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * @author Libra
 * @since 2018/6/21
 */
class CountdownViewModel : BaseViewModel {
    constructor() : super(AppContext.getApplication())

    constructor(application: Application) : super(application)

    var itemViewModelList = MutableLiveData<ArrayList<BaseViewModel>>()
    var itemLayoutId = MutableLiveData<Int>()
    var itemClick: ((countdown: Countdown) -> Unit)? = null

    fun getCountdownList() {
        DaoManager.getInstance(getApplication()).getRecentCountdownListF().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    val itemList = ArrayList<BaseViewModel>()
                    for (countdown in list) {
                        val item = CountdownItemViewModel()
                        item.content.value = countdown.des
                        item.title.value = countdown.content
                        item.itemClick = View.OnClickListener { itemClick?.invoke(countdown) }
                        val day = (countdown.timeL - System.currentTimeMillis()) / 1000 / 60 / 60 / 24
                        item.day.value = String.format("%02d", day+1)
                        itemList.add(item)
                    }
                    itemViewModelList.value = itemList
                }
    }
}