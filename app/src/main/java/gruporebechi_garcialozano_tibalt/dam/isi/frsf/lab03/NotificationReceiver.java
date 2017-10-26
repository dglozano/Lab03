package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by diegogarcialozano on 17/10/17.
 */

public class NotificationReceiver extends BroadcastReceiver {

    private static final String tag = "NotificationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        String title = intent.getStringExtra("title");
        this.sendNotification(context, message, title);
    }

    private void sendNotification(Context ctx, String message, String title){
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nm = (NotificationManager) ctx.getSystemService(ns);
        int icon = android.R.drawable.sym_def_app_icon;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        PendingIntent pi = PendingIntent.getActivity(ctx, 0, intent, 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx.getApplicationContext())
                        .setSmallIcon(icon)
                        .setContentIntent(pi)
                        .setContentTitle(title)
                        .setContentText(message);
        nm.notify(1, mBuilder.build());
    }
}
