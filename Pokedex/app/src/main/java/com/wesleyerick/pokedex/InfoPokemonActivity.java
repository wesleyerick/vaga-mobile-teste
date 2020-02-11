package com.wesleyerick.pokedex;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.wesleyerick.pokedex.api.DataService;
import com.wesleyerick.pokedex.model.InfoPokemon;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InfoPokemonActivity extends AppCompatActivity {

    private TextView textId, textNome, textBaseExperience, textPeso, textAltura;
    private ImageView imagePokemon;
    private Retrofit retrofit;
    private Context context;

    private String pokemonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pokemon);

        textId = findViewById(R.id.textId);
        textNome = findViewById(R.id.textNome);
        textBaseExperience = findViewById(R.id.textBaseExperience);
        textPeso = findViewById(R.id.textPeso);
        textAltura = findViewById(R.id.textAltura);
        imagePokemon = findViewById(R.id.imagePokemon);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pokemonName = extras.getString("pokemonName");
        }

        textNome.setText(pokemonName);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        defineDados();
    }

    private void defineDados (){

        DataService dataService = retrofit.create(DataService.class);
        Call<InfoPokemon> call = dataService.getInfoPokemon(pokemonName);

        call.enqueue(new Callback<InfoPokemon>() {
            @Override
            public void onResponse(Call<InfoPokemon> call, Response<InfoPokemon> response) {
                if (response.isSuccessful()){
                    InfoPokemon infoPokemon = response.body();
                    textId.setText(infoPokemon.getId());
                    textBaseExperience.setText(infoPokemon.getBase_experience());

                    String altura = infoPokemon.getHeight();
                    StringBuilder alturaFormato = new StringBuilder(altura);
                    alturaFormato.insert(altura.length()-1, ".");

                    String peso = infoPokemon.getWeight();
                    StringBuilder pesoFormato = new StringBuilder(peso);
                    pesoFormato.insert(peso.length()-1, ".");

                    textAltura.setText("0" +alturaFormato);
                    textPeso.setText(pesoFormato);

                    ProgressBar progressBar;
                    progressBar = findViewById(R.id.progressBar);

                    int progress = Integer.parseInt(infoPokemon.getBase_experience());

                    progressBar.setProgress(progress);

                    String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ (infoPokemon.getId()) +".png";

                    Glide.with(InfoPokemonActivity.this)
                            .load(url)
                            .into(imagePokemon);



                }
            }

            @Override
            public void onFailure(Call<InfoPokemon> call, Throwable t) {

            }
        });



       // Log.i("POKEDEX", "Array: " + );
    }

}
