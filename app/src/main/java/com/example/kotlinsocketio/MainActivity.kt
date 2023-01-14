package com.example.kotlinsocketio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity(), MyClient.Listener {

    private val btnConnect: Button by lazy { findViewById(R.id.btnConnect) }
    private val client = MyClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnConnect.setOnClickListener {
            if (client.isConnected()) {
                disconnect()
            } else {
                connectToServer()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        client.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        client.removeListener(this)
    }

    private fun disconnect() {
        client.disconnect()
        btnConnect.text = "Connect"
    }

    private fun connectToServer() {
        val ip = "192.0.0.1"
        val port = 8080
        client.connect(ip = ip, port = port)
    }

    override fun onSocketConnected(ip: String, port: Int) {
        // Background thread이므로, 강제로 Main thread로 전환시켜줘야 함.
        runOnUiThread {
            btnConnect.text = "Disconnect"
            Toast.makeText(this, "Successfully connected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSocketConnectionFailed(e: Exception) {
        // Background thread이므로, 강제로 Main thread로 전환시켜줘야 함.
        runOnUiThread {
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
}