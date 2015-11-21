package com.bjtu.cfe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cfe.common.model.dto.FoodDTO;
import com.cfe.common.model.dto.OrderDTO;
import com.cfe.http.util.DataTransfer;
import com.cfe.http.util.TestInfo;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CustomerInfoActivity extends Activity{

	private ViewPager viewPager;//页卡内容
	private TextView textView1,textView2;
	private List<View> views;// Tab页面列表
	private int currIndex = 0;// 当前页卡编号
	private View view1,view2;//各个页卡
	private LinearLayout undone_layout, done_layout;
	
	private String tkid;
	private int oid;
	private TextView customer_address, customer_name, customer_tel, customer_time, customer_date, customer_num,
	                 customer_cash, customer_menu_num, customer_menu_cash;
	private RadioGroup customer_group;
	private RadioButton customer_best, customer_good, customer_bad;
	private Button  customer_confirm;

	private ListView customer_menu;
	private ArrayList<Map<String, String>> customer_menu_listData;
	
	private TextView title;
	private Button backspace;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.task_list);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

        title = (TextView) findViewById(R.id.title_text);
        undone_layout = (LinearLayout) findViewById(R.id.undone_task_layout);
        done_layout = (LinearLayout) findViewById(R.id.done_task_layout);
        backspace = (Button) findViewById(R.id.title_button);
        
        title.setText("详细信息");
        backspace.setOnClickListener(new BackListener());
        
		InitTextView();
		InitViewPager();

		Intent intent = getIntent();
		tkid = intent.getStringExtra("tkid");
		oid = intent.getIntExtra("oid", 0);
		
		Task task = new Task(this);
		task.execute();
	}

	private void InitViewPager() {
		viewPager=(ViewPager) findViewById(R.id.task_pager);
		views=new ArrayList<View>();
		LayoutInflater inflater=getLayoutInflater();
		view1=inflater.inflate(R.layout.customer_info, null);
		view2=inflater.inflate(R.layout.customer_menu, null);
		
		customer_address = (TextView) view1.findViewById(R.id.customer_address);
		customer_name = (TextView) view1.findViewById(R.id.customer_name);
		customer_tel = (TextView) view1.findViewById(R.id.customer_tel);
		customer_time = (TextView) view1.findViewById(R.id.customer_time);
		customer_date = (TextView) view1.findViewById(R.id.customer_date);
		customer_num = (TextView) view1.findViewById(R.id.customer_num);
		customer_cash = (TextView) view1.findViewById(R.id.customer_cash);
		customer_group = (RadioGroup) view1.findViewById(R.id.customer_group);
		customer_best = (RadioButton) view1.findViewById(R.id.customer_best);
		customer_good = (RadioButton) view1.findViewById(R.id.customer_good);
		customer_bad = (RadioButton) view1.findViewById(R.id.customer_bad);
		customer_confirm = (Button) view1.findViewById(R.id.customer_confirm);
		
		customer_menu_num = (TextView) view2.findViewById(R.id.customer_menu_num);
		customer_menu_cash = (TextView) view2.findViewById(R.id.customer_menu_cash);
		customer_menu = (ListView) view2.findViewById(R.id.customer_menu);
		
		views.add(view1);
		views.add(view2);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	 /**
	  *  初始化头标
	  */

	private void InitTextView() {
		textView1 = (TextView) findViewById(R.id.undone_task_tab);
		textView2 = (TextView) findViewById(R.id.done_task_tab);

		textView1.setText("客户信息");
		textView2.setText("订单详情");
		
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
    
    private SimpleAdapter getMenuAdapter() {

		return new SimpleAdapter(CustomerInfoActivity.this, customer_menu_listData,
				R.layout.menu_item, new String[] { "menu_name", "menu_num", "menu_cash" },
				new int[] { R.id.menu_name, R.id.menu_num, R.id.menu_cash });
	}
    
    private class Task extends AsyncTask<String, Integer, OrderDTO> {

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
		protected OrderDTO doInBackground(String... params) {
			// TODO Auto-generated method stub
			// TurUserBO user = DataTransfer.login(params[0], params[1]);
			
			//TODO
			//OrderDTO order = DataTransfer.getOrderDTO(tkid).get(oid);
			
			//Test-Start----------------
			OrderDTO order = (new TestInfo()).testOrders().get(0);
			//Test-End------------------
			
			return order;
		}

		@Override
		protected void onPostExecute(OrderDTO order)
		{
			mProgressDialog.dismiss();
			customer_menu_listData = new ArrayList<Map<String, String>>();
			int i;
			Map<String, String> map;
	    	
	    	customer_address.setText(order.getCid().getAddress());
	    	customer_name.setText(order.getCid().getName());
	    	customer_tel.setText(order.getCid().getPhone());
	    	customer_time.setText(order.getTime());
	    	customer_date.setText("");
	    	customer_num.setText(String.valueOf(order.getOid()));
	    	customer_cash.setText(String.valueOf(order.getPrice()));
	    	customer_good.setChecked(true);

	    	customer_menu_num.setText(String.valueOf(order.getOid()));
	    	customer_menu_cash.setText(String.valueOf(order.getPrice()));
			
	    	List<FoodDTO> foods = order.getFood();
	    	
			for (i = 0;foods!=null&&i < foods.size();i++)
			{
				map = new HashMap<String, String>();
				map.put("menu_name", foods.get(i).getName());
				map.put("menu_num", "1");
				map.put("menu_cash", String.valueOf(foods.get(i).getPrice()));
				customer_menu_listData.add(map);
			}
			customer_menu.setAdapter(getMenuAdapter());
		}
	}
    
//    class RateListener implements OnCheckedChangeListener
//	{
//
//		@Override
//		public void onCheckedChanged(RadioGroup arg0, int arg1) {
//			// TODO Auto-generated method stub
//			
//		}
//	}
    
    class ConfirmListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
//			customer_group.getCheckedRadioButtonId();
			RadioButton rb = (RadioButton) view1.findViewById(customer_group.getCheckedRadioButtonId());
		}
	}
    
    class BackListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			CustomerInfoActivity.this.finish();
			overridePendingTransition(0, android.R.anim.fade_out);
		}
	}
	
}
