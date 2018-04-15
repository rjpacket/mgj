package com.project.mgjandroid.net.volley.toolbox;
import org.json.JSONObject;
import com.project.mgjandroid.net.volley.NetworkResponse;
import com.project.mgjandroid.net.volley.ParseError;
import com.project.mgjandroid.net.volley.Response;
import com.project.mgjandroid.net.volley.Response.ErrorListener;
import com.project.mgjandroid.net.volley.Response.Listener;
import com.project.mgjandroid.utils.CheckUtils;

public class FastJsonObjectRequest<T> extends JsonRequest<T> {
	Class<T> clazz;

	/**
	 * Creates a new request.
	 * 
	 * @param method
	 *            the HTTP method to use
	 * @param url
	 *            URL to fetch the JSON from
	 * @param requestBody
	 *            A {@link String} to post with the request. Null is allowed and
	 *            indicates no parameters will be posted along with request.
	 * @param listener
	 *            Listener to receive the JSON response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
	public FastJsonObjectRequest(int method, String url, String requestBody, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, requestBody, listener, errorListener);
	}

	/**
	 * Creates a new request.
	 * 
	 * @param url
	 *            URL to fetch the JSON from
	 * @param listener
	 *            Listener to receive the JSON response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
	public FastJsonObjectRequest(String url, Listener<T> listener, ErrorListener errorListener) {
		super(Method.GET, url, null, listener, errorListener);
	}

	/**
	 * Creates a new request.
	 * 
	 * @param method
	 *            the HTTP method to use
	 * @param url
	 *            URL to fetch the JSON from
	 * @param listener
	 *            Listener to receive the JSON response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
	public FastJsonObjectRequest(int method, String url, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, null, listener, errorListener);
	}

	/**
	 * Creates a new request.
	 * 
	 * @param method
	 *            the HTTP method to use
	 * @param url
	 *            URL to fetch the JSON from
	 * @param jsonRequest
	 *            A {@link JSONObject} to post with the request. Null is allowed
	 *            and indicates no parameters will be posted along with request.
	 * @param listener
	 *            Listener to receive the JSON response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
	public FastJsonObjectRequest(int method, String url, String jsonRequest, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, (CheckUtils.isEmptyStr(jsonRequest)) ? null : jsonRequest, listener, errorListener);
		this.clazz = clazz;
	}

	/**
	 * Constructor which defaults to <code>GET</code> if
	 * <code>jsonRequest</code> is <code>null</code>, <code>POST</code>
	 * otherwise.
	 * 
	 * @see #JsonObjectRequest(int, String, JSONObject, Listener, ErrorListener)
	 */
	public FastJsonObjectRequest(String url, String jsonRequest, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
		this(CheckUtils.isEmptyStr(jsonRequest) ? Method.GET : Method.POST, url, jsonRequest, clazz, listener, errorListener);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		String jsonString = "";
		try {
			jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
			return Response.success(com.alibaba.fastjson.JSONObject.parseObject(jsonString, clazz), HttpHeaderParser.parseCacheHeaders(response));
		} catch (Exception e) {
			return Response.error(new ParseError(e, jsonString, HttpHeaderParser.parseCacheHeaders(response)));
		}
	}
}
