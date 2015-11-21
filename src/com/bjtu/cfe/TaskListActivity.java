package com.bjtu.cfe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cfe.common.model.dto.OrderDTO;
import com.cfe.common.model.dto.TaskDTO;
import com.cfe.http.util.DataTransfer;
import com.cfe.http.util.DateUtil;
import com.cfe.response.ResponseGetAllTasks;
import com.cfe.response.entity.PositionInfo;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TaskListActivity extends Activity{

	private ViewPager viewPager;//页卡内容
	private TextView textView1,textView2;
	private List<View> views;// Tab页面列表
	private int currIndex = 0;// 当前页卡编号
	private View view1,view2;//各个页卡
	private LinearLayout undone_layout, done_layout;

	private ListView done_task_list, undone_task_list;
	private ArrayList<Map<String, String>> done_task_listData, undone_task_listData;
	private String tkid;
	
	private TextView title;
	private Button backspace;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.task_list);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

        InitViews();   
		
		Task task = new Task(this);
		task.execute();
		
		//绑定页卡内元素点击事件
		done_task_list.setOnItemClickListener(new DoneListListener());//点击跳转到订单信息
		undone_task_list.setOnItemClickListener(new UndoneListListener());//点击跳转到订单信息
	}
	
	private void InitViews(){
		//绑定控件
        title = (TextView) findViewById(R.id.title_text);
        undone_layout = (LinearLayout) findViewById(R.id.undone_task_layout);
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
		view1=inflater.inflate(R.layout.undone_task, null);
		view2=inflater.inflate(R.layout.done_task, null);
		//绑定控件
		undone_task_list = (ListView) view1.findViewById(R.id.undone_task_list);
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

//		textView1.setText("未记录");
//		textView2.setText("已记录");
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
				undone_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_white));
				done_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_gray));
				textView1.setTextColor(res.getColor(R.color.black));
				textView2.setTextColor(res.getColor(R.color.dimgray));
			}
			else
			{
				done_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_white));
				undone_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_gray));
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
				undone_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_white));
				done_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_gray));
				textView1.setTextColor(res.getColor(R.color.black));
				textView2.setTextColor(res.getColor(R.color.dimgray));
			}
			else
			{
				done_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_white));
				undone_layout.setBackgroundDrawable(res.getDrawable(R.drawable.tab_bg_gray));
				textView2.setTextColor(res.getColor(R.color.black));
				textView1.setTextColor(res.getColor(R.color.dimgray));
			}
		}
    	
    }
    
    private SimpleAdapter getDoneAdapter() {
    	
    	//测试例子-Start----------------------------------------
		done_task_listData = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("num", "4");
		map.put("time", "12:00");
		map.put("date", "2013-9-29");
		done_task_listData.add(map);

		map = new HashMap<String, String>();
		map.put("num", "3");
		map.put("time", "13:00");
		map.put("date", "2013-9-29");
		done_task_listData.add(map);
		//测试例子-End-------------------------------------------

		return new SimpleAdapter(TaskListActivity.this, done_task_listData,
				R.layout.task_item, new String[] { "num", "time", "date"},
				new int[] { R.id.task_num, R.id.task_time, R.id.task_date });
	}
    
    private SimpleAdapter getUndoneAdapter() {
    	
    	//测试例子-Start--------------------------------------
		undone_task_listData = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("num", "5");
		map.put("time", "12:00");
		map.put("date", "2013-9-29");
		undone_task_listData.add(map);

		map = new HashMap<String, String>();
		map.put("num", "3");
		map.put("time", "13:00");
		map.put("date", "2013-9-29");
		undone_task_listData.add(map);
    	//测试例子-End-------------------------------------------

		return new SimpleAdapter(TaskListActivity.this, undone_task_listData,
				R.layout.task_item, new String[] { "num", "time", "date"},
				new int[] { R.id.task_num, R.id.task_time, R.id.task_date });
	}
    
    class DoneListListener implements OnItemClickListener
    {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
	    	Intent intent = new Intent(TaskListActivity.this, OrderListActivity.class);
	    	intent.putExtra("position", position);
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

			// TurUserBO user = DataTransfer.login(params[0], params[1]);
			
			//TODO
			//获得任务列表
			//List<TaskDTO> tasks = DataTransfer.getTask("2");
						
			//Test-Start----------------------------------------
			//List<TaskDTO> tasks =  new ArrayList<TaskDTO>();			
//			TaskDTO task = new TaskDTO();
//			task.setOrders(null);
//			task.setDistance(100.0);
//			task.setPath("path");
//			task.setState(0);
//			task.setTime("2015-11-04");
//			task.setTkid(134L);		
			//Test-End----------------------------------------
			
			//测试
			String phone = "23411413241";
			double x = 160.004;
			double y = 40.002;
						
			//2015-11-09-获得所有任务=待完成+已经完成
			ResponseGetAllTasks rp = DataTransfer.getAllTasks(phone, x, y);
					
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
				map.put("num", "5");	
				String timeString = rp.getFinished().get(i).getTime();
				map.put("time", DateUtil.splitStringDate(timeString, 0));
				map.put("date", DateUtil.splitStringDate(timeString, 1));
				undone_task_listData.add(map);			
			}
			done_task_list.setAdapter(getDoneAdapter());
			undone_task_list.setAdapter(getUndoneAdapter());
			
			//TODO
			//解析待完成任务的地点信息，在地图表示
		}

//		@Override
//		protected void onPostExecute(List<TaskDTO> tasks)
//		{
//			mProgressDialog.dismiss();
//			undone_task_listData = new ArrayList<Map<String, String>>();
//			done_task_listData = new ArrayList<Map<String, String>>();
//			int i;
//			Map<String, String> map;
//			
//			for (i = 0;i < tasks.size();i++)
//			{
//				map = new HashMap<String, String>();
//				if (tasks.get(i).getState() == 0)//没有完成
//				{
//					//TODO
//					//将任务信息添加到列表数据中
//					map.put("num", "5");
//					map.put("time", tasks.get(i).getTime());
//					map.put("date", "");
//					undone_task_listData.add(map);					
//				}
//				else//已经完成
//				{
//					//TODO
//					//将任务信息添加到列表数据中
//					map.put("num", "5");
//					map.put("time", tasks.get(i).getTime());
//					map.put("date", "");
//					done_task_listData.add(map);
//				}
//			}
//			done_task_list.setAdapter(getDoneAdapter());
//			undone_task_list.setAdapter(getUndoneAdapter());
//		}
	}
    
//    class BackListener implements OnClickListener
//	{
//		@Override
//		public void onClick(View v)
//		{
//			OrderExperienceActivity.this.finish();
//			overridePendingTransition(0, android.R.anim.fade_out);
//		}
//	}
	
}
