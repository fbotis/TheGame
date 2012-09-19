package com.fb.android.views;

import com.fb.android.R;

import android.app.Activity;
import android.os.Bundle;

public class GameView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.game_view);
    }
}
