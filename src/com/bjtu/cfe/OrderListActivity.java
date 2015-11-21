package com.bjtu.cfe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cfe.common.model.dto.CustomerDTO;
import com.cfe.common.model.dto.OrderDTO;
import com.cfe.common.model.dto.RestaurantDTO;
import com.cfe.common.model.dto.TaskDTO;
import com.cfe.http.util.DataTransfer;
import com.cfe.http.util.TestInfo;
import com.cfe.response.ResponseGetFinishedTask;
import com.cfe.response.entity.CustomerInfo;
import com.cfe.response.entity.OrderInfo;
import com.cfe.response.entity.RestaurantInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class OrderListActivity extends Activity {

	private TextView title;
	private Button backspace;
	
	private int tag, tkid;
	private ListView order_list;
	private ArrayList<Map<String, Object>> order_listData;
	public LinearLayout a;
	
	//private LinearLayout 
	
	//private List<OrderDTO> orders;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.order_list);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

        title = (TextView) findViewById(R.id.title_text);
        backspace = (Button) findViewById(R.id.title_button);
        
        title.setText("任务详情");
        backspace.setOnClickListener(new BackListener());

        Intent intent = getIntent();
        tkid = intent.getIntExtra("taskId", 0);
        
        order_list = (ListView) findViewById(R.id.order_list);
        
        Task task = new Task(this);
        task.execute();        
                
		order_list.setOnItemClickListener(new OrderListListener());
		tag = -1;
	}
	
	private SimpleAdapter getAdapter() {

		return new SimpleAdapter(OrderListActivity.this, order_listData,
				R.layout.order_item2, new String[] { "order_icon", "order_type", "order_name", "order_address", "order_tel", "customer_icon", "customer_type", "customer_name","customer_address","customer_tel"},
				new int[] { R.id.order_icon, R.id.order_type, R.id.order_name, R.id.order_address, R.id.order_tel,R.id.customer_icon, R.id.customer_type, R.id.customer_name,R.id.customer_address,R.id.customer_tel});
	}
	
	class OrderListListener implements OnItemClickListener
    {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (tag != position)
			{
				LinearLayout info;
				int i;
				
				for (i = 0;i < order_list.getChildCount();i++)
				{
					info = (LinearLayout) order_list.getChildAt(i).findViewById(R.id.order_info);
					info.setVisibility(View.GONE);
				}
				info = (LinearLayout) view.findViewById(R.id.order_info);
				info.setVisibility(View.VISIBLE);
				tag = position;
			}
			else
			{
		    	Map<String, Object> map = order_listData.get(position);
		    	
				if (((String) map.get("order_type")).equals("送餐客户"))
				{
			    	Intent intent = new Intent(OrderListActivity.this, CustomerInfoActivity.class);
//			    	intent.putExtra("name", (String) map.get("order_name"));
//			    	intent.putExtra("address", (String) map.get("order_address"));
//			    	intent.putExtra("tel", (String) map.get("order_tel"));
//			    	intent.putExtra("num", (String) map.get("order_num"));
//			    	intent.putExtra("time", (String) map.get("order_num"));
//			    	intent.putExtra("cash", (String) map.get("order_num"));
//			    	intent.p
			    	intent.putExtra("tkid", String.valueOf(tkid));
			    	intent.putExtra("oid", position);
			    	startActivity(intent);
				}
				else
				{
			    	Intent intent = new Intent(OrderListActivity.this, ShopInfoActivity.class);
			    	intent.putExtra("name", (String) map.get("order_name"));
			    	intent.putExtra("address", (String) map.get("order_address"));
			    	intent.putExtra("tel", (String) map.get("order_tel"));
			    	intent.putExtra("num", (String) map.get("order_num"));
			    	startActivity(intent);
				}
				
			}
		}
    }
    
    private class Task extends AsyncTask<String, Integer, ResponseGetFinishedTask> {

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
		protected ResponseGetFinishedTask doInBackground(String... params) {
			// TODO Auto-generated method stub
			// TurUserBO user = DataTransfer.login(params[0], params[1]);
			
			//TODO
			//请求订单列表
			ResponseGetFinishedTask rp = DataTransfer.getFinishedTask(tkid);
			
			//Test-Start--------------
			//orders = (new TestInfo()).testOrders();
			//Test-End--------------
			
			return rp;
		}
				
		@Override
		protected void onPostExecute(ResponseGetFinishedTask rp)
		{
			mProgressDialog.dismiss();
			order_listData = new ArrayList<Map<String, Object>>();
			
			if(rp.getState() == 0)
				return;
			
			int i, j;
			Map<String, Object> map;
			for(i=0; i<rp.getOrders().size(); i++){
				map = new HashMap<String, Object>();
				
				//商户
				RestaurantInfo restaurantInfo = rp.getOrders().get(i).getRestaurant();
				map.put("order_icon", R.drawable.icon_get);
				map.put("order_type", "取餐商户");
				map.put("order_name", restaurantInfo.getName());//orders.get(i).getRid().getName());
				map.put("order_address", restaurantInfo.getAddress());
				map.put("order_tel", restaurantInfo.getPhone());
				
				//客户
				CustomerInfo customerInfo = rp.getOrders().get(i).getCustomer();
				map.put("customer_icon", R.drawable.icon_get);
				map.put("customer_type", "订餐客户");
				map.put("customer_name", customerInfo.getName());
				map.put("customer_address", customerInfo.getAddress());//customer_tel
				map.put("customer_tel", customerInfo.getPhone());
				
				//map.put("order_num", String.valueOf(orders.get(i).getOid()));
				order_listData.add(map);
				
			}
			
//			String temp;
//			boolean first;
//			
//			//TODO
//			for (i = 0;i < orders.size();i++)
//			{
//				map = new HashMap<String, Object>();
//				first = true;
//				for (j = 0;j < order_listData.size();j++)
//				{
//					if (orders.get(i).getRid().getName().equals(order_listData.get(j).get("order_name")))
//					{
//						temp = (String) order_listData.get(j).get("order_num");
//						temp = temp + "\n" + orders.get(i).getOid();
//						order_listData.get(j).put("order_num", temp);
//						first = false;
//					}
//				}
//				if (first)
//				{
//					map.put("order_icon", R.drawable.icon_get);
//					map.put("order_type", "取餐商户");
//					map.put("order_name", orders.get(i).getRid().getName());
//					map.put("order_address", orders.get(i).getRid().getAddress());
//					map.put("order_tel", orders.get(i).getRid().getPhone());
//					map.put("order_num", String.valueOf(orders.get(i).getOid()));
//					order_listData.add(map);
//					map = new HashMap<String, Object>();
//				}
//				map.put("order_icon", R.drawable.icon_send);
//				map.put("order_type", "送餐客户");
//				map.put("order_name", orders.get(i).getCid().getName());
//				map.put("order_address", orders.get(i).getCid().getAddress());
//				map.put("order_tel", orders.get(i).getCid().getPhone());
//				map.put("order_num", String.valueOf(orders.get(i).getOid()));
//				order_listData.add(map);
//			}
			order_list.setAdapter(getAdapter());
		}
	}
  
    class BackListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			OrderListActivity.this.finish();
		}
	}
}
