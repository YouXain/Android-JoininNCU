package youxian.ncumap;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;



@SuppressLint({ "ViewConstructor", "SimpleDateFormat" })
class ListLayout extends LinearLayout{
	Context con;
	ListView mylist;
	EditText searchtext;
	ImageButton all_button;
	ImageButton today_button;
	ImageButton past_button;
	MyAdapter listadapter;
	List<ActivityData>alldata=new ArrayList<ActivityData>();
	List<ActivityData>notpastdata=new ArrayList<ActivityData>();
	List<ActivityData>listeddata;
	CheckBox stu_check;
	CheckBox tea_check;
	CheckBox peo_check;
	//CheckBox club;
	//CheckBox intern;
	//CheckBox prize;
	//CheckBox life;
	//CheckBox arts;
	//CheckBox service;
	List<CheckBox>checks;
	AlarmReceiver alarm;
	//private Boolean firstTime = null;
	private Boolean firstShow = null;
	public ListLayout(Context context, List<ActivityData> a, AlarmReceiver alarm, boolean firstTime) {
		super(context);
		//this.firstTime=firstTime;
		//if(firstTime && firstShow==null)
		firstShow=firstTime;
		this.alarm=alarm;
		init(context, a);
		// TODO Auto-generated constructor stub
	}
	private void init(Context context, List<ActivityData> a){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	inflater.inflate(R.layout.list, this);//描述跳出條架構的xml檔案  抽出來存到this  
		con=context;
		findview();
		alldata.addAll(a);
		notpastdata.addAll(a);
		notpastdata.removeAll(getPastData());
		resetprefs();
		initList(notpastdata);
		listeddata=notpastdata;
		setcheckbox();
		setlistbutton();
		inputsearch();
		
	}
	private void setcheckbox() {
		// TODO Auto-generated method stub
		checks=new ArrayList<CheckBox>();
		checks.add(stu_check);
		checks.add(tea_check);
		checks.add(peo_check);
		//checks.add(club);
		//checks.add(intern);
		//checks.add(prize);
		//checks.add(life);
		//checks.add(arts);
		//checks.add(service);
		for(CheckBox c:checks){
			c.setOnClickListener(checkboxclick);
		}
	}
	OnClickListener checkboxclick =new OnClickListener(){		
		@Override
		public void onClick(View v) {
			String s="";
			for(CheckBox c:checks){
				if(c.isChecked())
					s=s+"1";
				else
					s=s+"0";
			}
			activityShow(s);			
		}
			
	};
	private void activityShow(String s) {
		// TODO Auto-generated method stub
		List<ActivityData>checkdata=new ArrayList<ActivityData>();
		List<Integer>x= new ArrayList<Integer>();
		for(int i=0;i<s.length();i++){
			if(s.charAt(i)=='1'){
				x.add(i);
			}
		}
		if(listeddata!=null){
			for(ActivityData a:listeddata){
				int c=0;
				for(Integer k:x){
					if(a.getType().charAt(k)=='1'){
						c++;
					}
				}
				if(c==x.size())
					checkdata.add(a);
			}
			if(s.contentEquals("000"))
				initList(listeddata);
			else
				initList(checkdata);
		}
		
		
	}
	private void findview() {
		// TODO Auto-generated method stub
		  mylist=(ListView)findViewById(R.id.listview);
    	  searchtext=(EditText) findViewById(R.id.inputSearch);
    	  all_button=(ImageButton)findViewById(R.id.all);
    	  today_button=(ImageButton)findViewById(R.id.today);
    	  past_button=(ImageButton)findViewById(R.id.past);
    	  stu_check=(CheckBox)findViewById(R.id.stu_list);
    	  tea_check=(CheckBox)findViewById(R.id.tea_list);
    	  peo_check=(CheckBox)findViewById(R.id.peo_list);
    	  //club=(CheckBox)findViewById(R.id.club_list);
  		  //intern=(CheckBox)findViewById(R.id.intern_list);
  		  //prize=(CheckBox)findViewById(R.id.prize_list);
  		  //life=(CheckBox)findViewById(R.id.life_list);
  		  //arts=(CheckBox)findViewById(R.id.arts_list);
  		  //service=(CheckBox)findViewById(R.id.service_list);
	}
	@SuppressLint("SimpleDateFormat")
	private void setlistbutton() {
			// TODO Auto-generated method stub
		  OnClickListener click= new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v.equals(all_button)){
					//all_button.setBackgroundColor(Color.RED);
					all_button.setImageResource(R.drawable.ic_activities_click);
					today_button.setImageResource(R.drawable.ic_todays);
					past_button.setImageResource(R.drawable.ic_past);
					initList(notpastdata);
					listeddata=notpastdata;
				}
				if(v.equals(today_button)){
					all_button.setImageResource(R.drawable.ic_activities);
					today_button.setImageResource(R.drawable.ic_todays_click);
					past_button.setImageResource(R.drawable.ic_past);
					initList(getTodayData());
					listeddata=getTodayData();
				}
				if(v.equals(past_button)){
					all_button.setImageResource(R.drawable.ic_activities);
					today_button.setImageResource(R.drawable.ic_todays);
					past_button.setImageResource(R.drawable.ic_past_click);
					initList(getPastData());
					listeddata=getPastData();
				}
			}

			
			  
		  };
		  all_button.setOnClickListener(click);
		  today_button.setOnClickListener(click);
		  past_button.setOnClickListener(click);
		}
	@SuppressLint("SimpleDateFormat")
	private ArrayList<ActivityData> getPastData(){
		ArrayList<ActivityData> pasts= new ArrayList<ActivityData>();
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
		    	if(start!=null && end!=null && current.getTimeInMillis()>end.getTimeInMillis()){
		    		pasts.add(a);
		    	}
			}
		}
		return pasts;
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
		    	else if(start!=null && end!=null && start.getTimeInMillis()<=System.currentTimeMillis() 
		    			&& end.getTimeInMillis()>=System.currentTimeMillis()){
		    		todays.add(a);
		    	}
			}
		}
		return todays;
	}
	  private void inputsearch() {
			// TODO Auto-generated method stub
		  searchtext.addTextChangedListener(new TextWatcher() {
	          
	         @Override
	         public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	             // When user changed the Text
	             //MainActivity.this.adapter.getFilter().filter(cs);  
	        	 listadapter.getFilter().filter(cs.toString());
	         }
	          
	         @Override
	         public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                 int arg3) {
	             // TODO Auto-generated method stub
	              
	         }
	          
	         @Override
	         public void afterTextChanged(Editable arg0) {
	             // TODO Auto-generated method stub                          
	         }
	     });
	 }    
		
	  void initList(List<ActivityData> datas) {
			// TODO Auto-generated method stub  	
		  	listadapter=new MyAdapter(con,datas);
		  	mylist.setAdapter(listadapter);
			mylist.setOnItemClickListener(new OnItemClickListener(){
		        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
		        		Activity a=(Activity) con;
		        		final Intent intent = new Intent(con, ShowActivity.class);
		        		Bundle bundle = new Bundle();
		        		bundle.putBoolean("isfirstTime", firstShow);
		        		//bundle.putSerializable("alarm", alarm);
		        		bundle.putSerializable("clickedData", listeddata.get(position));
		        		intent.putExtras(bundle);
		        		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        		firstShow=false;
		        		MainActivity.notfirstShow();
		        		con.startActivity(intent);
		        		a.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		        		/*intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		        		ActivitySwitcher.animationOut(findViewById(R.id.total_layout), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
		        			@Override
		        			public void onAnimationFinished() {
		        				startActivity(intent);
		        			}
		        		});*/
		        	}
			});
		}

	  private void resetprefs() {
			// TODO Auto-generated method stub
			List<ActivityData>temps=new ArrayList<ActivityData>();
			SharedPreferences appSharedPrefs = PreferenceManager
		  		  .getDefaultSharedPreferences(con);
		    Editor prefsEditor = appSharedPrefs.edit();
		    Gson gson= new Gson();
		  	Map<String, ?> jsons=appSharedPrefs.getAll();
		  	jsons.remove("Alarm");
		  	for(Map.Entry<String,?> entry : jsons.entrySet())
		  	{
		  		ActivityData myd = gson.fromJson(entry.getValue().toString(), ActivityData.class);
		  		for(ActivityData a:alldata){
		  			if(myd.getTitle().contains(a.getTitle())){
		  				if(!myd.getStartTime().contains(a.getStartTime()) || !myd.getEndTime().contains(a.getEndTime())
		  					|| !myd.getAddress().contains(a.getAddress()) || !myd.getContext().contains(a.getContext())){
		  					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		  					Calendar o  = Calendar.getInstance();
		  					Calendar n  = Calendar.getInstance();
		  					try {
		  						o.setTime(df.parse(myd.getStartTime()));
		  						n.setTime(df.parse(a.getStartTime()));
		  					} catch (ParseException e) {
		  						// TODO Auto-generated catch block
		  						e.printStackTrace();
		  					}
		  					if((o.getTimeInMillis()-3600*1000)>System.currentTimeMillis() && alarm!=null){
		  						alarm.deletAlarm(con, myd);
		  						//Log.e("delete", "list");
		  					}
		  						
		  					if(n.getTimeInMillis()>System.currentTimeMillis() && alarm!=null)
		  					{
		  						alarm.startearlyTimer(con, a);
		  						//Log.e("create", "list");
		  					}
		  					final AlertDialog alertDialog = updateAlertDialog("活動修改通知","你追蹤的活動更新了，是否前往查看?", a);
		  					alertDialog.show();
		  				}
		  				myd=a;
		  				temps.add(myd);
		  				break;
		  			}
		  		}
		  	}
		  
		  		//if(myd!=null && myd.getTitle()!=null){
		  			
		  			
			  			
			  		
		  		//}
		  		
		  		
		  	
		  	
		  	prefsEditor.clear();
		  	prefsEditor.commit();
		  	int j=temps.size()-1;
		  	for(int i=0;i<=temps.size()-1;i++){
		  		String json = gson.toJson(temps.get(j));
	  	  		prefsEditor.putString(""+i, json);
	  	  		prefsEditor.commit();
	  	  		j--;
		  	}
		}
	  private AlertDialog updateAlertDialog(String title,String message, final ActivityData data){
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
				Activity a=(Activity) con;
        		final Intent intent = new Intent(con, ShowActivity.class);
        		Bundle bundle = new Bundle();
        		bundle.putBoolean("isfirstTime", firstShow);
        		//bundle.putSerializable("alarm", alarm);
        		bundle.putSerializable("clickedData", data);
        		intent.putExtras(bundle);
        		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        		firstShow=false;
        		MainActivity.notfirstShow();
        		con.startActivity(intent);
        		a.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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