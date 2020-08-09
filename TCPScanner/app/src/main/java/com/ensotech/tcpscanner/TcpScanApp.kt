package com.ensotech.tcpscanner

import android.app.Application
import timber.log.Timber

import timber.log.Timber.DebugTree


class TcpScanApp() : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}