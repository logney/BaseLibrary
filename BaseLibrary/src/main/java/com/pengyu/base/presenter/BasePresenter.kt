package com.pengyu.base.presenter

import com.pengyu.base.presenter.view.BaseView


/**
 * Created by PengYu on 2018/1/17.
 */

open class BasePresenter<T : BaseView> {
    lateinit var mView: T
}
