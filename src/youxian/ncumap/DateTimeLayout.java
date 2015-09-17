package youxian.ncumap;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;


class DateTimeLayout extends LinearLayout{
	final float layoutHeight = 800;//收起來時按鈕條流的寬度  單位DP
	static TextView title;
	TextView done;
	DatePicker datepicker;
	TimePicker timepicker;
	LinearLayout datetimelayout;
	ObjectAnimator objectAnimator1;
	String datetime="";
	static int count;
    static ObjectAnimator objectAnimator2;
    
	public DateTimeLayout(Context context) {
		super(context);
		init(context);
		// TODO Auto-generated constructor stub
	}
	public  DateTimeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		// TODO Auto-generated constructor stub
	}
	public  DateTimeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
		// TODO Auto-generated constructor stub
	}
	private void init(Context context) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	inflater.inflate(R.layout.datetimelayout, this);//描述跳出條架構的xml檔案  抽出來存到this
    	findview();
    	objectAnimator1 = ObjectAnimator.ofFloat((Object)this, "translationY", 0, layoutHeight);
        objectAnimator1.setDuration(1000);//收進去花的時間
        objectAnimator1.start();//開始先收進去
        objectAnimator2 = ObjectAnimator.ofFloat((Object)this, "translationY", layoutHeight, 0);
        objectAnimator2.setDuration(800);//收進去花的時間
        setdatetime();
        doneclick();
	}
	private void setdatetime() {
		// TODO Auto-generated method stub
		
	}
	private void doneclick() {
		// TODO Auto-generated method stub
		done.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String date=(""+datepicker.getYear()+"-"+(datepicker.getMonth()+1)+"-"+datepicker.getDayOfMonth());
				String time=(""+timepicker.getCurrentHour()+":"+timepicker.getCurrentMinute());
				datetime=(date+" "+time);
				if(count==1){
					UploadLayout.setStarttime(datetime);
				}
				else if(count==0){
					UploadLayout.setEndtime(datetime);
				}
				objectAnimator1.start();
			}
			
		});
	}
	private void findview() {
		// TODO Auto-generated method stub
		title=(TextView)findViewById(R.id.title_datetimelayout);
		done=(TextView)findViewById(R.id.done_datatimelayout);
		datepicker=(DatePicker)findViewById(R.id.datepicker);
		timepicker=(TimePicker)findViewById(R.id.timepicker);
		datetimelayout=(LinearLayout)findViewById(R.id.datetimelayout);
		
	}
	static void slideout(int i){
		count=i;
		if(i==1){
			title.setText("StartDate");
		}
		else if(i==0)
			title.setText("EndDate");
		objectAnimator2.start();
	}
	void slidein(){
		objectAnimator1.start();
	}
}