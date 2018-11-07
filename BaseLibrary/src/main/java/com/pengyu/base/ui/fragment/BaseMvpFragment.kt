package com.pengyu.base.ui.fragment

import android.os.Bundle
import com.pengyu.base.presenter.BasePresenter

/**
 * Created by PengYu on 2018/1/22.
 */
abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment() {
    public lateinit var mPresenter: T

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}