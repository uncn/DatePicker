package com.sunzn.picker.library.picker.year;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sunzn.picker.library.R;
import com.sunzn.picker.library.config.ScrollerConfig;
import com.sunzn.picker.library.data.WheelCalendar;
import com.sunzn.picker.library.picker.base.ActionBox;

/**
 * Created by sunzn on 2017/8/30.
 */

public class YearPickBox extends ActionBox<YearPickBox> {

    public interface ActionListener {

        void onEnsure(int startYear, int endYear);

        void onCancel();

    }

    public interface ConfigListener {

        void onConfig(ScrollerConfig value);

    }

    private boolean mCancelable = true;

    private boolean mCanceledOnTouch = true;

    private YearWheel mYearWheel;

    private ActionListener mActionListener;

    private ConfigListener mConfigListener;

    private ScrollerConfig mScrollerConfig;

    public static YearPickBox newBox(Context context) {
        return new YearPickBox(context, R.layout.picker_year_holder);
    }

    public YearPickBox(Context context, int resource) {
        super(context, resource);
        mScrollerConfig = new ScrollerConfig();
    }

    public YearPickBox setActionListener(ActionListener listener) {
        this.mActionListener = listener;
        return this;
    }

    public YearPickBox setConfigListener(ConfigListener listener) {
        this.mConfigListener = listener;
        return this;
    }

    @Override
    public void onActionBoxCreated() {
        initScrollerConfig(mScrollerConfig);
        mYearWheel = new YearWheel(getRootView(), mScrollerConfig);
        TextView cancel = (TextView) findViewById(R.id.tv_cancel);
        TextView ensure = (TextView) findViewById(R.id.tv_ensure);
        initActionView(cancel, ensure);
    }

    private void initScrollerConfig(ScrollerConfig config) {
        if (mConfigListener != null) {
            mConfigListener.onConfig(config);
        }
    }

    public YearPickBox setCyclic(boolean isCyclic) {
        this.mScrollerConfig.cyclic = isCyclic;
        return this;
    }

    public YearPickBox setMinYear(long milliseconds) {
        this.mScrollerConfig.mMinCalendar = new WheelCalendar(milliseconds);
        return this;
    }

    public YearPickBox setMaxYear(long milliseconds) {
        this.mScrollerConfig.mMaxCalendar = new WheelCalendar(milliseconds);
        return this;
    }

    public YearPickBox setStaYear(int year) {
        this.mScrollerConfig.mStaYear = year;
        return this;
    }

    public YearPickBox setEndYear(int year) {
        this.mScrollerConfig.mEndYear = year;
        return this;
    }

    public YearPickBox setCancelable(boolean cancelable) {
        this.mCancelable = cancelable;
        return this;
    }

    public YearPickBox setCanceledOnTouch(boolean canceledOnTouch) {
        this.mCanceledOnTouch = canceledOnTouch;
        return this;
    }

    @Override
    public boolean getCancelable() {
        return mCancelable;
    }

    @Override
    public boolean getCanceledOnTouch() {
        return mCanceledOnTouch;
    }

    private void initActionView(TextView cancel, TextView ensure) {
        if (cancel != null) {
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fade();
                    if (mActionListener != null) mActionListener.onCancel();
                }
            });
        }
        if (ensure != null) {
            ensure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fade();
                    if (mActionListener != null && mYearWheel != null) {
                        mActionListener.onEnsure(mYearWheel.getCurrentStaYear(), mYearWheel.getCurrentEndYear());
                    }
                }
            });
        }
    }


}
