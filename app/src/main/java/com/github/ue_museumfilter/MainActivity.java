package com.github.ue_museumfilter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.ue_museumfilter.dialogs.DialogFilter;
import com.github.ue_museumfilter.fragments.MapsFragment;
import com.github.ue_museumfilter.fragments.MuseumListFragment;
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
    MuseumListFragment museumListFragment;
    MapsFragment mapFragment;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        museumListFragment = new MuseumListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, museumListFragment).commit();
        currentFragment = museumListFragment;

        btn_open_dialog = findViewById(R.id.btn_buscar);
        btn_aplicar_filtro = findViewById(R.id.btn_aplicar_filtro);


        btn_open_dialog.setOnClickListener(v -> {
            cleanFilter();

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

                        if (museumRes == null) {
                            Toast.makeText(MainActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<Museum> museums = museumRes.getMuseums();

                        if (currentFragment == museumListFragment) {
                            museumListFragment.setMuseumList(museums);
                        } else if (currentFragment == mapFragment) {
                            mapFragment.setMuseumList(museums, distrito);
                        }

                    }
                }

                @Override
                public void onFailure(Call<MuseumRes> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        });
    }

    private void cleanFilter() {
        distrito = null;

        if (currentFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
        }

        if (currentFragment == museumListFragment) {
            museumListFragment = new MuseumListFragment();
            btn_aplicar_filtro.setText(R.string.btn_consult_list);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, museumListFragment).commit();
            currentFragment = museumListFragment;
        } else {
            mapFragment = new MapsFragment();
            btn_aplicar_filtro.setText(R.string.btn_consult_map);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mapFragment).commit();
            currentFragment = mapFragment;
        }
    }

    @Override
    public void onFilterSelected(String distrito) {
        this.distrito = distrito;
        System.out.println("Filter text: " + distrito);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
                currentFragment = museumListFragment;

                cleanFilter();
                Toast.makeText(this, "Mostrando lista", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_map:
                currentFragment = mapFragment;

                cleanFilter();
                Toast.makeText(this, "Mostrando mapa", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
