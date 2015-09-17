package youxian.ncumap;



import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("ViewConstructor")
class UploadLayout extends FrameLayout{
	Context con;
	LinearLayout container;
	EditText title_upload;
	EditText context_upload;
	static EditText imgaddress_upload;
	static TextView starttime;
	static TextView endtime;
	ImageButton uploadimage_upload;
	static ImageButton imgcancel_upload;
	Button submit_upload;
	DateTimeLayout datetimepage;
	CheckBox club;
	CheckBox intern;
	CheckBox prize;
	CheckBox life;
	CheckBox arts;
	CheckBox service;
	List<CheckBox>checks;
	String type;
	ActivityData mData;
	static byte[] byteArray;
	public UploadLayout(Context context, LinearLayout l) {
		super(context);
		init(context,l);
		// TODO Auto-generated constructor stub
	}
	public UploadLayout(Context context, LinearLayout l, ActivityData a) {
		super(context);
		mData=a;
		init(context,l);
		// TODO Auto-generated constructor stub
	}
	public UploadLayout(Context context, AttributeSet attrs, LinearLayout l) {
		super(context, attrs);
		init(context,l);
		// TODO Auto-generated constructor stub
	}
	public UploadLayout(Context context, AttributeSet attrs, int defStyle, LinearLayout l) {
		super(context, attrs, defStyle);
		init(context,l);
		// TODO Auto-generated constructor stub
	}
	
	private void init(Context context, LinearLayout l){
		con=context;
		container=l;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(mData==null)
			l.removeAllViews();
	    l.addView(inflater.inflate(R.layout.upload, this));//描述跳出條架構的xml檔案  抽出來存到this	    	
	    findview();
		datetimepage.setVisibility(INVISIBLE);
    	timeclick();
    	submitclick();
    	uploadimageclick();
    	setcheckbox();
    	if(mData!=null){
    		starttime.setText(mData.getStartTime());
			endtime.setText(mData.getEndTime());
			title_upload.setText(mData.getTitle());
			context_upload.setText(mData.getContext());
			if(mData.getFileBytes()!=null)
				imgaddress_upload.setText("picture from DB");
			String s =mData.getType();
			for(int i=0;i<s.length();i++){
				if(s.charAt(i)=='1')
					checks.get(i).setChecked(true);
			}
    	}
    	else{
    		byteArray=null;
    	}
    	imgcancel_upload.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imgaddress_upload.setText("");
				byteArray=null;
			}
			
		});
	}
	private void uploadimageclick() {
		// TODO Auto-generated method stub
		uploadimage_upload.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(mData==null)
					MainActivity.getImage((Activity)con,0);
				else
					EditActivity.getImage((Activity)con,0);
			}
		
		});
	}
	public static void storeImage(Uri imgUri,Bitmap bitmap) {
		// TODO Auto-generated method stub
		imgaddress_upload.setText(imgUri.getPath().toString());
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byteArray = stream.toByteArray();
		//int bytes = bitmap.getByteCount();
		//ByteBuffer buffer = ByteBuffer.allocate(bytes);
		//bitmap.copyPixelsToBuffer(buffer);
		//byteArray = buffer.array();
	}
	
	private void setcheckbox() {
		// TODO Auto-generated method stub
		type="";
		checks=new ArrayList<CheckBox>();
		checks.add(club);
		checks.add(intern);
		checks.add(prize);
		checks.add(life);
		checks.add(arts);
		checks.add(service);
		for(CheckBox c:checks){
			if(c.isChecked())
				type=type+"1";
			else
				type=type+"0";
		}
	}
	private void submitclick() {
		// TODO Auto-generated method stub
		submit_upload.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(starttime.getText().charAt(0)!='s' && endtime.getText().charAt(0)!='s' && title_upload.getText().length()!=0
						&& context_upload.getText().length()!=0){
					AlertDialog.Builder builder = new AlertDialog.Builder(con);
		      		builder.setMessage("Upload The Activity ?")
		      		.setCancelable(false)
		      		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							setcheckbox();
							int id=0;
							if(mData!=null)
								id=mData.getActivityId();
							
							/*final ActivityData a=new ActivityData(id, MainActivity.getUser(), getstringtime(), ""+starttime.getText(), 
									""+endtime.getText(), title_upload.getText().toString(),context_upload.getText().toString(),type,byteArray);
							*/
							final ProgressDialog myDialog = ProgressDialog.show
				        			  (con, "loading", "loading list", true);					
				        		    new AsyncTask<Void, Void, Void>()
				        	            {       		   	
				        	                @Override
				        	                protected Void doInBackground(Void... params)
				        	                {
				        	                	String s=null;
				        	                	//temp for work
				        	                	ActivityData a=null;
				        	                	if(mData!=null){
				        	                		boolean isupdate = Connector.SendEditUserDataCommand(a);
				        	                		Log.e(""+isupdate,"Error");
				        	                	}		        	                		
				        	                	else{
				        	                		s=Connector.SendWriteDataCommand(a);
				        	                	}
				        	                		
				        	              	  	Log.e(s,"Error");
				        	                	
				        	    	       		if(s==null){
				        	    	       				myDialog.dismiss();
				        	    	       				Log.e("1","Error");
				                             			//Toast.makeText(RoutePlan.this,"NotFound", Toast.LENGTH_SHORT).show();
				                             			this.onCancelled();
				                             		}
				        	    	       		//myDialog.dismiss();
				        	        	            return null;
				        	        	        }
				        	              @Override
				   	        	     protected void onPostExecute(Void result)//收尾
				   	        	     {   	
				        	            	  myDialog.dismiss();
				        	            	  if(mData!=null){
				        	            		  AlertDialog.Builder builder = new AlertDialog.Builder(con);
				        	            		  builder.setMessage("Edit Success")
				        	            		         .setCancelable(false)
				        	            		         .setPositiveButton("OK", new DialogInterface.OnClickListener() {
				        	            		             public void onClick(DialogInterface dialog, int id) {
				        	            		            	 ((Activity) con).finish();
				        	            		             }
				        	            		         });
				        	            		  AlertDialog alert = builder.create();
				        	            		  alert.show();		        	            		  
				        	            	  }
				        	            	  if(mData==null){
				        	            		  AlertDialog.Builder builder = new AlertDialog.Builder(con);
				        	            		  builder.setMessage("Upload Success")
				        	            		         .setCancelable(false)
				        	            		         .setPositiveButton("OK", new DialogInterface.OnClickListener() {
				        	            		             public void onClick(DialogInterface dialog, int id) {
				        	            		            	 MainActivity.user_button.performClick();
				        	            		             }
				        	            		         });
				        	            		  AlertDialog alert = builder.create();
				        	            		  alert.show();		        	            		  
				        	            	  }
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
				else{
					final AlertDialog alertDialog = getAlertDialog("Error","Please make sure you have filled in all info");
					alertDialog.show();
				}
				
			}
			
		});
	}
	private AlertDialog getAlertDialog(String title,String message){
	    //產生一個Builder物件
		Builder builder = new AlertDialog.Builder(con);
		//設定Dialog的標題
		builder.setTitle(title);
		//設定Dialog的內容
		builder.setMessage(message);
		//設定Positive按鈕資料
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		     //按下按鈕時顯示快顯
		     Toast.makeText(con, "您按下OK按鈕", Toast.LENGTH_SHORT).show();
		     }
	    });
       //設定Negative按鈕資料
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
           @Override
            public void onClick(DialogInterface dialog, int which) {
              //按下按鈕時顯示快顯
             Toast.makeText(con, "您按下Cancel按鈕", Toast.LENGTH_SHORT).show();
          }
        });
       //利用Builder物件建立AlertDialog
        return builder.create();
   }
	private String getstringtime() {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		String s=(""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH)+" "
				+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE));
		return s;
	}
	private void timeclick() {
		// TODO Auto-generated method stub

		OnClickListener textclick=new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datetimepage.setVisibility(VISIBLE);
				if(v.equals(starttime)){
					DateTimeLayout.slideout(1);
				}
				else if(v.equals(endtime)){
					DateTimeLayout.slideout(0);
				}
			}
			
		};
		starttime.setOnClickListener(textclick);
		endtime.setOnClickListener(textclick);
	}
	private void findview() {
		// TODO Auto-generated method stub
		title_upload=(EditText)findViewById(R.id.title_upload);
		context_upload=(EditText)findViewById(R.id.context_upload);
		uploadimage_upload=(ImageButton)findViewById(R.id.uploadimage_upload);
		submit_upload=(Button)findViewById(R.id.submit_upload);
		starttime=(TextView)findViewById(R.id.starttime_upload);
		endtime=(TextView)findViewById(R.id.endtime_upload);
		datetimepage=(DateTimeLayout)findViewById(R.id.datetimepage);
		club=(CheckBox)findViewById(R.id.club_upload);
		intern=(CheckBox)findViewById(R.id.intern_upload);
		prize=(CheckBox)findViewById(R.id.prize_upload);
		life=(CheckBox)findViewById(R.id.life_upload);
		arts=(CheckBox)findViewById(R.id.arts_upload);
		service=(CheckBox)findViewById(R.id.service_upload);
		imgaddress_upload=(EditText)findViewById(R.id.imgaddress_upload);
		imgcancel_upload=(ImageButton)findViewById(R.id.imgcancel_upload);
	}
	static void setStarttime(String s){
		starttime.setText(s);
	}
	static void setEndtime(String s){
		endtime.setText(s);
	}
	
}