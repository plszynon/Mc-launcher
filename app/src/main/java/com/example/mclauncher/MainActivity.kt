package com.example.mclauncher

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // Katalog domowy Termuxa - tam muszą leżeć skrypty .sh (patrz README)
    private val termuxHome = "/data/data/com.termux/files/home"

    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)

        findViewById<Button>(R.id.btnStartAll).setOnClickListener {
            runTermuxScript("start-all.sh")
        }
        findViewById<Button>(R.id.btnStartJava).setOnClickListener {
            runTermuxScript("start-java.sh")
        }
        findViewById<Button>(R.id.btnStartBedrock).setOnClickListener {
            runTermuxScript("start-bedrock.sh")
        }
        findViewById<Button>(R.id.btnStop).setOnClickListener {
            runTermuxScript("stop-all.sh")
        }
    }

    private fun runTermuxScript(scriptName: String) {
        val scriptPath = "$termuxHome/$scriptName"
        try {
            val intent = Intent()
            intent.setClassName("com.termux", "com.termux.app.RunCommandService")
            intent.action = "com.termux.RUN_COMMAND"
            intent.putExtra("com.termux.RUN_COMMAND_PATH", scriptPath)
            intent.putExtra("com.termux.RUN_COMMAND_ARGUMENTS", arrayOf<String>())
            intent.putExtra("com.termux.RUN_COMMAND_WORKDIR", termuxHome)
            intent.putExtra("com.termux.RUN_COMMAND_BACKGROUND", true)
            intent.putExtra("com.termux.RUN_COMMAND_SESSION_ACTION", "0")

            ContextCompat.startForegroundService(this, intent)
            log("Wysłano komendę: $scriptName")
        } catch (e: ActivityNotFoundException) {
            log("BŁĄD: Termux nie jest zainstalowany lub nie ma zgody na zdalne komendy.")
            Toast.makeText(this, "Zainstaluj Termux i włącz allow-external-apps", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            log("BŁĄD: ${e.message}")
        }
    }

    private fun log(msg: String) {
        tvStatus.text = "${tvStatus.text}\n$msg"
    }
}
