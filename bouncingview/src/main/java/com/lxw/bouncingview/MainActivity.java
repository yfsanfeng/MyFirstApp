package com.lxw.bouncingview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button mBtn;
    private List<String> mStrings;
    private MyRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
        initList();
        mAdapter = new MyRecyclerAdapter(mStrings, MainActivity.this);
    }

    private void initList() {
        mStrings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mStrings.add("第" + i + "项");
            Log.d(TAG, "initList() returned: " + i);
        }
    }

    private boolean isMenuOpened = false;
    private BounceMenu bounceMenu;

    @Override
    public void onClick(View v) {
        if (!isMenuOpened) {
            showMenu();
            isMenuOpened = true;
        } else {
            bounceMenu.dismiss();
            isMenuOpened = false;
        }

    }

    private void showMenu() {
        bounceMenu = BounceMenu.makeBounceMenu(MainActivity.this, findViewById(R.id.ll), R.layout.view_bouncing_menu, mAdapter);
        bounceMenu.show();
    }

}
