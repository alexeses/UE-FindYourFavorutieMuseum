package com.github.ue_museumfilter.utils;

import com.github.ue_museumfilter.utils.data.MuseumRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIRestService {
    public static final String BASE_URL = "https://datos.madrid.es/egob/catalogo/";

    @GET("201132-0-museos.json")
    Call<MuseumRes> getMuseums(
            @Query("distrito_nombre") String district
    );
}
