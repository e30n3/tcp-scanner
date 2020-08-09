package com.ensotech.tcpscanner

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.TimingLogger
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    @SuppressLint("TimberArgCount")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ports = (1..1000).toList()
        //val portWithStat = mutableListOf<Pair<Int, Boolean>>()
        val executor = Executors.newFixedThreadPool(16)

        for (port in ports) {
            val worker = PortScanner(port, tvPorts)
            executor.execute(worker)
        }
        Timber.tag("WIFI").d("is ${isWifiConnected()}")

    }

    private fun isWifiConnected(): Boolean {
        val connManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (mWifi != null) {
            return mWifi.isConnected
        } else return false
    }


}