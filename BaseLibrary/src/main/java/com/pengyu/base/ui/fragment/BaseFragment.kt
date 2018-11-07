package com.pengyu.base.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.SnackbarUtils
import com.pengyu.base.presenter.view.BaseView
import com.pengyu.base.widgets.MProDailog
import org.greenrobot.eventbus.EventBus

/**
 * Created by PengYu on 2018/1/22.
 */
abstract class BaseFragment : Fragment(), BaseView {
    private var isFirstVisible = true
    private var isFirstInvisible = true
    private var isPrepared: Boolean = false
    protected var isImmersionBarEnabled: Boolean = true
    private lateinit var mProDialog: MProDailog
    lateinit var snackbar: SnackbarUtils

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (getContentViewLayoutID(inflater) != 0) {
            inflater.inflate(getContentViewLayoutID(inflater), null)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProDialog = MProDailog.create(context!!)
        initViewsAndEvents(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPrepare()
    }

    @Synchronized
    private fun initPrepare() {
        if (isPrepared) {
            onFirstUserVisible()
        } else {
            isPrepared = true
        }
    }

    var isInVisible = false

    override fun onPause() {
        super.onPause()
        isInVisible = true
    }

    override fun onResume() {
        super.onResume()
        onUserVisible()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false
                initPrepare()
            } else {
                onUserVisible()
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false
                onFirstUserInvisible()
            } else {
                onUserInvisible()
            }
        }
    }

    protected abstract fun getContentViewLayoutID(inflater: LayoutInflater): Int

    protected abstract fun initViewsAndEvents(view: View, savedInstanceState: Bundle?)

    protected abstract fun onFirstUserVisible()

    protected abstract fun onUserVisible()

    protected abstract fun onFirstUserInvisible()

    protected abstract fun onUserInvisible()

    override fun showLodding() {
        if (mProDialog.isShowing) {
            return
        }
        mProDialog.showProDialog("")
    }

    override fun showLodding(msg: String) {
        if (mProDialog.isShowing) {
            return
        }
        mProDialog.showProDialog(msg)
    }

    override fun hideLodding() {
        mProDialog.dismiss()
    }


    override fun showSnackBar(msg: String) {
        mProDialog.dismiss()
    }

    override fun showError(msg: String) {
        mProDialog.dismiss()
    }

    override fun showSnackBarAndShowDailog(msg: String) {
        mProDialog.dismiss()
    }

    override fun showLoddingA() {

    }

    override fun hideLoddingA() {

    }

    override fun showEmpty() {

    }

    override fun showRetry() {

    }

    override fun showEmpty(list: List<*>?) {

    }
}