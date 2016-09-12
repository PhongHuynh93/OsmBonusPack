package dhbk.android.osmbonuspack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity {

    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        init map
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(48.13, -1.63);
        IMapController mapController = map.getController();
        mapController.setZoom(9);
        mapController.setCenter(startPoint);

//        init marker

        // add name of street on map
        addCustomOverlay();
    }

    // TODO: 9/10/16 dont want to show a marker for windows, show a info windows
    private void addCustomOverlay() {
        NameOfStreetOverlay startMarker = new NameOfStreetOverlay(map);
        startMarker.setPosition(new GeoPoint(48.13, -1.63));
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        // add marker overlay
        map.getOverlays().add(startMarker);

        // make the instruction table
//        startMarker.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        startMarker.setTitle("Đường Lý Thường Kiệt");

    }
}
