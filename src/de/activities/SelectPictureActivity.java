package de.activities;

import de.logic.ImageAdapter;
import de.schiebepuzzle2.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import java.io.FileNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class SelectPictureActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_selectpicture);

	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this));
	    
	    Button buttonLoadImage = (Button)findViewById(R.id.loadimage);

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
	    
	    buttonLoadImage.setOnClickListener(new Button.OnClickListener(){

	        @Override
	        public void onClick(View arg0) {
	         // TODO Auto-generated method stub
	         Intent select = new Intent(Intent.ACTION_PICK,
	           android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	         startActivityForResult(select, 0);
	        }});
	        }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // TODO Auto-generated method stub
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode==RESULT_OK){
     Uri imageUri = data.getData();


      // Sending image Uri to FullScreenActivity
      Intent i = new Intent(SelectPictureActivity.this, PuzzleActivity.class);
      // passing image Uri
      i.putExtra("imageUri", imageUri.toString());
      startActivity(i);
      
     }
    }
	}

