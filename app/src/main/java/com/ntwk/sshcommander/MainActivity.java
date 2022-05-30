package com.ntwk.sshcommander;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ntwk.sshcommander.ui.adapters.TabAdapter;
import com.ntwk.sshcommander.ui.entities.CommandEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager = findViewById(R.id.VP_viewPager);
        TabLayout tabLayout = findViewById(R.id.TL_tabs);

        viewPager.setAdapter(new TabAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 1) {
                        tab.setText(getString(R.string.terminal));
                    } else {
                        tab.setText(getString(R.string.commands));
                    }
                }
        ).attach();
    }
}