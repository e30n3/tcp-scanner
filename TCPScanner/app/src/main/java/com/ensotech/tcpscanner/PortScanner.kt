package com.ensotech.tcpscanner

import android.widget.TextView
import java.io.IOException
import java.net.DatagramSocket
import java.net.ServerSocket
import java.util.concurrent.Executors

class PortScanner(var port: Int, var tv: TextView) : Thread() {

    override fun run() {
        if (isAvailable(port)) {
            tv.post {
                val newMsg = "${tv.text}" + "\n" +
                        "$port is open."
                tv.text = newMsg
            }
        } else {
            tv.post {
                val newMsg = "${tv.text}" + "\n" +
                        "$port is closed."
                tv.text = newMsg
            }
        }
    }

    private fun isAvailable(port: Int): Boolean {
        require(!(port < 0 || port > 8000)) { "Invalid start port: $port" }
        var ss: ServerSocket? = null
        var ds: DatagramSocket? = null
        try {
            ss = ServerSocket(port)
            ss.reuseAddress = true
            ds = DatagramSocket(port)
            ds.reuseAddress = true
            return true
        } catch (e: IOException) {
        } finally {
            ds?.close()
            if (ss != null) {
                try {
                    ss.close()
                } catch (e: IOException) {
                    /* should not be thrown */
                }
            }
        }
        return false
    }
}