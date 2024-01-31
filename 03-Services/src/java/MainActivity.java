
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.app.ActivityManager;
import android.content.Context;
import androidx.core.app.ActivityManagerCompat;
import android.widget.Toast;
import androidx.core.content.ContextCompat;

/**
 * Service/Layanan adalah pekerjaan yg berjalan di latar belakang.
 * Terbagi mjd 2:
 *     - Background
 *     - Foreground
 */
public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            
            Button buttonStartBackgroundservice = findViewById(R.id.button_startBackgroundservice),
                   buttonStartForegroundservice = findViewById(R.id.button_startForegroundservice),
                   buttonStopBackgroundservice = findViewById(R.id.button_stopBackgroundservice),
                   buttonStopForegroundservice = findViewById(R.id.button_stopForegroundservice);
                   
            buttonStartBackgroundservice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!backgroundAlive) {
                        startService(new Intent(getBaseContext(), IsBackgroundservice.class));
                        backgroundAlive = true;
                        Toast.makeText(MainActivity.this, "Backgroundservice started...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Backgroundservice is started...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            
            buttonStartForegroundservice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(!foregroundAlive) {
                       ContextCompat.startForegroundService(getBaseContext(), new Intent(MainActivity.this, IsForegroundservice.class));
                       foregroundAlive = true;
                       Toast.makeText(MainActivity.this, "Foregroundservice started...", Toast.LENGTH_SHORT).show();
                   } else {
                       Toast.makeText(MainActivity.this, "Foregroundservice is started...", Toast.LENGTH_SHORT).show();
                   }
                }
            });
            
            buttonStopBackgroundservice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(backgroundAlive) {
                        Toast.makeText(MainActivity.this, "Backgroundservice is not started...", Toast.LENGTH_SHORT).show();
                    } else {
                        stopService(new Intent(getBaseContext(), IsBackgroundservice.class));
                        backgroundAlive = false;
                    }
                }
            });
            
            buttonStopForegroundservice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(backgroundAlive) {
                        Toast.makeText(MainActivity.this, "Foregroundservice is not started...", Toast.LENGTH_SHORT).show();
                    } else {
                        stopService(new Intent(getBaseContext(), IsForegroundservice.class));
                        foregroundAlive = false;
                    }
                }
            });
            
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            stopService(new Intent(getBaseContext(), IsBackgroundservice.class));
            stopService(new Intent(getBaseContext(), IsForegroundservice.class));
            
        }
    }
    
}