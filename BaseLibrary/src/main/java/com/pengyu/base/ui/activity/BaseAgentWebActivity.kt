package com.pengyu.base.ui.activity

import android.content.Intent
import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.just.agentweb.*

/**
 * Created by cenxiaozhong on 2017/7/22.
 *
 *
 * source code  https://github.com/Justson/AgentWeb
 */

abstract class BaseAgentWebActivity : BaseActivity() {

    protected var agentWeb: AgentWeb? = null
    private var mErrorLayoutEntity: ErrorLayoutEntity? = null


    protected val errorLayoutEntity: ErrorLayoutEntity
        get() {
            if (this.mErrorLayoutEntity == null) {
                this.mErrorLayoutEntity = ErrorLayoutEntity()
            }
            return mErrorLayoutEntity as ErrorLayoutEntity
        }


    val agentWebSettings: IAgentWebSettings<*>?
        get() = AgentWebSettingsImpl.getInstance()

    protected abstract val getAgentWebParent: ViewGroup

    protected abstract val getWebChromeClient: WebChromeClient

    protected abstract val geturl: String

    protected val indicatorColor: Int
        @ColorInt
        get() = -1

    protected val indicatorHeight: Int
        get() = -1

    protected val webViewClient: WebViewClient?
        get() = null


    protected val webView: WebView?
        get() = null

    protected val webLayout: IWebLayout<*, *>?
        get() = null

    protected val permissionInterceptor: PermissionInterceptor?
        get() = null

    val agentWebUIController: AgentWebUIControllerImplBase?
        get() = null

    val openOtherAppWay: DefaultWebClient.OpenOtherPageWays?
        get() = null

    protected val middleWareWebChrome: MiddlewareWebChromeBase
        get() = object : MiddlewareWebChromeBase() {

        }

    protected val middleWareWebClient: MiddlewareWebClientBase
        get() = object : MiddlewareWebClientBase() {

        }


    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        buildAgentWeb()
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        buildAgentWeb()
    }

    protected fun buildAgentWeb() {
        val mErrorLayoutEntity = errorLayoutEntity
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(getAgentWebParent, ViewGroup.LayoutParams(-1, -1))
            .useDefaultIndicator(indicatorColor, indicatorHeight)
            .setWebChromeClient(getWebChromeClient)
            .setWebViewClient(webViewClient)
            .setWebView(webView)
            .setPermissionInterceptor(permissionInterceptor)
            .setWebLayout(webLayout)
            .setAgentWebUIController(agentWebUIController)
            .interceptUnkownUrl()
            .setOpenOtherPageWays(openOtherAppWay)
            .useMiddlewareWebChrome(middleWareWebChrome)
            .useMiddlewareWebClient(middleWareWebClient)
            .setAgentWebWebSettings(agentWebSettings)
            .setMainFrameErrorView(mErrorLayoutEntity.getLayoutRes(), mErrorLayoutEntity.getReloadId())
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .createAgentWeb()
            .ready()
            .go(geturl)


    }


    protected class ErrorLayoutEntity {
        private var layoutRes = com.just.agentweb.R.layout.agentweb_error_page
        private var reloadId: Int = 0

        fun getLayoutRes(): Int {
            return layoutRes
        }

        fun getReloadId(): Int {
            return reloadId
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        return if (agentWeb != null && agentWeb!!.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        if (agentWeb != null) {
            agentWeb!!.webLifeCycle.onPause()
        }
        super.onPause()

    }

    override fun onResume() {
        if (agentWeb != null) {
            agentWeb!!.webLifeCycle.onResume()
        }
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onDestroy() {
        if (agentWeb != null) {
            agentWeb!!.webLifeCycle.onDestroy()
        }
        super.onDestroy()
    }
}
