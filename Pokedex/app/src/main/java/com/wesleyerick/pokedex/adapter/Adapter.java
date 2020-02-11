package com.wesleyerick.pokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wesleyerick.pokedex.R;
import com.wesleyerick.pokedex.model.Pokemon;


import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private ArrayList<Pokemon> listaPokemons;
    private Context context;

    public Adapter(Context context) {
        this.context = context;
        listaPokemons = new ArrayList<>();

    }

    public void adicionarListaPokemon(ArrayList<Pokemon> listaPokemon) {
        listaPokemons.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public ImageView image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textNome);
            image = itemView.findViewById(R.id.imagePokemon);


        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View pokemonLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_detalhe, parent, false);

        return new MyViewHolder(pokemonLista);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Pokemon pokemon = listaPokemons.get( position );

        holder.name.setText(pokemon.getName());
        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ pokemon.getNumber() +".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);



    }

    @Override
    public int getItemCount() {
        return listaPokemons == null ? 0 : listaPokemons.size();
    }

}


