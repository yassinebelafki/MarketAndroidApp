package com.MarketManagement.activities;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.MarketManagement.R;
import com.MarketManagement.databinding.ActivityMapsBinding;
import com.MarketManagement.database.DbAccessHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private DbAccessHelper dbAccessHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAccessHelper =new DbAccessHelper(this);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        List<HashMap<String,String>> allOffresInfo= dbAccessHelper.allOffers();
        Map<String, List<HashMap<String, String>>> infosGroupedByid = allOffresInfo.stream()
                .collect(Collectors.groupingBy(m -> m.get("id_user")));
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        Geocoder geo=new Geocoder(getApplicationContext(), Locale.FRANCE);
        try {
            for (Map.Entry<String, List<HashMap<String, String>>> entry : infosGroupedByid.entrySet()) {
                String id_user = entry.getKey();
                Log.i("info", "onMapReady:********************* "+id_user);
                HashMap<String,String> userInfo = dbAccessHelper.getUserById(id_user);
                String location = userInfo.get("vendorlocation");
                String fname= userInfo.get("first_name");
                String lname= userInfo.get("last_name");
                List<HashMap<String, String>> group = entry.getValue();
                List<Address> endroits=geo.getFromLocationName(location,1);
                if (!endroits.isEmpty()) {
                    Address address = endroits.get(0);
                    LatLng rendezvous = new LatLng(address.getLatitude(), address.getLongitude());
                    //fetching the values of the vendor
                    String snippetMark="";
                    for (HashMap<String, String> hashmap : group) {
                              snippetMark= snippetMark+hashmap.get("merchandise") + "(" + hashmap.get("price") + ")\n";
                    }

                    mMap.addMarker(new MarkerOptions().position(rendezvous).title("Shop "+fname+" "+lname)
                            .snippet(snippetMark));



                    mMap.moveCamera(CameraUpdateFactory.newLatLng(rendezvous));

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        }
}