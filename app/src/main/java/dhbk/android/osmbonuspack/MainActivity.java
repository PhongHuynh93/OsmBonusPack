package dhbk.android.osmbonuspack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        init map
        MapView map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(48.13, -1.63);
        IMapController mapController = map.getController();
        mapController.setZoom(9);
        mapController.setCenter(startPoint);

//        init marker
        NameOfStreetOverlay startMarker = new NameOfStreetOverlay(map);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        // add marker overlay
        map.getOverlays().add(startMarker);

        // make the instruction table
//        startMarker.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        startMarker.setTitle("Start point");

        // add name of street on map
        addCustomOverlay();
    }

    private void addCustomOverlay() {

    }
}
