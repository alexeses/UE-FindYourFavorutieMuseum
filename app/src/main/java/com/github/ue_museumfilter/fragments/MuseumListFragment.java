package com.github.ue_museumfilter.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ue_museumfilter.MuseumAdapter;
import com.github.ue_museumfilter.R;
import com.github.ue_museumfilter.utils.data.Museum;

import java.util.List;

public class MuseumListFragment extends Fragment {
    private RecyclerView recyclerView;

    public MuseumListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_museum_list, container, false);

        recyclerView = view.findViewById(R.id.rv_museos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public void setMuseumList(List<Museum> museums) {
        MuseumAdapter adapter = new MuseumAdapter(museums);
        recyclerView.setAdapter(adapter);
    }



}
