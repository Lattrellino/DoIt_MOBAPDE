package com.example.jayjay.doit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final int REQUEST_LOCATION = 0;
    public static final String TAG = "MainActivity";
    public static final String LOCATION_NAME = "locationName";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private GoogleMap mMap;
    private FusedLocationProviderClient client;
    private Marker userMarker;
    private String selectPlace;
    private Button mapbut;
    private Button buttonDone;

    //private ImageButton PlacePickerButton;
    //private


    PlaceAutocompleteFragment autocompleteFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        buttonDone = (Button) findViewById(R.id.done_but);

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(LOCATION_NAME,selectPlace);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override//this is where you can set the marker or anything on the place that you search
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                //place.
                selectPlace = place.getName().toString();
                mMap.clear();

                CameraPosition campos = new CameraPosition.Builder()
                        .target(place.getLatLng())
                        .zoom(17)
                        .bearing(20)
                        .tilt(0)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(campos));

                mMap.addMarker(new MarkerOptions()
                        .position(place.getLatLng())
                        .title(place.getName().toString()));


            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        mapFragment.getMapAsync(this);
    }

    /* AutoCompleteFragment GENERATOR
    PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
            getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
        @Override
        public void onPlaceSelected(Place place) {
            // TODO: Get info about the selected place.
            Log.i(TAG, "Place: " + place.getName());
        }

        @Override
        public void onError(Status status) {
            // TODO: Handle the error.
            Log.i(TAG, "An error occurred: " + status);
        }
    }); */



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
        mMap = googleMap;
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

            return;
        }
        else{
            getContinuousLocationUpdates();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        client.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: get intance of FudeLocationProvider
        client = LocationServices.getFusedLocationProviderClient(this);
    }

    public boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: if has no permission
            return false;
        } else {
            // TODO: if has permission
            return true;
        }
    }

    public void getContinuousLocationUpdates() {
        if (checkLocationPermission()) {

            // TODO: Set internal and fastest interval for the location request
            LocationRequest locationRequest = LocationRequest.create()
                    .setInterval(500)
                    .setFastestInterval(1000);

            client.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION
            );
        }
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            // TODO: get last location
            Location currentLocation = locationResult.getLastLocation();

            // TODO: get lat and lng from last location
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            if (userMarker != null) {

                // TODO: clear previous marker
                userMarker.remove();
            }

            // TODO: draw marker on latest location
            //userMarker = mMap.addMarker(new MarkerOptions().position(currentLatLng));

            // TODO: animate to zoom
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 1000));//change V:

            // TODO: enable building on map
            /*
            mMap.addCircle(new CircleOptions().center(currentLatLng)
                    .fillColor((Color.argb(50,100,100,100)))
                    .strokeColor((Color.parseColor("#00000000")))
                    .radius(500)
            );*/

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Log.i("MapsActivity", "User clicked on marker at " + marker.getPosition().toString());
                    return false;
                }
            });
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContinuousLocationUpdates();
            }
        }
    }

}
