package com.mobdeve.s17.samirsattendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
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
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

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
        osmController = (MapController) osmMapView.getController();
        osmMapView.setBuiltInZoomControls(true);
        osmMapView.setMultiTouchControls(true);
        IMapController mapController = osmMapView.getController();
        mapController.setZoom(9.5);
        GeoPoint startPoint = new GeoPoint(14.599512, 120.984222);
        mapController.setCenter(startPoint);
    }


    @Override
    public void onResume() {
        super.onResume();
        osmMapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        osmMapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}