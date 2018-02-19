package com.afeastoffriends.doctorsaathi;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.afeastoffriends.doctorsaathi.AppConfig.GEOMETRY;
import static com.afeastoffriends.doctorsaathi.AppConfig.ICON;
import static com.afeastoffriends.doctorsaathi.AppConfig.LATITUDE;
import static com.afeastoffriends.doctorsaathi.AppConfig.LOCATION;
import static com.afeastoffriends.doctorsaathi.AppConfig.LONGITUDE;
import static com.afeastoffriends.doctorsaathi.AppConfig.MIN_DISTANCE_CHANGE_FOR_UPDATES;
import static com.afeastoffriends.doctorsaathi.AppConfig.MIN_TIME_BW_UPDATES;
import static com.afeastoffriends.doctorsaathi.AppConfig.NAME;
import static com.afeastoffriends.doctorsaathi.AppConfig.OK;
import static com.afeastoffriends.doctorsaathi.AppConfig.OPENING_HOURS;
import static com.afeastoffriends.doctorsaathi.AppConfig.OPEN_NOW;
import static com.afeastoffriends.doctorsaathi.AppConfig.PLAY_SERVICES_RESOLUTION_REQUEST;
import static com.afeastoffriends.doctorsaathi.AppConfig.RATING;
import static com.afeastoffriends.doctorsaathi.AppConfig.REFERENCE;
import static com.afeastoffriends.doctorsaathi.AppConfig.STATUS;
import static com.afeastoffriends.doctorsaathi.AppConfig.TAG;
import static com.afeastoffriends.doctorsaathi.AppConfig.VICINITY;
import static com.afeastoffriends.doctorsaathi.AppConfig.ZERO_RESULTS;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    CoordinatorLayout mainCoordinatorLayout;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    double currentLatitude;
    double currentLongitude;
    StringBuilder googlePlacesUrl;
    //String nextPageToken;
    MarkerOptions markerOption = new MarkerOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isGooglePlayServicesAvailable()) {
            return;
        }
        setContentView(R.layout.activity_maps2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void showLocationSettings() {
        Snackbar snackbar = Snackbar
                .make(mainCoordinatorLayout, "Location Error: GPS Disabled!",
                        Snackbar.LENGTH_LONG)
                .setAction("Enable", new View.OnClickListener() {
                    @Override                    public void onClick(View v) {

                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.i("1", "Yaha chai hunna");

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] A,
            //                                          int[] grantResults)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
            }

            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        buildGoogleApiClient();
        Log.i("2","YAha chai hunxa ki hunna");
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        Log.i("3","Yo chai Api Client");
    }

    private void showCurrentLocation() {
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);

        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
    }

    private void loadNearByPlaces(final double latitude, double longitude, String nextPage) {
        Log.i("4","Chai Latitude"+latitude+longitude);
//YOU Can change this type at your own will, e.g hospital, cafe, restaurant.... and see how it all works
//        String type = "grocery_or_supermarket";
//        String googlePlace;
        if(nextPage!="Hello"){
            googlePlacesUrl =
                    new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude + "&radius=10000&types=hospital&sensor=true&key=AIzaSyCn-blCSTb2irKPZRKUQpXwPqGWn5iRPGQ&pagetoken="+nextPage);
        }else {
            googlePlacesUrl =
                    new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude + "&radius=10000&types=hospital&sensor=true&key=AIzaSyCn-blCSTb2irKPZRKUQpXwPqGWn5iRPGQ");
        }
                    //googlePlace.append("location=").append(latitude).append(",").append(longitude);
        //googlePlacesUrl.append("&radius=").append(PROXIMITY_RADIUS);
        //googlePlacesUrl.append("&types=").append(type);
        //googlePlacesUrl.append("&sensor=true");
        //googlePlacesUrl.append("&key=" + GOOGLE_BROWSER_API_KEY);

        Log.i("5","chai Latitudeeeee"+latitude+longitude+googlePlacesUrl);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,googlePlacesUrl.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        Log.i(TAG, "onResponse: Result= " + result.toString());
                        parseLocationResult(result);
                        if(!result.isNull("next_page_token")){
                            try {
                                String nextPageToken = result.getString("next_page_token");
                                loadNearByPlaces(currentLatitude,currentLongitude,nextPageToken);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: Error= " + error);
                        Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
                    }
                });
        Log.i("7","chai Latitudeyy"+request);
        AppController.getInstance().addToRequestQueue(request);
        Log.i("8","chai Latitudeyy"+latitude+longitude+request);
    }


    private void parseLocationResult(JSONObject result) {
        String id;
        String place_id;
        String placeName = null;
        String reference;
        String icon;
        String vicinity = null;
        String rating=null;
        boolean opennow = false;
        double latitude, longitude;

        try {
            Log.i("9","chai Hello"+result.getString(STATUS));
            JSONArray jsonArray = result.getJSONArray("results");
            if (result.getString(STATUS).equalsIgnoreCase(OK)) {
                Log.i("10","chai Latitudeyy");
                //mMap.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject place = jsonArray.getJSONObject(i);
                    //place_id = place.getString(PLACE_ID);
                    if (!place.isNull(NAME)) {
                        placeName = place.getString(NAME);
                    }
                    if (!place.isNull(VICINITY)) {
                        vicinity = place.getString(VICINITY);
                    }
                    if(!place.isNull(RATING)){
                        rating = place.getString(RATING);
                    }
                    if(!place.isNull(OPENING_HOURS)){
                        opennow = place.getJSONObject(OPENING_HOURS).getBoolean(OPEN_NOW);
                    }
                    latitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LATITUDE);
                    longitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LONGITUDE);
                    reference = place.getString(REFERENCE);
                    icon = place.getString(ICON);


//                    double dlon = longitude - currentLongitude;
//                    double dlat = latitude - currentLatitude;
//                    double a = Math.pow((Math.sin(dlat / 2)), 2) + (Math.cos(currentLatitude) * Math.cos(latitude) * Math.pow((Math.sin(dlon / 2)), 2));
//                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//                    Radius of Earth = 6373
//                    double d = 6373 * c;

                    LatLng latLng = new LatLng(latitude, longitude);
                    markerOption.position(latLng);
                    markerOption.title(placeName+" : Rating ="+rating+"/5");
                    String isItOpen;
                    if(opennow){
                        markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        isItOpen = "Open Now";
                    }else{
                        markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        isItOpen = "Not Open Now";
                    }
                    markerOption.snippet(vicinity+" , "+isItOpen);
                    mMap.addMarker(markerOption);
                }

//                Toast.makeText(getBaseContext(), jsonArray.length() + " Hospitals and Clinics Found!",
//                        Toast.LENGTH_LONG).show();
            } else if (result.getString(STATUS).equalsIgnoreCase(ZERO_RESULTS)) {
                Toast.makeText(getBaseContext(), "No Hospitals found in 10KM radius!!!",
                        Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseLocationResult: Error=" + e.getMessage());
        }
    }



    @Override
    public void onLocationChanged(Location location) {
        Log.i("11","Yo Chai Hewllow");
        mLastLocation = location;
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker.remove();
//        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));



        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        loadNearByPlaces(location.getLatitude(),location.getLongitude(),"Hello");
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
