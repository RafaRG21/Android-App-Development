package com.example.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.location.databinding.ActivityRutasBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RutasActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ActivityRutasBinding binding;
    private boolean firstMarker = false, secondMarker = false;
    LatLng p1, p2;
    protected int minimumDistance = 30;
    private final int PERMISSION_LOCATION = 999;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private String key = "AIzaSyCngJvHlT2HUyjALQdzVIiJik58zZY1o7U";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRutasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney and move the camera

        mMap = googleMap;
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)
        {
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gdl,15));
        mMap.setOnMapClickListener(this);
    }//onMapready



    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        if(!firstMarker) {
            p1 = latLng;
            mMap.addMarker(new MarkerOptions().position(new LatLng(p1.latitude, p1.longitude)).title("Origen"));
            firstMarker = true;
        }
        else if(!secondMarker){
            p2 = latLng;
            secondMarker = true;
            direction(String.valueOf(p1.latitude),String.valueOf(p1.longitude),String.valueOf(p2.latitude),String.valueOf(p2.longitude));
            mMap.addMarker(new MarkerOptions().position(new LatLng(p2.latitude, p2.longitude)).title("Destino"));
        }
        else{
            firstMarker = false;
            secondMarker = false;
            mMap.clear();
        }
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    private void direction(String latOrigin,String longOrigin,String latDest, String longDest){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Uri.parse("https://maps.googleapis.com/maps/api/directions/json")
                .buildUpon()
                .appendQueryParameter("destination", latDest+", "+longDest)
                .appendQueryParameter("origin", latOrigin+", "+longOrigin)
                .appendQueryParameter("mode", "driving")
                .appendQueryParameter("key", key)
                .toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (status.equals("OK")) {
                        JSONArray routes = response.getJSONArray("routes");

                        ArrayList<LatLng> points;
                        PolylineOptions polylineOptions = null;

                        for (int i=0;i<routes.length();i++){
                            points = new ArrayList<>();
                            polylineOptions = new PolylineOptions();
                            JSONArray legs = routes.getJSONObject(i).getJSONArray("legs");

                            for (int j=0;j<legs.length();j++){
                                JSONArray steps = legs.getJSONObject(j).getJSONArray("steps");

                                for (int k=0;k<steps.length();k++){
                                    String polyline = steps.getJSONObject(k).getJSONObject("polyline").getString("points");
                                    List<LatLng> list = decodePoly(polyline);

                                    for (int l=0;l<list.size();l++){
                                        LatLng position = new LatLng((list.get(l)).latitude, (list.get(l)).longitude);
                                        points.add(position);
                                    }
                                }
                            }
                            polylineOptions.addAll(points);
                            polylineOptions.width(10);
                            polylineOptions.color(ContextCompat.getColor(RutasActivity.this, R.color.purple_500));
                            polylineOptions.geodesic(true);
                        }
                        mMap.addPolyline(polylineOptions);
                        mMap.addMarker(new MarkerOptions().position(new LatLng(p1.latitude, p1.longitude)).title("Origen"));
                        mMap.addMarker(new MarkerOptions().position(new LatLng(p2.latitude, p2.longitude)).title("Destino"));

                        LatLngBounds bounds = new LatLngBounds.Builder()
                                .include(new LatLng(p1.latitude, p1.longitude))
                                .include(new LatLng(p2.latitude, p2.longitude)).build();
                        Point point = new Point();
                        getWindowManager().getDefaultDisplay().getSize(point);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, point.x, 150, 30));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RetryPolicy retryPolicy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(jsonObjectRequest);
    }//direction


    private List<LatLng> decodePoly(String encoded){
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b > 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }//poly

}//class