package com.pengyu.base.ui.activity

import com.pengyu.base.presenter.BasePresenter

/**
 * Created by PengYu on 2018/1/23.
 */
abstract class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity() {

    lateinit var mPresenter: T

}