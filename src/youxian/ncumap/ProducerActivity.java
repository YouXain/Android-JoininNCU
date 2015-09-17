package youxian.ncumap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class ProducerActivity extends Activity{
	LinearLayout mylayout;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.producer_activity);
	    findview();
	    
	}

	private void findview() {
		// TODO Auto-generated method stub
		mylayout=(LinearLayout)findViewById(R.id.producer_layout);
		mylayoutClick();
	}

	private void mylayoutClick() {
		// TODO Auto-generated method stub
		mylayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
	}
}
