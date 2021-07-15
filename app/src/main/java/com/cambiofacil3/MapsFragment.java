package com.cambiofacil3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cambiofacil3.data.model.Marcador;
import com.cambiofacil3.ui.login.LoginActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            //LatLng sydney = new LatLng(-34, 151);
            //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            String usuario = sharedPref.getString("usuario", "dani@gmail.com");

            Toast.makeText(getActivity(), "Mapa para: "+usuario, Toast.LENGTH_SHORT).show();

            String url = "https://ms-clientes-dev.mybluemix.net/api/marcadores?usuario="+usuario;
            StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        ArrayList<MarkerOptions> marcadores = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray marcadoresAux = jsonObject.optJSONArray("marcadores");
                        LatLng marcadorFinal = null;

                        googleMap.getUiSettings().setZoomControlsEnabled(true);
                        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                        LatLng lima = new LatLng(-12.04592, -77.030565);
                        googleMap.addMarker(new MarkerOptions()
                                .position(lima)
                                .title("Centro de Lima"));

                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-12.044956, -77.029831))
                                .title("Palacio de Gobierno"));

                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-12.046661, -77.029544))
                                .title("Catedral"));

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lima, 15));

                        for (int i=0; i< marcadoresAux.length(); i++){
                            /*JSONObject object = marcadoresAux.getJSONObject(i);

                            MarkerOptions aux = new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitud")))
                                    .title(object.getString("nombre"));

                            marcadores.add(aux);
                            marcadorFinal = new LatLng(object.getDouble("latitude"), object.getDouble("longitud"));
                            //jsonObject.getString("usuario")
                            Log.i("==>", object.toString());*/
                        }

                        Log.i("======>", response);
                        Log.i("======>", jsonObject.toString());

                        /*for (MarkerOptions aux : marcadores){
                            googleMap.addMarker(aux);
                        }*/
                        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marcadorFinal, 15));

                        /*

                        LatLng lima = new LatLng(-12.04592, -77.030565);
                        googleMap.addMarker(new MarkerOptions()
                                .position(lima)
                                .title("Centro de Lima"));

                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-12.044956, -77.029831))
                                .title("Palacio de Gobierno"));

                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-12.046661, -77.029544))
                                .title("Catedral"));

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lima, 15));
                                */


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("======>", "Error en Mapa");
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("======>", error.toString());
                        }
                    }
            );
            Volley.newRequestQueue(getActivity()).add(stringRequest);
            //
            /*googleMap.getUiSettings().setZoomControlsEnabled(true);
            //googleMap.setTrafficEnabled(true);

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            LatLng lima = new LatLng(-12.04592, -77.030565);
            googleMap.addMarker(new MarkerOptions()
                    .position(lima)
                    .title("Centro de Lima"));

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(-12.044956, -77.029831))
                    .title("Palacio de Gobierno"));

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(-12.046661, -77.029544))
                    .title("Catedral"));

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lima, 15));
*/
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}