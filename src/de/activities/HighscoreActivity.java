package de.activities;


import de.logic.DatabaseHandler;
import de.schiebepuzzle2.R;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

public class HighscoreActivity extends Activity {
	
	protected DatabaseHandler _dbHandler;
	protected ListView _listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highscore);

		_listview = (ListView) findViewById(R.id.listviewHighscore);

		_dbHandler = new DatabaseHandler(this);
		
		
	}

}
