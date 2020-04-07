package com.mycash.game.snakeladder.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mycash.game.snakeladder.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class CameraActivity extends AppCompatActivity implements Camera.PictureCallback {

    private static final String IMAGE_DIRECTORY = "/AppImage";
    private static final String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static Bitmap bitmap;
    private Camera mCamera;
    private CameraPreview mPreview;
    private FrameLayout mCameraView;
//    private Camera.PictureCallback mPicture;
    private FloatingActionButton btnCamera;
    private Button btnUpload;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mContext = this;
        mCameraView = findViewById(R.id.surfaceView);
        btnCamera = findViewById(R.id.btnCamera);
        btnUpload = findViewById(R.id.btnUpload);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        btnCamera.setOnClickListener(v -> {
            mCamera.takePicture(null, null, this);
        });

        btnUpload.setOnClickListener(v -> {
            startActivity(new Intent(CameraActivity.this, UploadActivity.class));
        });

    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void startCameraPreview() {
        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);
        mPreview = new CameraPreview(mContext, mCamera);
        mCameraView.addView(mPreview);


        if (mCamera == null) {
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
            mPreview.refreshCamera(mCamera);
            Log.d("nu", "null");
        } else {
            Log.d("nu", "no null");
        }
    }

    @SuppressLint("NoDelegateOnResumeDetector")
    public void onResume() {
        super.onResume();
        CameraActivityPermissionsDispatcher.startCameraPreviewWithPermissionCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        CameraActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //when on Pause, release camera in order to be used from other applications
        releaseCamera();
    }

    private void releaseCamera() {
        // stop and release camera
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private String getOutputMediaFile() {
        File folder = mContext.getExternalFilesDir(Environment.DIRECTORY_DCIM + File.separator +"Camera");
//        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File mediaStorageDir = new File(folder.getAbsolutePath());
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile.getAbsolutePath();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        if (bitmap != null) {
            String mPictureFileName = getOutputMediaFile();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(mPictureFileName));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(mContext, PictureActivity.class);
            intent.putExtra("path", mPictureFileName);
            startActivity(intent);

        }
    }
}
