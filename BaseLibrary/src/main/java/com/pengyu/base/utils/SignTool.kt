@file:Suppress("DEPRECATION")

package com.pengyu.base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import java.io.ByteArrayInputStream
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate


object SignTool {

    @SuppressLint("PackageManagerGetSignatures")
    fun getSingInfo(context: Context) {
        try {
            val packageInfo = context.packageManager.getPackageInfo("com.sina.weibo", PackageManager.GET_SIGNATURES)
            val signs = packageInfo.signatures
            val sign = signs[0]
            parseSignature(sign.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun parseSignature(signature: ByteArray) {
        try {
            val certFactory = CertificateFactory.getInstance("X.509")
            val cert = certFactory.generateCertificate(ByteArrayInputStream(signature)) as X509Certificate
            val pubKey = cert.getPublicKey().toString()
            val signNumber = cert.getSerialNumber().toString()
            System.out.println("signName:" + cert.getSigAlgName())
            println("pubKey:$pubKey")
            println("signNumber:$signNumber")
            System.out.println("subjectDN:" + cert.getSubjectDN().toString())
        } catch (e: CertificateException) {
            e.printStackTrace()
        }

    }


}