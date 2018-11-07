package com.pengyu.base.presenter.view

/**
 * Created by PengYu on 2018/1/17.
 */
interface BaseView {
    fun showLodding(msg: String)
    fun showLodding()
    fun hideLodding()
    fun showLoddingA()
    fun hideLoddingA()
    fun showError(msg: String)
    fun showSnackBar(msg: String)
    fun showSnackBarAndShowDailog(msg: String)
    fun showEmpty()
    fun showRetry()
    fun showEmpty(list:List<*>?)
}