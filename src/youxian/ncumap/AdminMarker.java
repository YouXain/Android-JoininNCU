package youxian.ncumap;

import java.util.List;


import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AdminMarker {
	List<MarkerOptions> markers;
	public AdminMarker(List<MarkerOptions> markers, int n){
		this.markers=markers;
		if(n==0)
			setNCUMarkers();
		else if(n==1)
			setCYCUMarkers();
	}
	private void setCYCUMarkers() {
		// TODO Auto-generated method stub
		
	}
	private void setNCUMarkers() {
		// TODO Auto-generated method stub
		MarkerOptions m;
		m=new MarkerOptions();
		m.position(new LatLng(24.968285, 121.195299));
		m.title("行政大樓");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building13));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.970041, 121.192892));
		m.title("O綜教館");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building03));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.968128, 121.193622));
		m.title("中正圖書館");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building11));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.970036, 121.194288));
		m.title("L3國鼎圖書資料館");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building15));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.968225, 121.194545));
		m.title("總圖書館");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building08));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.967826, 121.191804));
		m.title("資策會");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building05));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.966701, 121.190781));
		m.title("創新育成中心/國際事務處");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building01));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.970792, 121.194980));
		m.title("中大會館");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building18));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.968376, 121.195560));
		m.title("前門警衛室");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building14));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.965925, 121.190999));
		m.title("後門警衛室");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building09));
		markers.add(m);
		
		}
}
