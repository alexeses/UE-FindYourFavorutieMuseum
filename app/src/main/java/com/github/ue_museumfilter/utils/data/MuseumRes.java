package com.github.ue_museumfilter.utils.data;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MuseumRes implements Serializable {

    @SerializedName("@context")
    @Expose
    private Context context;
    @SerializedName("@graph")
    @Expose
    private List<Museum> museum;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Museum> getMuseums() {
        return museum;
    }

    public void setMuseum(List<Museum> museum) {
        this.museum = museum;
    }

    @Override
    public String toString() {
        return "MuseumRes{" +
                "context=" + context +
                ", museum=" + museum +
                '}';
    }
}