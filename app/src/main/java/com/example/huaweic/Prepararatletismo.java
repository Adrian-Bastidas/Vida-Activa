package com.example.huaweic;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaweic.services.LocationTracker;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.LocationSettingsRequest;
import com.huawei.hms.location.LocationSettingsResponse;
import com.huawei.hms.location.LocationSettingsStates;
import com.huawei.hms.location.SettingsClient;
import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Prepararatletismo extends onlocation implements OnMapReadyCallback{
    Timer timer;
    TimerTask timerTask;
    Double time=0.0;
    public static double distancia=0.0;
    public static ArrayList pintadolat, pintadolon;
    boolean timerstarted=false, finish=false;
    public TextView tit2, temp, dist;
    private static final String TAG = "Prepararatletismo";
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    public static int minuten=0, stunden=0, horasm=0, minutosm=0;
    private Polyline mPolyline;
    private static HuaweiMap hMap;
    private MapView mMapView;
    private ImageView comenzar, pausa, anterior, seguir, terminar;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public static Location loc1=null;
    public static boolean isStart=false;
    public static LocationTracker tracker=new LocationTracker();
    public static String actividad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepararatletismo);
        temp=findViewById(R.id.temp);
        dist=findViewById(R.id.dist);
        comenzar=findViewById(R.id.comenzar);
        pausa=findViewById(R.id.pausa);
        anterior=findViewById(R.id.anterior);
        seguir=findViewById(R.id.seguir);
        terminar=findViewById(R.id.terminar);
        seguir.setVisibility(View.INVISIBLE);
        terminar.setVisibility(View.INVISIBLE);
        pausa.setVisibility(View.INVISIBLE);
        timer=new Timer();
        actividad=getIntent().getStringExtra("actividad");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest mLocationRequest = new LocationRequest();
// Set the location update interval, in milliseconds.
        mLocationRequest.setInterval(10000);
// Set the location type.
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mMapView = findViewById(R.id.mapView);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        MapsInitializer.setApiKey("DAEDADyPHNPF1CeizC+H6zhzCii7bR9ZyhNMRX9tFjTzy6EbXkNmIpm19CnqK+R8u+BB5pV2H5C9J0o30wcEB9dIOP6qiBvZaV5Jjw==");
        mMapView.onCreate(null);
        mMapView.getMapAsync(this);
    }
    private void getLastLocation(){
        // Obtain the last known location.
        Task<Location> task = fusedLocationProviderClient.getLastLocation()
                // Define callback for success in obtaining the last known location.
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            Log.i(TAG, "Esta vacio");
                            return;
                        }
                        loc1=location;
                        hMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18));
                    }
                })
                // Define callback for failure in obtaining the last known location.
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.i(TAG, "No se pudo");
                    }
                });
    }
    public void cambiar(View view){
        Intent siguiente = new Intent(this, Personal.class);
        startActivity(siguiente);
    }
    public void terminar(View view){
        horasm=Math.round(stunden*1000);
        minutosm=minuten;
        distancia=Double.parseDouble(dist.getText().toString());
        Intent siguiente = new Intent(this, finAtletismo.class);
        siguiente.putExtra("horasm",horasm);
        siguiente.putExtra("minutosm",minutosm);
        siguiente.putExtra("distancia",distancia);
        siguiente.putExtra("actividad",actividad);
        startActivity(siguiente);
    }
    public void stopTracker(View view){
        if(isStart==false){
            isStart=true;
            pausa.setVisibility(View.VISIBLE);
            seguir.setVisibility(View.INVISIBLE);
            terminar.setVisibility(View.INVISIBLE);

            tracker.startLocationRequests(this, hMap,dist,isStart);
            cronometro();
        }
        else{
            isStart=false;
            pausa.setVisibility(View.INVISIBLE);
            seguir.setVisibility(View.VISIBLE);
            terminar.setVisibility(View.VISIBLE);
            tracker.startLocationRequests(this, hMap,dist,isStart);
            cronometro();
        }
    }
    public void setupTracker(View view){
        isStart=true;
        tracker.startLocationRequests(this, hMap,dist,isStart);
        cronometro();
        comenzar.setVisibility(View.INVISIBLE);
        pausa.setVisibility(View.VISIBLE);
        anterior.setVisibility(View.INVISIBLE);
    }
    public void cronometro(){
        if(timerstarted==false){
            timerstarted=true;
            starTimer();
        }
        else{
            timerstarted=false;
            timerTask.cancel();
        }
    }
    private void starTimer(){
        timerTask=new TimerTask() {
            public void run(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        temp.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }
    private String getTimerText(){
        int rounded=(int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        minuten=minutes;
        int hours = ((rounded % 86400) / 3600);
        stunden=hours;
        return formatTime(seconds, minutes, hours);
    }
    private String formatTime(int seconds, int minutes, int hours){
        return String.format("%02d",hours)+" : "+String.format("%02d",minutes)+" : "+String.format("%02d",seconds);
    }
    private void revisar(){
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        mLocationRequest = new LocationRequest();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
// Check the device location settings.
        settingsClient.checkLocationSettings(locationSettingsRequest)
                // Define the listener for success in calling the API for checking device location settings.
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        LocationSettingsStates locationSettingsStates =
                                locationSettingsResponse.getLocationSettingsStates();
                        StringBuilder stringBuilder = new StringBuilder();
                        // Check whether the location function is enabled.
                        stringBuilder.append(",\nisLocationUsable=")
                                .append(locationSettingsStates.isLocationUsable());
                        // Check whether HMS Core (APK) is available.
                        stringBuilder.append(",\nisHMSLocationUsable=")
                                .append(locationSettingsStates.isHMSLocationUsable());
                        Log.i(TAG, "checkLocationSetting onComplete:" + stringBuilder.toString());
                    }
                })
                // Define callback for failure in checking the device location settings.
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.i(TAG, "checkLocationSetting onFailure:" + e.getMessage());
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


    @RequiresPermission(allOf = {ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE})
    @Override
    public void onMapReady(HuaweiMap map){
        hMap = map;
        if(hMap!=null){
            // Enable the my-location layer.
            hMap.getUiSettings().isCompassEnabled();
            hMap.setMyLocationEnabled(true);
            getLastLocation();
            // Enable the my-location icon.
            hMap.getUiSettings().setMyLocationButtonEnabled(true);
            //hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hMap.);
        }
        else{
            Intent siguiente = new Intent(this, Prepararatletismo.class);
            startActivity(siguiente);
        }

    }
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}