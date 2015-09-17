package youxian.ncumap;



import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("ViewConstructor")
class UserLayout extends LinearLayout{
	Button login;
	EditText usertext;
	EditText passwordtext;
	TextView errorlog;
	Context con;
	LinearLayout container;
	List<ActivityData> userdata;
	public UserLayout(Context context, LinearLayout l) {
		super(context);
		init(context,l);
		// TODO Auto-generated constructor stub
	}
	public UserLayout(Context context, AttributeSet attrs, LinearLayout l) {
		super(context, attrs);
		init(context,l);
		// TODO Auto-generated constructor stub
	}
	public UserLayout(Context context, AttributeSet attrs, int defStyle, LinearLayout l) {
		super(context, attrs, defStyle);
		init(context,l);
		// TODO Auto-generated constructor stub
	}
	
	private void init(Context context, LinearLayout l){
		con=context;
		container=l;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	//描述跳出條架構的xml檔案  抽出來存到this
		l.removeAllViews();
    	l.addView(inflater.inflate(R.layout.user, this));
    	findview();
	}
	private void findview() {
		// TODO Auto-generated method stub
			login=(Button)findViewById(R.id.login);
	    	  usertext=(EditText)findViewById(R.id.inputUser);
	    	  passwordtext=(EditText)findViewById(R.id.inputPassword);
	    	  errorlog=(TextView)findViewById(R.id.errorlog);
	    	  login.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					loginclick();
				}
	    		  
	    	  });

	}
	void loginclick(){
		if(usertext.getText().toString()!="" && passwordtext.getText().toString()!=""){
			  final ProgressDialog myDialog = ProgressDialog.show
      		        (con, "loading", "loading list", true);	
			  new AsyncTask<Void, Void, Void>()
            {       		   	
				  String s="";
                @Override
                protected Void doInBackground(Void... params)
                {
                	
                	userdata=Connector.SendAuthenticateCommand(usertext.getText().toString(), passwordtext.getText().toString());
    	       		if(userdata==null){
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
            	  if(userdata==null){
	       				myDialog.dismiss();
	       				Log.e("1","Error");
	       				Toast.makeText(con,"Wrong account or password", Toast.LENGTH_SHORT).show();
           			}
            	  else{
            		  errorlog.setText(s);
            		  //MainActivity.islogin();
            		  //MainActivity.setUser(usertext.getText().toString());
            		  setlogin();
            		  myDialog.dismiss();
            	  }
            	  /*LoginLayout testlayout=new LoginLayout(con);
            	  LinearLayout main_layout = (LinearLayout)findViewById(R.id.main_layout);
	            	  main_layout.removeAllViews();
	            	  main_layout.addView(testlayout);*/
  	     }			
         }.execute();		    
		}
	}
	void setlogin(){
		new LoginLayout(con, container, userdata);
	}
	
}