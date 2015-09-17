package youxian.ncumap;

import java.util.List;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LifeMarker {
	List<MarkerOptions> markers;
	public LifeMarker(List<MarkerOptions> markers, int n){
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
		m.position(new LatLng(24.968298, 121.190956));
		m.title("ㄌく绑");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building13));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.967273, 121.195696));
		m.title("繻");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building03));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.967011, 121.195717));
		m.title("沮紈加");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building11));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.966748, 121.195755));
		m.title("笴美繻");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building15));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.965778, 121.193664));
		m.title("в笵加");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building08));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.965771, 121.194589));
		m.title("いァ厩秎Ы");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building05));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.965780, 121.195290));
		m.title("繺芔");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building01));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.970538, 121.195744));
		m.title("繺芔");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building02));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.967289, 121.190621));
		m.title("猀璪繺芔");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building11));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.970026, 121.193432));
		m.title("肞绘");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building18));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.965769, 121.194139));
		m.title("");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building14));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.970164, 121.191326));
		m.title("い打");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building09));
		markers.add(m);
		
		}
}
