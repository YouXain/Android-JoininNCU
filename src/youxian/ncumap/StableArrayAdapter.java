

package youxian.ncumap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StableArrayAdapter extends BaseAdapter {
	private LayoutInflater adapterLayoutInflater;
	private ImageView typeimage;
	private TextView username;
	private TextView datetime;
	private TextView title;
    HashMap<ActivityData, Integer> mIdMap = new HashMap<ActivityData, Integer>();
    View.OnTouchListener mTouchListener;
    List<ActivityData> datas;
    Context con;
    AlarmReceiver alarm;
    public StableArrayAdapter(Context context, int textViewResourceId,
            List<ActivityData> objects, View.OnTouchListener listener, AlarmReceiver alarm) {
    	con=context;
    	adapterLayoutInflater = LayoutInflater.from(context);
        mTouchListener = listener;
        datas=objects;
        this.alarm=alarm;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
    	ActivityData item = datas.get(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	convertView = adapterLayoutInflater.inflate(R.layout.list_row, null);
    	typeimage=(ImageView)convertView.findViewById(R.id.icon);
		username=(TextView)convertView.findViewById(R.id.username);
		datetime=(TextView)convertView.findViewById(R.id.datetime);
		title=(TextView)convertView.findViewById(R.id.title);
		checkType(datas.get(position).getType());
		username.setText(datas.get(position).getUserName());
		datetime.setText(datas.get(position).getUploadTime());
		title.setText(datas.get(position).getTitle());
		convertView.setOnTouchListener(mTouchListener);
		return convertView;
    }
    private void checkType(String type) {
		// TODO Auto-generated method stub
		if(type.contains("111"))
			typeimage.setImageResource(R.drawable.event_all);
		if(type.contains("100"))
			typeimage.setImageResource(R.drawable.event_s);
		if(type.contains("010"))
			typeimage.setImageResource(R.drawable.event_t);
		if(type.contains("001"))
			typeimage.setImageResource(R.drawable.event_p);
		if(type.contains("110"))
			typeimage.setImageResource(R.drawable.event_ts);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public ActivityData getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	public void remove(ActivityData item,Context con) {
		// TODO Auto-generated method stub
		ActivityData myd=null;
		datas.remove(item);
		notifyDataSetChanged();
		SharedPreferences appSharedPrefs = PreferenceManager
				  .getDefaultSharedPreferences(con);
		Editor editor=appSharedPrefs.edit();
				  Gson gson = new Gson();
					  Map<String, ?> jsons=appSharedPrefs.getAll();
					  for(Map.Entry<String,?> entry : jsons.entrySet())
				        {
						  	myd = gson.fromJson(entry.getValue().toString(), ActivityData.class);
						  	if(item.getActivityId()==myd.getActivityId()){
						  		editor.remove(entry.getKey());
						  		//editor.clear();
						  		editor.commit();
						  		alarm.deletAlarm(con, myd);
						  		//resetprefs();
						  		break;
						  	}         
				        }  
		
	}


}
