// IntentServiceInterface.java

package com.service_test_bynr;

import android.widget.Toast;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.JavaScriptModule;

import android.content.Intent;
import java.util.Map;
import java.util.HashMap;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Context;


public class IntentServiceInterface extends ReactContextBaseJavaModule {

  private static final String DURATION_SHORT_KEY = "SHORT";
  private static final String DURATION_LONG_KEY = "LONG";
  
 public interface RCTDeviceEventEmitter extends JavaScriptModule {
    void emit(String eventName, String data);
  }


  public IntentServiceInterface(ReactApplicationContext reactContext) {
    super(reactContext);
	BroadcastReceiver myReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        backToJS(intent.getStringExtra("data"));
      }
	};
	getReactApplicationContext().registerReceiver(myReceiver, new IntentFilter("backIntent"));
  }


  @Override
  public String getName() {
    return "IntentServiceInterface";
  }
  
  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
    constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
    return constants;
  }
  
  @ReactMethod
  public void show(String message, int duration) {
    Toast.makeText(getReactApplicationContext(), message,  Toast.LENGTH_SHORT).show();
  }
  
  @ReactMethod
  public void startService(String msg) {
    Intent myIntent = new Intent();	  
    myIntent.setClass(getReactApplicationContext(), MyIntentService.class);
	myIntent.putExtra("parameter", msg);
	getReactApplicationContext().startService(myIntent);
	  
  }
  
  private void backToJS(String msg) {  
	getReactApplicationContext()
		.getJSModule(RCTDeviceEventEmitter.class)
		.emit("onSessionConnect", msg);
	}
}