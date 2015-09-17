package youxian.ncumap;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;




public class EditActivity extends Activity{
	ImageButton previous_page_edit;
	ImageButton trash_edit;
	LinearLayout edit_layout;
	UploadLayout uploadlayout;
	ActivityData myData;
	ImageButton user;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.useredit);
	    findview();
	    setdatafrombundle();
	    addUploadLayout();
	    trashclick();
	    previousclick();
	  }
	private void previousclick() {
		// TODO Auto-generated method stub
		previous_page_edit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
	}
	@Override
    public void onPause() {
        super.onPause();
        MainActivity.user_button.performClick();
        
    }
	private void trashclick() {
		// TODO Auto-generated method stub
		trash_edit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			  AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
      		  builder.setMessage("Delete This Activity ?")
      		         .setCancelable(false)
      		         .setPositiveButton("OK", new DialogInterface.OnClickListener() {
      		   		@Override
      				public void onClick(DialogInterface dialog, int which) {
      				     //按下按鈕時顯示快顯
      		   		final ProgressDialog myDialog = ProgressDialog.show
              			  (EditActivity.this, "loading", "loading list", true);					
            		    new AsyncTask<Void, Void, Void>()
            	            {
            	                @Override
            	                protected Void doInBackground(Void... params)
            	                {
            	                	boolean isdelete = Connector.SendDeleteUserDataCommand(myData.getActivityId());	    	             	
            	    	       		if(isdelete){
            	    	       			myDialog.dismiss();
            	    	       			//Toast.makeText(EditActivity.this,"success", Toast.LENGTH_SHORT).show();
            	    	       		}
            	    	       		else{
            	    	       			myDialog.dismiss();
        	    	       				//Log.e("1","Error");
                             			//Toast.makeText(EditActivity.this,"fail", Toast.LENGTH_SHORT).show();
                             			this.onCancelled();
            	    	       		}
            	        	            return null;
            	        	        }
            	              @Override
       	        	     protected void onPostExecute(Void result)//收尾
       	        	     {   	           	             	            	  
            	            myDialog.dismiss();
            	            AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
  	            		  	builder.setMessage("Delete Success")
  	            		         .setCancelable(false)
  	            		         .setPositiveButton("OK", new DialogInterface.OnClickListener() {
  	            		             public void onClick(DialogInterface dialog, int id) {
  	            		            	 EditActivity.this.finish();
  	            		             }
  	            		         });
  	            		  	AlertDialog alert = builder.create();
  	            		  	alert.show();		  
      	        	     }
            	            }.execute();		    
      				     }
      		   		})
      		  		 .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}     		  			 
      		  		 });
      		  AlertDialog alert = builder.create();
      		  alert.show();		  
			}			
		});
	}
	private void addUploadLayout() {
		// TODO Auto-generated method stub
		if(myData!=null)
			uploadlayout=new UploadLayout(EditActivity.this,edit_layout,myData);
	}
	 public static void getImage(Activity activity, int requestCode){
		  Intent intent = new Intent();
	      
	      // 過濾檔案格式
	      intent.setType( "image/*" );
	      intent.setAction(Intent.ACTION_GET_CONTENT);    
	      // 建立 "檔案選擇器" 的 Intent  (第二個參數: 選擇器的標題)
	      Intent destIntent = Intent.createChooser( intent, "select Image" );
		  activity.startActivityForResult(destIntent,requestCode);
	  }
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
		  Bitmap bitmap = null;
		  if (resultCode == RESULT_OK && data.getData()!=null) {
			  InputStream stream;
			try {
				stream = getContentResolver().openInputStream(data.getData());
				bitmap = BitmapFactory.decodeStream(stream);
		        stream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //testimg.setImageBitmap(bitmap);
	        UploadLayout.storeImage(data.getData(),bitmap);
	      }
	  }
	private void findview() {
		// TODO Auto-generated method stub
		previous_page_edit=(ImageButton)findViewById(R.id.previous_page_edit);
		trash_edit=(ImageButton)findViewById(R.id.trash_edit);
		edit_layout=(LinearLayout)findViewById(R.id.edit_layout);
	}
	private void setdatafrombundle() {
		// TODO Auto-generated method stub
		Bundle bundle = getIntent().getExtras();
		myData=(ActivityData) bundle.getSerializable("clickedData");
	}

}