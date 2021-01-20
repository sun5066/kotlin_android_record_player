package github.sun5066.record.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import github.sun5066.record.R
import github.sun5066.record.databinding.ActivityRecordBinding
import github.sun5066.record.model.RecordData
import github.sun5066.record.ui.adapter.RecordViewModel
import java.io.IOException
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class RecordActivity : BaseActivity<ActivityRecordBinding>(), View.OnClickListener,
    RecordNavigator {
    private val TAG = this.javaClass.simpleName

    private var fileName: String = ""

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null

    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(android.Manifest.permission.RECORD_AUDIO)

    private lateinit var mUuid: UUID

    private val mRecordViewModel: RecordViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(RecordViewModel::class.java)
    }

    /**********************************************************************************************/

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

    override fun getLayoutResourceId(): Int = R.layout.activity_record

    override fun initDataBinding() {
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mRecordViewModel
    }

    override fun initView() {
        ActivityCompat.requestPermissions(this, permissions, 200)
        mBinding.btnRecord.setOnClickListener(this)
        mBinding.btnPlay.setOnClickListener(this)
        mBinding.btnSave.setOnClickListener(this)
    }

    override fun onRecord(start: Boolean) =
        if (start) startRecording() else stopRecording()

    override fun onPlay(start: Boolean) =
        if (start) startPlaying() else stopPlaying()

    override fun onClick(v: View?) {
        if (v == null) return
        val btn = v as Button

        when (v.id) {
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
                Log.d(TAG, fileName)
                this.onPlay(isPlaying)
            }
            R.id.btn_save -> {
                val recordData = RecordData()
                val sd = SimpleDateFormat("yyyy-MM-dd")
                val st = SimpleDateFormat("HH:mm:ss")
                val date = System.currentTimeMillis()

                recordData.date = sd.format(date).toString()
                recordData.time = st.format(date).toString()
                recordData.title = mBinding.editTitle.text.toString()
                recordData.writer = mBinding.editWriter.text.toString()
                recordData.record = "$mUuid.3gp"

                val returnIntent = Intent(this, RecordData::class.java)
                returnIntent.putExtra("record", recordData)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
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

    /**********************************************************************************************/

    private fun startRecording() {
        mUuid = UUID.randomUUID()
        fileName = "${externalCacheDir?.absolutePath}/$mUuid.3gp"

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(TAG, "prepare() failed")
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

    private fun startPlaying() {
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e(TAG, "prepare() failed")
            }
        }
    }

    private fun stopPlaying() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}