package de.activities;

import de.logic.DatabaseHandler;
import de.logic.HighscoreDataset;
import de.schiebepuzzle2.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	protected Button _button_newGame;
	protected Button _button_Highscore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_button_newGame = (Button) findViewById(R.id.buttonMainNewGame);
		_button_newGame.setOnClickListener(this);

		_button_Highscore = (Button) findViewById(R.id.buttonMainHighscore);
		_button_Highscore.setOnClickListener(this);

		DatabaseHandler db = new DatabaseHandler(this);

		//db.addHighscoreDataset(new HighscoreDataset("N.N.1", 0, null));
		//db.addHighscoreDataset(new HighscoreDataset("N.N.2", 0, null));
		//db.addHighscoreDataset(new HighscoreDataset("N.N.3", 0, null));
		//db.addHighscoreDataset(new HighscoreDataset("N.N.4", 0, null));
		//db.addHighscoreDataset(new HighscoreDataset("N.N.5", 0, null));

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.buttonMainNewGame:
			Intent intent1 = new Intent(this, SelectPictureActivity.class);
			startActivity(intent1);
			break;
		case R.id.buttonMainHighscore:
			Intent intent2 = new Intent(this, HighscoreActivity.class);
			startActivity(intent2);
			break;
		}

	}

}
