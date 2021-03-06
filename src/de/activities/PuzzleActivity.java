package de.activities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.logic.DatabaseHandler;
import de.logic.GameTimer;
import de.logic.GlobalConstants;
import de.logic.HighscoreDataset;
import de.logic.ImageAdapter;
import de.logic.OnSwipeTouchListener;
import de.logic.PuzzlePart;
import de.schiebepuzzle2.R;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class PuzzleActivity extends Activity {

	// Deklaration
	protected int _clickCounter;
	protected GameTimer _Time;
	protected int _width;

	protected TextView _counter = null;
	protected TextView _timer = null;
	protected RelativeLayout _relativeLayoutGame = null;

	protected TableLayout _gameTable;
	protected TableRow _r1;
	protected TableRow _r2;
	protected TableRow _r3;

	protected int puzzleSize;
	protected PuzzlePart[][] bitmapSnippets;
	protected ArrayList<PuzzlePart> bitmapRandom;

	protected ImageView _imageView;

	protected HighscoreDataset _highscore;

	protected Context _context;

	protected String _returnValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_puzzle);

		final DatabaseHandler db = new DatabaseHandler(this);

		_context = this;

		/*
		 * How to use Database Log.d("Insert: ", "Inserting ..");
		 * db.addHighscoreDataset(new HighscoreDataset("Marian", 4, "01:00"));
		 * db.addHighscoreDataset(new HighscoreDataset("Marcel", 6, "01:12"));
		 * db.addHighscoreDataset(new HighscoreDataset("Mirko", 8, "01:30"));
		 * 
		 * 
		 * Log.d("Read: ", "Reading.."); List<HighscoreDataset> scoreSets =
		 * db.getAllHighscoreDatasets();
		 * 
		 * for (HighscoreDataset ss : scoreSets) { String log =
		 * "Id: "+ss.getId()+" ,Name: " + ss.getName() + " ,Moves: " +
		 * ss.getMoves() + " ,Time:" + ss.getTime(); // Writing
		 * HighscoreDatasets to log Log.d("Name: ", log);
		 */

		_Time = new GameTimer();
		_clickCounter = 0;

		_counter = (TextView) findViewById(R.id.buttonGameCounter);
		_timer = (TextView) findViewById(R.id.buttonGameTimer);
		_relativeLayoutGame = (RelativeLayout) findViewById(R.id.LayoutGame);

		_gameTable = (TableLayout) findViewById(R.id.ContentGameImage);
		_r1 = (TableRow) findViewById(R.id.GameRow1);
		_r2 = (TableRow) findViewById(R.id.GameRow2);
		_r3 = (TableRow) findViewById(R.id.GameRow3);

		_relativeLayoutGame.setOnTouchListener(new OnSwipeTouchListener(
				PuzzleActivity.this) {
			String imgViewName;

			public void onSwipeTop() {
				/*
				 * Toast.makeText(PuzzleActivity.this, "top",
				 * Toast.LENGTH_SHORT) .show(); Log.d("onSwipeTop",
				 * "Nachoben gewischt");
				 */
				// das leere Puzzleteil ermitteln und dann, falls möglich,
				// tauschen
				for (int i = 0; i < bitmapSnippets[0].length; i++) {
					for (int j = 0; j < bitmapSnippets[0].length; j++) {
						if (bitmapSnippets[i][j].isWhitePartSet()) {
							// nur wenn sich das Weisse Feld nicht in der
							// untersten Reihe befindet
							if (i != (bitmapSnippets[0].length - 1)) {
								// PuzzleTeile verschieben
								// zuerst das untere hoch
								imgViewName = "imageView" + i + "_" + j;
								_imageView = (ImageView) findViewById(getResources()
										.getIdentifier(imgViewName, "id",
												getPackageName()));
								// das PuzzleTeil drunter wird nach oben
								// "verschoben"
								_imageView
										.setImageBitmap(bitmapSnippets[i + 1][j]
												.getPuzzleImage());

								// dann das obere (weiße) runter
								imgViewName = "imageView" + (i + 1) + "_" + j;
								_imageView = (ImageView) findViewById(getResources()
										.getIdentifier(imgViewName, "id",
												getPackageName()));
								// das PuzzleTeil drüber wird nach unten
								// "verschoben"
								_imageView.setImageBitmap(bitmapSnippets[i][j]
										.getPuzzleImage());

								// Positionen aktualisieren
								bitmapSnippets[i + 1][j]
										.setCurrentPosition(new int[] { i, j });
								bitmapSnippets[i][j]
										.setCurrentPosition(new int[] { i + 1,
												j });
								// Positionen im Array aktualisieren (Referenzen
								// vertauschen)
								PuzzlePart tauschpartner;
								tauschpartner = bitmapSnippets[i][j];
								bitmapSnippets[i][j] = bitmapSnippets[i + 1][j];
								bitmapSnippets[i + 1][j] = tauschpartner;
								Log.d("onSwipeTop",
										"PuzzleTeil nach oben verschoben");
								_clickCounter++;
								_counter.setText("" + _clickCounter);

								// Prüfen ob das Puzzle fertig ist
								if (isFinished(bitmapSnippets)) {
									// Das Puzzle Teil rechts unten wieder mit
									// dem ursprünglichen Bild befüllen und
									// Thread anhalten
									bitmapSnippets[bitmapSnippets[0].length - 1][bitmapSnippets[0].length - 1]
											.rewhite();
									imgViewName = "imageView"
											+ (bitmapSnippets[0].length - 1)
											+ "_"
											+ (bitmapSnippets[0].length - 1);
									_imageView = (ImageView) findViewById(getResources()
											.getIdentifier(imgViewName, "id",
													getPackageName()));
									_imageView
											.setImageBitmap(bitmapSnippets[bitmapSnippets[0].length - 1][bitmapSnippets[0].length - 1]
													.getPuzzleImage());
									/*
									 * Toast.makeText(PuzzleActivity.this,
									 * "feddisch", Toast.LENGTH_SHORT) .show();
									 */
									// HighscoreDataset

									_highscore = new HighscoreDataset(
											readName(_context), _clickCounter,
											_Time.getformatedTime());
									db.addHighscoreDataset(_highscore);

								}
								return;
							}
						}
					}
				}
			}

			public String readName(Context con) {

				LayoutInflater layoutInflater = LayoutInflater.from(con);

				View promptView = layoutInflater
						.inflate(R.layout.prompts, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						con);

				// set prompts.xml to be the layout file of the alertdialog
				// builder
				alertDialogBuilder.setView(promptView);

				final EditText input = (EditText) promptView
						.findViewById(R.id.userInputAlert);

				// setup a dialog window
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// get user input and set it to result
										_returnValue = (String) input.getText()
												.toString();
										Log.v("NAME", _returnValue);
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create an alert dialog
				AlertDialog alertD = alertDialogBuilder.create();

				alertD.show();

				return _returnValue;
			}

			public void onSwipeRight() {
				/*
				 * Toast.makeText(PuzzleActivity.this, "right",
				 * Toast.LENGTH_SHORT) .show();
				 */
				Log.d("onSwipeRight", "Nach Rechts gewischt");

				// das leere Puzzleteil ermitteln und dann, falls möglich,
				// tauschen
				for (int i = 0; i < bitmapSnippets[0].length; i++) {
					for (int j = 0; j < bitmapSnippets[0].length; j++) {
						if (bitmapSnippets[i][j].isWhitePartSet()) {
							// nur wenn sich das Weisse Feld nicht in der linken
							// Reihe befindet
							if (j != 0) {
								// PuzzleTeile verschieben
								// zuerst das linke nach rechts
								imgViewName = "imageView" + i + "_" + j;
								_imageView = (ImageView) findViewById(getResources()
										.getIdentifier(imgViewName, "id",
												getPackageName()));
								// das PuzzleTeil links wird nach rechts
								// "verschoben"
								_imageView
										.setImageBitmap(bitmapSnippets[i][j - 1]
												.getPuzzleImage());

								// dann das rechte (weiße) nach links
								imgViewName = "imageView" + (i) + "_" + (j - 1);
								_imageView = (ImageView) findViewById(getResources()
										.getIdentifier(imgViewName, "id",
												getPackageName()));
								// das PuzzleTeil rechts wird nach links
								// "verschoben"
								_imageView.setImageBitmap(bitmapSnippets[i][j]
										.getPuzzleImage());

								// Positionen aktualisieren
								bitmapSnippets[i][j - 1]
										.setCurrentPosition(new int[] { i, j });
								bitmapSnippets[i][j]
										.setCurrentPosition(new int[] { i,
												j - 1 });
								// Positionen im Array aktualisieren (Referenzen
								// vertauschen)
								PuzzlePart tauschpartner;
								tauschpartner = bitmapSnippets[i][j];
								bitmapSnippets[i][j] = bitmapSnippets[i][j - 1];
								bitmapSnippets[i][j - 1] = tauschpartner;
								Log.d("onSwipeRight",
										"PuzzleTeil nach rechts verschoben");
								_clickCounter++;
								_counter.setText("" + _clickCounter);

								// Prüfen ob das Puzzle fertig ist
								if (isFinished(bitmapSnippets)) {
									// Das Puzzle Teil rechts unten wieder mit
									// dem ursprünglichen Bild befüllen und
									// Thread anhalten
									bitmapSnippets[bitmapSnippets[0].length - 1][bitmapSnippets[0].length - 1]
											.rewhite();
									imgViewName = "imageView"
											+ (bitmapSnippets[0].length - 1)
											+ "_"
											+ (bitmapSnippets[0].length - 1);
									_imageView = (ImageView) findViewById(getResources()
											.getIdentifier(imgViewName, "id",
													getPackageName()));
									_imageView
											.setImageBitmap(bitmapSnippets[bitmapSnippets[0].length - 1][bitmapSnippets[0].length - 1]
													.getPuzzleImage());
									/*
									 * Toast.makeText(PuzzleActivity.this,
									 * "feddisch", Toast.LENGTH_SHORT) .show();
									 */
									_highscore = new HighscoreDataset(
											readName(_context), _clickCounter,
											_Time.getformatedTime());
									db.addHighscoreDataset(_highscore);

								}
								return;
							}
						}
					}
				}
			}

			public void onSwipeLeft() {
				/*
				 * Toast.makeText(PuzzleActivity.this, "left",
				 * Toast.LENGTH_SHORT) .show();
				 */
				Log.d("onSwipeLeft", "Nach Links gewischt");

				// das leere Puzzleteil ermitteln und dann, falls möglich,
				// tauschen
				for (int i = 0; i < bitmapSnippets[0].length; i++) {
					for (int j = 0; j < bitmapSnippets[0].length; j++) {
						if (bitmapSnippets[i][j].isWhitePartSet()) {
							// nur wenn sich das Weisse Feld nicht in der
							// rechten Reihe befindet
							if (j != bitmapSnippets[0].length - 1) {
								// PuzzleTeile verschieben
								// zuerst das rechte nach links
								imgViewName = "imageView" + i + "_" + j;
								_imageView = (ImageView) findViewById(getResources()
										.getIdentifier(imgViewName, "id",
												getPackageName()));
								// das PuzzleTeil rechts wird nach links
								// "verschoben"
								_imageView
										.setImageBitmap(bitmapSnippets[i][j + 1]
												.getPuzzleImage());

								// dann das linke (weiße) nach rechts
								imgViewName = "imageView" + (i) + "_" + (j + 1);
								_imageView = (ImageView) findViewById(getResources()
										.getIdentifier(imgViewName, "id",
												getPackageName()));
								// das PuzzleTeil rechts wird nach links
								// "verschoben"
								_imageView.setImageBitmap(bitmapSnippets[i][j]
										.getPuzzleImage());

								// Positionen aktualisieren
								bitmapSnippets[i][j + 1]
										.setCurrentPosition(new int[] { i, j });
								bitmapSnippets[i][j]
										.setCurrentPosition(new int[] { i,
												j + 1 });
								// Positionen im Array aktualisieren (Referenzen
								// vertauschen)
								PuzzlePart tauschpartner;
								tauschpartner = bitmapSnippets[i][j];
								bitmapSnippets[i][j] = bitmapSnippets[i][j + 1];
								bitmapSnippets[i][j + 1] = tauschpartner;
								Log.d("onSwipeRight",
										"PuzzleTeil nach links verschoben");
								_clickCounter++;
								_counter.setText("" + _clickCounter);

								// Prüfen ob das Puzzle fertig ist
								if (isFinished(bitmapSnippets)) {
									// Das Puzzle Teil rechts unten wieder mit
									// dem ursprünglichen Bild befüllen und
									// Thread anhalten
									bitmapSnippets[bitmapSnippets[0].length - 1][bitmapSnippets[0].length - 1]
											.rewhite();
									imgViewName = "imageView"
											+ (bitmapSnippets[0].length - 1)
											+ "_"
											+ (bitmapSnippets[0].length - 1);
									_imageView = (ImageView) findViewById(getResources()
											.getIdentifier(imgViewName, "id",
													getPackageName()));
									_imageView
											.setImageBitmap(bitmapSnippets[bitmapSnippets[0].length - 1][bitmapSnippets[0].length - 1]
													.getPuzzleImage());
									/*
									 * Toast.makeText(PuzzleActivity.this,
									 * "feddisch", Toast.LENGTH_SHORT) .show();
									 */
									_highscore = new HighscoreDataset(
											readName(_context), _clickCounter,
											_Time.getformatedTime());
									db.addHighscoreDataset(_highscore);

								}
								return;
							}
						}
					}
				}
			}

			public void onSwipeBottom() {
				/*
				 * Toast.makeText(PuzzleActivity.this, "bottom",
				 * Toast.LENGTH_SHORT).show();
				 */
				Log.d("onSwipeBottom", "Nach unten gewischt");

				// das leere Puzzleteil ermitteln und dann, falls möglich,
				// tauschen
				for (int i = 0; i < bitmapSnippets[0].length; i++) {
					for (int j = 0; j < bitmapSnippets[0].length; j++) {
						if (bitmapSnippets[i][j].isWhitePartSet()) {
							// nur wenn sich das Weisse Feld nicht in der
							// obersten Reihe befindet
							if (i != 0) {
								// PuzzleTeile verschieben
								// zuerst das obere runter
								imgViewName = "imageView" + (i) + "_" + j;
								_imageView = (ImageView) findViewById(getResources()
										.getIdentifier(imgViewName, "id",
												getPackageName()));
								// das PuzzleTeil drüber wird nach unten
								// "verschoben"
								_imageView
										.setImageBitmap(bitmapSnippets[i - 1][j]
												.getPuzzleImage());

								// dann das untere (weiße) hoch
								imgViewName = "imageView" + (i - 1) + "_" + j;
								_imageView = (ImageView) findViewById(getResources()
										.getIdentifier(imgViewName, "id",
												getPackageName()));
								// das PuzzleTeil drunter wird nach oben
								// "verschoben"
								_imageView.setImageBitmap(bitmapSnippets[i][j]
										.getPuzzleImage());

								// Positionen aktualisieren
								bitmapSnippets[i][j]
										.setCurrentPosition(new int[] { i - 1,
												j });
								bitmapSnippets[i - 1][j]
										.setCurrentPosition(new int[] { i, j });
								// Positionen im Array aktualisieren (Referenzen
								// vertauschen)
								PuzzlePart tauschpartner;
								tauschpartner = bitmapSnippets[i - 1][j];
								bitmapSnippets[i - 1][j] = bitmapSnippets[i][j];
								bitmapSnippets[i][j] = tauschpartner;
								Log.d("onSwipeBottom",
										"PuzzleTeil nach unten verschoben");
								_clickCounter++;

								_counter.setText("" + _clickCounter);

								// Prüfen ob das Puzzle fertig ist
								if (isFinished(bitmapSnippets)) {
									// Das Puzzle Teil rechts unten wieder mit
									// dem ursprünglichen Bild befüllen und
									// Thread anhalten
									bitmapSnippets[bitmapSnippets[0].length - 1][bitmapSnippets[0].length - 1]
											.rewhite();
									imgViewName = "imageView"
											+ (bitmapSnippets[0].length - 1)
											+ "_"
											+ (bitmapSnippets[0].length - 1);
									_imageView = (ImageView) findViewById(getResources()
											.getIdentifier(imgViewName, "id",
													getPackageName()));
									_imageView
											.setImageBitmap(bitmapSnippets[bitmapSnippets[0].length - 1][bitmapSnippets[0].length - 1]
													.getPuzzleImage());
									/*
									 * Toast.makeText(PuzzleActivity.this,
									 * "feddisch", Toast.LENGTH_SHORT) .show();
									 */
									_highscore = new HighscoreDataset(
											readName(_context), _clickCounter,
											_Time.getformatedTime());
									db.addHighscoreDataset(_highscore);

								}
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

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		_width = displaymetrics.widthPixels;
		// int height = dimension.heightPixels;

		Log.v("Display Breite", "" + _width);
		// _r1.setLayoutParams(new
		// LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		// _r1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// _width/3));
		// _r2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// _width/3));
		// _r3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// _width/3));

		// _gameTable.setLayoutParams(new
		// LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		// _width+"px"));

		/*
		 * _TimeRefresh = new Runnable() { public void run(){
		 * _counter.setText(""+_clickCounter); _handler.postDelayed(this, 1000);
		 * }
		 */
	};

	@Override
	protected void onStop() {

		super.onStop();

		if (bitmapFull != null) {
			bitmapFull.recycle();
		}

	}

	Bitmap bitmapFull = null;

	public void carveBitmap() {

		// get intent data
		Intent intent = getIntent();

		int position = 0;
		position = intent.getExtras().getInt("id");

		if (position != 0) {

			// Selected image id

			ImageAdapter imageAdapter = new ImageAdapter(this);

			int bitmapID = imageAdapter.mThumbIds[position];

			bitmapFull = BitmapFactory.decodeResource(getResources(), bitmapID);
		}

		else {

			try {
				Bitmap bitmapStorage = null;
				Uri imageUri = Uri.parse(intent.getStringExtra("imageUri"));
				bitmapStorage = MediaStore.Images.Media.getBitmap(
						this.getContentResolver(), imageUri);

				/**
				 * Erst ab API Level 13 Display display =
				 * getWindowManager().getDefaultDisplay(); Point size = new
				 * Point(); display.getSize(size); int width = size.x; int
				 * height = size.y;
				 **/

				// Seitenverhältnis des geladenen Bildes auslesen und berechnen
				double aspectRatioH = bitmapStorage.getHeight();
				double aspectRatioW = bitmapStorage.getWidth();
				double ratio = aspectRatioH / aspectRatioW;

				// Paramter der Bildschirmgröße und Verhältnis des Bildes
				// ermitteln
				Display display = getWindowManager().getDefaultDisplay();
				@SuppressWarnings("deprecation")
				int width_disp = display.getWidth();
				int width = (int) (width_disp * ratio);
				@SuppressWarnings("deprecation")
				int height_disp = display.getHeight();
				int height = (int) (height_disp * ratio);
				boolean filter = true;

				// Ausgabe der Bildkonvertierung-Paramter
				Log.d("Bildgröße", "Ratio " + aspectRatioH + " x "
						+ aspectRatioW + " = " + ratio + "  Displaymaße "
						+ height_disp + " x " + width_disp + "  Angepasst "
						+ height + " x " + width);

				// Bild mit ermittelten Paramtern anpassen
				bitmapFull = Bitmap.createScaledBitmap(bitmapStorage, width,
						height, filter);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Anzahl der seitlichen Puzzleteile
		puzzleSize = GlobalConstants.EASY;

		try {

			if (bitmapFull.getWidth() != bitmapFull.getHeight()) {
				Log.d(getLocalClassName(), "Bild nicht quadratisch");
				bitmapFull = cropBitmap(bitmapFull);

			}

			// Breite + Höhe des Ausschnitts berechnen
			int targetWidth = bitmapFull.getWidth() / puzzleSize;
			int targetHeight = bitmapFull.getHeight() / puzzleSize;

			bitmapSnippets = new PuzzlePart[puzzleSize][puzzleSize];
			PuzzlePart bitmapSnip;
			bitmapRandom = new ArrayList<PuzzlePart>();

			// Puzzleteile erstellen und in Array speichern
			int startPixelY = 0;
			for (int i = 0; i < puzzleSize; i++) {

				int startPixelX = 0;
				for (int j = 0; j < puzzleSize; j++) {

					bitmapSnip = new PuzzlePart(
							Bitmap.createBitmap(bitmapFull, startPixelX,
									startPixelY, targetWidth, targetHeight));
					bitmapSnip.setOriginPosition(new int[] { i, j });
					bitmapSnippets[i][j] = bitmapSnip;
					bitmapRandom.add(bitmapSnip);

					startPixelX += targetWidth;
				}
				startPixelY += targetHeight;
			}

			// Das Puzzle-Teil rechts unten ausgrauen
			bitmapSnippets[puzzleSize - 1][puzzleSize - 1].whiteout();
			Log.d("puzzleteile array",
					bitmapSnippets[puzzleSize - 1][puzzleSize - 1]
							.originPositionToString() + "wird ausgegraut");

			// Puzzleteile zufällig in den ImageViews anordnen
			int randomInt;
			String imgViewName;

			for (int i = 0; i < puzzleSize; i++) {

				for (int j = 0; j < puzzleSize; j++) {

					randomInt = (int) Math.round((Math.random() * (bitmapRandom
							.size() - 1)));

					imgViewName = "imageView" + i + "_" + j;

					_imageView = (ImageView) findViewById(getResources()
							.getIdentifier(imgViewName, "id", getPackageName()));
					_imageView.setImageBitmap(bitmapRandom.get(randomInt)
							.getPuzzleImage());
					bitmapRandom.get(randomInt).setCurrentPosition(
							new int[] { i, j });
					Log.d("Anordnung PuzzleTeile", "Anordnung von Teil "
							+ bitmapRandom.get(randomInt)
									.originPositionToString()
							+ "an Position "
							+ bitmapRandom.get(randomInt)
									.currentPositionToString());
					bitmapSnippets[i][j] = bitmapRandom.get(randomInt);
					bitmapRandom.remove(randomInt);
				}

			}

		}

		catch (Exception ex) {
			String errorMessage = "Exception in Methode QuarterBitmapActivity::onCreate() bei Bitmap-Verarbeitung aufgetreten: "
					+ ex.getMessage();
			Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
		}

	}

	public Bitmap cropBitmap(Bitmap original) {
		Bitmap cropBitmap;
		if (original.getWidth() >= original.getHeight()) {

			cropBitmap = Bitmap.createBitmap(original, original.getWidth() / 2
					- original.getHeight() / 2, 0, original.getHeight(),
					original.getHeight());
			Log.d(getLocalClassName(), "Bild wurde angepasst");

		} else {

			cropBitmap = Bitmap.createBitmap(original, 0, original.getHeight()
					/ 2 - original.getWidth() / 2, original.getWidth(),
					original.getWidth());
			Log.d(getLocalClassName(), "Bild wurde angepasst");
		}
		return cropBitmap;
	}

	public PuzzlePart[][] getBitmapSnippets() {
		return this.bitmapSnippets;
	}

	public Boolean isFinished(PuzzlePart[][] puzzleTeile) {
		for (int i = 0; i < puzzleTeile[0].length; i++) {
			for (int j = 0; j < puzzleTeile[0].length; j++) {
				if (puzzleTeile[i][j].isOnOriginPosition()) {
					Log.d("isFinished", "puzzleTeil ist an richtiger Stelle");
				} else {
					Log.d("isFinished",
							"mindestens ein puzzleTeil ist nicht an richtiger Stelle");
					return false;
				}

			}
		}

		return true;
	}

	/**
	 * public void setPuzzle (){
	 * 
	 * _imageView
	 * 
	 * }
	 */

}
