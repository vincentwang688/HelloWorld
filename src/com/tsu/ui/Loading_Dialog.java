package com.tsu.ui;

import com.tsu.utils.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class Loading_Dialog extends Dialog{

	private ImageView laoding;
	private AnimationDrawable animationDrawable;
	/**
	 * 缓冲界面
	 * @param context
	 */
	public Loading_Dialog(Context context) {
		super(context, R.style.dialog_activity);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_loading);
		laoding = (ImageView) this.findViewById(R.id.loading);
		laoding.setImageResource(R.drawable.loading_bg);
		animationDrawable = (AnimationDrawable) laoding.getDrawable();
		animationDrawable.start();
	}
}
