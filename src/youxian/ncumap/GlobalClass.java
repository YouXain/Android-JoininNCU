package youxian.ncumap;

import android.app.Application;

public class GlobalClass extends Application{
	private AlarmReceiver alarm;
	public AlarmReceiver getAlarm() {
        return alarm;
    }
     
    public void setAlarm(AlarmReceiver aAlarm) {
       alarm = aAlarm;
    }
}
