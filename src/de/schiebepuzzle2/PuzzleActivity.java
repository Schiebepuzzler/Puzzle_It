package de.schiebepuzzle2;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PuzzleActivity extends Activity implements OnTouchListener, OnClickListener{
	protected int _clickCounter;

	protected TextView _counter = null;
	protected RelativeLayout _relativeLayoutGame = null;
	protected Button _backButton = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_puzzle);

		_clickCounter = 0;
		
		_counter = (TextView) findViewById(R.id.buttonGameCounter);
		_backButton = (Button) findViewById(R.id.ButtonGameBackToMain);
		_relativeLayoutGame = (RelativeLayout) findViewById(R.id.LayoutGame);
		
		_relativeLayoutGame.setOnTouchListener(this);
		_backButton.setOnClickListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
	
		boolean pressed = false;
		
		switch (event.getAction()){
        case MotionEvent.ACTION_DOWN:
            pressed = true;
            
            _clickCounter++;
        	_counter.setText(""+_clickCounter);
        break;

        case MotionEvent.ACTION_MOVE:
            //User is moving around on the screen
        break;

        case MotionEvent.ACTION_UP:
            pressed = false;
        break;
        }
        return pressed;
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		this.finish();
		
	}

}
