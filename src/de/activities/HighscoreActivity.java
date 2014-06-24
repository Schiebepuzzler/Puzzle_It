package de.activities;

import java.util.List;

import de.logic.DatabaseHandler;
import de.logic.HighscoreDataset;
import de.schiebepuzzle2.R;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

public class HighscoreActivity extends Activity {

	protected DatabaseHandler _dbHandler;
	protected TextView _firstPlace;
	protected TextView _secondPlace;
	protected TextView _thirdPlace;
	protected TextView _fourthPlace;
	protected TextView _fifthPlace;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highscore);

		_dbHandler = new DatabaseHandler(this);

		_firstPlace = (TextView) findViewById(R.id.firstPlace);
		_secondPlace = (TextView) findViewById(R.id.secondPlace);
		_thirdPlace = (TextView) findViewById(R.id.thirdPlace);
		_fourthPlace = (TextView) findViewById(R.id.fourthPlace);
		_fifthPlace = (TextView) findViewById(R.id.fifthPlace);

		try {
		for (int i = 0; i < 5; i++) {
			switch (i) {

			case 0:
				_firstPlace.setText(_dbHandler.getHighscoreDataset(1)
						.toString());

				break;
			case 1:
				_secondPlace.setText(_dbHandler.getHighscoreDataset(2)
						.toString());

				break;
			case 2:
				_thirdPlace.setText(_dbHandler.getHighscoreDataset(3)
						.toString());

				break;
			case 3:
				_fourthPlace.setText(_dbHandler.getHighscoreDataset(4)
						.toString());

				break;
			case 4:
				_fifthPlace.setText(_dbHandler.getHighscoreDataset(5)
						.toString());
				break;
			default:
				break;
			}
			
		}
		
		}catch(Exception e){
			Log.e("ERROR", e.getMessage());
		}
		
/*
		_firstPlace.setText(_dbHandler.getHighscoreDataset(1).toString());
		_secondPlace.setText(_dbHandler.getHighscoreDataset(2).toString());
		_thirdPlace.setText(_dbHandler.getHighscoreDataset(3).toString());
		_fourthPlace.setText(_dbHandler.getHighscoreDataset(4).toString());
		_fifthPlace.setText(_dbHandler.getHighscoreDataset(5).toString());

*/
	}

}
