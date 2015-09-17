package youxian.ncumap;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindowAdapter implements InfoWindowAdapter, OnClickListener, OnTouchListener{
	private List<ActivityData>todaydata;
	private LayoutInflater Inflater;
	private TextView address;
	private TextView title;
	private TextView gotodetails;
	private Context con;
	public MyInfoWindowAdapter(LayoutInflater inflater, Context con, List<ActivityData>todaydata) {
	    this.Inflater=inflater;
	    this.con=con;
	    this.todaydata=todaydata;
	    
	}
	@Override
	public View getInfoContents(final Marker marker) {
		// TODO Auto-generated method stub
		
		//LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	//inflater.inflate(R.layout.list, this);//描述跳出條架構的xml檔案  抽出來存到this  
	    View infoWindow = Inflater.inflate(R.layout.infowindow, null);
	    // 顯示地標title
	    address = ((TextView)infoWindow.findViewById(R.id.address_infowindow));
	    address.setText(marker.getTitle());
	    // 顯示地標snippet
	    title = ((TextView)infoWindow.findViewById(R.id.title_infowindow));
	    title.setText(marker.getSnippet());
	    gotodetails = ((TextView)infoWindow.findViewById(R.id.gotodetails_infowindow));
	    return infoWindow;      
	}

	@Override
	public View getInfoWindow(Marker marker) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//Log.e("click2", "marker");
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
