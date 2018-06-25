package com.libra.guli.dao

import android.arch.persistence.room.Room
import android.content.Context
import com.libra.guli.dao.model.Countdown

import com.libra.guli.dao.model.Schedule

import java.util.ArrayList

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.flowable.FlowableCreate
import io.reactivex.schedulers.Schedulers

/**
 * @author Libra
 * @since 2018/6/25
 */
class DaoManager private constructor(context: Context) {
    private val db: GLDatabase

    init {
        db = Room.databaseBuilder(
                context.applicationContext,
                GLDatabase::class.java,
                DB_NAME).build()
    }

    fun saveSchedule(schedule: Schedule): Flowable<Boolean> {
        return Flowable.create(FlowableOnSubscribe<Boolean> { emitter ->
            db.scheduleDao().save(schedule)
            emitter.onNext(true)
            emitter.onComplete()
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun getScheduleList(): Flowable<List<Schedule>> {
        return db.scheduleDao().load().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun saveCountdown(countdown: Countdown): Flowable<Boolean> {
        return Flowable.create(FlowableOnSubscribe<Boolean> { emitter ->
            db.countdownDao().save(countdown)
            emitter.onNext(true)
            emitter.onComplete()
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun saveCountdownListSimple(countdown: ArrayList<Countdown>) {
        db.countdownDao().save(countdown)
    }

    fun saveCountdownList(countdown: ArrayList<Countdown>): Flowable<Boolean> {
        return Flowable.create(FlowableOnSubscribe<Boolean> { emitter ->
            db.countdownDao().save(countdown)
            emitter.onNext(true)
            emitter.onComplete()
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun getCountdownList(): Flowable<List<Countdown>> {
        return db.countdownDao().load()
    }

    fun getShowCountdownList(): Flowable<List<Countdown>> {
        return db.countdownDao().loadShow()
    }

    fun getRecentCountdownList(): List<Countdown> {
        return db.countdownDao().loadRecent(System.currentTimeMillis())
    }

    fun getRecentCountdownListF(): Flowable<List<Countdown>> {
        return Flowable.create(FlowableOnSubscribe<List<Countdown>> { emitter ->
            val list = db.countdownDao().loadRecent(System.currentTimeMillis())
            emitter.onNext(list)
            emitter.onComplete()
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }


    companion object {
        private val DB_NAME = "gl.db"
        private var instance: DaoManager? = null

        @Synchronized
        fun getInstance(context: Context): DaoManager {
            if (instance == null) {
                instance = DaoManager(context)
            }
            return instance as DaoManager
        }
    }

}
