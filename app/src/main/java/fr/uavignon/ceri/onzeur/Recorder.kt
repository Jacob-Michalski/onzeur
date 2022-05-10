package fr.uavignon.ceri.onzeur

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.media.MediaRecorder
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.File
import java.io.IOException
import java.lang.Exception

class Recorder(path: String) {
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false

    init {
        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder?.setOutputFile(path)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
    }


    public fun startRecording(): String {
        try {
            mediaRecorder?.prepare()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            return "Recording prepare failed (state) "
        } catch (e: SecurityException) {
            e.printStackTrace()
            return "Recording prepare failed (sec) "
        }
        try{
            mediaRecorder?.start()
            state = true
            return "Recording started"
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            return "Recording failed (state)"
        } catch (e: IOException) {
            e.printStackTrace()
            return "Recording failed (IO)"
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.cause)
            println(e.message)
            return "Recording failed (other)"
        }
    }

    public fun stopRecording(): String {
        if(state){
            mediaRecorder?.stop()
            mediaRecorder?.reset();    // set state to idle
            mediaRecorder?.release()
            state = false
            return "Recording endded"
        } else {
            return "No recording"
        }
    }


}