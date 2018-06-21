package rocks.ninjachen.hbridgek.ui.view;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;

import timber.log.Timber;

/**
 * to reduce the set fill color in de.hdodenhof.circleimageview.CircleImageView
 * Created by ninja on 9/28/16.
 */

public class CircleImageView extends de.hdodenhof.circleimageview.CircleImageView {
    int lastFillColor = -1;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setFillColor(@ColorInt int fillColor) {
//        super.setFillColor(fillColor);

        if(fillColor == lastFillColor){
            Timber.e("reduce once in CircleImageView");
            return;
        }
        else {
            lastFillColor = fillColor;
            super.setFillColor(fillColor);
        }
    }
}
