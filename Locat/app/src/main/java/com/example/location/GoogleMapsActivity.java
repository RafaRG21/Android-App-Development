package com.example.location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.location.databinding.ActivityGoogleMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ActivityGoogleMapsBinding binding;
    protected int minimumDistance = 30;
    private final int PERMISSION_LOCATION = 999;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGoogleMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setSmallestDisplacement(minimumDistance);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.e("Sesion11",
                        locationResult.getLastLocation().getLatitude() + "," +
                                locationResult.getLastLocation().getLongitude());
            }
        };
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        mMap.addMarker(new MarkerOptions()
                .title("Marca personal")
                .snippet("Mi sitio marcado")
                .draggable(true)

                .position(latLng));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull
            String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
        if (requestCode == PERMISSION_LOCATION) {
            if (
                    permissions[0].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)
                            &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            }//if-permission
        }//if-request
    }//onRequestPermissionsResult



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION

                    },
                    PERMISSION_LOCATION);
        }
// Add a marker in Sydney and move the camera
        LatLng gdl = new LatLng(20.666667, -103.333333);
        mMap.addMarker(new
                MarkerOptions().position(gdl).title("Guadalajara")
                .snippet("Hueles a tierra mojada"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gdl,15));
        mMap.setOnMapClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.activity_maps_map:
                mMap.clear();
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.activity_maps_terrain:
                mMap.clear();
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.activity_maps_hybrid:
                mMap.clear();
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.activity_maps_polylines:
                showPolylines();
                break;
        }//switch
    }//onClick

    protected void startLocationUpdates(){
        try{
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest,
                    locationCallback,null);
        } catch (SecurityException e){
        }
    }

    protected void stopLocationUpdates(){
        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }//stopLocationUpdates
    @Override
    protected void onStart() {
        super.onStart();
        startLocationUpdates();
    }//onStart
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }//onPause
    public void showPolylines(){
        if(mMap != null){
//El par??metro 2 es un rango de valores de 2.0 a 21.0
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
                    LatLng(20.68697,-103.35339),12));
            mMap.addPolyline(new PolylineOptions().geodesic(true)
                    .add(new LatLng(20.73882,-103.40063)) //Auditorio Telmex
                    .add(new LatLng(20.69676, -103.37541)) //Plaza Midtown
                    .add(new LatLng(20.67806, -103.34673)) //Catedral GDL
                    .add(new LatLng(20.64047, -103.31154)) //Parian
            );
        }//if
    }//showPolylines
}