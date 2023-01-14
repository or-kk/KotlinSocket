package com.example.kotlinsocketio

import java.io.IOException

class MyClient {

    interface Listener {
        fun onSocketConnected(ip: String, port: Int)
        fun onSocketConnectionFailed(e: Exception)
    }

    private val listeners = hashSetOf<Listener>()
    private val client by lazy { ClientSocket() }

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun connect(ip: String, port: Int) {
        Thread {
            try {
                client.connect(ip = ip, port = port)
                notifyConnected(ip = ip, port = port)
            } catch (e: IOException) {
                e.printStackTrace()
                notifyConnectionError(e)
            }
        }.start()
    }

    fun disconnect() {
        client.disconnect()
    }

    fun isConnected(): Boolean = client.isConnected()

    private fun notifyConnectionError(e: Exception) {
        for (listener in listeners) {
            listener.onSocketConnectionFailed(e)
        }
    }

    private fun notifyConnected(ip: String, port: Int) {
        for (listener in listeners) {
            listener.onSocketConnected(ip = ip, port = port)
        }
    }
}
