package com.bjtu.cfe;

import com.cfe.http.util.DataTransfer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
			isLogin = DataTransfer.login(login_id.getText().toString(), login_pw.getText().toString());
			//20151105-End----------------------
			if(isLogin){
				Intent intent = new Intent(LoginActivity.this, TaskListActivity.class);
				startActivity(intent);
				finish();
			}else{
				Toast.makeText(getApplicationContext(), "登陆信息错误",3000).show();
			}		
		}
    	
    }
}
