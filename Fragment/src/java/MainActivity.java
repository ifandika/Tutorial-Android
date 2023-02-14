
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.view.View;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import com.bfragment.main.fragments.HomeFragment;
import com.bfragment.main.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    Button buttonToHomeFragment, buttonToProfileFragment;
    
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Memuat/menampilkan fragment.
     * @param fragment		class yg dituju.
     */
    private void loadFragment(Fragment fragment) {
        try {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameLayout_fragmentContainerView, fragment, null);
            ft.commit();
            // Versi singkat
            // getSupportFragmentManager()
            //     .beginTransaction()
            //     .replace() atau .add()
            //     .commit()
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        
        // Tetapkan komponen.
        buttonToHomeFragment = findViewById(R.id.button_toHomeFragment);
        buttonToProfileFragment = findViewById(R.id.button_toProfileFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        // Konfigurasi tombol di klik
        buttonToHomeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Clicked Home Fragment");
                loadFragment(new HomeFragment());
            }
        });
        
        buttonToProfileFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Clicked Profile Fragment");
                loadFragment(new ProfileFragment());
            }
        });
    }
    
}