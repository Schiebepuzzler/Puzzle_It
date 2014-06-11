package de.activities;

import de.logic.ImageAdapter;
import de.schiebepuzzle2.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class SelectPictureActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_selectpicture);

	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this));

	    gridview.setOnItemClickListener(new OnItemClickListener() {	
	    	 @Override
	            public void onItemClick(AdapterView<?> parent, View v,
	                    int position, long id) {
	 
	                // Sending image id to FullScreenActivity
	                Intent i = new Intent(getApplicationContext(), PuzzleActivity.class);
	                // passing array index
	                i.putExtra("id", position);
	                startActivity(i);
	            }
	    });
	}
}
