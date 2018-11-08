package com.pengyu.base.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.pengyu.base.R
import com.pengyu.base.toast.DimensUtils
import com.pengyu.base.toast.IToastStyle
import com.pengyu.base.toast.XToast
import com.pengyu.base.toast.style.ToastBlackStyle

@Suppress("DEPRECATION")
class ToastUtils {

    /**
     * 取消吐司的显示
     */
    fun cancel() {
        checkToastState()
        toast!!.cancel()
    }

    companion object {

        private var sDefaultStyle: IToastStyle? = null

        /**
         * 获取当前Toast对象
         */
        var toast: Toast? = null
            private set

        /**
         * 初始化ToastUtils，建议在Application中初始化
         *
         * @param context 应用的上下文
         */
        @SuppressLint("ObsoleteSdkInt")
        fun init(context: Context) {
            var context1 = context
            // 检查默认样式是否为空，如果是就创建一个默认样式
            if (sDefaultStyle == null) {
                sDefaultStyle = ToastBlackStyle()
            }

            // 如果这个上下文不是全局的上下文，就自动换成全局的上下文
            if (context !== context.applicationContext) {
                context1 = context.applicationContext
            }

            val drawable = GradientDrawable()
            drawable.setColor(sDefaultStyle!!.getBackgroundColor()) // 设置背景色
            drawable.cornerRadius =
                    DimensUtils.dp2px(context1, sDefaultStyle!!.getCornerRadius().toFloat()).toFloat() // 设置圆角

            val textView = TextView(context1)
            textView.setTextColor(sDefaultStyle!!.getTextColor())
            textView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                DimensUtils.sp2px(context1, sDefaultStyle!!.getTextSize()).toFloat()
            )
            textView.setPadding(
                DimensUtils.dp2px(context1, sDefaultStyle!!.getPaddingLeft().toFloat()),
                DimensUtils.dp2px(context1, sDefaultStyle!!.getPaddingTop().toFloat()),
                DimensUtils.dp2px(context1, sDefaultStyle!!.getPaddingRight().toFloat()),
                DimensUtils.dp2px(context1, sDefaultStyle!!.getPaddingBottom().toFloat())
            )
            textView.layoutParams =
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            // setBackground API版本兼容
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                textView.background = drawable
            } else {
                textView.setBackgroundDrawable(drawable)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textView.z = sDefaultStyle!!.getZ().toFloat() // 设置 Z 轴阴影
            }

            if (sDefaultStyle!!.getMaxLines() > 0) {
                textView.maxLines = sDefaultStyle!!.getMaxLines() // 设置最大显示行数
            }

            toast = XToast(context1)
            toast!!.setGravity(sDefaultStyle!!.getGravity(), sDefaultStyle!!.getXOffset(), sDefaultStyle!!.getYOffset())
            //        sToast.setView(textView);
            setView(context1, R.layout.toast_custom_view)
        }

        /**
         * 显示一个对象的吐司
         *
         * @param object 对象
         */
        fun show(`object`: Any?) {
            show(`object`?.toString() ?: "null")
        }

        /**
         * 显示一个吐司
         *
         * @param id 如果传入的是正确的string id就显示对应字符串
         * 如果不是则显示一个整数的string
         */
        fun show(id: Int) {

            checkToastState()

            try {
                // 如果这是一个资源id
                show(toast!!.view.context.resources.getText(id))
            } catch (ignored: Resources.NotFoundException) {
                // 如果这是一个int类型
                show(id.toString())
            }

        }

        /**
         * 显示一个吐司
         *
         * @param text 需要显示的文本
         */
        fun show(text: CharSequence?) {

            checkToastState()

            if (text == null || text == "") return

            toast!!.duration = Toast.LENGTH_LONG
            toast!!.setText(text)
            toast!!.show()
        }

        /**
         * 给当前Toast设置新的布局，具体实现可看[XToast.setView]
         */
        fun setView(context: Context, layoutId: Int) {
            var context1 = context
            if (context !== context.applicationContext) {
                context1 = context.applicationContext
            }
            setView(View.inflate(context1, layoutId, null))
        }

        fun setView(view: View?) {

            checkToastState()

            if (view == null) {
                throw IllegalArgumentException("Views cannot be empty")
            }
            //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //            view.findViewById(R.id.toast_main_text_view_id).setZ(DimensUtils.dp2px(view.getContext(), 8)); // 设置 Z 轴阴影
            //        }
            toast!!.view = view
        }

        /**
         * 统一全局的Toast样式，建议在[android.app.Application.onCreate]中初始化
         *
         * @param style 样式实现类，框架已经实现三种不同的样式
         * 黑色样式：[ToastBlackStyle]
         * 白色样式：[com.pengyu.base.toast.style.ToastWhiteStyle]
         * 仿QQ样式：[com.pengyu.base.toast.style.ToastQQStyle]
         */
        fun initStyle(style: IToastStyle) {
            ToastUtils.sDefaultStyle = style
            //如果吐司已经创建，就重新初始化吐司
            if (toast != null) {
                //取消原有吐司的显示
                toast!!.cancel()
                //重新初始化吐司类
                init(toast!!.view.context.applicationContext)
            }
        }

        /**
         * 检查吐司状态，如果未初始化请先调用[ToastUtils.init]
         */
        private fun checkToastState() {
            //吐司工具类还没有被初始化，必须要先调用init方法进行初始化
            if (toast == null) {
                throw IllegalStateException("ToastUtils has not been initialized")
            } else {
                //            sToast.cancel();
            }
        }
    }
}
