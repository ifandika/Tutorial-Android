
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.net.Uri;
import android.content.ComponentName;

public class MainActivity extends AppCompatActivity {
    private Button buttonNext;
    private final int LAUNCH_CODE_PROFILE_ACTIVITY = 1;
    
    /**
     * Berisi jenis² konfigurasi Intent
     * @setClass = Class ini ke class mana/tujuan ?
     * @setAction = Jenis tindakan yg dilakukan.
     * @setComponent = 
     * @setData = Untuk data alamat/data yg akan di ekseskusi.
     * @setType = Tipe adalah jenis dari data, seperti gamabar/img, video/mp4 dll.
     * @setDataAndType = Gabungan setData dan setType
     * @setFlags = 
     * @addCategory = Jenis kategori Intent.
     */
    private void isIntent() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), <next_activity>.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setClassName("com.projek.main", "com.projek.main.<next_activity>");
        intent.setComponent(new ComponentName("com.projek.main.MainActivity", "com.projek.main.<next_activity>"));
        intent.setData(Uri.parse("https://developer.android.com"));
        intent.setDataAndType(Uri.parse("https://example.com/video.mp4"), "video/**");
        intent.setType();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setPackage("com.projek.main");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.putExtra("data", "Is data");
        Bundle bundle = new Bundle();
        bundle.putString("data", "Ini data");
        intent.putExtras(bundle);
    }
    
    // Intent bebas/tidak mengarah, 
    private void isImplicitIntents() {
        Intent implicit = new Intent();
        implicit.setAction(Intent.ACTION_VIEW);
        // implicit.setData(Uri.parse("https://www.unrealengine.com"));
        startActivity(implicit);
    }
    
    // Intent mengarah/tertuju.
    private void isExplicitIntents() {
        // startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        startActivity(new Intent()
            .setAction(Intent.ACTION_MAIN)
            .setComponent(new ComponentName(
                "com.bintent.main.MainActivity",
                "com.bintent.main.ProfileActivity")));
    }
    
    // Pergi ke activity.
    private void toActivity(Class<?> name) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, name);
        startActivity(intent);
    }
    
    // Pergi ke activity dengan membawa data dengan dibungkus objek Bundle.
    private void toActivityAndSendDataUsingBundle(Class<?> name, String data) {
        Intent intent = new Intent(MainActivity.this, name);
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    
    // Pergi ke activity dengan membawa data.
    private void toActivityAndSendData(Class<?> name, String data) {
        Intent intent = new Intent(MainActivity.this, name);
        intent.putExtra("data", data);
        startActivity(intent);
    }
    
    // Pergi ke activity dengan meminta kembalian.
    private void toActivityWithResult() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivityForResult(intent, LAUNCH_CODE_PROFILE_ACTIVITY);
    }
    
    // Fungsi menagkap hasil untuk fungsi startActivityForResult. @requestCode kode yg kita minta,
    // @resultCode kode yg dikembalikan, @data Intent yg dikembalikan untuk memberikan hasil.
    // Jika kode yg diminta = kode hasil maka cek kode hasil = RESULT_OK/RESULT_CANCELED/dll.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if(requestCode == LAUNCH_CODE_PROFILE_ACTIVITY) {
            switch(resultCode) {
                case RESULT_OK:
                    String result = data.getStringExtra("result");
                    generateToast(result);
                    break;
                case RESULT_CANCELED:
                    ...
                    break;
            }
        }
    }
    
    public void generateToast(String message) {
        if(!message.isEmpty()) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        
        buttonNext = findViewById(R.id.button_next);
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // toActivity(ProfileActivity.class);
                // toActivityWithResult();
                // toActivityAndSendData(ProfileActivity.class, "Ini surat cinta dari kipli");
                // toActivityAndSendDataUsingBundle(ProfileActivity.class, "Keep learn and Happy coding");
                isImplicitIntents();
                // isExplicitIntents();
            }
        });
    }
    
}