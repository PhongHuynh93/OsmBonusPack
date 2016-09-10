package dhbk.android.osmbonuspack;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayWithIW;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

/**
 * Created by huynhducthanhphong on 9/10/16.
 */
public class BasicInfoWindowPhong extends InfoWindow {
    public static final int UNDEFINED_RES_ID = 0;
    static int mTitleId = 0;
    static int mDescriptionId = 0;
    static int mSubDescriptionId = 0;
    static int mImageId = 0;

    private static void setResIds(Context context) {
        String packageName = context.getPackageName();
        mTitleId = context.getResources().getIdentifier("id/bubble_title", (String)null, packageName);
        mDescriptionId = context.getResources().getIdentifier("id/bubble_description", (String)null, packageName);
        mSubDescriptionId = context.getResources().getIdentifier("id/bubble_subdescription", (String)null, packageName);
        mImageId = context.getResources().getIdentifier("id/bubble_image", (String)null, packageName);
        if(mTitleId == 0 || mDescriptionId == 0 || mSubDescriptionId == 0 || mImageId == 0) {
            Log.e("OsmDroid", "BasicInfoWindow: unable to get res ids in " + packageName);
        }

    }

    public BasicInfoWindowPhong(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
        if(mTitleId == 0) {
            setResIds(mapView.getContext());
        }

        this.mView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent e) {
                if(e.getAction() == 1) {
                    BasicInfoWindowPhong.this.close();
                }

                return true;
            }
        });
    }

    public void onOpen(Object item) {
        OverlayWithIW overlay = (OverlayWithIW)item;
        String title = overlay.getTitle();
        if(title == null) {
            title = "";
        }

        ((TextView)this.mView.findViewById(mTitleId)).setText(title);
        String snippet = overlay.getSnippet();
        if(snippet == null) {
            snippet = "";
        }

        Spanned snippetHtml = Html.fromHtml(snippet);
        ((TextView)this.mView.findViewById(mDescriptionId)).setText(snippetHtml);
        TextView subDescText = (TextView)this.mView.findViewById(mSubDescriptionId);
        String subDesc = overlay.getSubDescription();
        if(subDesc != null && !"".equals(subDesc)) {
            subDescText.setText(Html.fromHtml(subDesc));
            subDescText.setVisibility(0);
        } else {
            subDescText.setVisibility(8);
        }

    }

    public void onClose() {
    }
}
