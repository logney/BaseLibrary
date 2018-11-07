package com.pengyu.base.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SnackbarUtils
import com.gyf.barlibrary.ImmersionBar
import com.gzsm.activityloading.LoadingAndRetryManager
import com.gzsm.activityloading.OnLoadingAndRetryListener
import com.pengyu.base.R
import com.pengyu.base.presenter.view.BaseView
import com.pengyu.base.widgets.MProDailog
import org.greenrobot.eventbus.EventBus

/**
 * Created by PengYu on 2018/1/23.
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {

    protected lateinit var mLoadingAndRetryManager: LoadingAndRetryManager
    private var mImmersionBar: ImmersionBar? = null
    private var isImmersionBarEnabled: Boolean = true
    lateinit var mProDialog: MProDailog
    lateinit var snackbar: SnackbarUtils

    private var bar_back: View? = null
    internal var loadView: View? = null

    var isScreenAdapt: Boolean = true
    var designWidthInPx: Int = 360
    var designHeightInPx: Int = 360

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isScreenAdapt) {
            if (ScreenUtils.isPortrait()) {
                ScreenUtils.adaptScreen4VerticalSlide(this, designWidthInPx)
            } else {
                ScreenUtils.adaptScreen4HorizontalSlide(this, designHeightInPx)
            }
        }
        setContentView(setLayoutId())
        mProDialog = MProDailog.create(this)
        if (isImmersionBarEnabled) {
            initImmersionBar()
        }
        bar_back = findViewById(R.id.bar_back)
        loadView = findViewById(R.id.loadView)

        mLoadingAndRetryManager = LoadingAndRetryManager.generate(loadView, object : OnLoadingAndRetryListener() {
            override fun setRetryEvent(retryView: View) {

            }
        })
        initViewAndDate()
    }

    private fun initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar!!.init()
        mImmersionBar!!.titleBar(R.id.toolbar)
                .fullScreen(false)
//                .statusBarDarkFont(true, 0.5f)//状态栏字体是深色，不写默认为亮色
//                .flymeOSStatusBarFontColor(R.color.black)  //修改flyme OS状态栏字体颜色
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)  //单独指定软键盘模式
                .init()
        findViewById<View>(R.id.bar_back).setOnClickListener {
            onBackPressed()
        }
    }

    protected fun setTitleVisibility(visibility: Boolean) {
        if (visibility) {
            findViewById<View>(R.id.bar_title).visibility = View.VISIBLE
        } else {
            findViewById<View>(R.id.bar_title).visibility = View.GONE
        }

    }

    protected fun setBankVisibility(visibility: Boolean) {
        if (visibility) {
            findViewById<View>(R.id.back_root).visibility = View.VISIBLE
        } else {
            findViewById<View>(R.id.back_root).visibility = View.GONE
        }
    }

    protected fun setTitle(title: String) {
        findViewById<TextView>(R.id.bar_title).text = title
    }

    protected fun setACTitle(title: String) {
        findViewById<TextView>(R.id.bar_title).text = title
    }

    protected fun setRightTitle(title: String) {
        findViewById<TextView>(R.id.right_title).text = title
    }

    protected fun setRightClick(click: View.OnClickListener) {
        findViewById<View>(R.id.right_title).setOnClickListener(click)
    }

    protected fun setRightLongClick(click: View.OnLongClickListener) {
        findViewById<View>(R.id.right_title).setOnLongClickListener(click)
    }

    protected fun setRightShareClick(click: View.OnClickListener) {
        val share = findViewById<View>(R.id.share)
        share.visibility = View.VISIBLE
        share.setOnClickListener(click)
    }

    protected fun setBarAdd(click: View.OnClickListener) {
        val bar_add = findViewById<View>(R.id.bar_add)
        bar_add.visibility = View.VISIBLE
        bar_add.setOnClickListener(click)
    }

    protected abstract fun setLayoutId(): Int
    protected abstract fun initViewAndDate()

    override fun onDestroy() {
        super.onDestroy()
        if (mImmersionBar != null) {
            mImmersionBar!!.destroy()  //在BaseActivity里销毁
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        ScreenUtils.cancelAdaptScreen()
    }

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

    override fun showLoddingA() {
        mLoadingAndRetryManager.showLoading()
    }

    override fun showEmpty() {
        mLoadingAndRetryManager.showEmpty()
    }

    override fun showRetry() {
        mLoadingAndRetryManager.showRetry()
    }

    override fun hideLoddingA() {
        mLoadingAndRetryManager.showContent()
    }

    override fun showEmpty(list: List<*>?) {
        hideLodding()
        if (list == null || list.isEmpty()) {
            showEmpty()
        } else {
            hideLoddingA()
        }
    }

    override fun showSnackBar(msg: String) {
        if (bar_back == null) {
            return
        }
        mProDialog.dismiss()
        snackbar = SnackbarUtils.with(bar_back!!)
        snackbar.setDuration(SnackbarUtils.LENGTH_LONG)
        snackbar.setMessageColor(Color.WHITE)
        snackbar.setAction("O K") {
            SnackbarUtils.dismiss()
        }
        snackbar.setMessage(msg).show()
    }

    override fun showSnackBarAndShowDailog(msg: String) {
        if (bar_back == null) {
            return
        }
        snackbar = SnackbarUtils.with(bar_back!!)
        snackbar.setDuration(SnackbarUtils.LENGTH_LONG)
        snackbar.setAction("O K") {
            SnackbarUtils.dismiss()
        }
        snackbar.setMessage(msg).show()
    }

    override fun showError(msg: String) {
        if (bar_back == null) {
            return
        }
        mProDialog.dismiss()
        snackbar = SnackbarUtils.with(bar_back!!)
        snackbar.setDuration(3000)
        snackbar.setAction("O K") {
            SnackbarUtils.dismiss()
        }
        snackbar.setMessage(msg).showWarning()
    }

}