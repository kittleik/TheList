package thelist.levelasian.ntnu.thelist;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.os.Bundle;
/**
 * Created by hk on 10.03.15.
 */
public class SelectionMap extends Activity implements OnMapReadyCallback  {

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);

    private GoogleMap map;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectionmap);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        onMapReady(map);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
                .title("Hamburg"));
        Marker kiel = map.addMarker(new MarkerOptions()
                .position(KIEL)
                .title("Kiel")
                .snippet("Kiel is cool")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));
    }
}