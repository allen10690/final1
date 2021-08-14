package com.example.final1

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_qr_scan.*
import java.util.jar.Manifest
import com.google.android.gms.vision.barcode.Barcode
import java.io.IOException
import android.util.SparseArray
import com.google.android.gms.vision.Detector.Detections


class QR_Scan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scan)
        getPermissionCamera()

        var surfaceView: SurfaceView=findViewById(R.id.surfaceView)
        var textview: TextView=findViewById(R.id.textView_inQR_scan)
        var cameraSource : CameraSource
        var barcodeDetector:BarcodeDetector
        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.QR_CODE).build()
        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(300, 300).build()


        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {//這一大串是他自己跑的，我也不懂啊
            override fun surfaceCreated(@NonNull surfaceHolder: SurfaceHolder) {
                if (ActivityCompat.checkSelfPermission(
                        applicationContext,
                        android.Manifest.permission.CAMERA
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) return
                try {
                    cameraSource.start(surfaceHolder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            override fun surfaceChanged(
                @NonNull surfaceHolder: SurfaceHolder,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }
            override fun surfaceDestroyed(@NonNull surfaceHolder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}
            override fun receiveDetections(detections: Detections<Barcode>) {
                val qrCodes = detections.detectedItems
                if (qrCodes.size() != 0) {
                    textview.post(Runnable { textview.text = qrCodes.valueAt(0).displayValue })
                }
            }
        })


    }

    private fun getPermissionCamera(){
        if(ActivityCompat.checkSelfPermission(this@QR_Scan,android.Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@QR_Scan,
                arrayOf(android.Manifest.permission.CAMERA),1
            )
        }
    }
}
