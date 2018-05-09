package com.cauchy.androidexplore.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.cauchy.androidexplore.R;
import com.cauchy.androidexplore.view.TouchEventView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TouchEventActivity extends AppCompatActivity {
    @BindView(R.id.view1)
    TouchEventView view1;
    @BindView(R.id.view2)
    TouchEventView view2;
    @BindView(R.id.view3)
    TouchEventView view3;

    private final String TAG = getClass().getSimpleName();
    Unbinder unbinder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.view1, R.id.view2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view1:
                Log.i(TAG, "on click view1");
                break;
            case R.id.view2:
                Log.i(TAG, "on click view2");
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
