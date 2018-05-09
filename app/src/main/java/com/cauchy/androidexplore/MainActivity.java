package com.cauchy.androidexplore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cauchy.androidexplore.activity.AIDLClientActivity;
import com.cauchy.androidexplore.activity.LifeCicleActivity;
import com.cauchy.androidexplore.activity.MessengerClientActivity;
import com.cauchy.androidexplore.activity.TouchEventActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.activity_life_cicle)
    Button activityLifeCicle;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        Log.i(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        Log.i(TAG, "onDestroy");
    }

    @OnClick({R.id.activity_life_cicle, R.id.messenger, R.id.aidl, R.id.touch_event})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.activity_life_cicle:
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(new Intent(this, LifeCicleActivity.class));
                break;
            case R.id.messenger:
                startActivity(new Intent(this, MessengerClientActivity.class));
                break;
            case R.id.aidl:
                startActivity(new Intent(this, AIDLClientActivity.class));
                break;
            case R.id.touch_event:
                startActivity(new Intent(this, TouchEventActivity.class));
                break;
            default:
                break;
        }
    }

}
