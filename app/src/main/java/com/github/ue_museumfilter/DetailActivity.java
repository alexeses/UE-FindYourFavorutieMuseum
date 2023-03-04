package com.github.ue_museumfilter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView tvMuseumDistrict;
    private TextView tvMuseumArea;
    private TextView tvMuseumAddress;
    private TextView tvMuseumDescription;
    private TextView tvMuseumSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvMuseumName = findViewById(R.id.tv_museum_name);
        tvMuseumDistrict = findViewById(R.id.tv_museum_district);
        tvMuseumArea = findViewById(R.id.tv_museum_area);
        tvMuseumAddress = findViewById(R.id.tv_museum_address);
        tvMuseumDescription = findViewById(R.id.tv_museum_description);
        tvMuseumSchedule = findViewById(R.id.tv_museum_schedule);

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("museum_title");
            String district = intent.getStringExtra("museum_district");
            String area = intent.getStringExtra("museum_area");
            String address = intent.getStringExtra("museum_address");
            String description = intent.getStringExtra("museum_description");
            String schedule = intent.getStringExtra("museum_schedule");

            tvMuseumName.setText(name);
            tvMuseumDistrict.setText(district);
            tvMuseumArea.setText(area);
            tvMuseumAddress.setText(address);
            tvMuseumDescription.setText(description);
            tvMuseumSchedule.setText(schedule);
        }
    }
}