package com.blackcoin.ironmanflashlight;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;

import layout.IronManWidgetActivity;


public class MainActivity extends AppCompatActivity {


    private boolean light;

    private ImageView Background;

    public static Camera camera;

    Camera.Parameters par1_on;

    Camera.Parameters par2_off;

    public static void log(String log) {
        Log.i("AMP : ", log);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // for fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // check if app's widget is enable
        if(IronManWidgetActivity.WidgetLight){

            log("Bug : FUUUUUUUUUUUUCK!");

            // this line does'nt work!!!
            IronManWidgetActivity.remoteViews.setViewVisibility(R.id.appwidget_image2, View.INVISIBLE);

            IronManWidgetActivity.WidgetLight = false;
        }

        // inflating the layout
        setContentView(R.layout.activity_main);

        final Button flash = (Button) findViewById(R.id.flash);

        Background = (ImageView) findViewById(R.id.bg);

        try{
            camera.release();

        }catch (Exception e){

            log(e.toString());
        }

        camera = getCameraInstance();


        par1_on = camera.getParameters();

        par2_off = camera.getParameters();


        par1_on.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

        par2_off.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);


        log("get camera ok!");


        // check if the device has the ability
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {


            log("Have Flash");
            flash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!light) {

                        Background.setVisibility(View.VISIBLE);

                        TurnOn();

                    } else {

                        Background.setVisibility(View.INVISIBLE);

                        TurnOff();
                    }
                }
            });
        }

    }

    public void TurnOn(){

        // turn on the Flash light

        camera.setParameters(par1_on);

        if(android.os.Build.VERSION.SDK_INT >= 18)
        {
            camera.startPreview();
        }

        light = true;

    }

    public void TurnOff(){

        // turn off the Flash light

        camera.setParameters(par2_off);

        light = false;

    }

    @Override
    protected void onStop() {

        TurnOff();

        // release the camera
        camera.release();

        super.onStop();
    }



    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available in use or does not exist
            log("Couldn't get the Camera !!!!");
        }
        return c; // returns null if camera is unavailable
    }
}
