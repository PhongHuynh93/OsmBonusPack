package dhbk.android.osmbonuspack;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.OverlayWithIW;

import dhbk.android.osmbonuspack.InfoWindow.NameOfStreetInfoWindow;

/**
 * Created by huynhducthanhphong on 9/10/16.
 */

public class NameOfStreetOverlay extends OverlayWithIW {
    public static boolean ENABLE_TEXT_LABELS_WHEN_NO_IMAGE = false;
    protected int mTextLabelBackgroundColor;
    protected int mTextLabelForegroundColor;
    protected int mTextLabelFontSize;
    protected Drawable mIcon;
    protected GeoPoint mPosition;
    // FIXME: 9/10/16 the rotation of the marker, when we scroll the map, also scroll the info windows
    protected float mBearing;
    protected float mAnchorU;
    protected float mAnchorV;
    protected float mIWAnchorU;
    protected float mIWAnchorV;
    protected float mAlpha;
    protected boolean mDraggable;
    protected boolean mIsDragged;
    protected boolean mFlat;
    protected OnMarkerClickListener mOnMarkerClickListener;
    protected OnMarkerDragListener mOnMarkerDragListener;
    protected Drawable mImage;
    protected boolean mPanToView;
    protected Point mPositionPixels;
    protected static NameOfStreetInfoWindow mDefaultInfoWindow = null;
    protected static Drawable mDefaultIcon = null;
    protected Resources resource;
    public static final float ANCHOR_CENTER = 0.5F;
    public static final float ANCHOR_LEFT = 0.0F;
    public static final float ANCHOR_TOP = 0.0F;
    public static final float ANCHOR_RIGHT = 1.0F;
    public static final float ANCHOR_BOTTOM = 1.0F;

    public NameOfStreetOverlay(MapView mapView) {
        this(mapView, mapView.getContext());
    }

    public NameOfStreetOverlay(MapView mapView, Context resourceProxy) {
        // FIXME: 9/10/16 change the color of the info windows
        this.mTextLabelBackgroundColor = -1;
        this.mTextLabelForegroundColor = -16777216; // it has an alpha color
        // FIXME: 9/10/16 change the textsize
        this.mTextLabelFontSize = 24;
        this.resource = mapView.getContext().getResources();
        this.mBearing = 0.0F;
        this.mAlpha = 1.0F;
        this.mPosition = new GeoPoint(0.0D, 0.0D);
        this.mAnchorU = 0.5F;
        this.mAnchorV = 0.5F;
        this.mIWAnchorU = 0.5F;
        this.mIWAnchorV = 0.0F;
        this.mDraggable = false;
        this.mIsDragged = false;
        this.mPositionPixels = new Point();
        this.mPanToView = true;
        this.mFlat = false;
        this.mOnMarkerClickListener = null;
        this.mOnMarkerDragListener = null;

        // FIXME: 9/10/16 default icon for marker when click, show an info windows
        if (mDefaultIcon == null) {
            mDefaultIcon = resourceProxy.getResources().getDrawable(org.osmdroid.library.R.drawable.marker_default);
        }

//        fixme - change default info windows layout
        this.mIcon = mDefaultIcon;
        if (mDefaultInfoWindow == null || mDefaultInfoWindow.getMapView() != mapView) {
            mDefaultInfoWindow = new NameOfStreetInfoWindow(org.osmdroid.library.R.layout.bonuspack_bubble, mapView);
        }

        this.setInfoWindow(mDefaultInfoWindow);
    }

    public void setIcon(Drawable icon) {
        if (ENABLE_TEXT_LABELS_WHEN_NO_IMAGE && icon == null && this.mTitle != null && this.mTitle.length() > 0) {
            Paint background = new Paint();
            background.setColor(this.mTextLabelBackgroundColor);
            Paint p = new Paint();
            p.setTextSize((float) this.mTextLabelFontSize);
            p.setColor(this.mTextLabelForegroundColor);
            p.setAntiAlias(true);
            p.setTypeface(Typeface.DEFAULT_BOLD);
            p.setTextAlign(Paint.Align.LEFT);
            int width = (int) (p.measureText(this.getTitle()) + 0.5F);
            float baseline = (float) ((int) (-p.ascent() + 0.5F));
            int height = (int) (baseline + p.descent() + 0.5F);
            Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(image);
            c.drawPaint(background);
            c.drawText(this.getTitle(), 0.0F, baseline, p);
            this.mIcon = new BitmapDrawable(this.resource, image);
        }

        if (!ENABLE_TEXT_LABELS_WHEN_NO_IMAGE && icon != null) {
            this.mIcon = icon;
        }

        if (this.mIcon == null) {
            this.mIcon = mDefaultIcon;
        }

    }

    public GeoPoint getPosition() {
        return this.mPosition;
    }

    public void setPosition(GeoPoint position) {
        this.mPosition = position.clone();
    }

    public float getRotation() {
        return this.mBearing;
    }

    public void setRotation(float rotation) {
        this.mBearing = rotation;
    }

    public void setAnchor(float anchorU, float anchorV) {
        this.mAnchorU = anchorU;
        this.mAnchorV = anchorV;
    }

    public void setInfoWindowAnchor(float anchorU, float anchorV) {
        this.mIWAnchorU = anchorU;
        this.mIWAnchorV = anchorV;
    }

    public void setAlpha(float alpha) {
        this.mAlpha = alpha;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public void setDraggable(boolean draggable) {
        this.mDraggable = draggable;
    }

    public boolean isDraggable() {
        return this.mDraggable;
    }

    public void setFlat(boolean flat) {
        this.mFlat = flat;
    }

    public boolean isFlat() {
        return this.mFlat;
    }

    public void remove(MapView mapView) {
        mapView.getOverlays().remove(this);
    }

    public void setOnMarkerClickListener(OnMarkerClickListener listener) {
        this.mOnMarkerClickListener = listener;
    }

    public void setOnMarkerDragListener(OnMarkerDragListener listener) {
        this.mOnMarkerDragListener = listener;
    }

    public void setImage(Drawable image) {
        this.mImage = image;
    }

    public Drawable getImage() {
        return this.mImage;
    }

    public void setInfoWindow(NameOfStreetInfoWindow infoWindow) {
        this.mInfoWindow = infoWindow;
    }

    public void setPanToView(boolean panToView) {
        this.mPanToView = panToView;
    }

    // FIXME: 9/10/16 show info windows
    public void showInfoWindow() {
        if (this.mInfoWindow != null) {
            boolean markerWidth = false;
            boolean markerHeight = false;
            int markerWidth1 = this.mIcon.getIntrinsicWidth();
            int markerHeight1 = this.mIcon.getIntrinsicHeight();
            int offsetX = (int) (this.mIWAnchorU * (float) markerWidth1) - (int) (this.mAnchorU * (float) markerWidth1);
            int offsetY = (int) (this.mIWAnchorV * (float) markerHeight1) - (int) (this.mAnchorV * (float) markerHeight1);
            // fixme open info windows at this position
            this.mInfoWindow.open(this, this.mPosition, offsetX, offsetY);
        }
    }

    public boolean isInfoWindowShown() {
        if (!(this.mInfoWindow instanceof NameOfStreetInfoWindow)) {
            return super.isInfoWindowOpen();
        } else {
            NameOfStreetInfoWindow iw = (NameOfStreetInfoWindow) this.mInfoWindow;
            return iw != null && iw.isOpen() && iw.getMarkerReference() == this;
        }
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        if (!shadow) {
            if (this.mIcon != null) {
                Projection pj = mapView.getProjection();
                pj.toPixels(this.mPosition, this.mPositionPixels);
                int width = this.mIcon.getIntrinsicWidth();
                int height = this.mIcon.getIntrinsicHeight();
                Rect rect = new Rect(0, 0, width, height);
                rect.offset(-((int) (this.mAnchorU * (float) width)), -((int) (this.mAnchorV * (float) height)));
                this.mIcon.setBounds(rect);
                this.mIcon.setAlpha((int) (this.mAlpha * 255.0F));
                float rotationOnScreen = this.mFlat ? -this.mBearing : mapView.getMapOrientation() - this.mBearing;
                drawAt(canvas, this.mIcon, this.mPositionPixels.x, this.mPositionPixels.y, false, rotationOnScreen);
            }
        }
    }

    public void onDetach(MapView mapView) {
        Bitmap bitmap;
        if (Build.VERSION.SDK_INT < 9 && this.mIcon instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) this.mIcon).getBitmap();
            if (bitmap != null) {
                bitmap.recycle();
            }
        }

        this.mIcon = null;
        if (Build.VERSION.SDK_INT < 9 && this.mImage instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) this.mImage).getBitmap();
            if (bitmap != null) {
                bitmap.recycle();
            }
        }

        cleanDefaults();
        this.mOnMarkerClickListener = null;
        this.mOnMarkerDragListener = null;
        this.resource = null;
        this.setRelatedObject((Object) null);
        this.closeInfoWindow();
        this.onDestroy();
        super.onDetach(mapView);
    }

    public static void cleanDefaults() {
        mDefaultIcon = null;
        mDefaultInfoWindow = null;
    }

    public boolean hitTest(MotionEvent event, MapView mapView) {
        Projection pj = mapView.getProjection();
        pj.toPixels(this.mPosition, this.mPositionPixels);
        Rect screenRect = pj.getIntrinsicScreenRect();
        int x = -this.mPositionPixels.x + screenRect.left + (int) event.getX();
        int y = -this.mPositionPixels.y + screenRect.top + (int) event.getY();
        boolean hit = this.mIcon.getBounds().contains(x, y);
        return hit;
    }

    public boolean onSingleTapConfirmed(MotionEvent event, MapView mapView) {
        boolean touched = this.hitTest(event, mapView);
        return touched ? (this.mOnMarkerClickListener == null ? this.onMarkerClickDefault(this, mapView) : this.mOnMarkerClickListener.onMarkerClick(this, mapView)) : touched;
    }

    public void moveToEventPosition(MotionEvent event, MapView mapView) {
        Projection pj = mapView.getProjection();
        this.mPosition = (GeoPoint) pj.fromPixels((int) event.getX(), (int) event.getY());
        mapView.invalidate();
    }

    public boolean onLongPress(MotionEvent event, MapView mapView) {
        boolean touched = this.hitTest(event, mapView);
        if (touched && this.mDraggable) {
            this.mIsDragged = true;
            this.closeInfoWindow();
            if (this.mOnMarkerDragListener != null) {
                this.mOnMarkerDragListener.onMarkerDragStart(this);
            }

            this.moveToEventPosition(event, mapView);
        }

        return touched;
    }

    public boolean onTouchEvent(MotionEvent event, MapView mapView) {
        if (this.mDraggable && this.mIsDragged) {
            if (event.getAction() == 1) {
                this.mIsDragged = false;
                if (this.mOnMarkerDragListener != null) {
                    this.mOnMarkerDragListener.onMarkerDragEnd(this);
                }

                return true;
            } else if (event.getAction() == 2) {
                this.moveToEventPosition(event, mapView);
                if (this.mOnMarkerDragListener != null) {
                    this.mOnMarkerDragListener.onMarkerDrag(this);
                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // FIXME: 9/10/16 show windows when click the marker
    protected boolean onMarkerClickDefault(NameOfStreetOverlay marker, MapView mapView) {
        marker.showInfoWindow();
        if (marker.mPanToView) {
            mapView.getController().animateTo(marker.getPosition());
        }

        return true;
    }

    public int getTextLabelBackgroundColor() {
        return this.mTextLabelBackgroundColor;
    }

    public void setTextLabelBackgroundColor(int mTextLabelBackgroundColor) {
        this.mTextLabelBackgroundColor = mTextLabelBackgroundColor;
    }

    public int getTextLabelForegroundColor() {
        return this.mTextLabelForegroundColor;
    }

    public void setTextLabelForegroundColor(int mTextLabelForegroundColor) {
        this.mTextLabelForegroundColor = mTextLabelForegroundColor;
    }

    public int getTextLabelFontSize() {
        return this.mTextLabelFontSize;
    }

    public void setTextLabelFontSize(int mTextLabelFontSize) {
        this.mTextLabelFontSize = mTextLabelFontSize;
    }

    public interface OnMarkerDragListener {
        void onMarkerDrag(NameOfStreetOverlay var1);

        void onMarkerDragEnd(NameOfStreetOverlay var1);

        void onMarkerDragStart(NameOfStreetOverlay var1);
    }

    public interface OnMarkerClickListener {
        boolean onMarkerClick(NameOfStreetOverlay var1, MapView var2);
    }
}

