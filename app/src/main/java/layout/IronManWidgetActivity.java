package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.blackcoin.ironmanflashlight.R;


public class IronManWidgetActivity extends AppWidgetProvider {

    private static final String SYNC_CLICKED    = "automaticWidgetSyncButtonClick";
    boolean light = false;

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

            RemoteViews remoteViews;
            ComponentName lightWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.iron_man_widget_activity);
            lightWidget = new ComponentName(context, IronManWidgetActivity.class);

            if(!light) {
                remoteViews.setViewVisibility(R.id.appwidget_image2, 1);
                light = true;
            }
            else{

                remoteViews.setViewVisibility(R.id.appwidget_image2, 2);
                light = false;

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


    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

