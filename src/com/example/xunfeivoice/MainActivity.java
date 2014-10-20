package com.example.xunfeivoice;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private static final String TAG = "MainActivity";
	/**
	 * 语音识别界面，以Dialog形式提供，
	 * 可以通过在说话过程中点击Dialog界面做“停止录音”操作，
	 * 点击Dialog界面以外完成 “取消识别”操作。可
	 * 以调用系统的setOnDismissListener(OnDismissListener)来设置 Dialog取消的监听器。
	 * 
	 * 
	 */
	private RecognizerDialog recognizerDialog;
	
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView = (TextView) findViewById(R.id.textView1);
		
		Button button1=(Button) findViewById(R.id.button1);		
		button1.setOnClickListener(this);
		
		
		initRecognizerDialog();
		
	}
      
	private void initRecognizerDialog() {
		/**
		 * 初始化识别Dialog
		 */
		recognizerDialog = new RecognizerDialog(this, initListener);
		/**
		 * 设置识别监听器，用于回调识别状态。
		 */
		recognizerDialog.setListener(recognizerDialogListener);
		
		setParameter();
	}
    /**
     * 给recognizerDialog设置参数
     */
	private void setParameter() {
		/**
		 * VAD前端点超时,其他具体的详细参数设置见文档
		 */
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1://对话框听写
			recognizerDialog.show();
			break;
		default:
			break;
		}
	}
   /**
    * 初始化完成回调接口:errorCode - 错误码，0表示成功
    */
   InitListener initListener=new InitListener() {
		
		@Override
		public void onInit(int arg0) {
			// TODO Auto-generated method stub
			Log.i(TAG, String.valueOf(arg0));
			
		}
	};
	
	/**
	 * 语音对话框的语音识别监听器
	 */
	RecognizerDialogListener recognizerDialogListener=new RecognizerDialogListener() {
		
		@Override
		public void onResult(RecognizerResult result, boolean arg1) {
			// TODO Auto-generated method stub
			Log.i(TAG, result.getResultString());
			String string=JsonParser.parseIatResult(result.getResultString());
			textView.setText(string);
		}
		
		@Override
		public void onError(SpeechError arg0) {
			// TODO Auto-generated method stub
			Log.i(TAG, String.valueOf(arg0));
			textView.setText("错误码:"+String.valueOf(arg0));
		}
	};
	


}
