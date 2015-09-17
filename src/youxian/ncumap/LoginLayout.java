package youxian.ncumap;




import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


@SuppressLint("ViewConstructor")
class LoginLayout extends LinearLayout{
	Context con;
	LinearLayout container;
	TextView username_login;
	TextView logout_login;
	ListView postedlist;
	List<ActivityData> userdata;
	MyAdapter listadapter;
	public LoginLayout(Context context, LinearLayout l, List<ActivityData> userdata) {
		super(context);
		init(context,l,userdata);
		// TODO Auto-generated constructor stub
	}
	public LoginLayout(Context context, AttributeSet attrs, LinearLayout l, List<ActivityData> userdata) {
		super(context, attrs);
		init(context,l,userdata);
		// TODO Auto-generated constructor stub
	}
	public LoginLayout(Context context, AttributeSet attrs, int defStyle, LinearLayout l, List<ActivityData> userdata) {
		super(context, attrs, defStyle);
		init(context,l,userdata);
		// TODO Auto-generated constructor stub
	}
	
	private void init(Context context, LinearLayout l, List<ActivityData> userdata){
		con=context;
		container=l;
		this.userdata=userdata;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		l.removeAllViews();
    	l.addView(inflater.inflate(R.layout.userlogin, this));//描述跳出條架構的xml檔案  抽出來存到this
    	findview();
    	initList(userdata);
    	logoutclick();
    	//username_login.setText(MainActivity.getUser());
	}
	private void initList(List<ActivityData> datas) {
		listadapter=new MyAdapter(con,datas);
		postedlist.setAdapter(listadapter);
		postedlist.setOnItemClickListener(new OnItemClickListener(){
			 public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				Activity a=(Activity) con;
	        	final Intent intent = new Intent(con, EditActivity.class);
	        	Bundle bundle = new Bundle();
	        	bundle.putSerializable("clickedData", userdata.get(position));
	        	intent.putExtras(bundle);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        	con.startActivity(intent);
	        	a.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			 }
		});
	}
	private void logoutclick() {
		// TODO Auto-generated method stub
		logout_login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//MainActivity.setUser("");
				//MainActivity.isnotlogin();
				new UserLayout(con, container);
			}
			
		});
	}
	private void findview() {
		// TODO Auto-generated method stub
		username_login=(TextView)findViewById(R.id.username_login);
		logout_login=(TextView)findViewById(R.id.logout_login);
		postedlist=(ListView)findViewById(R.id.postedlist);
	}

	
}