package studio.lunabee.android.core.widgets.statefulview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import studio.lunabee.android.core.lunabeecore.R;

/**
 * Created by maravalmaxime on 08/02/2017.
 */

public class StatefulView extends FrameLayout {

    public enum State {DATA, LOADING, EMPTY, ERROR}

    private State mState = State.DATA;

    private View mDataView;
    private ImageView mImageView;
    private TextView mTitleView;
    private TextView mSubtitleView;
    private ProgressBar mProgressBar;

    private Drawable mEmptyDrawable;
    private CharSequence mEmptyTitle;
    private CharSequence mEmptySubtitle;

    private Drawable mErrorDrawable;
    private CharSequence mErrorTitle;
    private CharSequence mErrorSubtitle;

    public StatefulView(Context context) {
        this(context, null);
    }

    public StatefulView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatefulView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.stateful_view, this);
        mImageView = (ImageView) findViewById(R.id.image);
        mTitleView = (TextView) findViewById(R.id.title);
        mSubtitleView = (TextView) findViewById(R.id.subtitle);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        setVisibility(GONE);
    }

    /* STATE */

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        if (mState != state) {
            mState = state;
            updateState();
        }
    }

    private void updateState() {
        switch (mState) {
            case LOADING:
                setVisibility(VISIBLE);
                mProgressBar.setVisibility(VISIBLE);
                mImageView.setVisibility(GONE);
                mTitleView.setVisibility(GONE);
                mSubtitleView.setVisibility(GONE);
                if (mDataView != null) {
                    mDataView.setVisibility(GONE);
                }
                break;
            case EMPTY:
                setVisibility(VISIBLE);
                mProgressBar.setVisibility(GONE);
                if (mDataView != null) {
                    mDataView.setVisibility(GONE);
                }
                setImageView(mEmptyDrawable);
                setTitleView(mEmptyTitle);
                setSubtitleView(mEmptySubtitle);
                break;
            case ERROR:
                setVisibility(VISIBLE);
                mProgressBar.setVisibility(GONE);
                if (mDataView != null) {
                    mDataView.setVisibility(GONE);
                }
                setImageView(mErrorDrawable);
                setTitleView(mErrorTitle);
                setSubtitleView(mErrorSubtitle);
                break;
            case DATA:
            default:
                setVisibility(GONE);
                if (mDataView != null) {
                    mDataView.setVisibility(VISIBLE);
                }
                break;
        }
    }

    /* STATEFUL VIEW */

    private void setImageView(Drawable drawable) {
        if (drawable == null) {
            mImageView.setVisibility(GONE);
        } else {
            mImageView.setVisibility(VISIBLE);
            mImageView.setImageDrawable(drawable);
        }
    }

    private void setTitleView(CharSequence title) {
        if (TextUtils.isEmpty(title)) {
            mTitleView.setVisibility(GONE);
        } else {
            mTitleView.setVisibility(VISIBLE);
            mTitleView.setText(title);
        }
    }

    private void setSubtitleView(CharSequence subtitle) {
        if (TextUtils.isEmpty(subtitle)) {
            mSubtitleView.setVisibility(GONE);
        } else {
            mSubtitleView.setVisibility(VISIBLE);
            mSubtitleView.setText(subtitle);
        }
    }

    /* DATA VIEW */

    public View getDataView() {
        return mDataView;
    }

    public void setDataView(View view) {
        mDataView = view;
        if (mDataView != null) {
            if (mState != State.DATA) {
                mDataView.setVisibility(GONE);
            } else {
                mDataView.setVisibility(VISIBLE);
            }
        }
    }

    /* EMPTY VIEW */

    public void setEmptyDrawable(Drawable emptyDrawable) {
        mEmptyDrawable = emptyDrawable;
        if (mState == State.EMPTY) {
            setImageView(mEmptyDrawable);
        }
    }

    public Drawable getEmptyDrawable() {
        return mEmptyDrawable;
    }

    public void setEmptyDrawable(@DrawableRes int emptyDrawableRes) {
        mEmptyDrawable = ResourcesCompat.getDrawable(getResources(), emptyDrawableRes, null);
        if (mState == State.EMPTY) {
            setImageView(mEmptyDrawable);
        }
    }

    public CharSequence getEmptyTitle() {
        return mEmptyTitle;
    }

    public void setEmptyTitle(CharSequence emptyTitle) {
        mEmptyTitle = emptyTitle;
        if (mState == State.EMPTY) {
            setTitleView(mEmptyTitle);
        }
    }

    public CharSequence getEmptySubtitle() {
        return mEmptySubtitle;
    }

    public void setEmptySubtitle(CharSequence emptySubtitle) {
        mEmptySubtitle = emptySubtitle;
        if (mState == State.EMPTY) {
            setSubtitleView(mEmptySubtitle);
        }
    }

    /* ERROR VIEW */

    public void setErrorDrawable(Drawable errorDrawable) {
        mErrorDrawable = errorDrawable;
        if (mState == State.ERROR) {
            setImageView(mErrorDrawable);
        }
    }

    public Drawable getErrorDrawable() {
        return mErrorDrawable;
    }

    public void setErrorDrawable(@DrawableRes int errorDrawableRes) {
        mErrorDrawable = ResourcesCompat.getDrawable(getResources(), errorDrawableRes, null);
        if (mState == State.ERROR) {
            setImageView(mErrorDrawable);
        }
    }

    public CharSequence getErrorTitle() {
        return mErrorTitle;
    }

    public void setErrorTitle(CharSequence errorTitle) {
        mErrorTitle = errorTitle;
        if (mState == State.ERROR) {
            setTitleView(mErrorTitle);
        }
    }

    public CharSequence getErrorSubtitle() {
        return mErrorSubtitle;
    }

    public void setErrorSubtitle(CharSequence errorSubtitle) {
        mErrorSubtitle = errorSubtitle;
        if (mState == State.ERROR) {
            setSubtitleView(mErrorSubtitle);
        }
    }
}
