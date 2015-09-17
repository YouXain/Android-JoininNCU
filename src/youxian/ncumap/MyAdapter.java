package youxian.ncumap;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


public class MyAdapter extends BaseAdapter implements Filterable{
	private LayoutInflater adapterLayoutInflater;
	private ImageView typeimage;
	private TextView username;
	private TextView datetime;
	private TextView title;
	List<ActivityData> allData;
	List<ActivityData> nallData;
	List<ActivityData> fixedData;
	public MyAdapter(Context c,List<ActivityData> a){
		adapterLayoutInflater = LayoutInflater.from(c);
		allData=a;
		fixedData=a;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return allData.size();
	}

	@Override
	public ActivityData getItem(int position) {
		// TODO Auto-generated method stub
		return allData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return allData.get(position).getActivityId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = adapterLayoutInflater.inflate(R.layout.list_row, null);
		typeimage=(ImageView)convertView.findViewById(R.id.icon);
		username=(TextView)convertView.findViewById(R.id.username);
		datetime=(TextView)convertView.findViewById(R.id.datetime);
		title=(TextView)convertView.findViewById(R.id.title);
		checkType(allData.get(position).getType());
		username.setText(allData.get(position).getUserName());
		datetime.setText(allData.get(position).getUploadTime());
		title.setText(allData.get(position).getTitle());
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
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return new Filter(){

			@Override
			protected FilterResults performFiltering(CharSequence charSequence) {
				// TODO Auto-generated method stub
				FilterResults results = new FilterResults();

                //If there's nothing to filter on, return the original data for your list
                if(charSequence == null || charSequence.length() == 0)
                {
                    results.values = fixedData;
                    results.count = fixedData.size();
                }
                else
                {
                    nallData=new ArrayList<ActivityData>();
                    for(int i=0 ; i<=fixedData.size()-1;i++)
                    {
                        //In this loop, you'll filter through originalData and compare each item to charSequence.
                        //If you find a match, add it to your new ArrayList
                        //I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
                        if(fixedData.get(i).getTitle().contains(charSequence))
                        {
                            nallData.add(fixedData.get(i));
                        }
                    }            

                    results.values = nallData;
                    results.count = nallData.size();
                    //results.count = filteredResultsData.size();
                }

                return results;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				// TODO Auto-generated method stub
				 if (results.count == 0)
				        notifyDataSetInvalidated();
				    else {
				        allData = (List<ActivityData>) results.values;
				        notifyDataSetChanged();
				    }
				
			}
			
		 };
	}

		
}