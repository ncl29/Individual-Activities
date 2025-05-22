package com.example.whowroteit;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                if (!(this instanceof MainActivity)) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                return true;
            } else if (id == R.id.nav_notification) {
                if (!(this instanceof NotificationActivity)) {
                    Intent intent = new Intent(this, NotificationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                return true;
            } else if (id == R.id.nav_alarm) {
                if (!(this instanceof AlarmActivity)) {
                    Intent intent = new Intent(this, AlarmActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                return true;
            } else if (id == R.id.nav_job) {
                if (!(this instanceof NotificationSchedulerActivity)) {
                    Intent intent = new Intent(this, NotificationSchedulerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                return true;
            }
            return false;
        });

        // Update selected navigation item
        if (this instanceof MainActivity) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        } else if (this instanceof NotificationActivity) {
            bottomNavigationView.setSelectedItemId(R.id.nav_notification);
        } else if (this instanceof AlarmActivity) {
            bottomNavigationView.setSelectedItemId(R.id.nav_alarm);
        } else if (this instanceof NotificationSchedulerActivity) {
            bottomNavigationView.setSelectedItemId(R.id.nav_job);
        }
    }

    protected void selectCurrentNavItem(int itemId) {
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(null);
            bottomNavigationView.setSelectedItemId(itemId);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    if (!(this instanceof MainActivity)) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                    return true;
                } else if (id == R.id.nav_notification) {
                    if (!(this instanceof NotificationActivity)) {
                        Intent intent = new Intent(this, NotificationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                    return true;
                } else if (id == R.id.nav_alarm) {
                    if (!(this instanceof AlarmActivity)) {
                        Intent intent = new Intent(this, AlarmActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                    return true;
                } else if (id == R.id.nav_job) {
                    if (!(this instanceof NotificationSchedulerActivity)) {
                        Intent intent = new Intent(this, NotificationSchedulerActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                    return true;
                }
                return false;
            });
        }
    }

    protected void updateSelectedNavigationItem() {
        if (bottomNavigationView != null) {
            if (this instanceof MainActivity) {
                bottomNavigationView.setSelectedItemId(R.id.nav_home);
            } else if (this instanceof NotificationActivity) {
                bottomNavigationView.setSelectedItemId(R.id.nav_notification);
            } else if (this instanceof AlarmActivity) {
                bottomNavigationView.setSelectedItemId(R.id.nav_alarm);
            } else if (this instanceof NotificationSchedulerActivity) {
                bottomNavigationView.setSelectedItemId(R.id.nav_job);
            }
        }
    }

}
