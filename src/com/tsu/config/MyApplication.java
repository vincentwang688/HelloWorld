package com.tsu.config;

import android.app.Application;
import android.view.WindowManager;

/**
 * 必须在清单文件中注册
 * @author dds
 *
 */
public class MyApplication extends Application{
	private WindowManager.LayoutParams wmlp = new WindowManager.LayoutParams();
	
	public WindowManager.LayoutParams getLayoutParams() {
		return wmlp;
	}

}
