package com.github.ue_museumfilter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.ue_museumfilter.R;
import com.github.ue_museumfilter.utils.data.Museum;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsFragment extends Fragment {

    private GoogleMap googleMap;
    private List<Museum> museums;
    private TextView tv_title;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsFragment.this.googleMap = googleMap;

            tv_title = getActivity().findViewById(R.id.tv_title);

            LatLng madrid = new LatLng(40.416775, -3.703790);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(madrid, 10));
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


            if (museums != null) {
                for (Museum museum : museums) {
                    System.out.println(museum.getTitle());
                    LatLng location = new LatLng(museum.getLocation().getLatitude(), museum.getLocation().getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(location).title(museum.getTitle()));
                }
            }

        }
    };

    public void setMuseumList(List<Museum> museums, String distrito) {
        this.museums = museums;

        // Si el distrito es null, es que se ha pulsado en el botón de "Todos los distritos"
        if (distrito == null) {
            tv_title.setText("Todos los distritos");
        } else {
            tv_title.setText(distrito);
        }

        if (googleMap != null) {
            googleMap.clear();
            if (museums != null) {
                for (Museum museum : museums) {
                    LatLng location = new LatLng(museum.getLocation().getLatitude(), museum.getLocation().getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(location).title(museum.getTitle()));
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}
