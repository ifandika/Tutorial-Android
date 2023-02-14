
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private final String tag = "MainActivity";
    
    // Saat apk di klik/dijalankan tapi belum interaksi dengan pengguna.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(tag, "callback onCreate()");
        setContentView(R.layout.activity_main);
		
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
    }
    
    // Saat apk sudah jalan dan terdapat tampilan.
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(tag, "callback onStart()");
    }
    
    // Saat apk berinteraksi dengan pengguna.
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(tag, "callback onResume()");
    }
    
    // Ketika beralih activity/ke program lain.
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(tag, "callback onPause()");
    }
    
    // Dipanggil ketika apk di minimize, diletakan di latar belankang.
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(tag, "callback onStop()");
    }
    
    // Jika benar² sudah tidak diperlukan/apk di tutup/OS perlu memori sehingga
    // dihapus dari memori.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(tag, "callback onDestroy()");
    }
}