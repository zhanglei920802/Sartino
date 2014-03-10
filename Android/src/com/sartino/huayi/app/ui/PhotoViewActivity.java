/**
 * 
 */
package com.sartino.huayi.app.ui;

import java.util.List;
import android.content.Intent;

import android.os.Bundle;
import android.view.Window;
import com.sartino.huayi.app.R;
import com.sartino.huayi.app.adapter.PhotoViewAdapter;
import com.sartino.huayi.app.ui.widgets.GalleryFlow;

public class PhotoViewActivity extends BaseActivity {
	private static final String TAG = "PhotoViewActivity";
	private GalleryFlow gallery;
	private PhotoViewAdapter galleyAdapter = null;
	private List<String> datas = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.photoview);
		gallery = (GalleryFlow) findViewById(R.id.gallery);

		// --------------Intent----------------------------
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		datas = bundle.getStringArrayList("data");
		galleyAdapter = new PhotoViewAdapter(PhotoViewActivity.this, datas);

		gallery.setAdapter(galleyAdapter);

	}
}
