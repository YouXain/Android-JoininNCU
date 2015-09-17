package youxian.ncumap;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MapActivity extends FragmentActivity implements OnMarkerClickListener{
	/** Map */
	private GoogleMap mMap;
	//private TextView txtOutput;
	private Marker markerMe;

	/** 記錄軌跡 */
	private ArrayList<LatLng> traceOfMe;

	/** GPS */
	private LocationManager locationMgr;
	private String provider;
	final LatLng ncu = new LatLng(24.968372, 121.191783);
	private LatLng selectedLocation;
	private ImageButton moveCamera;
	private ImageButton clear;
	//private ImageButton updater;
	private Spinner spinner;
	private Spinner type_spinner;
	private Spinner campus_spinner;
	//private TextView goText;
	//private TextView clearText;
	//private String[] PlaceArray;
	private int campusInt=0;
	private ArrayAdapter<CharSequence> placeList;
	private ArrayAdapter<CharSequence> typeList;
	private ArrayAdapter<CharSequence> campusList;
	private List<ActivityData>targetdata=new ArrayList<ActivityData>();
	private List<MarkerOptions>markers=new ArrayList<MarkerOptions>();
	private List<Marker>actsmarkers=new ArrayList<Marker>();
	private List<Marker>placeMarkers=new ArrayList<Marker>();
	private List<MarkerOptions>showingMarkers=new ArrayList<MarkerOptions>();
	private MarkerOptions targetmarker;
	private Location mylocation;
	private List<ActivityData>alldata;
	private List<Polyline> polylines = new ArrayList<Polyline>();
	private Boolean firstTime = null;
	private ShowcaseView s;
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_map);
	    findview();
	    getbundle();
	    setInfoWindow();
	    Showcase();
	}
	private void Showcase() {
		// TODO Auto-generated method stub
		if(firstTime!=null && firstTime){
			s = new ShowcaseView.Builder(MapActivity.this)
		    .setTarget(new ViewTarget(findViewById(R.id.camera_map)))
		    .setContentTitle("顯示全景")
		    .setContentText("視野調整至顯示整個中央大學的範圍")
		    
		    .setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					showcasehide();
						
					new ShowcaseView.Builder(MapActivity.this)
				    .setTarget(new ViewTarget(findViewById(R.id.clearbutton_map)))
				    .setContentTitle("清除路線")
				    .setContentText("對建築物進行導航之後，可清除顯示中的路線")
				    .build();
				}
		    	
		    })
		    .build();
			
		}
		
	}
	private void showcasehide() {
		// TODO Auto-generated method stub
		s.hide();
	}
	private void findview() {
		// TODO Auto-generated method stub
		FragmentManager myFragmentManager = getSupportFragmentManager();
		SupportMapFragment mySupportMapFragment = (SupportMapFragment)myFragmentManager.findFragmentById(R.id.map);
		mMap = mySupportMapFragment.getMap();
		mMap.getUiSettings().setZoomControlsEnabled(false);
		mMap.setOnMarkerClickListener(this);
		//mMap.getUiSettings().setZoomGesturesEnabled(false);
		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){

			@Override
			public void onInfoWindowClick(Marker marker) {
				// TODO Auto-generated method stub
				if(marker.getSnippet()==null){
					if(polylines!=null){
						for(Polyline p:polylines){
							p.remove();
						}
					}
					drawline(mylocation, marker.getPosition());
				}
				else{
					if(marker.getSnippet().contains(":")){
						String[] s1=marker.getSnippet().split(" : ");
						for(ActivityData a: targetdata){
							if(marker.getTitle().contains(a.getAddress()) && s1[1].contains(a.getTitle())){
								final Intent intent = new Intent(MapActivity.this, ShowActivity.class);
				        		Bundle bundle = new Bundle();
				        		bundle.putSerializable("clickedData", a);
				        		intent.putExtras(bundle);
				        		startActivity(intent);
							}
						}
					}
				}
				
				
			}
			
		});
		//mMap.setOnMarkerClickListener(markerClick);
		campus_spinner=(Spinner)findViewById(R.id.campus_spinner_map);
		moveCamera=(ImageButton)findViewById(R.id.camera_map);
		clear=(ImageButton)findViewById(R.id.clearbutton_map);
		//updater=(ImageButton)findViewById(R.id.update_map);
		spinner=(Spinner)findViewById(R.id.spinner_map);
		type_spinner=(Spinner)findViewById(R.id.type_spinner_map);
		
		//goText=(TextView)findViewById(R.id.go_map);
		//clearText=(TextView)findViewById(R.id.clear_map);
	}
	@SuppressWarnings("unchecked")
	private void getbundle() {
		// TODO Auto-generated method stub
		Bundle bundle = getIntent().getExtras();
		firstTime=bundle.getBoolean("isfirstTime");
		int i=bundle.getInt("key");
		//Log.e(""+i, "key");
		
		if(i==1){
			ActivityData temp=(ActivityData) bundle.getSerializable("targetData");
			targetdata.add(temp);
			for(ActivityData a:targetdata){
				String[] splited=a.getStartTime().split(" ");
				MarkerOptions m=new MarkerOptions();
				m.position(new LatLng(Double.parseDouble(a.getLat()), Double.parseDouble(a.getLng())));
				m.title(a.getAddress());
				m.snippet(splited[1]+" : "+a.getTitle());
				m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_red48));
				//todaymarkers.add(m);
				targetmarker=m;
				mMap.addMarker(m).showInfoWindow();
				setUIGone();
				//drawline();
			}
		}
		else if(i==0){
			ArrayList<ActivityData> temp=(ArrayList<ActivityData>) bundle.getSerializable("mapData");
			targetdata=temp;
			setMarkerbyData();
		}
		else{
			
		}
			
		
		
	}
	private void setUIGone() {
		// TODO Auto-generated method stub
		//updater.setVisibility(View.GONE);
		campus_spinner.setVisibility(View.GONE);
		type_spinner.setVisibility(View.GONE);
		spinner.setVisibility(View.GONE);
		clear.setVisibility(View.GONE);
	}
	@Override
    public boolean onMarkerClick(final Marker marker) {
		marker.setTitle(marker.getTitle());
        moveCameraTo(marker.getPosition());
        marker.showInfoWindow();
        for(int i=0;i<placeMarkers.size();i++){
        	if(marker.equals(placeMarkers.get(i))){
        		spinner.setSelection(i+1);
        	}
        }
        
		return true;
    }
	private void setInfoWindow() {
		// TODO Auto-generated method stub
		MyInfoWindowAdapter InfoWindow = null;
		if(targetdata!=null){
			InfoWindow = new MyInfoWindowAdapter(getLayoutInflater(),this.getApplicationContext(),targetdata);
			mMap.setInfoWindowAdapter(InfoWindow);
		}
		else{
			
		}
		/*
		if(todaydata!=null && targetdata==null){
			//todaydata.addAll(targetdata);
			InfoWindow = new MyInfoWindowAdapter(getLayoutInflater(),this.getApplicationContext(),todaydata);
			mMap.setInfoWindowAdapter(InfoWindow);
		}	
		else if(todaydata==null && targetdata!=null){
			InfoWindow = new MyInfoWindowAdapter(getLayoutInflater(),this.getApplicationContext(),targetdata);
			mMap.setInfoWindowAdapter(InfoWindow);
		}
		else{
			
		}*/
		
	}
	private void drawline(Location location, LatLng dest) {
		// TODO Auto-generated method stub
		if(location!=null){
			LatLng origin = new LatLng(location.getLatitude(), location.getLongitude());
	        //LatLng dest = targetmarker.getPosition();

	        // Getting URL to the Google Directions API
	        String url = getDirectionsUrl(origin, dest);

	        DownloadTask downloadTask = new DownloadTask();

	        // Start downloading json data from Google Directions API
	        downloadTask.execute(url);
		}
		else
			Toast.makeText(MapActivity.this, "Locate failed", Toast.LENGTH_SHORT).show();
		
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		//setviewmarkers();
		initCampusSpinner();
		initTypeSpinner();

		clear.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(polylines!=null){
					for(Polyline p:polylines){
						p.remove();
					}
				}
			}
			
		});
		moveCamera.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CameraPosition currentPlace = new CameraPosition.Builder()
		        .target(ncu)
		        .bearing(90).zoom(16.2f).build();
				mMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
			}
			
		});
		/*updater.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateData();
			}
			
		});*/
	}
	private void initCampusSpinner() {
		campusList = ArrayAdapter.createFromResource(this, R.array.campusArray, R.layout.spinner_item);
		campusList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		campus_spinner.setAdapter(campusList);
		campus_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg0.getSelectedItem().toString().contains("中央大學")){
					campusInt=0;
					moveCameraToCampus(ncu);
					spinner.setSelection(0);
				}
				else if(arg0.getSelectedItem().toString().contains("中原大學")){
					campusInt=1;
					moveCameraToCampus(new LatLng(24.958403,121.243370));
					spinner.setSelection(0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	private void initTypeSpinner() {
		// TODO Auto-generated method stub
		//PlaceArray=getResources().getStringArray(R.array.placeArray);
		typeList = ArrayAdapter.createFromResource(this, R.array.typeArray, R.layout.spinner_item);
		typeList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		type_spinner.setAdapter(typeList);
		type_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg0.getSelectedItem().toString().contains("教學研究")){
					showingMarkers.removeAll(showingMarkers);
					new StudyMarker(showingMarkers, campusInt);
					addviewmarkers(showingMarkers);
					placeList = ArrayAdapter.createFromResource(MapActivity.this, R.array.studyplaceArray, R.layout.spinner_item);
					initSpinner();
				}
				else if(arg0.getSelectedItem().toString().contains("行政資源")){
					showingMarkers.removeAll(showingMarkers);
					new AdminMarker(showingMarkers, campusInt);
					addviewmarkers(showingMarkers);
					placeList = ArrayAdapter.createFromResource(MapActivity.this, R.array.adminplaceArray, R.layout.spinner_item);
					initSpinner();
				}
				else if(arg0.getSelectedItem().toString().contains("生活機能")){
					showingMarkers.removeAll(showingMarkers);
					new LifeMarker(showingMarkers, campusInt);
					addviewmarkers(showingMarkers);
					placeList = ArrayAdapter.createFromResource(MapActivity.this, R.array.lifeplaceArray, R.layout.spinner_item);
					initSpinner();
				}
				else if(arg0.getSelectedItem().toString().contains("運動休閒")){
					showingMarkers.removeAll(showingMarkers);
					new SportMarker(showingMarkers, campusInt);
					addviewmarkers(showingMarkers);
					placeList = ArrayAdapter.createFromResource(MapActivity.this, R.array.sportplaceArray, R.layout.spinner_item);
					initSpinner();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	private void addviewmarkers(List<MarkerOptions> markers) {
		// TODO Auto-generated method stub
	    //mMap.clear();
		if(markers!=null){
			showingMarkers=markers;
			int size=placeMarkers.size();
			for(int i=0;i<size;i++){
				placeMarkers.get(i).remove();
			}
			
		}
		placeMarkers.removeAll(placeMarkers);
		
		
		
		for(MarkerOptions m1: showingMarkers){
			placeMarkers.add(mMap.addMarker(m1));
		}
		
		
		if(actsmarkers!=null){
			for(Marker m2: actsmarkers){
				m2.showInfoWindow();
				m2.hideInfoWindow();
			}
		}
		
		
		
	}
	private void initSpinner() {
		placeList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(placeList);
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				for(Marker p: placeMarkers){
					if(arg0.getSelectedItem().toString().contains(p.getTitle())){
						p.showInfoWindow();
						//selectedLocation=p.getPosition();
						moveCameraTo(p.getPosition());
						break;
					}
					//selectedLocation=null;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
	}
	private void initMap() {
		// TODO Auto-generated method stub
		moveCameraToCampus(ncu);
		//mMap.getUiSettings().setScrollGesturesEnabled(false);
		//moveCameraTo(ncu);
		
		//mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ncu,16.2f));
	}
	private void moveCameraToCampus(LatLng target){
		CameraPosition currentPlace = new CameraPosition.Builder()
        .target(target)
        .bearing(90).zoom(16.2f).build();
		mMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
	}
	@Override
	protected void onStart() {
	    super.onStart();

	    initView();
	    initMap();
	    if (initLocationProvider()) {
	        whereAmI();
	        if(targetmarker!=null){
	        	moveCameraTo(targetmarker.getPosition());
	        	drawline(mylocation, targetmarker.getPosition());
	        }
	    }else{
	        //txtOutput.setText("請開啟定位！");
	    	Toast.makeText(MapActivity.this,"請開啟GPS", Toast.LENGTH_SHORT).show();
	    }
	}
	@Override
	protected void onStop() {
	    locationMgr.removeUpdates(locationListener);
	    super.onStop();
	}
	@Override
	protected void onResume() {
		// animateIn this activity
		//ActivitySwitcher.animationIn(findViewById(R.id.total_layout), getWindowManager());
		super.onResume();
	}
	@Override
	protected void onPause() {
		// animateIn this activity
		//ActivitySwitcher.animationIn(findViewById(R.id.total_layout), getWindowManager());
		super.onPause();
	}/*
	private void updateData() {
		// TODO Auto-generated method stub
		
		final ProgressDialog myDialog = ProgressDialog.show
				  (MapActivity.this, "loading", "loading list", true);					
			    new AsyncTask<Void, Void, Void>()
		            {
		                @Override
		                protected Void doInBackground(Void... params)
		                {
		                	alldata=Connector.SendGetDataCommand();	    	             	
		    	       		if(alldata==null){
		    	       				myDialog.dismiss();
		    	       				Log.e("1","Error");
	                   			//Toast.makeText(RoutePlan.this,"NotFound", Toast.LENGTH_SHORT).show();
	                   			this.onCancelled();
	                   		}
		    	       		myDialog.dismiss();
		        	            return null;
		        	        }
		              @Override
	     	     protected void onPostExecute(Void result)//收尾
	     	     {   	
		            	if(alldata==null){
		            		myDialog.dismiss();
		            		Log.e("2","Error");
		            		Toast.makeText(MapActivity.this,"連線失敗:你沒上網或是我沒開server", Toast.LENGTH_SHORT).show();
		            	}
		            	else{
		            		mMap.clear();
		            		targetdata=getTodayData();
		            		setMarkerbyData();
		            		myDialog.dismiss();
		            	}           	  
	    	     }
		       }.execute();		    
	}*/
	@SuppressLint("SimpleDateFormat")
	private ArrayList<ActivityData> getTodayData(){
		ArrayList<ActivityData> todays= new ArrayList<ActivityData>();
		//Calendar c = Calendar.getInstance();
		//String s =""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
		Date today = new Date();
		//Log.e(""+today.toString(), "today");
		//Log.e(""+alldata.size(), "all");
		if(alldata!=null){
			for(ActivityData a:alldata){
				//String startTime=a.getStartTime();
				//String endTime=a.getEndTime();
		    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		    	
		    	Date enddate = null;
		    	Date startdate = null;
				try {
					startdate = dateFormat.parse(a.getStartTime());
					enddate = dateFormat.parse(a.getEndTime());
					//Log.e(""+startdate.toString(), "s");
					//Log.e(""+enddate.toString(), "e");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	if(startdate!=null && enddate!=null && today.after(startdate) && today.before(enddate)){
		    		todays.add(a);
		    	}
			}
		}
		
		//Log.e(""+todays.size(), "123");
		return todays;
	}
	private void setMarkerbyData(){
		actsmarkers.removeAll(actsmarkers);
		for(ActivityData a:targetdata){
			String[] splited=a.getStartTime().split(" ");
			MarkerOptions m=new MarkerOptions();
			m.position(new LatLng(Double.parseDouble(a.getLat()), Double.parseDouble(a.getLng())+0.0002));
			m.title(a.getAddress());
			m.snippet(splited[1]+" : "+a.getTitle());
			m.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_blue48));
			markers.add(m);
			actsmarkers.add(mMap.addMarker(m));
			//mMap.addMarker(m);
		
		}
	}
	private void moveCameraTo(LatLng target){
		CameraPosition currentPlace = new CameraPosition.Builder()
        .target(target)
        .bearing(90).zoom(17.5f).build();
		mMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
	}
	private void whereAmI() {
		// TODO Auto-generated method stub
		mylocation = locationMgr.getLastKnownLocation(provider);
		updateWithNewLocation(mylocation);

		//GPS Listener
		locationMgr.addGpsStatusListener(gpsListener);

		//Location Listener
		int minTime = 5000;//ms
		int minDist = 5;//meter
		locationMgr.requestLocationUpdates(provider, minTime, minDist, locationListener);
	}
	GpsStatus.Listener gpsListener = new GpsStatus.Listener() {
	    @Override
	    public void onGpsStatusChanged(int event) {
	        switch (event) {
	            case GpsStatus.GPS_EVENT_STARTED:
	            //Log.d("TAG", "GPS_EVENT_STARTED");
	            //Toast.makeText(MapActivity.this, "GPS_EVENT_STARTED", Toast.LENGTH_SHORT).show();
	            break;
	            case GpsStatus.GPS_EVENT_STOPPED:
	            //Log.d("TAG", "GPS_EVENT_STOPPED");
	            //Toast.makeText(MapActivity.this, "GPS_EVENT_STOPPED", Toast.LENGTH_SHORT).show();
	            break;
	            case GpsStatus.GPS_EVENT_FIRST_FIX:
	            //Log.d("TAG", "GPS_EVENT_FIRST_FIX");
	            //Toast.makeText(MapActivity.this, "GPS_EVENT_FIRST_FIX", Toast.LENGTH_SHORT).show();
	            break;
	            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
	            //Log.d("TAG", "GPS_EVENT_SATELLITE_STATUS");
	            break;
	       }
	    }
	};
	LocationListener locationListener = new LocationListener(){
		 @Override
		 public void onLocationChanged(Location location) {
		  updateWithNewLocation(location);
		 }

		 @Override
		 public void onProviderDisabled(String provider) {
		  updateWithNewLocation(null);
		 }

		 @Override
		 public void onProviderEnabled(String provider) {

		 }

		 @Override
		 public void onStatusChanged(String provider, int status, Bundle extras) {
		  switch (status) {
		      case LocationProvider.OUT_OF_SERVICE:
		      //Log.v("TAG", "Status Changed: Out of Service");
		      Toast.makeText(MapActivity.this, "Status Changed: Out of Service", Toast.LENGTH_SHORT).show();
		      break;
		      case LocationProvider.TEMPORARILY_UNAVAILABLE:
		      //Log.v("TAG", "Status Changed: Temporarily Unavailable");
		      Toast.makeText(MapActivity.this, "Status Changed: Temporarily Unavailable", Toast.LENGTH_SHORT).show();
		      break;
		      case LocationProvider.AVAILABLE:
		      //Log.v("TAG", "Status Changed: Available");
		      Toast.makeText(MapActivity.this, "Status Changed: Available", Toast.LENGTH_SHORT).show();
		      break;
		  }
		 }

		};
	/**
	 * 更新並顯示新位置
	 * @param location
	 */
	private void updateWithNewLocation(Location location) {
	 String where = "";
	 if (location != null) {
	  //經度
	  double lng = location.getLongitude();
	  //緯度
	  double lat = location.getLatitude();
	  //速度
	  float speed = location.getSpeed();
	  //時間
	  long time = location.getTime();
	  String timeString = getTimeString(time);

	  where = "經度: " + lng + 
	   "\n緯度: " + lat + 
	   "\n速度: " + speed + 
	   "\n時間: " + timeString +
	   "\nProvider: " + provider;

	  //"我"
	  showMarkerMe(lat, lng);
	  //cameraFocusOnMe(lat, lng);
	  trackToMe(lat, lng);

	 }else{
		 //Log.e("e", "null location");
	 }

	 //顯示資訊
	 //txtOutput.setText(where);
	}
	/**
	 * 顯示"我"在哪裡
	 * @param lat
	 * @param lng
	 */
	private void showMarkerMe(double lat, double lng){
	 if (markerMe != null) {
	  markerMe.remove();
	 }

	 MarkerOptions markerOpt = new MarkerOptions();
	 markerOpt.position(new LatLng(lat, lng));
	 markerOpt.title("我在這裡");
	 markerMe = mMap.addMarker(markerOpt);

	 //Toast.makeText(this, "lat:" + lat + ",lng:" + lng, Toast.LENGTH_SHORT).show();
	}
	private void cameraFocusOnMe(double lat, double lng){
		 CameraPosition camPosition = new CameraPosition.Builder()
		    .target(new LatLng(lat, lng))
		    .zoom(16)
		    .build();

		 mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPosition));
	}
	private void trackToMe(double lat, double lng){
		 if (traceOfMe == null) {
		  traceOfMe = new ArrayList<LatLng>();
		}
		 traceOfMe.add(new LatLng(lat, lng));

		 PolylineOptions polylineOpt = new PolylineOptions();
		 for (LatLng latlng : traceOfMe) {
		  polylineOpt.add(latlng);
		 }
		 polylineOpt.width(4);
		 polylineOpt.color(Color.BLUE);
		 
		 Polyline line = mMap.addPolyline(polylineOpt);
		 line.setWidth(10);
		}
	private String getTimeString(long timeInMilliseconds){
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 return format.format(timeInMilliseconds);
		}
	
	
	/**
	 * GPS初始化，取得可用的位置提供器
	 * @return
	 */
	private boolean initLocationProvider() {
	 locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	 //1.選擇最佳提供器
	 Criteria criteria = new Criteria();
	 criteria.setAccuracy(Criteria.ACCURACY_FINE);
	 criteria.setAltitudeRequired(false);
	 criteria.setBearingRequired(false);
	 criteria.setCostAllowed(true);
	 criteria.setPowerRequirement(Criteria.POWER_LOW);
	  
	 provider = locationMgr.getBestProvider(criteria, true);
	  
	if (provider != null) {
	  return true;
	 }

	 //2.選擇使用GPS提供器
	 //if (locationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	  //provider = LocationManager.GPS_PROVIDER;
	  //return true;
	 //}

	 //3.選擇使用網路提供器
	 //if (locationMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
	 // provider = LocationManager.NETWORK_PROVIDER;
	 //return true;
	 //}

	 return false;
	}
	private String getDirectionsUrl(LatLng origin,LatLng dest){
		 
        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
 
        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
 
        // Sensor enabled
        String sensor = "sensor=false";
 
        // Waypoints
        String mode = "mode=walking";
        /*
        for(int i=2;i<markerPoints.size();i++){
            LatLng point  = (LatLng) markerPoints.get(i);
            if(i==2)
                waypoints = "waypoints=";
            waypoints += point.latitude + "," + point.longitude + "|";
        }*/
 
        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+mode;
 
        // Output format
        String output = "json";
 
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
 
        return url;
    }
	private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
 
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
 
            // Connecting to url
            urlConnection.connect();
 
            // Reading data from url
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb  = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }
 
            data = sb.toString();
 
            br.close();
 
        }catch(Exception e){
            //Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
	 private class DownloadTask extends AsyncTask<String, Void, String>{
		 
	        // Downloading data in non-ui thread
	        @Override
	        protected String doInBackground(String... url) {
	 
	            // For storing data from web service
	 
	            String data = "";
	 
	            try{
	                // Fetching the data from web service
	                 data = downloadUrl(url[0]);
	            }catch(Exception e){
	                //Log.d("Background Task",e.toString());
	            }
	            return data;
	        }
	 
	        // Executes in UI thread, after the execution of
	        // doInBackground()
	        @Override
	        protected void onPostExecute(String result) {
	            super.onPostExecute(result);
	 
	            ParserTask parserTask = new ParserTask();
	 
	            // Invokes the thread for parsing the JSON data
	            parserTask.execute(result);
	        }
	    }
	 
	    /** A class to parse the Google Places in JSON format */
	    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
	 
	        // Parsing the data in non-ui thread
	        @Override
	        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
	 
	        JSONObject jObject;
	        List<List<HashMap<String, String>>> routes = null;
	 
	            try{
	                jObject = new JSONObject(jsonData[0]);
	                DirectionsJSONParser parser = new DirectionsJSONParser();
	 
	                // Starts parsing data
	                routes = parser.parse(jObject);
	            }catch(Exception e){
	                e.printStackTrace();
	            }
	            return routes;
	        }
	 
	        // Executes in UI thread, after the parsing process
	        @Override
	        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
	 
	            ArrayList<LatLng> points = null;
	            PolylineOptions lineOptions = null;
	 
	            // Traversing through all the routes
	            for(int i=0;i<result.size();i++){
	                points = new ArrayList<LatLng>();
	                lineOptions = new PolylineOptions();
	 
	                // Fetching i-th route
	                List<HashMap<String, String>> path = result.get(i);
	 
	                // Fetching all the points in i-th route
	                for(int j=0;j<path.size();j++){
	                    HashMap<String,String> point = path.get(j);
	 
	                    double lat = Double.parseDouble(point.get("lat"));
	                    double lng = Double.parseDouble(point.get("lng"));
	                    LatLng position = new LatLng(lat, lng);
	 
	                    points.add(position);
	                }
	 
	                // Adding all the points in the route to LineOptions
	                lineOptions.addAll(points);
	                lineOptions.width(4);
	                lineOptions.color(Color.RED);
	            }
	 
	             // Drawing polyline in the Google Map for the i-th route
	             polylines.add(mMap.addPolyline(lineOptions));
	         }
	    }
}
