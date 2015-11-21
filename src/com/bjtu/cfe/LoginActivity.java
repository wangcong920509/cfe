package com.bjtu.cfe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText login_id, login_pw;
	private CheckBox keep_pw, auto_login;
	private Button login;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        login_id = (EditText) findViewById(R.id.login_id);
        login_pw = (EditText) findViewById(R.id.login_pw);
        //keep_pw = (CheckBox) findViewById(R.id.keep_pw);
        //auto_login = (CheckBox) findViewById(R.id.auto_login);
        login = (Button) findViewById(R.id.login_button);
        
        login.setOnClickListener(new LoginListener());
    }
    
    class LoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {			
			
			Boolean isLogin = false;
			//用户登陆信息验证
			//20151105-Start--------------------
			//isLogin = DataTransfer.login(login_id.getText().toString(), login_pw.getText().toString());
			String phone = login_id.getText().toString();
			String pass = login_pw.getText().toString();
			String login_path = "login/";		
			String url = "http://166.111.81.196:8078/" + login_path;
			String params = "?phone="+phone+"&pass="+pass;
			url += params;
			new HttpAsyncTask().execute(url);
			
			//20151105-End----------------------
					
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
        	try {
        		JSONObject jsonObject = new JSONObject(result);
    			int state = jsonObject.getInt("state");
    			//0：登录失败；1：登陆成功；2：参数缺失
    			if(state == 1){
    				Intent intent = new Intent(LoginActivity.this, TaskListActivity.class);
    				startActivity(intent);
    				finish();
    			}
    			else{
    				Toast.makeText(getApplicationContext(), "登陆信息错误",3000).show();
    			}
			}
        	catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
