package com.github.ue_museumfilter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.github.ue_museumfilter.utils.APIRestService;
import com.github.ue_museumfilter.utils.RetrofitClient;
import com.github.ue_museumfilter.utils.data.Museum;
import com.github.ue_museumfilter.utils.data.MuseumRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements DialogFilter.OnFilterSelectedListener {

    Button btn_open_dialog;
    Button btn_aplicar_filtro;
    String distrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_open_dialog = findViewById(R.id.btn_buscar);
        btn_aplicar_filtro = findViewById(R.id.btn_aplicar_filtro);

        btn_open_dialog.setOnClickListener(v -> {
            DialogFilter dialogFilter = new DialogFilter();
            dialogFilter.show(getSupportFragmentManager(), "dialogFilter");
        });

        btn_aplicar_filtro.setOnClickListener(v -> {
            Retrofit retrofit = RetrofitClient.getClient(APIRestService.BASE_URL);
            APIRestService apiRestService = retrofit.create(APIRestService.class);
            Call<MuseumRes> call = apiRestService.getMuseums(distrito);

            call.enqueue(new retrofit2.Callback<MuseumRes>() {
                @Override
                public void onResponse(Call<MuseumRes> call, Response<MuseumRes> response) {
                    if (response.isSuccessful()) {
                        MuseumRes museumRes = response.body();
                        List<Museum> museums = museumRes.getMuseums();
                        MuseumAdapter adapter = new MuseumAdapter(museums);
                        RecyclerView recyclerView = findViewById(R.id.rv_museos);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    }
                }


                @Override
                public void onFailure(Call<MuseumRes> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }

        });
        });
    }

    @Override
    public void onFilterSelected(String distrito) {
        this.distrito = distrito;
        System.out.println("Filter text: " + distrito);
    }
}