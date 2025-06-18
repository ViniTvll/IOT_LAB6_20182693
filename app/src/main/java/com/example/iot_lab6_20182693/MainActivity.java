package com.example.iot_lab6_20182693;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.example.iot_lab6_20182693.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Default fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new Linea1Fragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();
            if (id == R.id.nav_linea1) {
                fragment = new Linea1Fragment();
            } else if (id == R.id.nav_limapass) {
                fragment = new LimaPassFragment();
            } else if (id == R.id.nav_resumen) {
                fragment = new ResumenFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment).commit();
                return true;
            }
            return false;
        });
    }
}
