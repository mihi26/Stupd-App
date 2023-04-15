package com.ptit.androidptit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ptit.androidptit.Utils.Database;
import com.ptit.androidptit.fragments.InfoFragment;
import com.ptit.androidptit.fragments.ListItemFragment;
import com.ptit.androidptit.fragments.StatsFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.nav_list:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListItemFragment()).commit();
                    return true;
                case R.id.nav_information:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InfoFragment()).commit();
                    return true;
                case R.id.nav_statistic:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StatsFragment()).commit();
                    return true;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.nav_list);
    }


}