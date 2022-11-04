package com.pragma.catbreeds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetalleActivity extends AppCompatActivity {

    TextView tvDescripcionD,tvTemperamentoD,tvPaisOrigenD,tvInteligenciD,tvAdaptabilidadD,tvEsperanzaD,tvNivelAfectoD,tvNivelEnergiaD,tvNombreRazaD;
    ImageView imgGatoD,btnAtras;
    String descripcionD,esperanzaD,imagenD,paisD,razaD,temperamentoD;
    int adaptibilidadD,inteligenciaD,afectoD,energiaD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Bundle parametros = this.getIntent().getExtras();
        tvAdaptabilidadD = findViewById(R.id.tv_AdaptabilidadDesc);
        tvDescripcionD = findViewById(R.id.tv_DescripcionDesc);
        tvTemperamentoD = findViewById(R.id.tv_TemperamentoDesc);
        tvPaisOrigenD = findViewById(R.id.tv_PaisOrigenDesc);
        tvInteligenciD = findViewById(R.id.tv_InteligenciaDesc);
        tvEsperanzaD = findViewById(R.id.tv_EsperanzaVidaDesc);
        tvNivelAfectoD = findViewById(R.id.tv_NivelAfectoDesc);
        tvNivelEnergiaD = findViewById(R.id.tv_NivelEnergiaDesc);
        tvNombreRazaD = findViewById(R.id.tvNombreRazaDetalle);
        imgGatoD = findViewById(R.id.imgGatoDetalle);
        btnAtras = findViewById(R.id.btnAtras);

        descripcionD = parametros.getString("descripcion");
        esperanzaD = parametros.getString("esperanza");
        imagenD = parametros.getString("imagen");
        paisD = parametros.getString("pais");
        razaD = parametros.getString("raza");
        temperamentoD = parametros.getString("temperamento");
        adaptibilidadD = parametros.getInt("adaptibilidad");
        inteligenciaD = parametros.getInt("inteligencia");
        afectoD = parametros.getInt("afecto");
        energiaD = parametros.getInt("energia");

        cargarData();

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void cargarData() {
        Glide.with(this).load(String.valueOf(imagenD)).into(imgGatoD);
        tvNombreRazaD.setText(razaD);
        tvDescripcionD.setText(descripcionD);
        tvTemperamentoD.setText(getString(R.string.s_temperamento) + " " + temperamentoD);
        tvPaisOrigenD.setText(getString(R.string.pais_de_origen) + " " + paisD);
        tvAdaptabilidadD.setText(getString(R.string.s_adaptabilidad) + " " + adaptibilidadD);
        tvInteligenciD.setText(getString(R.string.s_inteligencia) + " " + inteligenciaD);
        tvEsperanzaD.setText(getString(R.string.s_esperanza) + " " + esperanzaD);
        tvNivelAfectoD.setText(getString(R.string.s_niveafecto) + " " + afectoD);
        tvNivelEnergiaD.setText(getString(R.string.s_nivelenergia) + " " + energiaD);

    }
}