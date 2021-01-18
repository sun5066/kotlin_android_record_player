package github.sun5066.record.ui

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import github.sun5066.record.R
import java.io.IOException

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var fileName: String = ""

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null

    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(android.Manifest.permission.RECORD_AUDIO)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == 200) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else false
        if (!permissionToRecordAccepted) finish() // 권한이 없으면 액티비티 종료
    }

    /**********************************************************************************************/

    private fun onRecord(start: Boolean) = if (start) {
        startRecording()
    } else {
        stopRecording()
    }

    private fun startRecording() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("TAG", "prepare() failed")
            }
            start()
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }

    /**********************************************************************************************/

    private fun onPlay(start: Boolean) = if (start) {
        startPlaying()
    } else {
        stopPlaying()
    }

    private fun startPlaying() {
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("TAG", "prepare() failed")
            }
        }
    }

    private fun stopPlaying() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    /**********************************************************************************************/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fileName = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"

        ActivityCompat.requestPermissions(this, permissions, 200)
        findViewById<Button>(R.id.btn_record).setOnClickListener(this)
        findViewById<Button>(R.id.btn_play).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == null) return
        val btn = v as Button

        when (btn.id) {
            R.id.btn_record -> {
                var isRecord = false
                if (btn.text.toString().toLowerCase() == "record") {
                    isRecord = true
                    btn.text = "stop"
                } else {
                    isRecord = false
                    btn.text = "Record"
                }
                this.onRecord(isRecord)
            }
            R.id.btn_play -> {
                var isPlaying = false
                if (btn.text.toString().toLowerCase() == "play") {
                    isPlaying = true
                    btn.text = "stop"
                } else {
                    isPlaying = false
                    btn.text = "Play"
                }
                this.onPlay(isPlaying)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaRecorder?.release()
        mediaRecorder = null

        mediaPlayer?.release()
        mediaPlayer = null
    }
}