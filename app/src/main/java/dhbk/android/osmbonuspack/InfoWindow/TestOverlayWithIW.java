package dhbk.android.osmbonuspack.InfoWindow;

import android.content.Context;

import org.osmdroid.views.overlay.Overlay;

import dhbk.android.osmbonuspack.InfoWindow.TestInfoWindow;

/**
 * Created by huynhducthanhphong on 9/12/16.
 */
public abstract class TestOverlayWithIW extends Overlay {
    protected String mTitle;
    protected String mSnippet;
    protected String mSubDescription;
    protected TestInfoWindow mInfoWindow;
    protected Object mRelatedObject;

    /**
     * @deprecated
     */
    @Deprecated
    public TestOverlayWithIW(Context ctx) {
        this();
    }

    public TestOverlayWithIW() {
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setSnippet(String snippet) {
        this.mSnippet = snippet;
    }

    public String getSnippet() {
        return this.mSnippet;
    }

    public void setSubDescription(String subDescription) {
        this.mSubDescription = subDescription;
    }

    public String getSubDescription() {
        return this.mSubDescription;
    }

    public void setRelatedObject(Object relatedObject) {
        this.mRelatedObject = relatedObject;
    }

    public Object getRelatedObject() {
        return this.mRelatedObject;
    }

    public void setInfoWindow(TestInfoWindow infoWindow) {
        this.mInfoWindow = infoWindow;
    }

    public TestInfoWindow getInfoWindow() {
        return this.mInfoWindow;
    }

    public void closeInfoWindow() {
        if (this.mInfoWindow != null) {
            this.mInfoWindow.close();
        }

    }

    public void onDestroy() {
        if (this.mInfoWindow != null) {
            this.mInfoWindow.close();
            this.mInfoWindow.onDetach();
            this.mInfoWindow = null;
            this.mRelatedObject = null;
        }

    }

    public boolean isInfoWindowOpen() {
        return this.mInfoWindow != null && this.mInfoWindow.isOpen();
    }
}
