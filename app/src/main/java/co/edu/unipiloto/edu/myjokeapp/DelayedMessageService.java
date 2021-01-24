package co.edu.unipiloto.edu.myjokeapp;

import android.app.IntentService;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import android.app.PendingIntent;
import android.app.NotificationManager;




public class DelayedMessageService extends IntentService {

    public static  final String EXTRA_MESSAGE = "message";
    public static final int NOTIFICATION_ID = 5453;

    public DelayedMessageService() {
        super("DelayedMessageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this){
            try {
                wait(10000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        String text = intent.getStringExtra(EXTRA_MESSAGE);
        showText(text);
    }

    private void showText(final String text){

        //creacion de la notificacion builder

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(getString(R.string.question))
                .setContentText(text).setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{0,1000}).setAutoCancel(true);

        //Creacion de la accion
        Intent actionIntent = new Intent(this, MainActivity.class);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(DelayedMessageService.this,
                                                                    0,
                                                                    actionIntent,
                                                                    PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(actionPendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

}
