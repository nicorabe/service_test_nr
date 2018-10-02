package com.service_test_bynr;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;
import android.support.annotation.Nullable;
import android.app.Notification;
import android.app.NotificationManager;

public class MyIntentService extends IntentService {
	private static final int NOTIFICATION_ID = 9000;
	private Notification.Builder noBuilder;
    private NotificationManager notificationManager;
	
    
    public MyIntentService() {
        super("MyIntentService");
    }
	
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId){
		 noBuilder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.lllogo1)
                .setContentTitle("LilianLabs")
                .setContentText("Service gestartet");


        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, noBuilder.build());
		
		return super.onStartCommand(intent, flags, startId);
	}

    

    @Override
     protected void onHandleIntent(@Nullable Intent intent) {
		sendMessageToJS("service started");
		sendMessageToJS("parameter: " + intent.getStringExtra("parameter"));
		
		 
		try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		sendMessageToJS("read data");
		
		try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		sendMessageToJS("data received");
		
		try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendMessageToJS("result:{\"createdAt\":\"2018-07-19T09:19:19.195+0000\",\"parameter\": [\"chlorineFree\", \"chlorineCombined\", \"chlorineTotal\", \"ph\", \"temperature\"]}");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	
	@Override
    public void onDestroy(){
        super.onDestroy();
		
		sendMessageToJS("service inactive");
		this.notificationManager.cancel(NOTIFICATION_ID);
		
   }
   
   public void sendMessageToJS(String msg){
		Intent backIntent = new Intent("backIntent");
		backIntent.putExtra("data", msg);
		sendBroadcast(backIntent);
   }

	
}
