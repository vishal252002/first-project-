package com.example.user.torch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   private String TAG="MainActivity";
    private static final int REQUEST_CODE =10002;
     private static boolean premission=false;
     private CameraManager cameraManager;
     private static String camera_id;
     private Camera camera;
     private Camera.Parameters parameters;
     private boolean isFlash=false;
    private boolean isOn=false;
     private boolean isReady=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //check device sdk

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            isFlash=true;
            if (hasFlash()){
                if (checkPermission()){
                    Log.d(TAG,"OnCreate: checking selfpermission");
                    cameraManager=(CameraManager) getSystemService(CAMERA_SERVICE);
                    try {
                        Log.d(TAG,"onCreate: getting camera id");
                        camera_id=cameraManager.getCameraIdList()[0];
                        isReady=true;
                    }catch (CameraAccessException e){
                        e.printStackTrace();
                    }
                    }
                }else{
                Toast.makeText(this,"device doesn't support this app",Toast.LENGTH_SHORT).show();

            }
            }else {
            //device below marshmellow
            if (hasFlash()){
                camera=Camera.open();
                parameters=camera.getParameters();
                isReady=true;
            }else {
                Toast.makeText(this,"device doesn't support this app",Toast.LENGTH_SHORT).show();

            }
        }
        //finding view by its id

        final ImageView b=findViewById(R.id.b);

        b.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (isFlash){
                    if (!isOn){
                        //button is on state
                        try {
                            Log.d(TAG,"onClick :torch on");
                            cameraManager.setTorchMode(camera_id,false);
                            isOn=true;
                            b.setImageResource(R.drawable.download);
                        }catch (CameraAccessException e){
                            e.printStackTrace();
                        }
                    }else {
                        try {
                            Log.d(TAG,"onClick: torch off");
                            cameraManager.setTorchMode(camera_id,true);
                            isOn=false;
                            b.setImageResource(R.drawable.images);
                        }catch (CameraAccessException e){
                            e.printStackTrace();
                        }
                        //button off state
                    }
                }else {
                    if (!isOn){
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                        isOn=true;
                        camera.setParameters(parameters);
                        camera.startPreview();
                        b.setImageResource(R.drawable.images);
                    }else {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        isOn=false;camera.setParameters(parameters);
                        camera.stopPreview();
                        b.setImageResource(R.drawable.download);
                    }
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission granted",Toast.LENGTH_SHORT).show();

            }else {
                System.exit(0);
            }
        }
    }

    public boolean hasFlash() {

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            return true;
        }
        return false;
    }

    public boolean checkPermission(){
        //device sdk is mashmellow or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                return true;
            }else{
                //request runtime permission
                String [] permissions_f={Manifest.permission.CAMERA};
                requestPermissions(permissions_f,REQUEST_CODE);
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
