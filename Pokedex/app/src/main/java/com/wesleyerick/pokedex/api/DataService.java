package com.wesleyerick.pokedex.api;


import com.wesleyerick.pokedex.model.InfoPokemon;
import com.wesleyerick.pokedex.model.Pokemon;
import com.wesleyerick.pokedex.model.PokemonResponsivo;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface DataService {

    @GET("pokemon")
    Call<PokemonResponsivo> getPokemonResponsivo(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{name}/")
    Call<InfoPokemon> getInfoPokemon(@Path("name") String name);
}
