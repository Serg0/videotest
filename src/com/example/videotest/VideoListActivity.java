package com.example.videotest;

import java.util.ArrayList;
import java.util.Arrays;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VideoListActivity extends Activity {

	public final static String BK_VIDEO_URL = "com.example.videotest.videourl";
	public final static String BK_VIDEO_IFRAME = "com.example.videotest.iframe";
	public final static String IFRAME = "<html><head></head><body style=\"margin:0\"><iframe src=\"%s?player_id=player&title=0&byline=0&portrait=0&autoplay=1&api=1\" height=\"100%%\" width=\"auto\" frameborder=\"0\" webkitAllowFullScreen mozallowfullscreen allowFullScreen></iframe></body></html>";
	
	private ArrayList<String> videosList;
	private ListView listView;
	private VideoListActivity instance;
	private int videosCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_list);
		instance = this;
		videosList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.videos_list)));
		videosCount = videosList.size();
		for (int i = 0; i < videosCount; i++) {
			String videoURL = videosList.get(i);
			String videoURLinIframe = String.format(IFRAME, videoURL);
			videosList.add(videoURLinIframe);
			
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				  android.R.layout.simple_list_item_1, android.R.id.text1, videosList);
		
		listView = (ListView) findViewById(R.id.listViewVideos);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
	            Intent intent = new Intent(instance, VideoActivity.class);
	            intent.putExtra(BK_VIDEO_URL, videosList.get(position));
	            if(position > videosCount-1)
	            	 intent.putExtra(BK_VIDEO_IFRAME, true);
	            startActivity(intent);
				
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videosList.get(position))));
				return false;
			}
		});
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.video_list, menu);
		return true;
	}

}
