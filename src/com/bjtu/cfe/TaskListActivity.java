package com.bjtu.cfe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.cfe.http.util.DataTransfer;
import com.cfe.http.util.PreferenceUtil;
import com.cfe.response.ResponseGetAllTasks;
import com.cfe.response.ResponseNewTask;
import com.cfe.response.entity.PositionInfo;

public class TaskListActivity extends Activity{
	
	private String phone;
	private ViewPager viewPager;//页卡内容
	private TextView textView1,textView2;
	private List<View> views;// Tab页面列表
	private int currIndex = 0;// 当前页卡编号
	private View view1,view2;//各个页卡
	private LinearLayout done_layout;

	private ListView done_task_list;
	private MapView undoneMap = null;
    BaiduMap mBaiduMap;
	private ArrayList<Map<String, String>> done_task_listData, undone_task_listData;
	private String tkid;
	
	private TextView title;
	private Button backspace;
	
	private LocationClient mLocClient;
	boolean isFirstLoc = true;
    public MyLocationListenner myListener = new MyLocationListenner();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		SDKInitializer.initialize(getApplicationContext()); 
		setContentView(R.layout.task_list);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

        InitViews();   
		
		Task task = new Task(this);
		task.execute();
		
		//绑定页卡内元素点击事件
		done_task_list.setOnItemClickListener(new DoneListListener());//点击跳转到订单信息
		
	}
	
	@Override  
    protected void onDestroy() {  
        super.onDestroy(); 
        undoneMap.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        undoneMap.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        undoneMap.onPause();  
        }  
	
	
	private void InitViews(){
		//绑定控件
        title = (TextView) findViewById(R.id.title_text);
        //undone_layout = (LinearLayout) findViewById(R.id.undone_task_layout);
        done_layout = (LinearLayout) findViewById(R.id.done_task_layout);
        backspace = (Button) findViewById(R.id.title_button);
        
        backspace.setVisibility(View.INVISIBLE);
        
        title.setText("任务列表");
        
		InitTextView();
		InitViewPager();
	}

	private void InitViewPager() {
		views=new ArrayList<View>();
		LayoutInflater inflater=getLayoutInflater();
		
		//绑定页卡容器
		viewPager=(ViewPager) findViewById(R.id.task_pager);		
		//绑定样式文件
		view1=inflater.inflate(R.layout.undone_task2, null);
		view2=inflater.inflate(R.layout.done_task, null);
		//绑定控件
		undoneMap = (MapView) view1.findViewById(R.id.ic_map);
		Button btnFinish = (Button) view1.findViewById(R.id.btn_finish);
		Button btnNew = (Button) view1.findViewById(R.id.btn_accept_order);
		btnFinish.setOnClickListener(new Button.OnClickListener(){  
            public void onClick(View v) {   
            	String url = "http://166.111.81.196:8078/finish_current/?phone=" + phone;
            	new HttpAsyncTask().execute(url);
            }
        });
		btnNew.setOnClickListener(new Button.OnClickListener(){  
            public void onClick(View v) {
            	String url = "http://166.111.81.196:8078/new_task/?phone=" + phone;
            	new HttpAsyncTask2().execute(url);
            }
        });
		
		mBaiduMap = undoneMap.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
		done_task_list = (ListView) view2.findViewById(R.id.done_task_list);
		//添加页卡到容器
		views.add(view1);
		views.add(view2);
		//设置页卡容器
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(0);
		//绑定页卡切换事件
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	 /**
	  *  初始化头标
	  */
	private void InitTextView() {
		//绑定控件
		textView1 = (TextView) findViewById(R.id.undone_task_tab);
		textView2 = (TextView) findViewById(R.id.done_task_tab);

		//绑定事件
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));		
	}

	/** 
	 *     
	 * 头标点击监听 3 */
	private class MyOnClickListener implements OnClickListener{
        private int index = 0;
        public MyOnClickListener(int i){
        	index = i;
        }
		public void onClick(View v) {
			viewPager.setCurrentItem(index);
			
			Resources res = getResources();
			
			if (index == 0)
			{
				//undone_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_white));
				done_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_gray));
				textView1.setTextColor(res.getColor(R.color.black));
				textView2.setTextColor(res.getColor(R.color.dimgray));
			}
			else
			{
				done_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_white));
				//undone_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_gray));
				textView2.setTextColor(res.getColor(R.color.black));
				textView1.setTextColor(res.getColor(R.color.dimgray));
			}
		}
		
	}
	
	public class MyViewPagerAdapter extends PagerAdapter{
		private List<View> mListViews;
		
		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) 	{	
			container.removeView(mListViews.get(position));
		}


		@Override
		public Object instantiateItem(ViewGroup container, int position) {			
			 container.addView(mListViews.get(position), 0);
			 return mListViews.get(position);
		}

		@Override
		public int getCount() {			
			return  mListViews.size();
		}
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {			
			return arg0==arg1;
		}
	}

    public class MyOnPageChangeListener implements OnPageChangeListener{

//    	int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		public void onPageScrollStateChanged(int arg0) {
			
			
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
			
		}

		public void onPageSelected(int arg0) {
			currIndex = arg0;
			
			Resources res = getResources();
			
			if (currIndex == 0)
			{
				//undone_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_white));
				done_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_gray));
				textView1.setTextColor(res.getColor(R.color.black));
				textView2.setTextColor(res.getColor(R.color.dimgray));
			}
			else
			{
				done_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_white));
				//undone_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_gray));
				textView2.setTextColor(res.getColor(R.color.black));
				textView1.setTextColor(res.getColor(R.color.dimgray));
			}
		}
    	
    }
    
    private SimpleAdapter getDoneAdapter() {
    	
    	//测试例子-Start----------------------------------------
//		done_task_listData = new ArrayList<Map<String, String>>();
//
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("num", "4");
//		map.put("time", "12:00");
//		map.put("date", "2013-9-29");
//		done_task_listData.add(map);
//
//		map = new HashMap<String, String>();
//		map.put("num", "3");
//		map.put("time", "13:00");
//		map.put("date", "2013-9-29");
//		done_task_listData.add(map);
		//测试例子-End-------------------------------------------

		return new SimpleAdapter(TaskListActivity.this, done_task_listData,
				R.layout.task_item2, new String[] { "taskId", "time", "date"},
				new int[] { R.id.task_num, R.id.task_time, R.id.task_date });
	}
    
    class DoneListListener implements OnItemClickListener
    {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
	    	Intent intent = new Intent(TaskListActivity.this, OrderListActivity.class);
	    	//View view2 = getc
	    	//Adapter adapter = parent.getAdapter();
	    	TextView tv = (TextView) view.findViewById(R.id.task_num);
	    	String taskId = tv.getText().toString();
	    	intent.putExtra("taskId", Integer.parseInt(taskId));
	    	startActivity(intent);
		}
    }
    
    class UndoneListListener implements OnItemClickListener
    {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
	    	Intent intent = new Intent(TaskListActivity.this, OrderListActivity.class);
	    	intent.putExtra("position", position);//送餐员的位置信息
	    	//TODO
	    	//方案1：传递任务ID，order界面再次网络请求orderlist
	    	//方案2：直接传递orderList给order UI
	    	//intent.putExtra();
	    	startActivity(intent);
		}
    }
    
    private class Task extends AsyncTask<String, Integer, ResponseGetAllTasks> {

    	Context mContext;
    	ProgressDialog mProgressDialog;
    	
		public Task(Context context) {
			// TODO Auto-generated constructor stub
			super();
    		mContext = context;
    		mProgressDialog = new ProgressDialog(context);
    		mProgressDialog.setIndeterminate(true);
    		mProgressDialog.setMessage("努力加载中...");
		}
    	
    	@Override
    	protected void onPreExecute() {
    		mProgressDialog.show();
    	}

		@Override
		protected ResponseGetAllTasks doInBackground(String... params) {
			//TODO
			
			//测试
			phone = PreferenceUtil.getString(mContext, "phone", "");//"34352132434";
			//double x = 160.004;
			//double y = 40.002;
						
			//2015-11-09-获得所有任务=待完成+已经完成
			ResponseGetAllTasks rp = DataTransfer.getAllTasks(phone);
					
			return rp;
		}
		
		@Override
		protected void onPostExecute(ResponseGetAllTasks rp)
		{
			mProgressDialog.dismiss();
			
			undone_task_listData = new ArrayList<Map<String, String>>();
			done_task_listData = new ArrayList<Map<String, String>>();		
								
			if(rp.getState() == 0){
				return;
			}

			int i;
			Map<String, String> map;
			
			//20151109-解析已经完成的任务
			for (i = 0;i < rp.getFinished().size();i++)
			{
				map = new HashMap<String, String>();

				// 将任务信息添加到列表数据中
				map.put("taskId", rp.getFinished().get(i).getId());	
				//String timeString = rp.getFinished().get(i).getTime();
				//map.put("time", DateUtil.splitStringDate(timeString, 0));
				map.put("time", rp.getFinished().get(i).getTime());
				map.put("date", "");
				done_task_listData.add(map);			
			}
			done_task_list.setAdapter(getDoneAdapter());	
			
			//TODO
			//解析待完成任务的地点信息，在地图表示
			List<PositionInfo> on_task = rp.getOn_task();
			on_task = msort(on_task);
			List<LatLng> points = new ArrayList<LatLng>();
			LatLng p0 = new LatLng(on_task.get(0).getY(), on_task.get(0).getX());
			LatLng p1 = new LatLng(on_task.get(1).getY(), on_task.get(1).getX());
			LatLng p2 = new LatLng(on_task.get(2).getY(), on_task.get(2).getX());
			LatLng p3 = new LatLng(on_task.get(3).getY(), on_task.get(3).getX());
			LatLng p4 = new LatLng(on_task.get(4).getY(), on_task.get(4).getX());
			LatLng p5 = new LatLng(on_task.get(5).getY(), on_task.get(5).getX());
			LatLng p6 = new LatLng(on_task.get(6).getY(), on_task.get(6).getX());
			LatLng p7 = new LatLng(on_task.get(7).getY(), on_task.get(7).getX());
			LatLng p8 = new LatLng(on_task.get(8).getY(), on_task.get(8).getX());
			LatLng p9 = new LatLng(on_task.get(9).getY(), on_task.get(9).getX());
            points.add(p0);
			points.add(p1);
            points.add(p2);
            points.add(p3);
            points.add(p4);
            points.add(p5);
            points.add(p6);
            points.add(p7);
            points.add(p8);
            points.add(p9);
            OverlayOptions ooText0 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(0).getDesc())
                    .position(p0);
            OverlayOptions ooText1 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(1).getDesc())
                    .position(p1);
            OverlayOptions ooText2 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(2).getDesc())
                    .position(p2);
            OverlayOptions ooText3 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(3).getDesc())
                    .position(p3);
            OverlayOptions ooText4 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(4).getDesc())
                    .position(p4);
            OverlayOptions ooText5 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(5).getDesc())
                    .position(p5);
            OverlayOptions ooText6 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(6).getDesc())
                    .position(p6);
            OverlayOptions ooText7 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(7).getDesc())
                    .position(p7);
            OverlayOptions ooText8 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(8).getDesc())
                    .position(p8);
            OverlayOptions ooText9 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(9).getDesc())
                    .position(p9);
            mBaiduMap.addOverlay(ooText0);
            mBaiduMap.addOverlay(ooText1);
            mBaiduMap.addOverlay(ooText2);
            mBaiduMap.addOverlay(ooText3);
            mBaiduMap.addOverlay(ooText4);
            mBaiduMap.addOverlay(ooText5);
            mBaiduMap.addOverlay(ooText6);
            mBaiduMap.addOverlay(ooText7);
            mBaiduMap.addOverlay(ooText8);
            mBaiduMap.addOverlay(ooText9);
            OverlayOptions ooPolyline = new PolylineOptions().width(10)
                    .color(0xAAFF0000).points(points);
            mBaiduMap.addOverlay(ooPolyline);	
		}
	}
    
    public List<PositionInfo> msort(List<PositionInfo> ps){
		List<PositionInfo> result = new ArrayList<PositionInfo>();
		int flag[] = new int[10];
		int count = 1;
		flag[0] = 0;
		result.add(ps.get(0));
		for(int i = 1; i < 10; i++){
			flag[i] = -1;
		}
		int last = 0;
		while(count < 10){
			double minLength = 1000;
			int shortpos = -1;
			for(int i = 1; i < 10; i++){
				if(flag[i] != -1)
					continue;
				if(dis(ps.get(last), ps.get(i)) < minLength){
					minLength = dis(ps.get(last), ps.get(i));
					shortpos = i;	
				}
			}
			flag[shortpos] = 0;
			last = shortpos;
			result.add(ps.get(shortpos));
			count++;
		}
		return result;
	}
	
	public double dis(PositionInfo p1, PositionInfo p2){
		double a1 = p1.getX() - p2.getY();
		double a2 = p1.getX() - p2.getY();
		return Math.sqrt(a1 * a1 + a2 * a2);
	}
    
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || undoneMap == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
    
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        	JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				int state = jsonObject.getInt("state");
				if(state == 1){
					mBaiduMap.clear();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }
    }
    
    private class HttpAsyncTask2 extends AsyncTask<String, Void, ResponseNewTask> {
        @Override
        protected ResponseNewTask doInBackground(String... urls) {
        	ResponseNewTask rp = DataTransfer.getNewTask(phone);
			return rp;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ResponseNewTask result) {
        	List<PositionInfo> on_task = result.getOn_task();
			on_task = msort(on_task);
			List<LatLng> points = new ArrayList<LatLng>();
			LatLng p0 = new LatLng(on_task.get(0).getY(), on_task.get(0).getX());
			LatLng p1 = new LatLng(on_task.get(1).getY(), on_task.get(1).getX());
			LatLng p2 = new LatLng(on_task.get(2).getY(), on_task.get(2).getX());
			LatLng p3 = new LatLng(on_task.get(3).getY(), on_task.get(3).getX());
			LatLng p4 = new LatLng(on_task.get(4).getY(), on_task.get(4).getX());
			LatLng p5 = new LatLng(on_task.get(5).getY(), on_task.get(5).getX());
			LatLng p6 = new LatLng(on_task.get(6).getY(), on_task.get(6).getX());
			LatLng p7 = new LatLng(on_task.get(7).getY(), on_task.get(7).getX());
			LatLng p8 = new LatLng(on_task.get(8).getY(), on_task.get(8).getX());
			LatLng p9 = new LatLng(on_task.get(9).getY(), on_task.get(9).getX());
            points.add(p0);
			points.add(p1);
            points.add(p2);
            points.add(p3);
            points.add(p4);
            points.add(p5);
            points.add(p6);
            points.add(p7);
            points.add(p8);
            points.add(p9);
            OverlayOptions ooText0 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(0).getDesc())
                    .position(p0);
            OverlayOptions ooText1 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(1).getDesc())
                    .position(p1);
            OverlayOptions ooText2 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(2).getDesc())
                    .position(p2);
            OverlayOptions ooText3 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(3).getDesc())
                    .position(p3);
            OverlayOptions ooText4 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(4).getDesc())
                    .position(p4);
            OverlayOptions ooText5 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(5).getDesc())
                    .position(p5);
            OverlayOptions ooText6 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(6).getDesc())
                    .position(p6);
            OverlayOptions ooText7 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(7).getDesc())
                    .position(p7);
            OverlayOptions ooText8 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(8).getDesc())
                    .position(p8);
            OverlayOptions ooText9 = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(on_task.get(9).getDesc())
                    .position(p9);
            mBaiduMap.addOverlay(ooText0);
            mBaiduMap.addOverlay(ooText1);
            mBaiduMap.addOverlay(ooText2);
            mBaiduMap.addOverlay(ooText3);
            mBaiduMap.addOverlay(ooText4);
            mBaiduMap.addOverlay(ooText5);
            mBaiduMap.addOverlay(ooText6);
            mBaiduMap.addOverlay(ooText7);
            mBaiduMap.addOverlay(ooText8);
            mBaiduMap.addOverlay(ooText9);
            OverlayOptions ooPolyline = new PolylineOptions().width(10)
                    .color(0xAAFF0000).points(points);
            mBaiduMap.addOverlay(ooPolyline);
       }
    }
    
    public static String GET(String url){
		InputStream inputStream = null;
		String result = "";
		try {
			
			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			
			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			
			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
			
			// convert inputstream to string
			if(inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";
		
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		
		return result;
	}
    
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        
        inputStream.close();
        return result;
    }
    
	
}
