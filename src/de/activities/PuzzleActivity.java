package de.activities;

import java.util.ArrayList;

import de.logic.GameTimer;
import de.logic.GlobalConstants;
import de.schiebepuzzle2.R;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PuzzleActivity extends Activity implements OnTouchListener{
	
	//Deklaration
	protected int _clickCounter;
	protected GameTimer _Time;
	
	protected TextView _counter = null;
	protected TextView _timer = null;
	protected RelativeLayout _relativeLayoutGame = null;

	
	protected int puzzleSize;
	protected Bitmap[][] bitmapSnippets;
	protected ArrayList<Bitmap> bitmapRandom;
	
	protected ImageView _imageView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_puzzle);

		_Time = new GameTimer();
		_clickCounter = 0;
		
		_counter = (TextView) findViewById(R.id.buttonGameCounter);
		_timer = (TextView) findViewById(R.id.buttonGameTimer);
		_relativeLayoutGame = (RelativeLayout) findViewById(R.id.LayoutGame);
		
		_relativeLayoutGame.setOnTouchListener(this);
		
		this.carveBitmap();
		
		Thread t = new Thread() {

			@Override
			public void run() {
				
				try {
					
					while (!isInterrupted()) {
						
						Thread.sleep(500);
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								_timer.setText(_Time.getformatedTime());
							}
						});
					}
					
			    } catch (InterruptedException e) {
			    	
			    }
			}
		};

		t.start();
		
		/*
		_TimeRefresh = new Runnable() {
			public void run(){
				_counter.setText(""+_clickCounter);
				_handler.postDelayed(this, 1000);
			}
		
		};
		*/
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
	
	public void carveBitmap(){
		
		//  Anzahl der seitlichen Puzzleteile
		puzzleSize = GlobalConstants.EASY;
		
		try {
			Bitmap bitmapFull = BitmapFactory.decodeResource(getResources(), R.drawable.guitar);
			
			// Breite + Höhe des Ausschnitts berechnen
			int targetWidth  = bitmapFull.getWidth () / puzzleSize;
		    int targetHeight = bitmapFull.getHeight() / puzzleSize;
		    
		    bitmapSnippets = new Bitmap[puzzleSize][puzzleSize];
		    Bitmap bitmapSnip;
		    bitmapRandom = new ArrayList<Bitmap>();
		    
		    // Puzzleteile erstellen und in Array speichern
		    int startPixelY = 0;
		    for (int i = 0; i < puzzleSize; i++) {
		    	
		    	int startPixelX = 0;
		    	for (int j = 0; j < puzzleSize; j++){
		    		
		    		bitmapSnip = Bitmap.createBitmap(bitmapFull, startPixelX, startPixelY, targetWidth, targetHeight );
		    		bitmapSnippets[i][j] = bitmapSnip;
		    		bitmapRandom.add(bitmapSnip);
		    		
		    		
		    		startPixelX += targetWidth;
		    	}
		    	startPixelY += targetHeight;
		    }
		    
		     
		    // Puzzleteile zufällig in den ImageViews anordnen
		    int randomInt;
		    String imgViewName;
		    
		    for (int i = 0; i < puzzleSize; i++){
			
		    	for (int j = 0; j < puzzleSize; j++){
		    		
			    	randomInt = (int) Math.round((Math.random() * (bitmapRandom.size()-1)));
			    	Log.d("PuzzleActivity", ""+ randomInt);	
			    	
			    	imgViewName = "imageView" + i + "_" + j;
			    	
			    	_imageView = (ImageView) findViewById(getResources().getIdentifier(imgViewName, "id", getPackageName()));
			    	_imageView.setImageBitmap(bitmapRandom.get(randomInt));
			    	
			    	bitmapRandom.remove(randomInt);
		    	}
	
		    }
		    
		}
		
		catch (Exception ex) {
			String errorMessage = "Exception in Methode QuarterBitmapActivity::onCreate() bei Bitmap-Verarbeitung aufgetreten: " + ex.getMessage();
			Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
		}	
		
	}
	
	/**public void setPuzzle (){
		
		_imageView
		
	}*/

}
