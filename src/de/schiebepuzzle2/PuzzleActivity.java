package de.schiebepuzzle2;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PuzzleActivity extends Activity implements OnTouchListener, OnClickListener{
	protected int _clickCounter;

	protected TextView _counter = null;
	protected RelativeLayout _relativeLayoutGame = null;
	protected Button _backButton = null;
	
	protected int puzzleSize;
	protected Bitmap[][] bitmapSnippets;
	
	protected ImageView _imageView;
	
	
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
		
		this.carveBitmap();
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
		    
		    // Puzzleteile erstellen und in Array speichern
		    int startPixelY = 0;
		    for (int i = 0; i < puzzleSize; i++) {
		    	
		    	int startPixelX = 0;
		    	for (int j = 0; j < puzzleSize; j++){
		    		
		    		bitmapSnip = Bitmap.createBitmap(bitmapFull, startPixelX, startPixelY, targetWidth, targetHeight );
		    		bitmapSnippets[i][j] = bitmapSnip;
		    		startPixelX += targetWidth;
		    	}
		    	startPixelY += targetHeight;
		    }
		    
		    _imageView = (ImageView) findViewById(R.id.imageView1_1);
		    _imageView.setImageBitmap(bitmapSnippets[0][0]);
		    _imageView = (ImageView) findViewById(R.id.imageView1_2);
		    _imageView.setImageBitmap(bitmapSnippets[1][1]);
		    _imageView = (ImageView) findViewById(R.id.imageView1_3);
		    _imageView.setImageBitmap(bitmapSnippets[2][2]);
		    _imageView = (ImageView) findViewById(R.id.imageView2_1);
		    _imageView.setImageBitmap(bitmapSnippets[0][1]);
		    _imageView = (ImageView) findViewById(R.id.imageView2_2);
		    _imageView.setImageBitmap(bitmapSnippets[1][2]);
		    _imageView = (ImageView) findViewById(R.id.imageView2_3);
		    _imageView.setImageBitmap(bitmapSnippets[2][0]);
		    _imageView = (ImageView) findViewById(R.id.imageView3_1);
		    _imageView.setImageBitmap(bitmapSnippets[0][2]);
		    _imageView = (ImageView) findViewById(R.id.imageView3_2);
		    _imageView.setImageBitmap(bitmapSnippets[1][0]);
		    _imageView = (ImageView) findViewById(R.id.imageView3_3);
		    _imageView.setImageBitmap(bitmapSnippets[2][1]);
		    
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
