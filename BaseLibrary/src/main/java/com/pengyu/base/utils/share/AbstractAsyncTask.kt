package com.pengyu.base.utils.share

import android.os.AsyncTask

/**
 * Created by zhanglifeng on 16/6/17
 */
abstract class AbstractAsyncTask<T> : AsyncTask<Void, Int, T>() {
    var exception: Exception? = null
        private set
    var data: T? = null
        private set

    override fun doInBackground(vararg voids: Void): T? {
        try {
            data = doLoadData()
            exception = null
        } catch (e: Exception) {
            data = null
            exception = e
        }

        return data
    }

    @Throws(Exception::class)
    protected abstract fun doLoadData(): T

    override fun onPostExecute(t: T) {
        try {
            if (exception == null) {
                onSuccess(t)
            } else {
                onException(exception!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            onFinally()
        }

    }

    open fun onSuccess(t: T) {}

    open fun onFinally() {}

    open fun onException(exception: Exception) {

    }
}
