package com.mjurinic.argus.argus.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.mjurinic.argus.argus.R;
import com.mjurinic.argus.argus.camera.BasicSurfaceView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 0;

    @BindView(R.id.camera_preview)
    FrameLayout cameraPreview;

    private Camera camera;
    private BasicSurfaceView surfaceView;
    private boolean hasCameraPermission;

    private void initSurfaceView() {
        surfaceView = new BasicSurfaceView(getApplicationContext(), camera);
        cameraPreview.addView(surfaceView);
    }

    private void initCamera() {
        if (hasPermissions() && camera == null) {
            camera = Camera.open();
            camera.setDisplayOrientation(90);

            initSurfaceView();
        }
    }

    private void closeCamera() {
        cameraPreview.removeAllViews();

        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    public boolean hasPermissions() {
        hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        if (!hasCameraPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }

        return hasCameraPermission;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                hasCameraPermission = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeCamera();
    }
}
