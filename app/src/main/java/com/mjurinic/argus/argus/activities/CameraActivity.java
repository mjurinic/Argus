package com.mjurinic.argus.argus.activities;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.mjurinic.argus.argus.R;
import com.mjurinic.argus.argus.camera.BasicSurfaceView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraActivity extends AppCompatActivity {

    @BindView(R.id.camera_preview)
    FrameLayout cameraPreview;

    private Camera camera;
    private BasicSurfaceView surfaceView;

    private void initSurfaceView() {
        surfaceView = new BasicSurfaceView(getApplicationContext(), camera);
        cameraPreview.addView(surfaceView);
    }

    private void initCamera() {
        if (camera == null) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        // TODO permissions

        initCamera();
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
