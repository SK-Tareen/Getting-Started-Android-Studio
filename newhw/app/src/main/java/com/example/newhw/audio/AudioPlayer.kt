package com.example.newhw.audio

import android.media.MediaPlayer
import android.util.Log

class AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun playAudio(filePath: String) {
        stopAudio() // Ensure any existing audio is stopped before playing a new one

        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(filePath)
                prepare()
                start()
                setOnCompletionListener {
                    stopAudio() // Release resources after playback finishes
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("AudioPlayer", "Error playing audio: ${e.message}")
            }
        }
    }

    private fun stopAudio() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
            mediaPlayer = null
        }
    }
}
