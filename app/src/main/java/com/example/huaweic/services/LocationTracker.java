package com.example.huaweic.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ComponentActivity;

import com.example.huaweic.Prepararatletismo;
import com.example.huaweic.finEjercicio;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.LocationSettingsRequest;
import com.huawei.hms.location.LocationSettingsResponse;
import com.huawei.hms.location.SettingsClient;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
public class LocationTracker extends LocationCallback {
    public static int km=0,m=0;
    public static Context cont;
    public static ArrayList<Double> pintadoLat, pintadoLon;
    public static int horasm, minutosm;
    public static Double dist;
    public static Double kilometraje;
    public static boolean isStart, acabar;
    public static Location loc01, loc02;
    public static int num=0;
    public static Double distanciam=0.0;
    public static TextView dist1;
    private HuaweiMap mMap;
    public static final String TAG = "LocationTracker";
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    public static Location loc1, loc2;
    private Polyline mPolyline;
    public void startLocationRequests(Context context, HuaweiMap hMap, TextView dist, boolean comienza){
        pintadoLat=new ArrayList<Double>();
        pintadoLon=new ArrayList<Double>();
        mMap=hMap;
        dist1=dist;
        isStart=comienza;
        cont=context;
        mFusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(context);
        SettingsClient mSettingsClient=LocationServices.getSettingsClient(context);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
        Task<LocationSettingsResponse> locationSettingsResponseTask = mSettingsClient.checkLocationSettings(locationSettingsRequest);
        locationSettingsResponseTask.addOnSuccessListener(locationSettingsResponse -> {
            Log.i(TAG, "check location settings success");
            mFusedLocationProviderClient
                    .requestLocationUpdates(mLocationRequest, this, Looper.getMainLooper())
                    .addOnSuccessListener(aVoid -> {
                        Log.i(TAG, "requestLocationUpdatesWithCallback onSuccess");
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "requestLocationUpdatesWithCallback onFailure:" + e.getMessage());
                    });
        }).addOnFailureListener(e -> Log.e(TAG, "checkLocationSetting onFailure:" + e.getMessage()));
    }
    @Override
    public void onLocationResult(LocationResult locationResult) {
        if(isStart==true){
            if (locationResult != null) {
                List<Location> locations = locationResult.getLocations();
                if (!locations.isEmpty()) {
                    for (Location location : locations) {
                        Log.i(TAG, "onLocationResult location[Longitude,Latitude,Accuracy]:" + location.getLongitude()
                                + "," + location.getLatitude() + "," + location.getAccuracy());

                        if(loc1==null && loc2==null){
                            loc1=location;
                            loc01=location;
                            pintadoLat.add(location.getLatitude());
                            pintadoLon.add(location.getLongitude());
                        }
                        else{
                            loc2=location;
                            loc02=location;
                            dibujar();
                            distancia();
                            loc1=loc2;
                            loc01=loc02;
                            pintadoLat.add(location.getLatitude());
                            pintadoLon.add(location.getLongitude());
                        }
                    }
                }
            }
        }
        if(acabar==true){

        }
    }
    public void distancia(){
        Double distkm=0.0;
        int radio=6378;
        distanciam+=2*radio*(Math.asin(//comienzo del primer parentesis
                Math.sqrt(//raiz cuadrada
                        Math.pow(Math.sin(((loc2.getLatitude()-loc01.getLatitude())/2)),2)+ //primer seno al cuadrado
                        Math.cos(loc01.getLatitude())*Math.cos(loc2.getLatitude())* //Cosenos
                        Math.pow(Math.sin((loc2.getLongitude()-loc01.getLongitude())/2),2) //segundo seno al cuadrado
                ))
        );
        distkm=Math.round((distanciam/100.0)*100.0)/100.0;
        dist1.setText(String.valueOf(distkm));
    }
    public void dibujar(){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc2.getLatitude(), loc2.getLongitude()),18));
        mPolyline = mMap.addPolyline(
                new PolylineOptions().add(
                        new LatLng(loc1.getLatitude(),loc1.getLongitude()),
                        new LatLng(loc2.getLatitude(),loc2.getLongitude()))
                        .color(Color.BLUE)
                        .width(3));
        loc1=loc2;
    }
}


