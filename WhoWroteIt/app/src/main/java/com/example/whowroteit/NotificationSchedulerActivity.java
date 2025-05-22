package com.example.whowroteit;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationSchedulerActivity extends BaseActivity {

    private JobScheduler mScheduler;
    private static final int JOB_ID = 0;
    private Switch mDeviceIdleSwitch;
    private Switch mDeviceChargingSwitch;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_job_service);
        setupBottomNavigation();

        mDeviceIdleSwitch = findViewById(R.id.idleSwitch);
        mDeviceChargingSwitch = findViewById(R.id.chargingSwitch);
        mSeekBar = findViewById(R.id.seekBar);
        final TextView seekBarProgress = findViewById(R.id.seekBarProgress);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 0){
                    seekBarProgress.setText(i + "s");
                }else{
                    seekBarProgress.setText("Not Set");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void scheduleJob(View view) {
        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        RadioGroup networkOptions = findViewById(R.id.networkOptions);
        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;

        if (networkOptions.getCheckedRadioButtonId() == R.id.noNetwork) {
            selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
        } else if (networkOptions.getCheckedRadioButtonId() == R.id.anyNetwork) {
            selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
        } else if (networkOptions.getCheckedRadioButtonId() == R.id.wifiNetwork) {
            selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
        }

        ComponentName serviceName = new ComponentName(getPackageName(), NotificationJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(selectedNetworkOption)
                .setRequiresDeviceIdle(mDeviceIdleSwitch.isChecked())
                .setRequiresCharging(mDeviceChargingSwitch.isChecked());

        int seekBarInteger = mSeekBar.getProgress();
        boolean seekBarSet = seekBarInteger > 0;
        if (seekBarSet){
            builder.setOverrideDeadline(seekBarInteger * 1000);
        }

        boolean constraintSet = selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE || mDeviceChargingSwitch.isChecked() ||
                mDeviceIdleSwitch.isChecked() || seekBarSet;
        if (constraintSet){
            JobInfo myJobInfo = builder.build();
            mScheduler.schedule(myJobInfo);
            Toast.makeText(this, "Job Scheduled, job will run when " + "the constraints are met.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Please set at least one constraint", Toast.LENGTH_SHORT).show();
        }
    }
    public void cancelJobs(View view) {
        if (mScheduler != null){
            mScheduler.cancelAll();
            mScheduler = null;
            Toast.makeText(this, "Jobs Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSelectedNavigationItem();
    }
}

