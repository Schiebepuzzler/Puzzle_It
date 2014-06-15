package de.activities;

import de.schiebepuzzle2.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
	protected Button _button_newGame = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_button_newGame = (Button) findViewById(R.id.buttonMainNewGame);
		_button_newGame.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		Intent intent = new Intent(this, SelectPictureActivity.class);
		startActivity(intent);
		
	}

}
