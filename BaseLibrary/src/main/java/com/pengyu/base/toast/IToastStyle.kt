package com.pengyu.base.toast

interface IToastStyle{
    abstract fun getGravity(): Int  // 吐司的重心
    abstract fun getXOffset(): Int  // X轴偏移
    abstract fun getYOffset(): Int  // Y轴偏移
    abstract fun getZ(): Int  // 吐司Z轴坐标

    abstract fun getCornerRadius(): Int  // 圆角大小
    abstract fun getBackgroundColor(): Int  // 背景颜色

    abstract fun getTextColor(): Int  // 文本颜色
    abstract fun getTextSize(): Float  // 文本大小
    abstract fun getMaxLines(): Int  // 最大行数

    abstract fun getPaddingLeft(): Int  // 左边内边距
    abstract fun getPaddingTop(): Int  // 顶部内边距
    abstract fun getPaddingRight(): Int  // 右边内边距
    abstract fun getPaddingBottom(): Int  // 底部内边距
}
