package com.example.botones

import android.accessibilityservice.AccessibilityService
import android.media.AudioManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class KeyService : AccessibilityService() {
    override fun onAccessibilityEvent(e: AccessibilityEvent?) {}
    override fun onInterrupt() {}

    override fun onKeyEvent(event: KeyEvent): Boolean {
        val on = getSharedPreferences("p", MODE_PRIVATE).getBoolean("on", true)
        if (!on || event.keyCode != KeyEvent.KEYCODE_VOLUME_UP) return false
        if (event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) toggleMic()
        return true
    }

    private fun toggleMic() {
        val am = getSystemService(AUDIO_SERVICE) as AudioManager
        val mute = !am.isMicrophoneMute
        am.isMicrophoneMute = mute
        val v = getSystemService(Vibrator::class.java)
        val pattern = if (mute) longArrayOf(0, 250) else longArrayOf(0, 60, 80, 60)
        v.vibrate(VibrationEffect.createWaveform(pattern, -1))
        Toast.makeText(this,
            if (mute) "Micrófono silenciado" else "Micrófono activo",
            Toast.LENGTH_SHORT).show()
    }
}
