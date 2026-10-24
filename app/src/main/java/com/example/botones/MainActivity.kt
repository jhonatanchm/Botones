package com.example.botones

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        val prefs = getSharedPreferences("p", MODE_PRIVATE)
        val pad = (24 * resources.displayMetrics.density).toInt()

        val info = TextView(this).apply {
            text = "Subir volumen = silenciar / activar micrófono.\n\n" +
                "1. Toca el botón y activa el servicio \"Botones\" en Accesibilidad.\n" +
                "2. Usa el interruptor para encender o apagar el remapeo."
            textSize = 16f
        }
        val open = Button(this).apply {
            text = "Abrir ajustes de accesibilidad"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }
        val sw = Switch(this).apply {
            text = "Remapeo activo  "
            textSize = 18f
            isChecked = prefs.getBoolean("on", true)
            setOnCheckedChangeListener { _, c ->
                prefs.edit().putBoolean("on", c).apply()
            }
        }
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad * 2, pad, pad)
            addView(info)
            addView(open)
            addView(sw)
        }
        setContentView(root)
    }
}
