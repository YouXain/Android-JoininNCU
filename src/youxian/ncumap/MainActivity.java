package youxian.ncumap;




import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import youxian.ncumap.ActivityData;
import youxian.ncumap.Connector;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Bundle;



import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends FragmentActivity{
	//bottom layout
	ImageButton list_button;
	static ImageButton user_button;
	ImageButton follow_button;
	ImageButton write_button;
	ImageButton update_button;
	//top layout
	TextView top_title;
	UploadLayout uploadlayout;
	FrameLayout main_layout;
	UserLayout userlayout;
	LoginLayout loginlayout;
	ListLayout listlayout;
	FollowLayout followlayout;
	List<ActivityData>alldata;
	List<ActivityData>userdata=new ArrayList<ActivityData>();
	List<ActivityData>todaydata;
	boolean isHomePageShowed=false;
	ImageView homepage;
	ImageView aboutus;
	AlarmReceiver alarm=new AlarmReceiver();
	GlobalClass globalVariable;
	private Boolean firstTime = null;
	private Boolean firstFollow = true;
	private Boolean firstList = true;
	private Boolean firstMap = false;
	private static Boolean firstShow = false;
	private int mode=0;
	//String FB_id;
	//ImageView log;
	//Facebook fb;
  public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //FB_id=getString(R.string.fb_id);
    //fb= new Facebook(FB_id);
    //log=(ImageView)findViewById(R.id.top_log);
    //logclick();
    initLayout();
    globalVariable = (GlobalClass) getApplicationContext();
    globalVariable.setAlarm(alarm);
    firstTime=isFirstTime();
    /*
    PackageInfo packageInfo;
    try {
        packageInfo = getPackageManager().getPackageInfo("com.example.app", PackageManager.GET_SIGNATURES);
        for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String key = new String(Base64.encode(md.digest(), 0));
                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Hash key", key);
        } 
        }
        catch (NameNotFoundException e1) {
        	Log.e("Name not found", e1.toString());
        }
 
        catch (NoSuchAlgorithmException e) {
        	Log.e("No such an algorithm", e.toString());
        }
        catch (Exception e){
        	Log.e("Exception", e.toString());
        }
    */    
    //initHomePage();
    //updateLogImage();
  }
  private boolean isFirstTime() {
		if (firstTime == null) {
			SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
			firstTime = mPreferences.getBoolean("firstTime", true);
			if (firstTime) { 
			    SharedPreferences.Editor editor = mPreferences.edit();
			    editor.putBoolean("firstTime", false);
			    editor.commit();
				firstMap = true;
				firstShow = true;
				//globalVariable.setAlarm(new AlarmReceiver());
				//alarm=globalVariable.getAlarm();
				//alarm=new AlarmReceiver();
			}
		}
		if(!firstTime){
			//SharedPreferences appSharedPrefs = PreferenceManager
		  	//		  .getDefaultSharedPreferences(MainActivity.this);
		    //Gson gson = new Gson();
			//Gson gson = new GsonBuilder()
			//.registerTypeAdapter(Intent.class, new InterfaceAdapter<Intent>())
			//.registerTypeAdapter(PendingIntent.class, new InterfaceAdapter<PendingIntent>())
			//.setPrettyPrinting()
            //.create();
			//String json = appSharedPrefs.getString("Alarm", "");
		    //alarm = gson.fromJson(json, AlarmReceiver.class);
		    //globalVariable.setAlarm(alarm);
		    //alarm=globalVariable.getAlarm();
		    //alarm = gson.fromJson(json, AlarmReceiver.class);
		    //if(alarm!=null){
		    //	Log.e("alarm not null", "123");
		    //}
		    //else{
		    //  	globalVariable.setAlarm(new AlarmReceiver());
			//	alarm=globalVariable.getAlarm();
			//	Log.e("alarm null", "123");
		    //}
		        	
		}
		return firstTime;
	}
  /*
  private void logclick() {
	// TODO Auto-generated method stub
	log.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Session.openActiveSession(MainActivity.this, true, new Session.StatusCallback() {

			      // callback when session changes state
			      @Override
			      public void call(Session session, SessionState state, Exception exception) {
			        if (session.isOpened()) {

			          // make request to the /me API
			          Request.newMeRequest(session, new Request.GraphUserCallback() {

			            // callback after Graph API response with user object
			            

						@Override
						public void onCompleted(GraphUser user, Response response) {
							// TODO Auto-generated method stub
							
						}
			          }).executeAsync();
			        }
			      }

				
			    });
		}
	});
}*/
@Override
	protected void onResume() {
		// animateIn this activity
		//ActivitySwitcher.animationIn(findViewById(R.id.total_layout), getWindowManager());
		super.onResume();
		if(mode==1){
			followlayout=new FollowLayout(MainActivity.this, main_layout, alarm);
      	  	top_title.setText("Liked Activity");
      	  	update_button.setVisibility(View.INVISIBLE);
		}
	    //firstTime=isFirstTime();
	}
  @Override
	protected void onPause() {
		// animateIn this activity
		//ActivitySwitcher.animationIn(findViewById(R.id.total_layout), getWindowManager());
	  	

  		
		super.onPause();
		//firstTime=false;
		
	}
  @Override
	protected void onStop() {
	  
	  //SharedPreferences appSharedPrefs = PreferenceManager
	//		  .getDefaultSharedPreferences(MainActivity.this);
      //Editor prefsEditor = appSharedPrefs.edit();
      //Gson gson = new Gson();
      //Gson gson = new GsonBuilder()
      //.registerTypeAdapter(Intent.class, new InterfaceAdapter<Intent>())
      //.registerTypeAdapter(PendingIntent.class, new InterfaceAdapter<PendingIntent>())
      //.setPrettyPrinting()
      //.create();
      //String json = gson.toJson(globalVariable.getAlarm());
      //prefsEditor.putString("Alarm", json);
      //prefsEditor.commit();
      //Log.e("456", "456");
	  super.onStop();
}
  class IntentSerializer implements JsonSerializer<Intent>
  {

	@Override
	public JsonElement serialize(Intent src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();

	      return jsonObject;
	}
  }
  @Override
	protected void onDestroy() {
	  super.onDestroy();
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
	  super.onActivityResult(requestCode, resultCode, data);

  }
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
	  if (keyCode == KeyEvent.KEYCODE_BACK && !isHomePageShowed)
      {
    	  mode=0;
		  addHomePage();
		  update_button.setVisibility(View.INVISIBLE);
		  top_title.setText("");
          return true;
      }
	  else if (keyCode == KeyEvent.KEYCODE_BACK && isHomePageShowed)
      {
    	  final AlertDialog alertDialog = finishAlertDialog("確認通知","要結束中大參一腳?");
		  alertDialog.show();
          
          return true;
      }
      
      return super.onKeyDown(keyCode, event);
  }
  private void initLayout() {
	// TODO Auto-generated method stub
	  main_layout=(FrameLayout)findViewById(R.id.main_layout);
	  top_title=(TextView)findViewById(R.id.top_title);
	  homepage=(ImageView)findViewById(R.id.homepage);
	  aboutus=(ImageView)findViewById(R.id.about_us);
	  aboutusClick();
	  update_button = (ImageButton)findViewById(R.id.top_update);
	  updatebuttonclick();
	  //update_button.setClickable(false);
	  update_button.setVisibility(View.GONE);
	  list_button = (ImageButton)findViewById(R.id.list_button);
	  listbuttonclick();
	  //user_button = (ImageButton)findViewById(R.id.user_button);
	  //userbuttonclick();
	  follow_button = (ImageButton)findViewById(R.id.follow_button);
	  followbuttonclick();
	  write_button = (ImageButton)findViewById(R.id.write_button);
	  writebuttonclick();
        	    
  }
  private void aboutusClick() {
	// TODO Auto-generated method stub
	  aboutus.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent();
    	    i.setClass(MainActivity.this, ProducerActivity.class);
    	    startActivity(i);   
		}
		  
	  });
}

  private void addHomePage() {
	  
	  homepage.setImageResource(R.drawable.ic_apphome);
	  main_layout.removeAllViews();
	  main_layout.addView(homepage);
	  main_layout.addView(aboutus);
	  aboutusClick();
	  isHomePageShowed=true;
		// TODO Auto-generated method stub
		
  }
  private void updatebuttonclick() {
	// TODO Auto-generated method stub
	  update_button.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final ProgressDialog myDialog = ProgressDialog.show
					  (MainActivity.this, "loading", "loading list", true);					
				    new AsyncTask<Void, Void, Void>()
			            {
			                @Override
			                protected Void doInBackground(Void... params)
			                {
			                	alldata=Connector.SendGetDataCommand();	    	             	
			    	       		if(alldata==null){
			    	       				myDialog.dismiss();
			    	       				//Log.e("1","Error");
		                   			//Toast.makeText(RoutePlan.this,"NotFound", Toast.LENGTH_SHORT).show();
		                   			this.onCancelled();
		                   		}
			    	       		myDialog.dismiss();
			        	            return null;
			        	        }
			              @Override
		     	     protected void onPostExecute(Void result)//收尾
		     	     {   	
			            	if(alldata==null){
			            		myDialog.dismiss();
			            		//Log.e("2","Error");
			            		Toast.makeText(MainActivity.this,"連線失敗:你沒上網或是我沒開server", Toast.LENGTH_SHORT).show();
			            	}
			            	else{
			            		main_layout.removeAllViews();
			            		listlayout=new ListLayout(MainActivity.this,alldata, alarm, firstTime);
			            		main_layout.addView(listlayout);
			            		top_title.setText("Activity List");
			            		myDialog.dismiss();
			            	}           	  
		    	     }
			            }.execute();		    
		}
		  
	  });
	  
}
private void writebuttonclick() {
	// TODO Auto-generated method stub
	  write_button.setOnClickListener(new OnClickListener(){
          public void onClick(View v){
        	  mode=0;
        	  if(alldata!=null){
        			Intent i = new Intent();
        			Bundle bundle = new Bundle();
        			bundle.putBoolean("isfirstTime", firstMap);
        			bundle.putInt("key", 0);
        			bundle.putSerializable("mapData", getTodayData());
        			i.putExtras(bundle);
        		    i.setClass(MainActivity.this, MapActivity.class);
        		    firstMap=false;
        		    startActivity(i);    
        		}
        		else{
        			final ProgressDialog myDialog = ProgressDialog.show
        	  			  (MainActivity.this, "loading", "loading list", true);					
        			    new AsyncTask<Void, Void, Void>()
        		            {
        		                @Override
        		                protected Void doInBackground(Void... params)
        		                {
        		                	alldata=Connector.SendGetDataCommand();	    	             	
        		    	       		if(alldata==null){
        		    	       				myDialog.dismiss();
        		    	       				
        	                     			//Toast.makeText(MainActivity.this,"NotFound", Toast.LENGTH_SHORT).show();
        	                     			this.onCancelled();
        	                     		}
        		    	       		myDialog.dismiss();
        		        	            return null;
        		        	        }
        		              @Override
        	       	     protected void onPostExecute(Void result)//收尾
        	       	     {   	
        		            	if(alldata==null){
        		            		myDialog.dismiss();
        		            		//Log.e("Data is null","Error");
        		            		Toast.makeText(MainActivity.this,"連線失敗", Toast.LENGTH_SHORT).show();
        		            	}
        		            	else{
        		            		Intent i = new Intent();
        		            		Bundle bundle = new Bundle();
        		            		bundle.putBoolean("isfirstTime", firstMap);
        		        			bundle.putInt("key", 0);
        		            		bundle.putSerializable("mapData", getTodayData());
        		            		i.putExtras(bundle);
        		            	    i.setClass(MainActivity.this, MapActivity.class);
        		            	    firstMap=false;
        		            	    startActivity(i);   
        		            	}           	  
        	      	     }
        		            }.execute();		    
        		}  
        	  //uploadlayout=new UploadLayout(MainActivity.this, main_layout);
        	  /*if(islogin){
        		  uploadlayout=new UploadLayout(MainActivity.this, main_layout);
            	  top_title.setText("New Activity");
        	  }
        	  else{
        		  final AlertDialog alertDialog = getAlertDialog("Not allowed","Please log in first");
        		  alertDialog.show();
        	  }*/
            }	
        });
}
private void followbuttonclick() {
	// TODO Auto-generated method stub
	  follow_button.setOnClickListener(new OnClickListener(){
          public void onClick(View v){
        	  //final Intent intent = new Intent(MainActivity.this, FollowActivity.class);
        	  //startActivity(intent);
        	  //overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
        	  mode=1;
        	  isHomePageShowed=false;
        	  followlayout=new FollowLayout(MainActivity.this, main_layout, alarm);
        	  top_title.setText("Liked Activity");
        	  update_button.setVisibility(View.INVISIBLE);
        	  followshowcase();
          }
      });
}
private void followshowcase(){
	if(firstTime!=null && firstTime && firstFollow){
		new ShowcaseView.Builder(MainActivity.this)
	    .setTarget(new ViewTarget(findViewById(R.id.top_title)))
	    .setContentTitle("追蹤中的活動")
	    .setContentText("顯示你已追蹤的活動，將活動向右滑動可以取消追蹤")
	    .build();
	}
	firstFollow=false;
}


@SuppressLint("SimpleDateFormat")
private ArrayList<ActivityData> getTodayData(){
	ArrayList<ActivityData> todays= new ArrayList<ActivityData>();
	//Calendar c = Calendar.getInstance();
	//String s =""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
	Calendar current = Calendar.getInstance();
	//Log.e(""+alldata.size(), "all");
	if(alldata!=null){
		for(ActivityData a:alldata){
			//String startTime=a.getStartTime();
			//String endTime=a.getEndTime();
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    	Calendar start = Calendar.getInstance();
	    	Calendar end = Calendar.getInstance();
			try {
				start.setTime(dateFormat.parse(a.getStartTime()));
				end.setTime(dateFormat.parse(a.getEndTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if(start!=null && end!=null && start.get(Calendar.YEAR)== current.get(Calendar.YEAR) 
	    			&& start.get(Calendar.MONTH)== current.get(Calendar.MONTH)
	    			&& start.get(Calendar.DAY_OF_MONTH)== current.get(Calendar.DAY_OF_MONTH)
	    			&& current.getTimeInMillis()<end.getTimeInMillis()){
	    		todays.add(a);
	    	}
		}
	}
	return todays;
}
  private void listbuttonclick() {
	// TODO Auto-generated method stub
	  list_button.setOnClickListener(new OnClickListener(){
          public void onClick(View v){
        	  mode=0;
        	  isHomePageShowed=false;
        	  if(alldata==null){
        		  final ProgressDialog myDialog = ProgressDialog.show
            			  (MainActivity.this, "loading", "loading list", true);					
          		    new AsyncTask<Void, Void, Void>()
          	            {
          	                @Override
          	                protected Void doInBackground(Void... params)
          	                {
          	                	alldata=Connector.SendGetDataCommand();	    	             	
          	    	       		if(alldata==null){
          	    	       				myDialog.dismiss();
          	    	       				//Log.e("1","Error");
                               			//Toast.makeText(RoutePlan.this,"NotFound", Toast.LENGTH_SHORT).show();
                               			this.onCancelled();
                               		}
          	    	       		myDialog.dismiss();
          	        	            return null;
          	        	        }
          	              @Override
     	        	     protected void onPostExecute(Void result)//收尾
     	        	     {   	
          	            	if(alldata==null){
          	            		myDialog.dismiss();
          	            		//Log.e("2","Error");
          	            		Toast.makeText(MainActivity.this,"連線失敗", Toast.LENGTH_SHORT).show();
          	            	}
          	            	else{
          	            		main_layout.removeAllViews();
            	            	listlayout=new ListLayout(MainActivity.this,alldata, alarm, firstShow);
            	            	main_layout.addView(listlayout);
            	            	top_title.setText("Activity List");
            	            	update_button.setVisibility(View.VISIBLE);
            	            	listshowcase();
            	            	myDialog.dismiss();
          	            	}           	  
    	        	     }
          	            }.execute();		    
        	  }
        	  else{
        		  main_layout.removeAllViews();
        		  listlayout=new ListLayout(MainActivity.this,alldata, alarm, firstShow);
        		  main_layout.addView(listlayout);
        		  top_title.setText("Activity List");
        		  update_button.setVisibility(View.VISIBLE);
        		  listshowcase();
        	  }
        	 
          }
      });
  }
  public static void notfirstShow(){
	  firstShow=false;
  }
  private void listshowcase(){
	  if(firstTime!=null && firstTime && firstList){
			new ShowcaseView.Builder(MainActivity.this)
		    .setTarget(new ViewTarget(findViewById(R.id.top_update)))
		    .setContentTitle("更新活動列表")
		    .setContentText("更新最新上傳的活動資料")
		    .build();
		}
	  firstList=false;
  }
  private AlertDialog finishAlertDialog(String title,String message){
	    //產生一個Builder物件
		Builder builder = new AlertDialog.Builder(this);
		//設定Dialog的標題
		builder.setTitle(title);
		//設定Dialog的內容
		builder.setMessage(message);
		//設定Positive按鈕資料
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);;
		    //按下按鈕時顯示快顯
			
		    //Toast.makeText(MainActivity.this, "您按下OK按鈕", Toast.LENGTH_SHORT).show();
		    }
	    });
     //設定Negative按鈕資料
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
         @Override
          public void onClick(DialogInterface dialog, int which) {
            //按下按鈕時顯示快顯
           //Toast.makeText(MainActivity.this, "您按下Cancel按鈕", Toast.LENGTH_SHORT).show();
        }
      });
     //利用Builder物件建立AlertDialog
      return builder.create();
 }

} 
