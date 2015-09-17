package youxian.ncumap;




import java.util.Arrays;
import java.util.List;
import java.util.Map;









import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ShowActivity extends Activity{
	TextView username;
	TextView uploadtime;
	TextView starttime;
	TextView endtime;
	TextView title;
	TextView address;
	TextView context;
	ImageView poster;
	ImageButton follow;
	ImageButton previouspage;
	ImageButton gotomap;
	ImageButton fbshare;
	ActivityData myData;
	Context con;
	AlarmReceiver alarm;
	Boolean isfollowed=false;
	TextView share;
	Button btnClose;
	Session mysession;
	private boolean isShared=false;
	private UiLifecycleHelper uiHelper;
	ShowcaseView sv;
	private Boolean firstTime = null;
	GlobalClass globalVariable;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		con =this.getApplicationContext();
	    setContentView(R.layout.list_activity);
	    globalVariable = (GlobalClass) getApplicationContext();
	    alarm=globalVariable.getAlarm();
	    uiHelper = new UiLifecycleHelper(this, null);
	    uiHelper.onCreate(savedInstanceState);
	    //share=(TextView)findViewById(R.id.share_activity);
	    findview();
	    setdatafrombundle();
	    initshareddata();
	    //shareclick();
	    setImage();
	    //LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	//View view = inflater.inflate(R.layout.list_activity, null);
	    //View view = inflater.inflate(R.layout.fragment, container, false);
	    //if(isFirstTime()){
	    Showcase();
	    
	    //}
	    posterclick();
	    gotomapclick();
	    fbshareclick();
	    followclick();
	    previouspageclick();
	    
	  }
	private void Showcase() {
		// TODO Auto-generated method stub
		//Log.e(""+firstTime, "f");
		if(firstTime!=null && firstTime){
			sv = new ShowcaseView.Builder(ShowActivity.this)
		    .setTarget(new ViewTarget(findViewById(R.id.follow_activity)))
		    .setContentTitle("追蹤活動")
		    .setContentText("在活動開始前一天及前一小時提醒你，別錯過了精采的活動")
		    .setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showcasehide();
					sv = new ShowcaseView.Builder(ShowActivity.this)
				    .setTarget(new ViewTarget(findViewById(R.id.gotomap_activity)))
				    .setContentTitle("活動導航")
				    .setContentText("顯示活動地點，進行導航")
				    .build();
					
				}
		    	
		    })
		    .build();
		}
	}
	private void showcasehide(){
		sv.hide();
	}
	 private void fbshareclick() {
		// TODO Auto-generated method stub
		 fbshare.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//fbshare.setSelected(true);
					Session.openActiveSession(ShowActivity.this, true, new Session.StatusCallback() {
						@Override
						public void call(Session session, SessionState state,Exception exception) {
							onSessionStateChange(session, state, exception);
							if (session.isOpened() && !isShared) {								
								List<String> permissions = Arrays.asList("public_profile");
						        Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(ShowActivity.this, permissions);
						        session.requestNewReadPermissions(newPermissionsRequest);
						        mysession=session;
						        //share();
						        //Log.e("123", "call");
							}
							
						}
					});



					/*
					List<String> permissions = new ArrayList<String>();
				    permissions.add("publish_stream");
				    s = new Session.Builder(ShowActivity.this).build();
				    s.addCallback(sessionStatusCallback);
				    Session.OpenRequest openRequest = new Session.OpenRequest(
				      ShowActivity.this);
				    openRequest.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
				    openRequest.setRequestCode(Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE);
				    openRequest.setPermissions(permissions);
				    s.openForPublish(openRequest);
				    */
				}
				 
			 });
	}

	
	 private void onSessionStateChange(Session session, SessionState state,
			   Exception exception) {
			  if (session!=mysession) {
			   return;
			  }

			  if (state.isOpened()) {
			   // Log in just happened.
			   Toast.makeText(getApplicationContext(), "session opened",
			     Toast.LENGTH_SHORT).show();
			  } else if (state.isClosed()) {
			   // Log out just happened. Update the UI.
			   Toast.makeText(getApplicationContext(), "session closed",
			     Toast.LENGTH_SHORT).show();
			  }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data); 
		 uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
		        @Override
		        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
		            //Log.e("Activity", String.format("Error: %s", error.toString()));
		            isShared=true;
		        }

		        @Override
		        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
		            //Log.e("Activity", "Success!");
		            isShared=true;
		        }
		    });
		if(mysession!=null && !isShared) {
	    	//Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	    	if(mysession.isOpened()){
	    		//publishStory();
	    		//Log.e(""+isShared, "555");
	    		share();
	    	}
	    	//else
				//Toast.makeText(getApplicationContext(), "session not opened", Toast.LENGTH_SHORT).show();
	    }
		else if(mysession!=null && isShared){
			//Toast.makeText(getApplicationContext(), "is shared", Toast.LENGTH_SHORT).show();
		}
		//else{
			//Toast.makeText(getApplicationContext(), "is shared", Toast.LENGTH_SHORT).show();
			//isShared=false;
		//}
	    //super.onActivityResult(requestCode, resultCode, data); 
	}
	private void share(){
		String showingWeb="";
		if(myData.getWebsite().isEmpty())
			showingWeb="";
		else
			showingWeb=myData.getWebsite();
		FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
		.setName(myData.getTitle())
		.setCaption("開始時間: "+myData.getStartTime())
		.setDescription("活動地點: "+myData.getAddress())
        .setLink(showingWeb)
        .setPicture("http://i.imgur.com/asdQre0.png?1")
        .build();
		uiHelper.trackPendingDialogCall(shareDialog.present());
		isShared=true;
	}
		public void publishStory() {
			 Bundle params = new Bundle();
			 params.putString("name", myData.getTitle());
			 params.putString("caption", myData.getStartTime());
			 params.putString("description", myData.getAddress());
			 params.putString("link", myData.getWebsite());
			 params.putString("picture", "http://i.imgur.com/asdQre0.png?1");

			 WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(ShowActivity.this, mysession, params))
			   .setOnCompleteListener(new OnCompleteListener() {

			   @Override
			   public void onComplete(Bundle values,FacebookException error) {
			    if (error == null) {
			     // When the story is posted, echo the success
			     // and the post Id.
			     final String postId = values.getString("post_id");
			     if (postId != null) {
			                                           // do some stuff
			     } else {
			     // User clicked the Cancel button
			     Toast.makeText(getApplicationContext(),
			       "Publish cancelled", Toast.LENGTH_SHORT).show();
			     }
			    } else if (error instanceof FacebookOperationCanceledException) {
			     // User clicked the "x" button
			     Toast.makeText(getApplicationContext(),
			      "Publish cancelled", Toast.LENGTH_SHORT).show();
			    } else {
			     // Generic, ex: network error
			     Toast.makeText(getApplicationContext(),
			      "Error posting story", Toast.LENGTH_SHORT).show();
			    }
			   }

			

			  }).setFrom("").build();
			 feedDialog.show();

			

	}
	
	private void setImage() {
		// TODO Auto-generated method stub
		if(myData.getFileBytes()!=null){
			Bitmap bitmap = BitmapFactory.decodeByteArray(myData.getFileBytes() , 0, myData.getFileBytes().length);
			poster.setImageBitmap(bitmap);
			poster.setClickable(true);
	      }
		
	}
	 @Override
		protected void onResume() {
			// animateIn this activity
			//ActivitySwitcher.animationIn(findViewById(R.id.total_layout), getWindowManager());
			super.onResume();
			uiHelper.onResume();
		}
	@Override  
	public void onPause(){
        super.onPause();
        uiHelper.onPause();
        SharedPreferences appSharedPrefs = PreferenceManager
  			  .getDefaultSharedPreferences(con);
        Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
  	  	Map<String, ?> jsons=appSharedPrefs.getAll();
  	  	jsons.remove("Alarm");
  	  	if(follow.getTag()=="0" && isfollowed){
  	  		for(Map.Entry<String,?> entry : jsons.entrySet())
  	  		{
  	  			ActivityData myd = gson.fromJson(entry.getValue().toString(), ActivityData.class);
  	  			if(myd.getActivityId()==myData.getActivityId()){
  	  				prefsEditor.remove(entry.getKey());
  	  				//editor.clear();
  	  				prefsEditor.commit();
  	  				alarm.deletAlarm(con, myData);
  	  				//resetprefs();
  	  				break;
  	  			}
  	  		}
  	  	}
  	  	else if(follow.getTag()=="1" && !isfollowed){
  	  		String json = gson.toJson(myData);
  	  		prefsEditor.putString(""+jsons.size(), json);
  	  		prefsEditor.commit();
  	  		//Log.e(""+jsons.size(), "123");
  	  		//Toast.makeText(con, "Object stored in SharedPreferences", Toast.LENGTH_SHORT).show();
  	  		//disablefollow();
  	  		//alarm.deletAlarm(myData);
  	  		alarm.startearlyTimer(con, myData);
  	  	}
  	  	
  	  	/*
  	  	for(Map.Entry<String,?> entry : jsons.entrySet())
  	  	{
		  	ActivityData myd = gson.fromJson(entry.getValue().toString(), ActivityData.class);
		  	if(myd.getActivityId()==myData.getActivityId()){
		  		if(follow.getTag()=="1" && !isfollowed){
	        	  	prefsEditor.remove(entry.getKey());
			  		//editor.clear();
			  		prefsEditor.commit();
			  		break;
		  		}
		  		else if(follow.getTag()=="0" && isfollowed){
			  		String json = gson.toJson(myData);
	        	  	prefsEditor.putString(""+jsons.size(), json);
	        	  	prefsEditor.commit();
	        	  	//Toast.makeText(con, "Object stored in SharedPreferences", Toast.LENGTH_SHORT).show();
	        	  	//disablefollow();
	        	  	alarm.startearlyTimer(con, myData);
	        	  	break;
		  		}
		  	}
		  		
  	  	}*/
  	  	
  	  	/*
        if(follow.getTag()=="1" && !isfollowed){
        	
        	  	String json = gson.toJson(myData);
        	  	prefsEditor.putString(""+jsons.size(), json);
        	  	prefsEditor.commit();
        	  	//Toast.makeText(con, "Object stored in SharedPreferences", Toast.LENGTH_SHORT).show();
        	  	//disablefollow();
        	  	alarm.startearlyTimer(con, myData);
        	  	uiHelper.onPause();
        	  	//alarm.startcurrentTimer(con, myData);
        }*/
    }
	

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	private void initshareddata() {
		// TODO Auto-generated method stub

		SharedPreferences appSharedPrefs = PreferenceManager
				  .getDefaultSharedPreferences(con);
		Gson gson = new Gson();
		Map<String, ?> jsons=appSharedPrefs.getAll();
		jsons.remove("Alarm");
		  for(Map.Entry<String,?> entry : jsons.entrySet())
	        {
			  	ActivityData myd = gson.fromJson(entry.getValue().toString(), ActivityData.class);
			  	if(myd.getActivityId()==myData.getActivityId()){
			  		follow.setImageResource(R.drawable.ic_followed);
			  		follow.setTag("1");
			  		isfollowed=true;
			  		break;
			  	}
			  	else{
			  		follow.setImageResource(R.drawable.ic_followthis);
			  		follow.setTag("0");
			  		isfollowed=false;
			  	}
			  		
	        }
	}

	private void setdatafrombundle() {
		// TODO Auto-generated method stub
		Bundle bundle = getIntent().getExtras();
		firstTime=bundle.getBoolean("isfirstTime");
		//alarm=(AlarmReceiver) bundle.getSerializable("alarm");
		myData=(ActivityData) bundle.getSerializable("clickedData");
		//SharedPreferences appSharedPrefs = PreferenceManager
	  	//		  .getDefaultSharedPreferences(con);
	    //    Gson gson = new Gson();
	    //    String json = appSharedPrefs.getString("Alarm", "");
	    //alarm = gson.fromJson(json, AlarmReceiver.class);
		username.setText(myData.getUserName());
		uploadtime.setText(myData.getUploadTime());
		starttime.setText(myData.getStartTime());
		endtime.setText(myData.getEndTime());
		address.setText(myData.getAddress());
		title.setText(myData.getTitle());
		context.setText(myData.getContext()+"\n"+myData.getWebsite());
		context.setMovementMethod(LinkMovementMethod.getInstance());
		//poster.setClickable(false);
		/*
		if(myData.getFileBytes()!=null){
			Bitmap bmp = BitmapFactory.decodeByteArray(myData.getFileBytes(), 0, myData.getFileBytes().length);
			poster.setImageBitmap(bmp);
			//poster.setClickable(true);
		}*/
		
	}
	private void findview() {
		// TODO Auto-generated method stub
		poster=(ImageView)findViewById(R.id.image_activity);
		username=(TextView)findViewById(R.id.top_title_activity);
		uploadtime=(TextView)findViewById(R.id.uploadtime_activity);
		starttime=(TextView)findViewById(R.id.starttime_activity);
		endtime=(TextView)findViewById(R.id.endtime_activity);
		address=(TextView)findViewById(R.id.address_activity);
		title=(TextView)findViewById(R.id.title_activity);
		context=(TextView)findViewById(R.id.context_activity);
		follow=(ImageButton)findViewById(R.id.follow_activity);
		previouspage=(ImageButton)findViewById(R.id.previous_page_activity);
		gotomap=(ImageButton)findViewById(R.id.gotomap_activity);
		fbshare=(ImageButton)findViewById(R.id.fbshare_activity);
	}
	private void previouspageclick() {
		// TODO Auto-generated method stub
		previouspage.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
                finish();
                overridePendingTransition(R.anim.slide_go, R.anim.slide_back);
            }
        });
	}
	private void gotomapclick() {
		// TODO Auto-generated method stub
		gotomap.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
            	Intent intent = new Intent(ShowActivity.this,MapActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("key", 1);
        		bundle.putSerializable("targetData", myData);
        		intent.putExtras(bundle);
        		ShowActivity.this.finish();
        		startActivity(intent);
            }
        });
	}
	private void followclick() {
		// TODO Auto-generated method stub
		follow.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(follow.getTag()=="1"){
					follow.setImageResource(R.drawable.ic_followthis);
					follow.setTag("0");
				}
				else{
					follow.setImageResource(R.drawable.ic_followed);
					follow.setTag("1");
				}
				
			}
			
		});
	}
	
	private void posterclick() {
		// TODO Auto-generated method stub

		poster.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	final Dialog nagDialog = new Dialog(ShowActivity.this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
                nagDialog.setCancelable(false);
                nagDialog.setContentView(R.layout.showposter);
                btnClose = (Button)nagDialog.findViewById(R.id.btnIvClose);
                ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.preview_image);
                ivPreview.setImageDrawable(poster.getDrawable());
                ivPreview.setOnTouchListener(new View.OnTouchListener() { 
                	Matrix matrix = new Matrix(); 
                	Matrix savedMatrix = new Matrix(); 
                	PointF startPoint = new PointF(); 
                	PointF midPoint = new PointF(); 
                	float oldDist = 1f; 
                	static final int NONE = 0; 
                	static final int DRAG = 1; 
                	static final int ZOOM = 2; 
                	int mode = NONE;
					@Override
					public boolean onTouch(View arg0, MotionEvent event) {
						// TODO Auto-generated method stub
						ImageView view = (ImageView) arg0;
						switch (event.getAction() & MotionEvent.ACTION_MASK) {
						case MotionEvent.ACTION_DOWN:
							savedMatrix.set(matrix);
						    startPoint.set(event.getX(), event.getY());
						    mode = DRAG;
						    break;
						case MotionEvent.ACTION_POINTER_DOWN:
							oldDist = spacing(event);
							if (oldDist > 10f) {
								 savedMatrix.set(matrix);
							     midPoint(midPoint, event);
							     mode = ZOOM;
							}
							break;
						case MotionEvent.ACTION_POINTER_UP:
							mode = NONE;
							break;
						case MotionEvent.ACTION_MOVE:
							if (mode == DRAG) {
								 matrix.set(savedMatrix);
							     matrix.postTranslate(event.getX() - startPoint.x,
							     event.getY() - startPoint.y);
							}
							else if(mode == ZOOM) {
								float newDist = spacing(event);
								if (newDist > 10f) {
									matrix.set(savedMatrix); 
									float scale = newDist / oldDist; 
									matrix.postScale(scale, scale, midPoint.x, midPoint.y);
								}
							}
							break;
						}
						view.setImageMatrix(matrix);
						return true;
					}
                });

                	
                btnClose.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                    	btnClose.setSelected(true);
                        nagDialog.dismiss();
                    }
                });
                if(myData.getFileBytes()!=null)
                	nagDialog.show();
                
            }
        });
		
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);

		int orientation = newConfig.orientation;
		if (orientation == Configuration.ORIENTATION_PORTRAIT){
			findViewById(R.layout.showposter).startAnimation(AnimationUtils.loadAnimation(this,R.anim.rotate_to_vertical));
		}
	    //Log.d("tag", "Portrait");
		else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
			findViewById(R.layout.showposter).startAnimation(AnimationUtils.loadAnimation(this,R.anim.rotate));
		}
	   // Log.d("tag", "Landscape");



	}
	@SuppressLint("FloatMath") 
	private float spacing(MotionEvent event) { 
		float x = event.getX(0) - event.getX(1); 
		float y = event.getY(0) - event.getY(1); 
		return FloatMath.sqrt(x * x + y * y); 
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1); 
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2); 
		}

	
	
	
}