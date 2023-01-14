package com.example.kotlinsocketio

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class ClientSocket {

    private var socket: Socket? = null
    private var out: PrintWriter? = null
    private var br: BufferedReader? = null

    @Throws(IOException::class)
    fun connect(ip: String?, port: Int) {
        val clientSocket = Socket(ip, port).also { socket = it }
        out = PrintWriter(clientSocket.getOutputStream(), true)
        br = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
    }

    @Throws(IOException::class)
    fun sendMessage(msg: String?): String {
        out!!.println(msg)
        return br!!.readLine()
    }

    fun disconnect() {
        br?.use { }
        out?.use { }
        socket?.use { }
    }

    fun isConnected(): Boolean {
        return socket?.isConnected == true
    }
}