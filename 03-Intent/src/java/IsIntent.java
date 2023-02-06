
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
	/**
	 * @var buttonImplicit = Kirim intent bebas.
	 * @var buttonExplicit = Kirim intent mengarah.
	 * @var buttonSendData = Kirim intent dengan membawa data.
	 * @var editTextInputData = Input data yg akan dibawa.
	 * @var textViewResult = Menampilkan data yg dibawa.
	 */
    Button buttonImplicit, buttonExplicit, buttonSendData;
    EditText editTextInputData;
    TextView textViewResult;
    
    private void initComponents() {
        buttonImplicit = findViewById(R.id.<nama_id>);
        buttonExplicit = findViewById(R.id.<nama_id>);
        buttonSendData = findViewById(R.id.<nama_id>);
        editTextInputData = findViewById(R.id.<nama_id>);
        textViewResult = findViewById(R.id.<nama_id>);
    }
    
    private void implicitIntentActionSend() {
        Intent implicit = new Intent();
        implicit.setAction(Intent.ACTION_SEND);
        implicit.setType("text/plain");
        implicit.putExtra(Intent.EXTRA_TEXT, "Is Implicit Intent");
        //startActivity(implicit);
        Intent appChooser = Intent.createChooser(implicit, "Pilih salah satu");
        startActivity(appChooser);
    }
    
    // Menuju website tertentu dengan Intent
    private void explicitIntentViewWebsite(String url) {
        startActivity(new Intent(
            Intent.ACTION_VIEW, Uri.parse(url)));
    }
    
    private void isAppChooser() {
        Intent base = new Intent(Intent.ACTION_VIEW);
        base.setData(Uri.parse("https://github.com"));
        Intent appChooser = Intent.createChooser(base, "Pilih salah satu");
        startActivity(appChooser);
    }
    
    private void sendEmail() {
        startActivity(new Intent()
            .setAction(Intent.ACTION_SEND)
            .setType("text/plain")
            /*.setType(HTTP.PLAIN_TEXT_TYPE)*/
            .putExtra(Intent.EXTRA_EMAIL, new String[] {"maul.ifandika@gmail.com"})
            .putExtra(Intent.EXTRA_SUBJECT, "Is Subject")
            .putExtra(Intent.EXTRA_TEXT, "Is Text")
            .putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment")));
    }
    
    private void toSettingsActivity() {
        Intent explicit = new Intent(getApplicationContext(), SettingsActivity.class);
        explicit.setData(Uri.parse("Welcome Real Words"));
        startActivity(explicit);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        initComponents();
        
        buttonImplicit.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		
        	}
        });
        
        buttonExplicit.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		
        	}
        });
        
        buttonSendData.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent data = new Intent(MainActivity.this, SettingsActivity.class);
        		data.putExtra("name", "Joni");
        		startActivity(data);
        	}
        });
    }
}