package studio.lunabee.android.core.animations;

import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.List;

public class ViewPropertyAnimatorCompatSet {

    private final List<ViewPropertyAnimatorCompat> mAnimators;
    private ViewPropertyAnimatorListener mListener;
    private long mDuration = -1;
    private Interpolator mInterpolator;
    private boolean mIsStarted;
    private final ViewPropertyAnimatorListener mProxyListener = new ViewPropertyAnimatorListenerAdapter() {
        private boolean mProxyStarted = false;
        private int mProxyEndCount = 0;

        @Override
        public void onAnimationStart(View view) {
            if (mProxyStarted) {
                return;
            }
            mProxyStarted = true;
            if (mListener != null) {
                mListener.onAnimationStart(null);
            }
        }

        @Override
        public void onAnimationEnd(View view) {
            if (++mProxyEndCount == mAnimators.size()) {
                if (mListener != null) {
                    mListener.onAnimationEnd(null);
                }
                onEnd();
            }
        }

        void onEnd() {
            mProxyEndCount = 0;
            mProxyStarted = false;
            onAnimationsEnded();
        }
    };

    public ViewPropertyAnimatorCompatSet() {
        mAnimators = new ArrayList<>();
    }

    public ViewPropertyAnimatorCompatSet play(ViewPropertyAnimatorCompat animator) {
        if (!mIsStarted) {
            mAnimators.add(animator);
        }
        return this;
    }

    public ViewPropertyAnimatorCompatSet playSequentially(ViewPropertyAnimatorCompat anim1, ViewPropertyAnimatorCompat anim2) {
        mAnimators.add(anim1);
        anim2.setStartDelay(anim1.getDuration());
        mAnimators.add(anim2);
        return this;
    }

    public void start() {
        if (mIsStarted) {
            return;
        }
        for (ViewPropertyAnimatorCompat animator : mAnimators) {
            if (mDuration >= 0) {
                animator.setDuration(mDuration);
            }
            if (mInterpolator != null) {
                animator.setInterpolator(mInterpolator);
            }
            if (mListener != null) {
                animator.setListener(mProxyListener);
            }
            animator.start();
        }

        mIsStarted = true;
    }

    private void onAnimationsEnded() {
        mIsStarted = false;
    }

    public void cancel() {
        if (!mIsStarted) {
            return;
        }
        for (ViewPropertyAnimatorCompat animator : mAnimators) {
            animator.cancel();
        }
        mIsStarted = false;
    }

    public ViewPropertyAnimatorCompatSet setDuration(long duration) {
        if (!mIsStarted) {
            mDuration = duration;
        }
        return this;
    }

    public ViewPropertyAnimatorCompatSet setInterpolator(Interpolator interpolator) {
        if (!mIsStarted) {
            mInterpolator = interpolator;
        }
        return this;
    }

    public ViewPropertyAnimatorCompatSet setListener(ViewPropertyAnimatorListener listener) {
        if (!mIsStarted) {
            mListener = listener;
        }
        return this;
    }
}

