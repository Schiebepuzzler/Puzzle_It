package de.activities;

import java.util.ArrayList;

import de.logic.GameTimer;
import de.logic.GlobalConstants;
import de.logic.OnSwipeTouchListener;
import de.logic.PuzzlePart;
import de.schiebepuzzle2.R;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
	protected PuzzlePart[][] bitmapSnippets;
	protected ArrayList<PuzzlePart> bitmapRandom;
	
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
		
		_relativeLayoutGame.setOnTouchListener(new OnSwipeTouchListener(PuzzleActivity.this) {
			String imgViewName;
		    public void onSwipeTop() {
		        Toast.makeText(PuzzleActivity.this, "top", Toast.LENGTH_SHORT).show();
		        Log.d("onSwipeTop", "Nachoben gewischt");
		        
		        //das leere Puzzleteil ermitteln und dann, falls möglich, tauschen
		        for(int i=0; i<bitmapSnippets[0].length; i++){
		        	for (int j=0; j<bitmapSnippets[0].length; j++){
		        		if(bitmapSnippets[i][j].isWhitePartSet()){
		        			//nur wenn sich das Weisse Feld nicht in der untersten Reihe befindet
		        			if(i != (bitmapSnippets[0].length -1)){
		        				//PuzzleTeile verschieben
		        				//zuerst das untere hoch
		        				imgViewName = "imageView" + i + "_" + j;
		        				_imageView = (ImageView) findViewById(getResources().getIdentifier(imgViewName, "id", getPackageName()));
		        				//das PuzzleTeil drunter wird nach oben "verschoben"
		    			    	_imageView.setImageBitmap(bitmapSnippets[i+1][j].getPuzzleImage());
		    			    
		    			    	
		    			    	//dann das obere (weiße) runter
		    			    	imgViewName = "imageView" + (i+1) + "_" + j;
		        				_imageView = (ImageView) findViewById(getResources().getIdentifier(imgViewName, "id", getPackageName()));
		        				//das PuzzleTeil drüber wird nach unten "verschoben"
		    			    	_imageView.setImageBitmap(bitmapSnippets[i][j].getPuzzleImage());
		    			    	
		    			    	//Positionen aktualisieren
		    			    	bitmapSnippets[i+1][j].setCurrentPosition(new int[] {i, j});
		    			    	bitmapSnippets[i][j].setCurrentPosition(new int[] {i+1, j});
		    			    	//Positionen im Array aktualisieren (Referenzen vertauschen)
		    			    	PuzzlePart tauschpartner;
		    			    	tauschpartner = bitmapSnippets[i][j];
		    			    	bitmapSnippets[i][j] = bitmapSnippets[i+1][j];
		    			    	bitmapSnippets[i+1][j] = tauschpartner;
		    			    	Log.d("onSwipeTop", "PuzzleTeil nach oben verschoben");
		    			    	return;
		        			}
		        		}
		        	}
		        }
		    }
		    public void onSwipeRight() {
		        Toast.makeText(PuzzleActivity.this, "right", Toast.LENGTH_SHORT).show();
		        Log.d("onSwipeRight", "Nach Rechts gewischt");
		        
		        //das leere Puzzleteil ermitteln und dann, falls möglich, tauschen
		        for(int i=0; i<bitmapSnippets[0].length; i++){
		        	for (int j=0; j<bitmapSnippets[0].length; j++){
		        		if(bitmapSnippets[i][j].isWhitePartSet()){
		        			//nur wenn sich das Weisse Feld nicht in der linken Reihe befindet
		        			if(j != 0){
		        				//PuzzleTeile verschieben
		        				//zuerst das linke nach rechts
		        				imgViewName = "imageView" + i + "_" + j;
		        				_imageView = (ImageView) findViewById(getResources().getIdentifier(imgViewName, "id", getPackageName()));
		        				//das PuzzleTeil links wird nach rechts "verschoben"
		    			    	_imageView.setImageBitmap(bitmapSnippets[i][j-1].getPuzzleImage());
		    			    
		    			    	
		    			    	//dann das rechte (weiße) nach links
		    			    	imgViewName = "imageView" + (i) + "_" + (j-1);
		        				_imageView = (ImageView) findViewById(getResources().getIdentifier(imgViewName, "id", getPackageName()));
		        				//das PuzzleTeil rechts wird nach links "verschoben"
		    			    	_imageView.setImageBitmap(bitmapSnippets[i][j].getPuzzleImage());
		    			    	
		    			    	//Positionen aktualisieren
		    			    	bitmapSnippets[i][j-1].setCurrentPosition(new int[] {i, j});
		    			    	bitmapSnippets[i][j].setCurrentPosition(new int[] {i, j-1});
		    			    	//Positionen im Array aktualisieren (Referenzen vertauschen)
		    			    	PuzzlePart tauschpartner;
		    			    	tauschpartner = bitmapSnippets[i][j];
		    			    	bitmapSnippets[i][j] = bitmapSnippets[i][j-1];
		    			    	bitmapSnippets[i][j-1] = tauschpartner;
		    			    	Log.d("onSwipeRight", "PuzzleTeil nach rechts verschoben");
		    			    	return;
		        			}
		        		}
		        	}
		        }
		    }
		    public void onSwipeLeft() {
		        Toast.makeText(PuzzleActivity.this, "left", Toast.LENGTH_SHORT).show();
		        Log.d("onSwipeLeft", "Nach Links gewischt");
		        
		        //das leere Puzzleteil ermitteln und dann, falls möglich, tauschen
		        for(int i=0; i<bitmapSnippets[0].length; i++){
		        	for (int j=0; j<bitmapSnippets[0].length; j++){
		        		if(bitmapSnippets[i][j].isWhitePartSet()){
		        			//nur wenn sich das Weisse Feld nicht in der rechten Reihe befindet
		        			if(j != bitmapSnippets[0].length-1){
		        				//PuzzleTeile verschieben
		        				//zuerst das rechte nach links
		        				imgViewName = "imageView" + i + "_" + j;
		        				_imageView = (ImageView) findViewById(getResources().getIdentifier(imgViewName, "id", getPackageName()));
		        				//das PuzzleTeil rechts wird nach links "verschoben"
		    			    	_imageView.setImageBitmap(bitmapSnippets[i][j+1].getPuzzleImage());
		    			    
		    			    	
		    			    	//dann das linke (weiße) nach rechts
		    			    	imgViewName = "imageView" + (i) + "_" + (j+1);
		        				_imageView = (ImageView) findViewById(getResources().getIdentifier(imgViewName, "id", getPackageName()));
		        				//das PuzzleTeil rechts wird nach links "verschoben"
		    			    	_imageView.setImageBitmap(bitmapSnippets[i][j].getPuzzleImage());
		    			    	
		    			    	//Positionen aktualisieren
		    			    	bitmapSnippets[i][j+1].setCurrentPosition(new int[] {i, j});
		    			    	bitmapSnippets[i][j].setCurrentPosition(new int[] {i, j+1});
		    			    	//Positionen im Array aktualisieren (Referenzen vertauschen)
		    			    	PuzzlePart tauschpartner;
		    			    	tauschpartner = bitmapSnippets[i][j];
		    			    	bitmapSnippets[i][j] = bitmapSnippets[i][j+1];
		    			    	bitmapSnippets[i][j+1] = tauschpartner;
		    			    	Log.d("onSwipeRight", "PuzzleTeil nach links verschoben");
		    			    	return;
		        			}
		        		}
		        	}
		        }
		    }
		    public void onSwipeBottom() {
		        Toast.makeText(PuzzleActivity.this, "bottom", Toast.LENGTH_SHORT).show();
		        Log.d("onSwipeBottom", "Nach unten gewischt");
		        
		        //das leere Puzzleteil ermitteln und dann, falls möglich, tauschen
		        for(int i=0; i<bitmapSnippets[0].length; i++){
		        	for (int j=0; j<bitmapSnippets[0].length; j++){
		        		if(bitmapSnippets[i][j].isWhitePartSet()){
		        			//nur wenn sich das Weisse Feld nicht in der obersten Reihe befindet
		        			if(i != 0){
		        				//PuzzleTeile verschieben
		        				//zuerst das obere runter
		        				imgViewName = "imageView" + (i) + "_" + j;
		        				_imageView = (ImageView) findViewById(getResources().getIdentifier(imgViewName, "id", getPackageName()));
		        				//das PuzzleTeil drüber wird nach unten "verschoben"
		    			    	_imageView.setImageBitmap(bitmapSnippets[i-1][j].getPuzzleImage());
		    			    
		    			    	
		    			    	//dann das untere (weiße) hoch
		    			    	imgViewName = "imageView" + (i-1) + "_" + j;
		        				_imageView = (ImageView) findViewById(getResources().getIdentifier(imgViewName, "id", getPackageName()));
		        				//das PuzzleTeil drunter wird nach oben "verschoben"
		    			    	_imageView.setImageBitmap(bitmapSnippets[i][j].getPuzzleImage());
		    			    	
		    			    	//Positionen aktualisieren
		    			    	bitmapSnippets[i][j].setCurrentPosition(new int[] {i-1, j});
		    			    	bitmapSnippets[i-1][j].setCurrentPosition(new int[] {i, j});
		    			    	//Positionen im Array aktualisieren (Referenzen vertauschen)
		    			    	PuzzlePart tauschpartner;
		    			    	tauschpartner = bitmapSnippets[i-1][j];
		    			    	bitmapSnippets[i-1][j] = bitmapSnippets[i][j];
		    			    	bitmapSnippets[i][j] = tauschpartner;
		    			    	Log.d("onSwipeBottom", "PuzzleTeil nach unten verschoben");
		    			    	return;
		        			}
		        		}
		        	}
		        }
		    }

		public boolean onTouch(View v, MotionEvent event) {
		    return gestureDetector.onTouchEvent(event);
		}
		});
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
		    
		    bitmapSnippets = new PuzzlePart[puzzleSize][puzzleSize];
		    PuzzlePart bitmapSnip;
		    bitmapRandom = new ArrayList<PuzzlePart>();
		    
		    // Puzzleteile erstellen und in Array speichern
		    int startPixelY = 0;
		    for (int i = 0; i < puzzleSize; i++) {
		    	
		    	int startPixelX = 0;
		    	for (int j = 0; j < puzzleSize; j++){
		    		
		    		bitmapSnip = new PuzzlePart(Bitmap.createBitmap(bitmapFull, startPixelX, startPixelY, targetWidth, targetHeight ));
		    		bitmapSnip.setOriginPosition(new int[] {i, j});
		    		bitmapSnippets[i][j] = bitmapSnip;
		    		bitmapRandom.add(bitmapSnip);
		    		
		    		
		    		startPixelX += targetWidth;
		    	}
		    	startPixelY += targetHeight;
		    }
		    
		    //Das Puzzle-Teil rechts unten ausgrauen
			bitmapSnippets[puzzleSize-1][puzzleSize-1].whiteout();
			Log.d("puzzleteile array", bitmapSnippets[puzzleSize-1][puzzleSize-1].originPositionToString() +"wird ausgegraut");	
	    	
		     
		    // Puzzleteile zufällig in den ImageViews anordnen
		    int randomInt;
		    String imgViewName;
		    
		    for (int i = 0; i < puzzleSize; i++){
			
		    	for (int j = 0; j < puzzleSize; j++){
		    		
			    	randomInt = (int) Math.round((Math.random() * (bitmapRandom.size()-1)));
			    	
			    	
			    	imgViewName = "imageView" + i + "_" + j;
			    	
			    	_imageView = (ImageView) findViewById(getResources().getIdentifier(imgViewName, "id", getPackageName()));
			    	_imageView.setImageBitmap(bitmapRandom.get(randomInt).getPuzzleImage());
			    	bitmapRandom.get(randomInt).setCurrentPosition(new int[] {i, j});
			    	Log.d("Anordnung PuzzleTeile", "Anordnung von Teil " + bitmapRandom.get(randomInt).originPositionToString() + "an Position " + bitmapRandom.get(randomInt).currentPositionToString());	
			    	bitmapSnippets[i][j] = bitmapRandom.get(randomInt);
			    	bitmapRandom.remove(randomInt);
		    	}
	
		    }
		   
		    
		    
		}
		
		catch (Exception ex) {
			String errorMessage = "Exception in Methode QuarterBitmapActivity::onCreate() bei Bitmap-Verarbeitung aufgetreten: " + ex.getMessage();
			Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
		}	
		
	}
	
	public PuzzlePart[][] getBitmapSnippets(){
		return this.bitmapSnippets;
	}
	
	/**public void setPuzzle (){
		
		_imageView
		
	}*/

}
