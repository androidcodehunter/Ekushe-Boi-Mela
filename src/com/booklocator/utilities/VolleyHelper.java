package com.booklocator.utilities;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.booklocator.interfaces.JsonTaskCompleteListener;

public class VolleyHelper {
	private static VolleyHelper uniqueInstance = new VolleyHelper();
	private RequestQueue mRequestQueue;
	private static Context context;
	public static final String TAG = "VolleyTag";
	private static JsonTaskCompleteListener<JSONObject>jsonCallBack;

	public static VolleyHelper getInstance(Context context,JsonTaskCompleteListener<JSONObject>jsonCallBack) {
		VolleyHelper.context = context;
		VolleyHelper.jsonCallBack=jsonCallBack;
		return uniqueInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(context);
		}
		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		VolleyLog.d("Adding request to queue: %s", req.getUrl());
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	public void getJsonObject(String url) {
		
		JsonObjectRequest req = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							VolleyLog.v("Response:%n %s", response.toString(4));
							Log.e("Response Successful", "Hurah");
							jsonCallBack.onJsonObject(response);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.e("Error: ", error.getMessage());
					}
				});
		addToRequestQueue(req);
	}

}
