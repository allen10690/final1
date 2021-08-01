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
import android.location.LocationManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.SupportMapFragment


class MapsActivity : FragmentActivity(), OnMapReadyCallback ,LocationListener ,LocationSource{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var REQUEST_PERMISSION_FOR_ACCESS_FINE_LOCATION=100 //定義權限編號
    private lateinit var mLocationMgr :LocationManager
    private lateinit var mLocationChangedListener: LocationSource.OnLocationChangedListener
    private lateinit var mylocationtxt : String


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
        setContentView(R.layout.activity_maps)
        mLocationMgr = getSystemService(LOCATION_SERVICE) as LocationManager//開一個location


        // 建立SupportMapFragment，並且設定Map的callback
        val supportMapFragment = SupportMapFragment()
        supportMapFragment.getMapAsync(this)

        // 把SupportMapFragment放到介面佈局檔裡頭的FrameLayout顯示。
        val m=supportFragmentManager.beginTransaction()
        m.replace(R.id.map, supportMapFragment).commit()




    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled=true
        mMap.setLocationSource(this)

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(25.033611, 121.5650)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in 101"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
                var location = mLocationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if(location==null){
                location=mLocationMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
            if(location!=null){
                Toast.makeText(this@MapsActivity,"取得了一次定位",Toast.LENGTH_LONG).show()
                onLocationChanged(location)
            }else{
                Toast.makeText(this@MapsActivity,"沒有定位資料",Toast.LENGTH_LONG).show()
            }
        }


    }

    override fun onLocationChanged(location: Location) {
        if(mLocationChangedListener !=null){
            mLocationChangedListener.onLocationChanged(location)
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude,location.longitude)))//可能要改!!!
        val la=location.latitude.toString()
        val lo=location.longitude.toString()
        mylocationtxt="我的經度是"+lo+"，我的緯度是:"+la
        val mylocationinfo : TextView =findViewById(R.id.mylocationinfo)
        mylocationinfo.setText(mylocationtxt)

    }

    override fun activate(p0: LocationSource.OnLocationChangedListener) {
        mLocationChangedListener = p0
        checkLocationPermissionAndEnableIt(true)
        Toast.makeText(this@MapsActivity,"地圖塗層啟用",Toast.LENGTH_LONG).show()
    }

    override fun deactivate() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onStop(){
        super.onStop()
        checkLocationPermissionAndEnableIt(false)//停止定位
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

        //已經有使用者同意
        if(on){
            if(mLocationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                mLocationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,500L,5f,this)
                Toast.makeText(this@MapsActivity,"用GPS定位",Toast.LENGTH_LONG).show()
            }else{
                if(mLocationMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                    mLocationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,500L,5f,this)
                    Toast.makeText(this@MapsActivity,"用網路定位",Toast.LENGTH_LONG).show()
                }
            }
        }else{
            mLocationMgr.removeUpdates(this)
            Toast.makeText(this@MapsActivity,"定位已經停用了",Toast.LENGTH_LONG).show()
        }


    }



}