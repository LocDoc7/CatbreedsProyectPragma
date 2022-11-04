package com.pragma.catbreeds.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pragma.catbreeds.Model.Gatos;
import com.pragma.catbreeds.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GatosAdapter extends RecyclerView.Adapter<GatosAdapter.GatosHolder> {

    List<Gatos> gatosList;
    Context context;
    onClick onClick;

    public GatosAdapter(List<Gatos> gatosList, Context context, onClick onClick) {
        this.gatosList = gatosList;
        this.context = context;
        this.onClick = onClick;
    }

    public void setFilteredList(List<Gatos> filteredList){
        this.gatosList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public GatosHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_cats,parent,false);
        return new GatosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GatosHolder holder, int position) {
        holder.tvEsperanzaVida.setText(String.valueOf(gatosList.get(position).getEsperanzaVidaGato()));
        holder.tvInteligencia.setText(String.valueOf(gatosList.get(position).getInteligenciaGato()));
        holder.tvPaisOrigen.setText(String.valueOf(gatosList.get(position).getPaisGato()));
        Glide.with(context).load(String.valueOf(gatosList.get(position).getImgGato())).into(holder.imgGato);
        holder.tvNombreRaza.setText(String.valueOf(gatosList.get(position).getRazaGato()));
        holder.tvMas.setOnClickListener(view -> {
            onClick.onClick(gatosList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return gatosList.size();
    }

    public interface onClick{
        void onClick(Gatos gatos);
    }

    public class GatosHolder extends RecyclerView.ViewHolder {
        TextView tvNombreRaza,tvMas,tvPaisOrigen,tvInteligencia,tvEsperanzaVida;
        ImageView imgGato;

        public GatosHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgGato = itemView.findViewById(R.id.imgGato);
            tvNombreRaza = itemView.findViewById(R.id.tvNombreRaza);
            tvMas = itemView.findViewById(R.id.tvMas);
            tvPaisOrigen = itemView.findViewById(R.id.tv_pais);
            tvInteligencia = itemView.findViewById(R.id.tv_inteligencia);
            tvEsperanzaVida = itemView.findViewById(R.id.tv_esperanzavida);
        }
    }
}
