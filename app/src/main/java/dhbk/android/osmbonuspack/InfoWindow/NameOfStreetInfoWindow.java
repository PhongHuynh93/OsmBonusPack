package dhbk.android.osmbonuspack.InfoWindow;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.osmdroid.views.MapView;

import dhbk.android.osmbonuspack.InfoWindow.BasicInfoWindowPhong;
import dhbk.android.osmbonuspack.NameOfStreetOverlay;

/**
 * Created by huynhducthanhphong on 9/10/16.
 * todo use this class to make only the info windows, not the marker
 */
public class NameOfStreetInfoWindow extends BasicInfoWindowPhong {
    protected NameOfStreetOverlay mMarkerRef;

    public NameOfStreetInfoWindow(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }

    public NameOfStreetOverlay getMarkerReference() {
        return this.mMarkerRef;
    }

    public void onOpen(Object item) {
        super.onOpen(item);
        this.mMarkerRef = (NameOfStreetOverlay) item;
        ImageView imageView = (ImageView) this.mView.findViewById(mImageId);
        Drawable image = this.mMarkerRef.getImage();
        if (image != null) {
            imageView.setImageDrawable(image);
            imageView.setVisibility(0);
        } else {
            imageView.setVisibility(8);
        }

    }

    public void onClose() {
        super.onClose();
        this.mMarkerRef = null;
    }
}
