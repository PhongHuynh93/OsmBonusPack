package dhbk.android.osmbonuspack.InfoWindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by huynhducthanhphong on 9/12/16.
 */
public abstract class TestInfoWindow {
    protected View mView;
    protected boolean mIsVisible;
    protected MapView mMapView;

    public TestInfoWindow(int layoutResId, MapView mapView) {
        this.mMapView = mapView;
        this.mIsVisible = false;
        ViewGroup parent = (ViewGroup) mapView.getParent();
        Context context = mapView.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mView = inflater.inflate(layoutResId, parent, false);
        this.mView.setTag(this);
    }

    public MapView getMapView() {
        return this.mMapView;
    }

    public View getView() {
        return this.mView;
    }

    public void open(Object object, GeoPoint position, int offsetX, int offsetY) {
        this.close();
        this.onOpen(object);
        MapView.LayoutParams lp = new MapView.LayoutParams(-2, -2, position, 8, offsetX, offsetY);
        this.mMapView.addView(this.mView, lp);
        this.mIsVisible = true;
    }

    public void close() {
        if (this.mIsVisible) {
            this.mIsVisible = false;
            ((ViewGroup) this.mView.getParent()).removeView(this.mView);
            this.onClose();
        }

    }

    public void onDetach() {
        this.close();
        if (this.mView != null) {
            this.mView.setTag((Object) null);
        }

        this.mView = null;
        this.mMapView = null;
    }

    public boolean isOpen() {
        return this.mIsVisible;
    }

    public static void closeAllInfoWindowsOn(MapView mapView) {
        ArrayList opened = getOpenedInfoWindowsOn(mapView);
        Iterator i$ = opened.iterator();

        while (i$.hasNext()) {
            TestInfoWindow infoWindow = (TestInfoWindow) i$.next();
            infoWindow.close();
        }

    }

    public static ArrayList<TestInfoWindow> getOpenedInfoWindowsOn(MapView mapView) {
        int count = mapView.getChildCount();
        ArrayList opened = new ArrayList(count);

        for (int i = 0; i < count; ++i) {
            View child = mapView.getChildAt(i);
            Object tag = child.getTag();
            if (tag != null && tag instanceof TestInfoWindow) {
                TestInfoWindow infoWindow = (TestInfoWindow) tag;
                opened.add(infoWindow);
            }
        }

        return opened;
    }

    public abstract void onOpen(Object var1);

    public abstract void onClose();
}
