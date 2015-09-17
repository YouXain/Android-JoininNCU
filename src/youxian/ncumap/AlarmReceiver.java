package youxian.ncumap;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class AlarmReceiver extends BroadcastReceiver implements Serializable{
	private static final long serialVersionUID = 1L;
	List<ActivityData>followedData=new ArrayList<ActivityData>();
	//List<Intent> intents=new ArrayList<Intent>();
	//List<PendingIntent> pendingintents=new ArrayList<PendingIntent>();
	public AlarmReceiver(){
		
	}
    @Override
    public void onReceive(Context context, Intent intent) {
        //....do something
    	Bundle bundle = intent.getExtras();
    	ActivityData myData=(ActivityData) bundle.getSerializable("Data");
    	NotificationManager noMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    	String s[]=myData.getStartTime().split(" ");
    	NotificationCompat.Builder mBuilder = 
    	    	new NotificationCompat.Builder(context)
    	        .setContentTitle(myData.getTitle())
    	        .setContentText("It's coming soon : "+s[1]+" >>details")
    	        .setSmallIcon(R.drawable.ic_logo)
    	        .setDefaults(Notification.DEFAULT_VIBRATE)
    	        .setDefaults(Notification.DEFAULT_SOUND)
    	        .setAutoCancel(true);
    	    	Intent resultIntent = new Intent(context, ShowActivity.class);
    	    	Bundle notifybundle = new Bundle();
    			notifybundle.putSerializable("clickedData", myData);
    			resultIntent.putExtras(notifybundle);
    			resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	    	TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
    	    	stackBuilder.addParentStack(ShowActivity.class);
    	    	stackBuilder.addNextIntent(resultIntent);
    	    	PendingIntent resultPendingIntent =
    	    	        stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
    	    	mBuilder.setContentIntent(resultPendingIntent); 	    	
    	    	noMgr.notify(myData.getActivityId(), mBuilder.build());
    }
    @SuppressLint("SimpleDateFormat")
	public void startearlyTimer(Context context,ActivityData a){
    	
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar cal  = Calendar.getInstance();
		try {
			cal.setTime(df.parse(a.getStartTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long hourago=cal.getTimeInMillis()-3600*1000;
		long dayago=cal.getTimeInMillis()-86400*1000;
		//Log.e(""+hourago, "timer");
		//Log.e(""+System.currentTimeMillis(), "now");
		if(hourago>System.currentTimeMillis()){
			Intent intent = new Intent(context, AlarmReceiver.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("Data", a);
			intent.putExtras(bundle);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//intents.add(intent);
			PendingIntent pending = PendingIntent.getBroadcast(context, UploadTimeToInt(a.getUploadTime()), intent, PendingIntent.FLAG_UPDATE_CURRENT);
			//pendingintents.add(pending);
			//Log.e(""+UploadTimeToInt(a.getUploadTime()), "create");
			AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			//AlarmManager alarmManager2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, hourago, pending);
			
			if(dayago>System.currentTimeMillis()){
				alarmManager.set(AlarmManager.RTC_WAKEUP, dayago, pending);
			}
			//alarmManager.cancel(pending);
			//MyCountDownTimer timer= new MyCountDownTimer(i,1,context,a);
			//timer.start();
		}
		
    }
 
    public void deletAlarm(Context context, ActivityData a){
    	/*int targetI=-1;
    	for(int i=0;i<=intents.size()-1;i--){
    		Bundle bundle = intents.get(i).getExtras();
        	ActivityData myData=(ActivityData) bundle.getSerializable("Data");
        	if(myData.getTitle().contains(a.getTitle())){
        		targetI=i;
        		break;
        	}
        		
    	}
    	if(targetI!=-1 && pendingintents.size()!=0 && intents.size()!=0){
    		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    		alarmManager.cancel(pendingintents.get(targetI));
        	intents.remove(targetI);
        	pendingintents.remove(targetI);
    	}*/
    	Intent intent = new Intent(context, AlarmReceiver.class);
    	boolean alarmUp = (PendingIntent.getBroadcast(context, UploadTimeToInt(a.getUploadTime()), 
                intent, PendingIntent.FLAG_NO_CREATE) != null);
    	if(alarmUp){
    		PendingIntent pending = PendingIntent.getBroadcast(context, UploadTimeToInt(a.getUploadTime()), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    		alarmManager.cancel(pending);
    		//Log.e("sucess", "alarm");
    	}
    }
	private int UploadTimeToInt(String Uploadtime){
		String s[]=Uploadtime.split(" ");
		String s1[]=s[0].split("-");
		String s2[]=s[1].split(":");
		String TimeString=s1[1]+s1[2]+s2[0]+s2[1];
		return Integer.parseInt(TimeString);
	}



}