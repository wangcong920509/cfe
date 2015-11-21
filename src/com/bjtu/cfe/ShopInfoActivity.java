package com.bjtu.cfe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class ShopInfoActivity extends Activity{

	private ViewPager viewPager;//“≥ø®ƒ⁄»›
	private TextView textView1,textView2;
	private List<View> views;// Tab“≥√Ê¡–±Ì
	private int currIndex = 0;// µ±«∞“≥ø®±‡∫≈
	private View view1,view2;//∏˜∏ˆ“≥ø®
	private LinearLayout undone_layout, done_layout;
	
	private TextView shop_address, shop_name, shop_tel, shop_num;

	private ExpandableListView shop_menu;
	private ArrayList<Map<String, String>> shop_main_listData;
	private ArrayList<List<Map<String, String>>> shop_sub_listData;
	
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
        
        title.setText("œÍœ∏–≈œ¢");
        backspace.setOnClickListener(new BackListener());
        
		InitTextView();
		InitViewPager();

		getInfo();
		shop_menu.setAdapter(getMenuAdapter());
	}

	private void InitViewPager() {
		viewPager=(ViewPager) findViewById(R.id.task_pager);
		views=new ArrayList<View>();
		LayoutInflater inflater=getLayoutInflater();
		view1=inflater.inflate(R.layout.shop_info, null);
		view2=inflater.inflate(R.layout.shop_menu, null);
		
		shop_address = (TextView) view1.findViewById(R.id.shop_address);
		shop_name = (TextView) view1.findViewById(R.id.shop_name);
		shop_tel = (TextView) view1.findViewById(R.id.shop_tel);
		shop_num = (TextView) view1.findViewById(R.id.shop_num);
		
		shop_menu = (ExpandableListView) view2.findViewById(R.id.shop_menu);
		
		views.add(view1);
		views.add(view2);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	 /**
	  *  ≥ı ºªØÕ∑±Í
	  */

	private void InitTextView() {
		textView1 = (TextView) findViewById(R.id.undone_task_tab);
		textView2 = (TextView) findViewById(R.id.done_task_tab);

		textView1.setText("øÕªß–≈œ¢");
		textView2.setText("∂©µ•œÍ«È");
		
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
	}

	/** 
	 *     
	 * Õ∑±Íµ„ª˜º‡Ã˝ 3 */
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

//    	int one = offset * 2 + bmpW;// “≥ø®1 -> “≥ø®2 ∆´“∆¡ø
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
    
    private void getInfo() {
    	
    	Intent intent = getIntent();
    	
    	shop_address.setText(intent.getStringExtra("address"));
    	shop_name.setText(intent.getStringExtra("name"));
    	shop_tel.setText(intent.getStringExtra("tel"));
    	shop_num.setText(intent.getStringExtra("num"));
    	shop_menu.setGroupIndicator(null);
    	shop_menu.setClickable(false);
    }
    
    private SimpleExpandableListAdapter getMenuAdapter() {
		shop_sub_listData = new ArrayList<List<Map<String, String>>>();
		shop_main_listData = new ArrayList<Map<String, String>>();
//		shop_sub_listData = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("list_num", "CFE-201312230001");
		map.put("list_cash", "50");
		shop_main_listData.add(map);

		map = new HashMap<String, String>();
		map.put("list_num", "CFE-201312230002");
		map.put("list_cash", "100");
		shop_main_listData.add(map);

		map = new HashMap<String, String>();
		map.put("list_num", "CFE-201312230003");
		map.put("list_cash", "80");
		shop_main_listData.add(map);

		List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
		map = new HashMap<String, String>();
		map.put("menu_name", "Ã˙∞Â’’…’÷Ì¿Ôºπ");
		map.put("menu_num", "1");
		map.put("menu_cash", "50");
		maps.add(map);
		
		map = new HashMap<String, String>();
		map.put("menu_name", "Ã˙∞Â’’…’÷Ì¿Ôºπ");
		map.put("menu_num", "1");
		map.put("menu_cash", "50");
		maps.add(map);
		
		map = new HashMap<String, String>();
		map.put("menu_name", "Ã˙∞Â’’…’÷Ì¿Ôºπ");
		map.put("menu_num", "1");
		map.put("menu_cash", "50");
		maps.add(map);
		shop_sub_listData.add(maps);
		
		maps = new ArrayList<Map<String, String>>();
		map = new HashMap<String, String>();
		map.put("menu_name", "Ã˙∞Â’’…’÷Ì¿Ôºπ");
		map.put("menu_num", "1");
		map.put("menu_cash", "50");
		maps.add(map);
		
		map = new HashMap<String, String>();
		map.put("menu_name", "Ã˙∞Â’’…’÷Ì¿Ôºπ");
		map.put("menu_num", "1");
		map.put("menu_cash", "50");
		maps.add(map);
		
		map = new HashMap<String, String>();
		map.put("menu_name", "Ã˙∞Â’’…’÷Ì¿Ôºπ");
		map.put("menu_num", "1");
		map.put("menu_cash", "50");
		maps.add(map);
		shop_sub_listData.add(maps);
		
		maps = new ArrayList<Map<String, String>>();
		map = new HashMap<String, String>();
		map.put("menu_name", "Ã˙∞Â’’…’÷Ì¿Ôºπ");
		map.put("menu_num", "1");
		map.put("menu_cash", "50");
		maps.add(map);
		
		map = new HashMap<String, String>();
		map.put("menu_name", "Ã˙∞Â’’…’÷Ì¿Ôºπ");
		map.put("menu_num", "1");
		map.put("menu_cash", "50");
		maps.add(map);
		
		map = new HashMap<String, String>();
		map.put("menu_name", "Ã˙∞Â’’…’÷Ì¿Ôºπ");
		map.put("menu_num", "1");
		map.put("menu_cash", "50");
		maps.add(map);
		shop_sub_listData.add(maps);

		return new SimpleExpandableListAdapter(ShopInfoActivity.this, shop_main_listData,
				R.layout.list_item, new String[] { "list_num", "list_cash" }, new int[] { R.id.list_num, R.id.list_cash }, shop_sub_listData,
				R.layout.menu_item, new String[] { "menu_name", "menu_num", "menu_cash" }, new int[] { R.id.menu_name, R.id.menu_num, R.id.menu_cash });
	}
    
    class BackListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			ShopInfoActivity.this.finish();
			overridePendingTransition(0, android.R.anim.fade_out);
		}
	}
	
}
