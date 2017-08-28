package studio.lunabee.android.core.animations;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by olivier2 on 24/03/2017.
 */

public class ResizeAnimation extends Animation {

    final int startWidth;
    final int targetWidth;
    View view;

    public ResizeAnimation(View view, int targetWidth) {
        this.view = view;
        this.targetWidth = targetWidth;
        startWidth = view.getWidth();
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newWidth = (int) (startWidth + (targetWidth - startWidth) * interpolatedTime);
        view.getLayoutParams().width = newWidth;
        view.requestLayout();
    }
}