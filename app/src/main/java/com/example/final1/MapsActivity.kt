package com.example.final1

import android.Manifest
import android.location.Location
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.final1.databinding.ActivityMapsBinding
import android.location.LocationListener
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.*

class MapsActivity : FragmentActivity(), OnMapReadyCallback ,LocationListener ,LocationSource{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var REQUEST_PERMISSION_FOR_ACCESS_FINE_LOCATION=100 //定義權限編號

    //測試權限
    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<out String>,
        @NonNull grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==REQUEST_PERMISSION_FOR_ACCESS_FINE_LOCATION){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                checkLocationPermissionAndEnableIt(true)
                return
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(25.033611, 121.5650)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in 101"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.isMyLocationEnabled=true

    }

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }

    override fun activate(p0: LocationSource.OnLocationChangedListener) {
        TODO("Not yet implemented")
    }

    override fun deactivate() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
        if (mMap!=null)
            checkLocationPermissionAndEnableIt(true)
    }

    private fun checkLocationPermissionAndEnableIt(on: Boolean) {
        if(ContextCompat.checkSelfPermission(this@MapsActivity,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            //這功能沒有使用者同意
            if(ActivityCompat.shouldShowRequestPermissionRationale(this@MapsActivity,android.Manifest.permission.ACCESS_FINE_LOCATION)){
                val listener = DialogInterface.OnClickListener { dialog, which ->
                    //答覆後執行onRequestPermissionsResult()
                    ActivityCompat.requestPermissions(this@MapsActivity, arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ),REQUEST_PERMISSION_FOR_ACCESS_FINE_LOCATION)
                }
                val altDlgBuilde =AlertDialog.Builder(this)
                altDlgBuilde.setTitle("提示")
                altDlgBuilde.setMessage("你沒有啟動Chital的定位功能")
                altDlgBuilde.setIcon(android.R.drawable.ic_dialog_info)
                altDlgBuilde.setCancelable(false)
                altDlgBuilde.setPositiveButton("確定",listener)
                altDlgBuilde.show()
                return
            }else{
                //答覆後執行onRequestPermissionsResult()
                ActivityCompat.requestPermissions(this@MapsActivity, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                    ),REQUEST_PERMISSION_FOR_ACCESS_FINE_LOCATION)

            }
        }


    }



}