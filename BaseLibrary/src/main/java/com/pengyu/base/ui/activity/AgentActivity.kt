package com.pengyu.base.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.pengyu.base.R
import com.pengyu.base.comment.BaseConstants
import kotlinx.android.synthetic.main.web_toolbar.*


class AgentActivity : BaseAgentWebActivity() {


    private var urls: String? = null
    private var mBackImageView: ImageView? = null
    private var mTitleTextView: TextView? = null

    companion object {
        fun activityStart(context: Context, url: String) {
            val intent = Intent(context, AgentActivity::class.java)
            intent.putExtra(BaseConstants.URLKEY, url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        urls = intent.extras?.getString(BaseConstants.URLKEY)
        super.onCreate(savedInstanceState)
    }


    private var mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {}

        override fun onReceivedTitle(view: WebView, title: String) {
            var titlex = title
            super.onReceivedTitle(view, title)
            if (mTitleTextView != null && !TextUtils.isEmpty(title)) {
                if (title.length > 10) {
                    titlex = title.substring(0, 10) + "..."
                }
            }
            mTitleTextView?.text = titlex
        }
    }


    override fun setLayoutId(): Int {
        return R.layout.activity_agent
    }

    override fun initViewAndDate() {
        mLoadingAndRetryManager.showContent()
        mTitleTextView = toolbar_title
        mBackImageView = iv_back
        mTitleTextView = toolbar_title
        mBackImageView?.visibility = View.VISIBLE
        mBackImageView?.setOnClickListener {
            if (!agentWeb!!.back()) {
                onBackPressed()
            }
        }
    }

    override val getAgentWebParent: ViewGroup
        get() = this.findViewById(R.id.root) as ViewGroup

    override val getWebChromeClient: WebChromeClient
        get() = mWebChromeClient

    override val geturl: String
        get() = urls ?: ""

}
