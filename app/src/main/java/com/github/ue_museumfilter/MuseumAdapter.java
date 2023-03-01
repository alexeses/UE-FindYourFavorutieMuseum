package com.github.ue_museumfilter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ue_museumfilter.utils.data.Museum;

import java.util.List;

public class MuseumAdapter extends RecyclerView.Adapter<MuseumAdapter.MuseumViewHolder> {

    private List<Museum> museums;

    public MuseumAdapter(List<Museum> museums) {
        this.museums = museums;
    }

    @NonNull
    @Override
    public MuseumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_musem, parent, false);
        return new MuseumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MuseumViewHolder holder, int position) {
        Museum museum = museums.get(position);
        holder.bind(museum);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);

                intent.putExtra("museum_title", museum.getTitle());
                intent.putExtra("museum_district", museum.getAddress().getDistrict());
                intent.putExtra("museum_area", museum.getAddress().getArea());
                intent.putExtra("museum_address", museum.getAddress().getStreetAddress());
                intent.putExtra("museum_description", museum.getOrganization().getOrganizationDesc());
                intent.putExtra("museum_schedule", museum.getOrganization().getSchedule());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return museums.size();
    }

    public static class MuseumViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMuseumName;

        public MuseumViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMuseumName = itemView.findViewById(R.id.tv_item_title);
        }

        public void bind(Museum museum) {
            tvMuseumName.setText(museum.getTitle());
        }
    }
}
