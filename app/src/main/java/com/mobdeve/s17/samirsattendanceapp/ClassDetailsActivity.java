package com.mobdeve.s17.samirsattendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.Objects;

public class ClassDetailsActivity extends AppCompatActivity {

    TextView tvClassName;
    TextView tvClassSchedule;
    TextView tvFaculty;
    TextView tvMembers;
    Button btnAttend;
    private String join_code;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private MapView osmMapView;
    private MapController osmController;
    private MyLocationNewOverlay osmOverlay;
    private boolean hasLocationFix = false;
    private final GeoPoint DLSU_LOCATION = new GeoPoint(14.5661089,120.9912146);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        tvClassName = (TextView) findViewById(R.id.tv_class_name);
        tvClassSchedule = (TextView) findViewById(R.id.tv_class_schedule);
        tvFaculty = (TextView) findViewById(R.id.tv_faculty);
        tvMembers = (TextView) findViewById(R.id.tv_members);
        this.join_code = getIntent().getStringExtra("classJoinCode");

        tvClassName.setText(getIntent().getStringExtra("className"));
        tvClassSchedule.setText(getIntent().getStringExtra("classSchedule"));
        tvFaculty.setText(getIntent().getStringExtra("classCreatorDisplayName"));
        tvMembers.setText(getIntent().getStringExtra("classMembers") + " / " + getIntent().getStringExtra("classCapacity"));

        btnAttend = (Button) findViewById(R.id.btn_attend);
        btnAttend.setOnClickListener(v -> {
            if (!hasLocationFix) {
                Toast.makeText(getApplicationContext(), "Location not found!", Toast.LENGTH_SHORT).show();
                return;
            }
            System.out.println(osmOverlay.getMyLocation());
            if (osmOverlay.getMyLocation().distanceToAsDouble(DLSU_LOCATION) > 500) {
                Toast.makeText(getApplicationContext(), "You are not in DLSU!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            String display_name = Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName();
            db.collection("attendance").add(new AttendanceData(uid, display_name, join_code));
            Toast.makeText(getApplicationContext(), "Attendance recorded!", Toast.LENGTH_SHORT).show();
            finish();
        });

        // OSM Integration
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        osmMapView = (MapView) findViewById(R.id.osm_mv_current_loc);
        osmMapView.setTileSource(TileSourceFactory.MAPNIK);
        osmMapView.setMultiTouchControls(true);

        // Center Map
        osmController = (MapController) osmMapView.getController();
        osmController.setZoom(12.5);
        GeoPoint startPoint = new GeoPoint(14.599512, 120.984222);
        osmController.setCenter(startPoint);

        // MyLocationOverlay
        osmOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx), osmMapView);
        osmOverlay.enableMyLocation();
        osmMapView.getOverlays().add(osmOverlay);

        osmOverlay.runOnFirstFix(() -> {
            runOnUiThread(() -> {
                osmController.animateTo(osmOverlay.getMyLocation());
                osmController.setZoom(17.5);
                hasLocationFix = true;

                osmOverlay.enableFollowLocation();
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        osmMapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
        osmOverlay.enableMyLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        osmMapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
        osmOverlay.disableMyLocation();
    }
}