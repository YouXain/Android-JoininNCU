package youxian.ncumap;

import java.util.List;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
class StudyMarker{
	List<MarkerOptions> markers;
	public StudyMarker(List<MarkerOptions> markers, int n){
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
		m.position(new LatLng(24.969662, 121.194549));
		m.title("A��Ǥ@�]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building13));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.968815, 121.194620));
		m.title("C2��ǤG�]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building03));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.969644, 121.195157));
		m.title("��ǤT�]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building11));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.967070, 121.192664));
		m.title("E�u�{�@�]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building15));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.967056, 121.191789));
		m.title("E1�u�{�G�]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building08));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.967660, 121.188299));
		m.title("E2�u�{�T�]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building05));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.968341, 121.187796));
		m.title("E3�u�{�|�]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building01));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.967250, 121.187727));
		m.title("E6�u�{���]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building02));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.967003, 121.194532));
		m.title("S��Ǥ@�]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building18));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.970070, 121.192397));
		m.title("S1��ǤG�]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building14));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.970508, 121.192402));
		m.title("S2��ǤT�]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building09));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.971327, 121.192158));
		m.title("S4��ǥ|�]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building05));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.971290, 121.192444));
		m.title("H2�z�ǰ|�о��]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building08));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.971240, 121.192683));
		m.title("S5��Ǥ��]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building10));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.970026, 121.190114));
		m.title("IL�깩���q�j��");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building07));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.970742, 121.190176));
		m.title("�Ȯa�ǰ|�j��");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building11));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.967546, 121.187590));
		m.title("R3�ӪŻ�����s����/��s���ߤj�ӤG��");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building11));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.967539, 121.187005));
		m.title("R2�ӪŻ�����s����/��s���ߤj�Ӥ@��");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building06));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.969978, 121.193669));
		m.title("I�ӧ��]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building11));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.968338, 121.191422));
		m.title("�оǺ�X��s�j��");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building01));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.970698, 121.193369));
		m.title("I1�޲z�G�]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building06));
		markers.add(m);
		m=new MarkerOptions();
		m.position(new LatLng(24.970824, 121.192645));
		m.title("MA�E�g�]");
		m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_building07));
		markers.add(m);
		
		}
}


