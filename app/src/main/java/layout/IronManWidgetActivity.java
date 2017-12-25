package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.blackcoin.ironmanflashlight.MainActivity;
import com.blackcoin.ironmanflashlight.R;


public class IronManWidgetActivity extends AppWidgetProvider {

    private static final String SYNC_CLICKED = "automaticWidgetSyncButtonClick";

    public static boolean WidgetLight;

    public static RemoteViews remoteViews;

    Camera.Parameters par1_on;

    Camera.Parameters par2_off;

    public  void log(String l){

        Log.i("AMP : ", l);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews;
        ComponentName lightWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.iron_man_widget_activity);
        lightWidget = new ComponentName(context, IronManWidgetActivity.class);

        remoteViews.setOnClickPendingIntent(R.id.appwidget_image, getPendingSelfIntent(context, SYNC_CLICKED));
        appWidgetManager.updateAppWidget(lightWidget, remoteViews);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);


        if (SYNC_CLICKED.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            ComponentName lightWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.iron_man_widget_activity);
            lightWidget = new ComponentName(context, IronManWidgetActivity.class);



            if(!WidgetLight) {
                remoteViews.setViewVisibility(R.id.appwidget_image2, View.VISIBLE);

                MainActivity.camera.release();

                MainActivity.camera = getCameraInstance();

                par1_on = MainActivity.camera.getParameters();

                par1_on.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

                MainActivity.camera.setParameters(par1_on);

                if(android.os.Build.VERSION.SDK_INT >= 18)
                {
                    MainActivity.camera.startPreview();
                }

                WidgetLight = true;

            } else {

                remoteViews.setViewVisibility(R.id.appwidget_image2, View.INVISIBLE);

                par2_off = MainActivity.camera.getParameters();

                par2_off.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

                MainActivity.camera.setParameters(par2_off);

                MainActivity.camera.release();

                WidgetLight = false;

            }

            appWidgetManager.updateAppWidget(lightWidget, remoteViews);

        }

    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }





    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

        RemoteViews remoteViews;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.iron_man_widget_activity);

        remoteViews.setViewVisibility(R.id.appwidget_image2, View.INVISIBLE);

        MainActivity.camera.release();

        WidgetLight = false;

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled

        RemoteViews remoteViews;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.iron_man_widget_activity);

        remoteViews.setViewVisibility(R.id.appwidget_image2, View.INVISIBLE);

        MainActivity.camera.release();

        WidgetLight = false;
    }


    public  Camera getCameraInstance(){
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

