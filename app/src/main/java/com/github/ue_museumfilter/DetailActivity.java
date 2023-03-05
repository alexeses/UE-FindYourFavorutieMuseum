package com.github.ue_museumfilter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ue_museumfilter.utils.APIRestService;
import com.github.ue_museumfilter.utils.RetrofitClient;
import com.github.ue_museumfilter.utils.data.Museum;
import com.github.ue_museumfilter.utils.data.MuseumRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity {

    private TextView tvMuseumName;
    private TextView tvMuseumDistrict;
    private TextView tvMuseumArea;
    private TextView tvMuseumAddress;
    private TextView tvMuseumDescription;
    private TextView tvMuseumSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent != null) {
            String id = intent.getStringExtra("museum_id");
            loadMuseum(id);
        }

        tvMuseumName = findViewById(R.id.tv_museum_name);
        tvMuseumDistrict = findViewById(R.id.tv_museum_district);
        tvMuseumArea = findViewById(R.id.tv_museum_area);
        tvMuseumAddress = findViewById(R.id.tv_museum_address);
        tvMuseumDescription = findViewById(R.id.tv_museum_description);
        tvMuseumSchedule = findViewById(R.id.tv_museum_schedule);
    }

    private void loadMuseum(String id) {
        Retrofit retrofit = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService apiRestService = retrofit.create(APIRestService.class);
        Call<MuseumRes> call = apiRestService.getMuseumID(id);

        call.enqueue(new retrofit2.Callback<MuseumRes>() {
            @Override
            public void onResponse(Call<MuseumRes> call, Response<MuseumRes> response) {
                if (response.isSuccessful()) {
                    MuseumRes museumRes = response.body();

                    List<Museum> museums = museumRes.getMuseums();
                    Museum museum = museums.get(0);

                    tvMuseumName.setText(museum.getTitle());

                    String district = museum.getAddress().getDistrict().getId();
                    tvMuseumDistrict.setText(district.substring(district.lastIndexOf("/") + 1));

                    String area = museum.getAddress().getArea().getId();
                    tvMuseumArea.setText(area.substring(area.lastIndexOf("/") + 1));

                    tvMuseumAddress.setText(museum.getAddress().getStreetAddress() + ", "
                            + museum.getAddress().getPostalCode()
                            + " " + museum.getAddress().getLocality());

                    tvMuseumDescription.setText(museum.getOrganization().getOrganizationDesc());
                    tvMuseumSchedule.setText(museum.getOrganization().getSchedule());

                    if (museumRes.getMuseums().isEmpty()) {
                        Toast.makeText(DetailActivity.this,
                                "No hay museos en este distrito", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MuseumRes> call, Throwable t) {
             System.out.println("ERROR: " + t.getMessage());
            }
        });
    }
}
