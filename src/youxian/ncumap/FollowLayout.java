package youxian.ncumap;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;



@SuppressLint({ "ViewConstructor", "SimpleDateFormat" })
class FollowLayout extends LinearLayout{
	Context con;
	FrameLayout mainlayout;
	ListView followlist;
	List<ActivityData> followdatas;
	StableArrayAdapter mAdapter;
	BackgroundContainer mBackgroundContainer;
	AlarmReceiver alarm;
    boolean mSwiping = false;
    boolean mItemPressed = false;
    SharedPreferences appSharedPrefs;
    HashMap<Long, Integer> mItemIdTopMap = new HashMap<Long, Integer>();
    private static final int SWIPE_DURATION = 250;
    private static final int MOVE_DURATION = 150;
	public FollowLayout(Context context,FrameLayout mainlayout, AlarmReceiver alarm) {
		super(context);
		con=context;
		this.alarm=alarm;
		//this.firstTime=firstTime;
		init(context,mainlayout);		
		// TODO Auto-generated constructor stub
	}
	
	private void init(Context context,FrameLayout mainlayout){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	mainlayout.removeAllViews();
		mainlayout.addView(inflater.inflate(R.layout.lise_follow, this));//描述跳出條架構的xml檔案  抽出來存到this
    	findview();
    	readSharedPreferences();
    	final ArrayList<ActivityData> followList = new ArrayList<ActivityData>();
    	for(ActivityData a: followdatas){
			followList.add(a);
		}
		initList(followList);
		//Showcase();
    	
	}
	private void initList(ArrayList<ActivityData> followList) {
		// TODO Auto-generated method stub
		mAdapter = new StableArrayAdapter(con,R.layout.opaque_text_view, followList,
                mTouchListener, alarm);
	  	followlist.setAdapter(mAdapter);
	  	followlist.setOnItemClickListener(mOnItemClickListener);
	}
	OnItemClickListener mOnItemClickListener = new OnItemClickListener(){
		 public void onItemClick(AdapterView<?> parent, View view, int position, long id){
			//Activity a=(Activity) con;
   		final Intent intent = new Intent(con, ShowActivity.class);
   		Bundle bundle = new Bundle();
   		//bundle.putSerializable("alarm", alarm);
   		bundle.putSerializable("clickedData", followdatas.get(position));
   		intent.putExtras(bundle);
   		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   		con.startActivity(intent);
   		((Activity) con).overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		 }
	};
	@SuppressLint("SimpleDateFormat")
	private void readSharedPreferences() {
		// TODO Auto-generated method stub
		followdatas =new ArrayList<ActivityData>();
		appSharedPrefs = PreferenceManager
				  .getDefaultSharedPreferences(con.getApplicationContext());
				  Gson gson = new Gson();
				  Map<String, ?> jsons=appSharedPrefs.getAll();
				  jsons.remove("Alarm");
					  for(Map.Entry<String,?> entry : jsons.entrySet())
				        {
						  	ActivityData myd = gson.fromJson(entry.getValue().toString(), ActivityData.class);
						  	followdatas.add(myd);  
						  	//String[] s=myd.getEndTime().split(" ");
						  	//SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");  
						  	//try {  
						  	//	date = format.parse(s[1]);  
						  	//} catch (ParseException e) {  
						  	    // TODO Auto-generated catch block  
						  	//    e.printStackTrace();  
						  	//}
						  	//if(date.before(curDate)){
						  	//	OutofDateList.add(myd);
						  	//}
						  	//else{
						  	//	followdatas.add(myd);  
						  	//}
						  	          
				        }
					  /*for(Map.Entry<String,?> entry : jsons.entrySet())
				        {
						  	for(ActivityData a:OutofDateList){
						  		if(gson.fromJson(entry.getValue().toString(), ActivityData.class).getActivityId()==a.getActivityId()){
						  			editor.remove(entry.getKey());
							  		editor.commit();
							  		break;
						  		}
						  	}
				        }*/  
	}
	private void findview() {
		// TODO Auto-generated method stub
		mBackgroundContainer = (BackgroundContainer) findViewById(R.id.listViewBackground);
		followlist=(ListView)findViewById(R.id.followlist);
	}
	
private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        
        float mDownX;
        private int mSwipeSlop = -1;
        
        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            if (mSwipeSlop < 0) {
                mSwipeSlop = ViewConfiguration.get(con).
                        getScaledTouchSlop();
            }
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mItemPressed) {
                    // Multi-item swipes not handled
                    return false;
                }
                mItemPressed = true;
                mDownX = event.getX();
                break;
            case MotionEvent.ACTION_CANCEL:
                v.setAlpha(1);
                v.setTranslationX(0);
                mItemPressed = false;
                break;
            case MotionEvent.ACTION_MOVE:
                {
                    float x = event.getX() + v.getTranslationX();
                    float deltaX = x - mDownX;
                    float deltaXAbs = Math.abs(deltaX);
                    if (!mSwiping) {
                        if (deltaXAbs > mSwipeSlop) {
                            mSwiping = true;
                            followlist.requestDisallowInterceptTouchEvent(true);
                            mBackgroundContainer.showBackground(v.getTop(), v.getHeight());
                        }
                    }
                    if (mSwiping) {
                        v.setTranslationX((x - mDownX));
                        v.setAlpha(1 - deltaXAbs / v.getWidth());
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                {
                	if(mItemPressed && ! mSwiping){
                		followlist.performItemClick(v, followlist.getPositionForView(v), v.getId());
                	}
                    // User let go - figure out whether to animate the view out, or back into place
                    if (mSwiping) {
                        float x = event.getX() + v.getTranslationX();
                        float deltaX = x - mDownX;
                        float deltaXAbs = Math.abs(deltaX);
                        float fractionCovered;
                        float endX;
                        float endAlpha;
                        final boolean remove;
                        if (deltaXAbs > v.getWidth() / 4) {
                            // Greater than a quarter of the width - animate it out
                            fractionCovered = deltaXAbs / v.getWidth();
                            endX = deltaX < 0 ? -v.getWidth() : v.getWidth();
                            endAlpha = 0;
                            remove = true;
                        } else {
                            // Not far enough - animate it back
                            fractionCovered = 1 - (deltaXAbs / v.getWidth());
                            endX = 0;
                            endAlpha = 1;
                            remove = false;
                        }
                        // Animate position and alpha of swiped item
                        // NOTE: This is a simplified version of swipe behavior, for the
                        // purposes of this demo about animation. A real version should use
                        // velocity (via the VelocityTracker class) to send the item off or
                        // back at an appropriate speed.
                        long duration = (int) ((1 - fractionCovered) * SWIPE_DURATION);
                        followlist.setEnabled(false);
                        v.animate().setDuration(duration).
                                alpha(endAlpha).translationX(endX).
                                withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Restore animated values
                                        v.setAlpha(1);
                                        v.setTranslationX(0);
                                        if (remove) {
                                            animateRemoval(followlist, v);
                                        } else {
                                            mBackgroundContainer.hideBackground();
                                            mSwiping = false;
                                            followlist.setEnabled(true);
                                        }
                                    }
                                });
                    }

                }
                mItemPressed = false;
                break;
            default: 
                return false;
            }
            return true;
        }
    };

    /**
     * This method animates all other views in the ListView container (not including ignoreView)
     * into their final positions. It is called after ignoreView has been removed from the
     * adapter, but before layout has been run. The approach here is to figure out where
     * everything is now, then allow layout to run, then figure out where everything is after
     * layout, and then to run animations between all of those start/end positions.
     */
    private void animateRemoval(final ListView listview, View viewToRemove) {
        int firstVisiblePosition = listview.getFirstVisiblePosition();
        for (int i = 0; i < listview.getChildCount(); ++i) {
            View child = listview.getChildAt(i);
            if (child != viewToRemove) {
                int position = firstVisiblePosition + i;
                long itemId = mAdapter.getItemId(position);
                mItemIdTopMap.put(itemId, child.getTop());
            }
        }
        // Delete the item from the adapter
        int position = followlist.getPositionForView(viewToRemove);
        mAdapter.remove(mAdapter.getItem(position),con.getApplicationContext());

        final ViewTreeObserver observer = listview.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                observer.removeOnPreDrawListener(this);
                boolean firstAnimation = true;
                int firstVisiblePosition = listview.getFirstVisiblePosition();
                for (int i = 0; i < listview.getChildCount(); ++i) {
                    final View child = listview.getChildAt(i);
                    int position = firstVisiblePosition + i;
                    long itemId = mAdapter.getItemId(position);
                    Integer startTop = mItemIdTopMap.get(itemId);
                    int top = child.getTop();
                    if (startTop != null) {
                        if (startTop != top) {
                            int delta = startTop - top;
                            child.setTranslationY(delta);
                            child.animate().setDuration(MOVE_DURATION).translationY(0);
                            if (firstAnimation) {
                                child.animate().withEndAction(new Runnable() {
                                    public void run() {
                                        mBackgroundContainer.hideBackground();
                                        mSwiping = false;
                                        followlist.setEnabled(true);
                                    }
                                });
                                firstAnimation = false;
                            }
                        }
                    } else {
                        // Animate new views along with the others. The catch is that they did not
                        // exist in the start state, so we must calculate their starting position
                        // based on neighboring views.
                        int childHeight = child.getHeight() + listview.getDividerHeight();
                        startTop = top + (i > 0 ? childHeight : -childHeight);
                        int delta = startTop - top;
                        child.setTranslationY(delta);
                        child.animate().setDuration(MOVE_DURATION).translationY(0);
                        if (firstAnimation) {
                            child.animate().withEndAction(new Runnable() {
                                public void run() {
                                    mBackgroundContainer.hideBackground();
                                    mSwiping = false;
                                    followlist.setEnabled(true);
                                }
                            });
                            firstAnimation = false;
                        }
                    }
                }
                mItemIdTopMap.clear();
                return true;
            }
        });
    }
	
}