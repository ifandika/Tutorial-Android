
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.app.PendingIntent;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import androidx.core.content.ContextCompat;
import android.widget.Button;
import androidx.core.text.util.FindAddress;
import androidx.core.app.NotificationCompat;

public class IsForegroundService extends Service {
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notifIntent = new Intent(IsForegroundservice.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
            IsForegroundservice.this, 0, notifIntent, 0
        );
            
        Notification notif = new NotificationCompat.Builder(IsForegroundservice.this, Applications.CHANNEL_ID)
            .setContentTitle("Example Service")
            .setContentText("Is Foreground Service starting...")
            .setContentIntent(pendingIntent)
            .build();
        startForeground(1, notif);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent p1) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
}