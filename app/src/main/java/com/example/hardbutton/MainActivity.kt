package com.example.hardbutton

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.method.ScrollingMovementMethod
import android.view.KeyEvent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private var brightness = 255
    private var countUp: Int = 0
    private var countDown: Int = 0
    private lateinit var txtView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtView = findViewById(R.id.textView)
        txtView.movementMethod = ScrollingMovementMethod()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            decrease()
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            increase()
        }
        return true
    }

    private fun increase() {
        val context = applicationContext
        val settingCanWrite = hasWriteSettingPermission(context)
        if (!settingCanWrite) {
            changeWriteSettingPermission(context)
        } else {
            if (brightness <= 245) {
                brightness += 10
                changeScreenBrightness(context, brightness)
                val k = brightness.toDouble() / 255
                Toast.makeText(
                    applicationContext,
                    "Brightness : ${round(k * 100)}%",
                    Toast.LENGTH_SHORT
                ).show()
                //Add the Counter Variable
                countUp += 1
                txtView.text = "The Volume Up Button Clicked $countUp Times"

            }
        }
    }

    private fun changeScreenBrightness(context: Context, brightness: Int) {
        //Change the System Setting
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
        //Change the Screen Brightness
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            brightness
        )

    }

    private fun changeWriteSettingPermission(context: Context) {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    private fun hasWriteSettingPermission(context: Context?): Boolean {
        return Settings.System.canWrite(context)
    }

    private fun decrease() {
        val context = applicationContext
        val settingCanWrite = hasWriteSettingPermission(context)
        if (!settingCanWrite) {
            changeWriteSettingPermission(context)
        } else {
            if (brightness >= 11) {
                brightness -= 10
                changeScreenBrightness(context, brightness)
                val k = brightness.toDouble() / 255
                Toast.makeText(
                    applicationContext,
                    "Brightness : ${round(k * 100)}%",
                    Toast.LENGTH_SHORT
                ).show()
                //Add the Counter Variable
                countDown += 1
                txtView.text = "The Volume Down Button Clicked $countDown Times"
            }
        }
    }
}