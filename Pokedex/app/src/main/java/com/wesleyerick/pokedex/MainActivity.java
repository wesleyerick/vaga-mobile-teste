package com.wesleyerick.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.wesleyerick.pokedex.adapter.Adapter;
import com.wesleyerick.pokedex.adapter.RecyclerItemClickListener;
import com.wesleyerick.pokedex.api.DataService;
import com.wesleyerick.pokedex.model.Pokemon;
import com.wesleyerick.pokedex.model.PokemonResponsivo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {


    private ArrayList<Pokemon> listaPokemon = new ArrayList<>();

    private RecyclerView recyclerView;
    private Adapter adapter;
    private int offset;
    private Boolean prontoParaCarregar;
    private EditText filtro;

    public Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        prontoParaCarregar = true;
        offset = 0;
        listarPokemons(offset);

        recyclerView = findViewById(R.id.recyclerView);
        filtro = findViewById(R.id.plainTextPesquisa);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        adapter = new Adapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter( adapter );

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @SuppressLint("ClickableViewAccessibility")
                            @Override
                            public void onItemClick(final View v, final int position) {

                                TextView name = v.findViewById(R.id.textNome);
                                String pokemonName = name.getText().toString();

                                Intent i = new Intent(MainActivity.this, InfoPokemonActivity.class);
                                i.putExtra("pokemonName", pokemonName);
                                startActivity(i);





                                Log.i("POKEDEX", "Nome: " + pokemonName);


                            }


                            @Override
                            public void onLongItemClick(View view, final int position) {


                            }

                        }

                )

        );


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int positionView = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                    if (prontoParaCarregar){
                        if ((visibleItemCount + positionView ) >= totalItemCount){

                            prontoParaCarregar = false;
                            offset += 20;
                            listarPokemons(offset);

                        }
                    }
                }
            }
        });

        filtro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    private void listarPokemons(int offset){

        DataService service = retrofit.create(DataService.class);

         Call<PokemonResponsivo> call = service.getPokemonResponsivo(20, offset);

        call.enqueue(new Callback<PokemonResponsivo>() {
            @Override
            public void onResponse(Call<PokemonResponsivo> call, Response<PokemonResponsivo> response) {
                prontoParaCarregar = true;
                if (response.isSuccessful()){

                   PokemonResponsivo pokemonResponsivo = response.body();
                   final ArrayList<Pokemon> listaPokemon = pokemonResponsivo.getResults();

                    adapter.adicionarListaPokemon(listaPokemon);


                }else{
                    Log.e("POKEDEX", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponsivo> call, Throwable t) {
                prontoParaCarregar = true;
                Log.e("POKEDEX", "onFailure: " + t.getMessage());
            }
        });

    }

    public void FiltrarPokemons(View view){


    }


    public void OrdenarAlfabeto(View view){


    }



}
