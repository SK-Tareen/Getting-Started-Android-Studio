package com.example.newhw.audio

import android.Manifest
import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class AudioRecorder(private val context: Context) {
    private var recorder: MediaRecorder? = null
    private var audioFilePath: String? = null

    @RequiresApi(Build.VERSION_CODES.S)
    fun startRecording(): String? {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) !=
            android.content.pm.PackageManager.PERMISSION_GRANTED) {
            throw SecurityException("RECORD_AUDIO permission not granted")
        }

        val fileName = "order_audio_${System.currentTimeMillis()}.mp3"
        val filePath = context.filesDir.absolutePath + "/$fileName"  // Safer location
        audioFilePath = filePath

        recorder = MediaRecorder(context)

        try {
            recorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setOutputFile(filePath)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                prepare()
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            recorder?.release()
            recorder = null
            return null
        }

        return filePath
    }

    fun stopRecording(): String? {
        return try {
            recorder?.let {
                it.stop()
                it.release()
                recorder = null
            }
            audioFilePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
