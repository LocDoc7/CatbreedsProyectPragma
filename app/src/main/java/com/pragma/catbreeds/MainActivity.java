package com.pragma.catbreeds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;
import com.pragma.catbreeds.Adapters.GatosAdapter;
import com.pragma.catbreeds.Model.Gatos;
import com.pragma.catbreeds.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    LinearLayout llContenedor,llCargando;
    RecyclerView rvGatos;
    ArrayList<Gatos> gatosArrayList;
    RequestQueue requestQueue;
    GatosAdapter gatosAdapter;
    JsonArrayRequest jsonArrayRequest;
    SearchView svBuscarxRaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llCargando = findViewById(R.id.llcargando);
        llContenedor = findViewById(R.id.llcontenedor);
        rvGatos = findViewById(R.id.rv_gatos);
        rvGatos.setHasFixedSize(true);
        gatosArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        svBuscarxRaza = findViewById(R.id.sv_BuscarRaza);
        cargarWebSerive();

        svBuscarxRaza.clearFocus();
        svBuscarxRaza.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });

    }

    private void fileList(String text) {
        List<Gatos> filteredList = new ArrayList<>();
        for (Gatos gatos: gatosArrayList){
            if (gatos.getRazaGato().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(gatos);
            }
        }

        if (filteredList.isEmpty()){
            Toast.makeText(this, getString(R.string.s_no_hay_datos), Toast.LENGTH_SHORT).show();
        }else{
            gatosAdapter.setFilteredList(filteredList);
        }
    }

    private void cargarWebSerive() {
        String url = Util.RUTA;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                llCargando.setVisibility(View.GONE);
                llContenedor.setVisibility(View.VISIBLE);
                LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL, false);
                rvGatos.setLayoutManager(manager);
                Gatos gatos = null;
                int size = response.length();
                for (int i=0; i<size; i++){
                    gatos = new Gatos();
                    try {
                        JSONObject jsonObject = new JSONObject(response.get(i).toString());
                        gatos.setAdaptabilidadGato(jsonObject.getInt("adaptability"));
                        gatos.setDescripcionGato(jsonObject.getString("description"));
                        gatos.setEsperanzaVidaGato(jsonObject.getString("life_span"));
                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("image"));
                        gatos.setImgGato(jsonObject1.getString("url"));
                        gatos.setInteligenciaGato(jsonObject.getInt("intelligence"));
                        gatos.setNivelAfectoGato(jsonObject.getInt("affection_level"));
                        gatos.setNivelEnergiaGato(jsonObject.getInt("energy_level"));
                        gatos.setPaisGato(jsonObject.getString("origin"));
                        gatos.setRazaGato(jsonObject.getString("name"));
                        gatos.setTemperamentoGato(jsonObject.getString("temperament"));
                        gatosArrayList.add(gatos);

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                gatosAdapter = new GatosAdapter(gatosArrayList, MainActivity.this, new GatosAdapter.onClick() {
                    @Override
                    public void onClick(Gatos gatos) {
                        Intent i = new Intent(MainActivity.this, DetalleActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("descripcion",gatos.getDescripcionGato());
                        bundle.putString("esperanza",gatos.getEsperanzaVidaGato());
                        bundle.putString("imagen",gatos.getImgGato());
                        bundle.putString("pais",gatos.getPaisGato());
                        bundle.putString("raza",gatos.getRazaGato());
                        bundle.putString("temperamento",gatos.getTemperamentoGato());
                        bundle.putInt("adaptibilidad",gatos.getAdaptabilidadGato());
                        bundle.putInt("inteligencia",gatos.getInteligenciaGato());
                        bundle.putInt("afecto",gatos.getNivelAfectoGato());
                        bundle.putInt("energia",gatos.getNivelEnergiaGato());

                        i.putExtras(bundle);

                        startActivity(i);
                    }
                });
                rvGatos.setAdapter(gatosAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap header = new HashMap();
                header.put("x-api-key","bda53789-d59e-46cd-9bc4-2936630fde39");
                return header;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }

}