package youxian.ncumap;

import java.util.List;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SportMarker {
	List<MarkerOptions> markers;
	public SportMarker(List<MarkerOptions> markers, int n){
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
		m.position(new LatLng(24.969644, 121.190857));
		m.title("籃球場(溜冰場旁)");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building21));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.968964, 121.189150));
		m.title("籃球場(機械館旁)");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building21));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.967777, 121.190806));
		m.title("排球場(依仁堂旁)");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building23));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.969283, 121.188805));
		m.title("排球場(網球場旁)");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building23));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.968326, 121.190874));
		m.title("健身房(依仁堂內)");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building05));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.969252, 121.190937));
		m.title("羽球館");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building01));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.969576, 121.191018));
		m.title("溜冰場");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building24));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.968992, 121.191416));
		m.title("棒球場");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building20));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.967637, 121.189890));
		m.title("田徑場");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building14));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.968998, 121.189327));
		m.title("攀岩場");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building09));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.969630, 121.189586));
		m.title("網球場");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building22));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.969993, 121.189235));
		m.title("室內游泳池");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building25));
		markers.add(m);
		}
}
