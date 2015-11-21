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

	private ViewPager viewPager;//ҳ������
	private TextView textView1,textView2;
	private List<View> views;// Tabҳ���б�
	private int currIndex = 0;// ��ǰҳ�����
	private View view1,view2;//����ҳ��
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
		
		//��ҳ����Ԫ�ص���¼�
		done_task_list.setOnItemClickListener(new DoneListListener());//�����ת��������Ϣ
		undone_task_list.setOnItemClickListener(new UndoneListListener());//�����ת��������Ϣ
	}
	
	private void InitViews(){
		//�󶨿ؼ�
        title = (TextView) findViewById(R.id.title_text);
        undone_layout = (LinearLayout) findViewById(R.id.undone_task_layout);
        done_layout = (LinearLayout) findViewById(R.id.done_task_layout);
        backspace = (Button) findViewById(R.id.title_button);
        
        backspace.setVisibility(View.INVISIBLE);
        
        title.setText("�����б�");
        
		InitTextView();
		InitViewPager();
	}

	private void InitViewPager() {
		views=new ArrayList<View>();
		LayoutInflater inflater=getLayoutInflater();
		
		//��ҳ������
		viewPager=(ViewPager) findViewById(R.id.task_pager);		
		//����ʽ�ļ�
		view1=inflater.inflate(R.layout.undone_task, null);
		view2=inflater.inflate(R.layout.done_task, null);
		//�󶨿ؼ�
		undone_task_list = (ListView) view1.findViewById(R.id.undone_task_list);
		done_task_list = (ListView) view2.findViewById(R.id.done_task_list);
		//���ҳ��������
		views.add(view1);
		views.add(view2);
		//����ҳ������
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(0);
		//��ҳ���л��¼�
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	 /**
	  *  ��ʼ��ͷ��
	  */
	private void InitTextView() {
		//�󶨿ؼ�
		textView1 = (TextView) findViewById(R.id.undone_task_tab);
		textView2 = (TextView) findViewById(R.id.done_task_tab);

		//���¼�
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));		

//		textView1.setText("δ��¼");
//		textView2.setText("�Ѽ�¼");
	}

	/** 
	 *     
	 * ͷ�������� 3 */
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

//    	int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
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
    	
    	//��������-Start----------------------------------------
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
		//��������-End-------------------------------------------

		return new SimpleAdapter(TaskListActivity.this, done_task_listData,
				R.layout.task_item, new String[] { "num", "time", "date"},
				new int[] { R.id.task_num, R.id.task_time, R.id.task_date });
	}
    
    private SimpleAdapter getUndoneAdapter() {
    	
    	//��������-Start--------------------------------------
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
    	//��������-End-------------------------------------------

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
	    	intent.putExtra("position", position);//�Ͳ�Ա��λ����Ϣ
	    	//TODO
	    	//����1����������ID��order�����ٴ���������orderlist
	    	//����2��ֱ�Ӵ���orderList��order UI
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
    		mProgressDialog.setMessage("Ŭ��������...");
		}
    	
    	@Override
    	protected void onPreExecute() {
    		mProgressDialog.show();
    	}

		@Override
		protected ResponseGetAllTasks doInBackground(String... params) {

			// TurUserBO user = DataTransfer.login(params[0], params[1]);
			
			//TODO
			//��������б�
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
			
			//����
			String phone = "23411413241";
			double x = 160.004;
			double y = 40.002;
						
			//2015-11-09-�����������=�����+�Ѿ����
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
			
			//20151109-�����Ѿ���ɵ�����
			for (i = 0;i < rp.getFinished().size();i++)
			{
				map = new HashMap<String, String>();

				// ��������Ϣ��ӵ��б�������
				map.put("num", "5");	
				String timeString = rp.getFinished().get(i).getTime();
				map.put("time", DateUtil.splitStringDate(timeString, 0));
				map.put("date", DateUtil.splitStringDate(timeString, 1));
				undone_task_listData.add(map);			
			}
			done_task_list.setAdapter(getDoneAdapter());
			undone_task_list.setAdapter(getUndoneAdapter());
			
			//TODO
			//�������������ĵص���Ϣ���ڵ�ͼ��ʾ
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
//				if (tasks.get(i).getState() == 0)//û�����
//				{
//					//TODO
//					//��������Ϣ��ӵ��б�������
//					map.put("num", "5");
//					map.put("time", tasks.get(i).getTime());
//					map.put("date", "");
//					undone_task_listData.add(map);					
//				}
//				else//�Ѿ����
//				{
//					//TODO
//					//��������Ϣ��ӵ��б�������
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
