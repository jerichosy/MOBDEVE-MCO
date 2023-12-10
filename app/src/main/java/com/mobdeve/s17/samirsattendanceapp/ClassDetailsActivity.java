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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ClassDetailsActivity extends AppCompatActivity {

    TextView tvClassName;
    TextView tvClassSchedule;
    TextView tvLearningMode;
    TextView tvFaculty;
    TextView tvMembers;
    TextView tvCurrentLocation;
    private MapView osmMapView;
    Button btnAttend;
    private String join_code;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private MapController osmController;
    private MyLocationNewOverlay osmOverlay;
    private boolean hasLocationFix = false;
    private final GeoPoint DLSU_LOCATION = new GeoPoint(14.5661089,120.9912146);
    private boolean isF2F = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        tvClassName = (TextView) findViewById(R.id.tv_class_name);
        tvClassSchedule = (TextView) findViewById(R.id.tv_class_schedule);
        tvLearningMode = (TextView) findViewById(R.id.tv_learning_mode);
        tvFaculty = (TextView) findViewById(R.id.tv_faculty);
        tvMembers = (TextView) findViewById(R.id.tv_members);
        tvCurrentLocation = (TextView) findViewById(R.id.tv_current_location);
        osmMapView = (MapView) findViewById(R.id.osm_mv_current_loc);
        btnAttend = (Button) findViewById(R.id.btn_attend);

        this.join_code = getIntent().getStringExtra("classJoinCode");

        tvClassName.setText(getIntent().getStringExtra("className"));
        tvClassSchedule.setText(getIntent().getStringExtra("classSchedule"));
        tvLearningMode.setText("Learning Mode: " + getIntent().getStringExtra("classLearningMode"));
        tvFaculty.setText(getIntent().getStringExtra("classCreatorDisplayName"));
        tvMembers.setText(getIntent().getStringExtra("classMembers") + " / " + getIntent().getStringExtra("classCapacity"));

        if (!getIntent().getStringExtra("classLearningMode").equals("F2F")) {
            isF2F = false;
            osmMapView.setVisibility(MapView.GONE);
            tvCurrentLocation.setVisibility(TextView.GONE);
        }

        if (isF2F) {
            // OSM Integration
            Context ctx = getApplicationContext();
            Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
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

        btnAttend.setOnClickListener(v -> {
            if (isF2F) {
                // Geolocation guard clause
                if (!hasLocationFix) {
                    Toast.makeText(getApplicationContext(), "Location not found!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Geolocation guard clause
                System.out.println(osmOverlay.getMyLocation());
                if (osmOverlay.getMyLocation().distanceToAsDouble(DLSU_LOCATION) > 500) {
                    Toast.makeText(getApplicationContext(), "You are not in DLSU!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Not a member guard clause  TODO: Do we need this?
            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            //FIXME: Conditions are not hitting properly
            db.collection("memberships").whereEqualTo("uid", Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                    .whereEqualTo("join_code", join_code)
                    .get()
                    .addOnCompleteListener(
                            task -> {
                                // If nothing found, then error
                                if (task.isSuccessful()) {
                                    if (task.getResult().size() == 0) {
                                        Toast.makeText(getApplicationContext(), "You are not a member of this class!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                    );

            // Attendance already recorded guard clause
            Date currentDate = new Date();
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.US);
            String date = dateFormat.format(currentDate);
            //FIXME: Conditions are not hitting properly
            db.collection("attendance").whereEqualTo("date", date)
                    .whereEqualTo("join_code", join_code)
                    .whereEqualTo("uid", Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).get().addOnCompleteListener(
                            task -> {
                                if (task.isSuccessful()) {
                                    if (task.getResult().size() != 0) {
                                        Toast.makeText(getApplicationContext(), "Attendance already recorded!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                    );

            // Success
            String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            String display_name = Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName();
            db.collection("attendance").add(new AttendanceData(uid, date, display_name, join_code));
            db.collection("attendance_total")
                    .whereEqualTo("uid", uid).get().addOnCompleteListener(
                            task -> {
                                if (task.isSuccessful()) {
                                    if (task.getResult().size() > 0) {
                                        // update attendance_total
                                        String id = task.getResult().getDocuments().get(0).getId();
                                        int attendance = task.getResult().getDocuments().get(0).getLong("attendance").intValue();
                                        db.collection("attendance_total").document(id).update("attendance", attendance + 1);
                                    } else {
                                        // add new record
                                        db.collection("attendance_total").add(new StudentRecord(display_name, uid, 1));
                                    }
                                }
                            }
                    );
            Toast.makeText(getApplicationContext(), "Attendance recorded!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isF2F) {
            osmMapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
            osmOverlay.enableMyLocation();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isF2F) {
            osmMapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
            osmOverlay.disableMyLocation();
        }
    }
}