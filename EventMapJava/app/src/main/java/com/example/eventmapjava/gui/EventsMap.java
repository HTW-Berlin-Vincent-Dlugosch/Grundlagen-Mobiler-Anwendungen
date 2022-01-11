package com.example.eventmapjava.gui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.eventmapjava.R;
import com.example.eventmapjava.logic.EventManager;
import com.example.eventmapjava.logic.UserManager;
import com.example.eventmapjava.model.ComponentManager;
import com.example.eventmapjava.model.Event;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayManager;

import java.util.List;

public class EventsMap extends AppCompatActivity {

    MapView map = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        // Initialize User
        setUpUser(ctx);
        //inflate and create the map
        setContentView(R.layout.activity_main);


        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        //Enable pinch to zoom
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        map.setMultiTouchControls(true);

        //Get mapController to set location and default zoom
        IMapController mapController = map.getController();
        mapController.setZoom(18.0);

        //getting and setting current location

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            setUpLocationDetection();
        }

        Thread replacePins = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    populateMapWithEvents();
                }
            }
        };
        replacePins.start();
    }

    @SuppressLint("MissingPermission")
    private void setUpLocationDetection() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
    }

    private final LocationListener locationListener = location -> {
        GeoPoint currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
        map.getController().setCenter(currentLocation);
        Log.v("LOCATION", location.toString());

    };

    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Log.i("LOCATION_PERMISSION", "Permission Granted");
                    setUpLocationDetection();
                    return;
                }
                finishAndRemoveTask();
            });

    MapEventsOverlay events = new MapEventsOverlay(new MapEventsReceiver() {
        @Override
        public boolean singleTapConfirmedHelper(GeoPoint p) {
            Log.v("CLICK_ON_MAP", "User clicked on point: " + p.toString());
            createEvent( p);
            return true;
        }

        @Override
        public boolean longPressHelper(GeoPoint p) {
            return false;
        }
    });

    private void populateMapWithEvents() {
        List<Overlay> currentOverlays = map.getOverlays();
        Log.d("OverlayListBeforeClear", Integer.toString(currentOverlays.size()));
        currentOverlays.removeIf(overlay -> overlay instanceof Marker);
        Log.d("OverlaysListAfterClear", Integer.toString(currentOverlays.size()));
        EventManager eventManager = ComponentManager.getComponentManager().getEventManager();
        for (Event event : eventManager.getEventList()) {
            GeoPoint location = new GeoPoint(event.getLocation()[0], event.getLocation()[1]);
            Marker eventMarker = new Marker(map);
            eventMarker.setTitle(event.getName());
            eventMarker.setPosition(location);
            eventMarker.setOnMarkerClickListener((marker, mapView)->{
                viewEvent(event.getEventID());
                return true;
            });
            map.getOverlays().add(eventMarker);
        }
    }

    private void createEvent(GeoPoint p) {
        Intent createEventIntent = new Intent(this, CreateEvent.class);
        createEventIntent.putExtra("Latitude", p.getLatitude());
        createEventIntent.putExtra("Longitude", p.getLongitude());
        startActivity(createEventIntent);
    }

    public void setUpUser(Context context) {
        UserManager userManager = ComponentManager.getComponentManager().getUserManager();
        Log.d("UserExists", Boolean.toString(userManager.currentUserExists(context)));
        if (userManager.currentUserExists(context)) {
            userManager.loadCurrentUser(context);
            return;
        }
        Intent createUserIntent = new Intent(this, CreateUser.class);
        startActivity(createUserIntent);
    }

    public void toggleMapMode(View v){
        OverlayManager overlayManager = map.getOverlayManager();
        if(overlayManager.contains(events)){
            overlayManager.remove(events);
            ((Button) v).setText(R.string.map_mode_toggle_create);
            return;
        }
        overlayManager.add(events);
        ((Button) v).setText(R.string.map_mode_toggle_view);
    }

    private void viewEvent(int eventID){
        Intent viewEventIntent = new Intent(this, ViewEvent.class);
        viewEventIntent.putExtra("EventID", eventID );
        startActivity(viewEventIntent);
    }
}