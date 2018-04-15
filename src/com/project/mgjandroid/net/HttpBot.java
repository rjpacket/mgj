package com.project.mgjandroid.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.project.mgjandroid.base.ImageCache;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.StreamUtils;
import com.project.mgjandroid.utils.StringUtils;

import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

public class HttpBot {

	public static final String GET = "GET";
	public static final String POST = "POST";
	private static final String DEFAULT_CHARSET_ENCODING = "UTF-8";
	private static final int DEFAULT_BUFFER_SIZE = 1024;
	private int readTimeOut = 20000;
	private int connectTimeOut = 20000;
	private int reponseCode;
	private InputStream inputStream;
	private HttpURLConnection connection;
	private HashMap<String, String> headers = null;
	private static HttpBot mHttpBot;
	private static final int DEFAULT_IMAGE_CONNECT_TIMEOUT = 5000;
	private static final int DEFAULT_IMAGE_READ_TIMEOUT = 10000;
	

	public static HttpBot getInstance() {
		if (mHttpBot == null) {
			mHttpBot = new HttpBot();
		}
		return mHttpBot;
	}

	public static String getResponseStr(InputStream in) {
		StringBuffer document = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				document.append(line + "\n");
			}
			reader.close();
			String responseStr = document.toString();
			return responseStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String post(String url, Map<String, String> paramMap) throws IOException, ConnectTimeoutException,
			SocketTimeoutException {

		paramMap = addParam(paramMap);
		MLog.showRrequestParam(url, paramMap);
		return request(url, paramMap, HttpBot.POST);
	}

	public String get(String url) throws IOException {
		url = addUrlParam(url);
		MLog.showRrequestParam(url, null);
		return request(url, null, HttpBot.GET);
	}

	public String getString(String url, Map<String, String> paramMap) throws Exception, IOException {
		// HttpClient client = new DefaultHttpClient();
		// HttpGet get = new HttpGet(url);
		// HttpResponse response = client.execute(get);
		// if (response.getStatusLine().getStatusCode() == 200) {
		// return EntityUtils.toString(response.getEntity());
		// }
		return HttpBot.getInstance().post(url, paramMap);
		// return HttpBot.getInstance().postJS(url, null, null, HttpBot.GET);
	}

	public String request(String url, Map<String, String> paramMap, String method) throws IOException, ConnectTimeoutException,
			SocketTimeoutException {
		if (HttpBot.POST.equals(method)) {
			connection = getHttpURLConnection(url, HttpBot.POST);
			connection.setDoOutput(true);
			OutputStream os = connection.getOutputStream();
			os.write(getParamBytes(paramMap));
		} else {
			connection = getHttpURLConnection(url, HttpBot.GET);
		}
		return getResponseStringFromConnection(connection);
	}

	/**
	 * 带头信息的http请求
	 * 
	 * @param url
	 * @param paramMap
	 * @param headers
	 * @param method
	 * @return
	 * @throws IOException
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 */
	private String request(String url, Map<String, String> paramMap, Map<String, String> headers, String method)
			throws IOException, ConnectTimeoutException, SocketTimeoutException {
		if (HttpBot.POST.equals(method)) {
			connection = getHttpURLConnection(url, HttpBot.POST);
			connection.setDoOutput(true);
			connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			if (headers != null && !headers.isEmpty()) {
				Set<Entry<String, String>> entrys = headers.entrySet();
				for (Map.Entry<String, String> entry : entrys) {
					connection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			OutputStream os = connection.getOutputStream();
			os.write(getParamBytes(paramMap));
		} else {
			connection = getHttpURLConnection(url, headers, HttpBot.GET);
		}
		return getResponseStringFromConnection(connection);
	}

	/**
	 * 带请求头且发送json数据
	 * 
	 * @param url
	 * @param headers
	 * @param requestJs
	 * @param method
	 * @return
	 * @throws IOException
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 */
	private String requestJS(String url, Map<String, String> headers, String requestJs, String method) throws IOException,
			ConnectTimeoutException, SocketTimeoutException {
		url = HttpConnectionUtils.setUrlParameter(url, addParam(null));
		if (HttpBot.POST.equals(method)) {
			connection = getHttpURLConnection(url, HttpBot.POST);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			if (headers != null && !headers.isEmpty()) {
				Set<Entry<String, String>> entrys = headers.entrySet();
				for (Map.Entry<String, String> entry : entrys) {
					connection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			if (CheckUtils.isNoEmptyStr(requestJs)) {
				OutputStream os = connection.getOutputStream();
				os.write(requestJs.getBytes());
			}

		} else {
			connection = getHttpURLConnection(url, headers, HttpBot.GET);
		}
		return getResponseStringFromConnection(connection);
	}

	public String getResponseStringFromConnection(HttpURLConnection conn) throws IOException {
		reponseCode = conn.getResponseCode();
		MLog.d("错误码" + reponseCode + "        url:" + conn.getURL());
		if (reponseCode >= 400) {
			inputStream = conn.getErrorStream();
		} else {
			inputStream = conn.getInputStream();
		}

		String contentEncoding = conn.getContentEncoding();
		MLog.s(contentEncoding);
		int contentLength = conn.getContentLength();
		if (contentEncoding != null && contentEncoding.indexOf("gzip") != -1) {
			inputStream = new GZIPInputStream(inputStream);
		} else if (contentEncoding != null && contentEncoding.indexOf("deflate") != -1) {
			inputStream = new InflaterInputStream(inputStream);
		} else {

		}
		MLog.s("打印大小" + inputStream.available());
		return readStringFromInputStream(inputStream, contentLength);
	}

	public void download(String url, File file) throws IOException {
		FileOutputStream fos = null;
		try {
			HttpURLConnection conn = getHttpURLConnection(url, HttpBot.GET);
			reponseCode = conn.getResponseCode();
			inputStream = conn.getInputStream();
			String contentEncoding = conn.getContentEncoding();
			if (contentEncoding != null && contentEncoding.indexOf("gzip") != -1) {
				inputStream = new GZIPInputStream(inputStream);
			} else if (contentEncoding != null && contentEncoding.indexOf("deflate") != -1) {
				inputStream = new InflaterInputStream(inputStream);
			}
			fos = new FileOutputStream(file);
			byte[] buff = new byte[1024];
			int n = -1;
			while ((n = inputStream.read(buff)) != -1) {
				fos.write(buff, 0, n);
			}
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {

			}
		}
	}

	public void addHeader(String key, String value) {
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		headers.put(key, value);
	}

	public Bitmap getBitmap(String url) throws IOException {
		HttpURLConnection conn = getHttpURLConnection(url, HttpBot.GET);
		reponseCode = conn.getResponseCode();
		inputStream = conn.getInputStream();
		return StreamUtils.readBitmapFromInputStream(inputStream, true);
	}

	/**
	 * 文件post上传
	 * 
	 * @param url
	 *            URL地址
	 * @param param
	 *            上传参数
	 * @param fileContent
	 *            文件内容
	 * @param clientFileName
	 *            文件名
	 * @param contentType
	 *            类型
	 */
	public String upload(String url, String param, byte[] fileContent, String clientFileName, String contentType)
			throws IOException {
		if (fileContent == null || fileContent.length == 0) {
			connection = getHttpURLConnection(url, HttpBot.GET);
		} else {
			String boundary = "---------------------------7d4a6d158c9";
			byte[] lineEnd = "\r\n".getBytes();
			connection = getHttpURLConnection(url, HttpBot.POST);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			OutputStream os = connection.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(os);
			bos.write(("--" + boundary).getBytes());
			bos.write(lineEnd);
			bos.write(("Content-Disposition: form-data; name=\"" + param + "\"; filename=\"" + clientFileName + "\"").getBytes());
			bos.write(lineEnd);
			bos.write(("Content-Type: " + contentType).getBytes());
			bos.write(lineEnd);
			bos.write(lineEnd);
			bos.write(fileContent);
			bos.write(lineEnd);
			bos.write(("--" + boundary + "--").getBytes());
			bos.write(lineEnd);
			bos.flush();
		}
		return getResponseStringFromConnection(connection);
	}

	/**
	 * 文件post上传多图
	 * 
	 * @param url
	 *            URL地址
	 * @param param
	 *            上传参数
	 * @param files
	 *            多图
	 */
	public String post(String url, Map<String, String> params, Map<String, File> files) {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		try {
			HttpURLConnection conn = getHttpURLConnection(url, HttpBot.POST);
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
			DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
			if (params != null) {
				// 首先组拼文本类型的参数
				StringBuilder sb = new StringBuilder();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					sb.append(PREFIX);
					sb.append(BOUNDARY);
					sb.append(LINEND);
					sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
					sb.append("Content-Type: text/plain; charset=" + DEFAULT_CHARSET_ENCODING + LINEND);
					sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
					sb.append(LINEND);
					sb.append(entry.getValue());
					sb.append(LINEND);
				}
				outStream.write(sb.toString().getBytes());

			}
			// 发送文件数据
			if (files != null) {
				for (Map.Entry<String, File> file : files.entrySet()) {

					StringBuilder sb1 = new StringBuilder();
					sb1.append(PREFIX);
					sb1.append(BOUNDARY);
					sb1.append(LINEND);
					sb1.append("Content-Disposition: form-data; name=\"" + file.getKey() + "\"; filename=\""
							+ file.getValue().getName() + "\"" + LINEND);
					sb1.append("Content-Type: image/jpeg; " + LINEND);
					sb1.append(LINEND);
					outStream.write(sb1.toString().getBytes());

					InputStream is = new FileInputStream(file.getValue());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						outStream.write(buffer, 0, len);
					}
					is.close();
					outStream.write(LINEND.getBytes());
				}
				// 请求结束标志
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
				outStream.write(end_data);
				outStream.flush();
				// // 得到响应码
				// int res = conn.getResponseCode();
				// MLog.s("响应码：" + res);
				// String responseStr = getResponseStr(conn.getInputStream());
				// MLog.s("响应：" + responseStr);
				// outStream.close();
				// conn.disconnect();
				return getResponseStringFromConnection(conn);
			}
		} catch (Exception e) {

		}
		return null;

	}

	/**
	 * post带头信息
	 * 
	 * @param url
	 * @param paramMap
	 * @param headers
	 * @param method
	 * @return
	 * @throws IOException
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 */
	public String post(String url, Map<String, String> paramMap, Map<String, String> headers, String method) throws IOException,
			ConnectTimeoutException, SocketTimeoutException {
		return request(url, paramMap, headers, method);
	}

	/**
	 * @author jiangweidong 请求方法，整合get与Post请求，及paramMap相对于get和post的转化
	 */
	public String doRequest(String method, String url, Map<String, String> paramMap) throws IOException, ConnectTimeoutException,
			SocketTimeoutException {
		paramMap = addParam(paramMap);// 添加公有参数
		Map<String, String> header = null;
		if (HttpBot.POST.equals(method)) {
			connection = getHttpURLConnection(url, HttpBot.POST);
			connection.setDoOutput(true);
			connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			if (header != null && !header.isEmpty()) {
				Set<Entry<String, String>> entrys = header.entrySet();
				for (Map.Entry<String, String> entry : entrys) {
					connection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			OutputStream os = connection.getOutputStream();
			os.write(getParamBytes(paramMap));
		} else {
			url = HttpConnectionUtils.setUrlParameter(url, paramMap);
			connection = getHttpURLConnection(url, header, HttpBot.GET);
		}
		return getResponseStringFromConnection(connection);
	}

	/**
	 * post带头信息和json数据
	 * 
	 * @param url
	 * @param headers
	 * @param requestJs
	 * @param method
	 * @return
	 * @throws IOException
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 */
	public String postJS(String url, Map<String, String> headers, String requestJs, String method) throws IOException,
			ConnectTimeoutException, SocketTimeoutException {
		return requestJS(url, headers, requestJs, method);
	}

	public void close() {
		try {
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (IOException e) {
			Log.w("HttpBot", "close http connection failed!");
		}
	}

	/**
	 * 获取连接
	 */
	public HttpURLConnection getHttpURLConnection(String urlStr, String method) throws IOException, ConnectTimeoutException,
			SocketTimeoutException {
		HttpURLConnection conn = null;
		URL url = new URL(urlStr);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setConnectTimeout(connectTimeOut);
		conn.setReadTimeout(readTimeOut);
		conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
		// conn.setRequestProperty("Accept-Encoding", "identity");
		conn.setRequestProperty("Accept-Charset", DEFAULT_CHARSET_ENCODING);
		addHttpHeaders(conn);
		disableConnectionReuseIfNecessary();
		return conn;
	}

	/**
	 * 获取连接
	 */
	public HttpURLConnection getHttpURLConnection(String urlStr, Map<String, String> headers, String method) throws IOException,
			ConnectTimeoutException, SocketTimeoutException {
		HttpURLConnection conn = null;
		URL url = new URL(urlStr);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setConnectTimeout(connectTimeOut);
		conn.setReadTimeout(readTimeOut);
		conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
		conn.setRequestProperty("Accept-Charset", DEFAULT_CHARSET_ENCODING);
		conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
		if (headers != null && !headers.isEmpty()) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Map.Entry<String, String> entry : entrys) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}

		addHttpHeaders(conn);
		disableConnectionReuseIfNecessary();
		return conn;
	}

	private void addHttpHeaders(HttpURLConnection conn) {
		if (headers == null || headers.isEmpty()) {
			return;
		}
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			conn.addRequestProperty(entry.getKey(), entry.getValue());
		}
	}

	private static void disableConnectionReuseIfNecessary() {
		// HTTP connection reuse which was buggy pre-froyo
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
			System.setProperty("http.keepAlive", "false");
		}
	}

	private static byte[] getParamBytes(Map<String, String> paramMap) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			String value = entry.getValue();
			if (value != null) {
				sb.append(entry.getKey()).append("=").append(StringUtils.encodeURL(value)).append("&");
			}
		}
		return sb.toString().getBytes();
	}

	private String readStringFromInputStream(InputStream is, int contentLength) throws IOException {
		int size = contentLength;
		String readString = null;
		if (contentLength <= 0) {
			size = DEFAULT_BUFFER_SIZE;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder(size);
		char[] buf = new char[DEFAULT_BUFFER_SIZE];
		int len = 0;
		try {
			while ((len = br.read(buf)) != -1) {
				sb.append(buf, 0, len);
			}
			readString = sb.toString();
			MLog.d(readString);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			// java.util.zip.GZIPInputStream.read(GZIPInputStream.java:169)
		} catch (ArrayStoreException e) {
			// destination of type @ is not an array
		} finally {
			if (br != null) {
				br.close();
				br = null;
			}
		}
		return readString;
	}

	public int getReponseCode() {
		return reponseCode;
	}

	public int getReadTimeOut() {
		return readTimeOut;
	}

	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	public int getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	/**
	 * @author Johnson 添加请求参数
	 * */
	public Map<String, String> addParam(Map<String, String> paramMap) {
		if (paramMap == null) {
			paramMap = new HashMap<String, String>();
		}
//		String sessionId = UserManager.getUserToken(App.getInstance().getInstance().getApplicationContext());
//		String udid = DeviceParameter.getUUID();
//
//		String id = UserManager.getUserId(App.getInstance().getApplicationContext());
//
//		int versionCode = CommonUtils.getCurrentVersionCode();
//		int deviceType = 2;
//		String pjcode = "2_2015_03_52";
//		long time = System.currentTimeMillis();
//		String md5 = udid + pjcode + time + deviceType;
//
//		paramMap.put("sessionid", sessionId);
//		paramMap.put("udid", udid);
//		paramMap.put("version", versionCode + "");
//		paramMap.put("devicetype", deviceType + "");
//		paramMap.put("device_size", (DeviceParameter.getScreenWidth() + "x" + DeviceParameter.getScreenHeight()));
//		s2 = StringUtils.encodeURL(android.os.Build.MANUFACTURER + "-" + android.os.Build.MODEL);
//
//		// System.out.println("-----HttpBot中机型---" + s2.replace("+", ""));
//		paramMap.put("devicemodel", s2.replace("+", ""));
//		paramMap.put("nettype", DeviceParameter.getNetWorkType(App.getInstance().getApplicationContext()));
//		paramMap.put("osversion", DeviceParameter.getOSVersion());
//		paramMap.put("pjcode", pjcode);
//		paramMap.put("time", time + "");
//		paramMap.put("securitykey", MD5Util.getMD5(md5.getBytes()));

		return paramMap;
	}

	public String addUrlParam(String url) {
		url = HttpConnectionUtils.setUrlParameter(url, addParam(null));
		return url;
	}

	public boolean downloadImage(String imageUrl) {
		if (TextUtils.isEmpty(imageUrl)) {
			return true;
		}
		File file = ImageCache.getInstance().getFileForKey(imageUrl);
		if (file.exists() && file.length() > 0) {
			return true;
		} else {
			return downloadImage(imageUrl, file);
		}
	}

	/**
	 * @param url
	 *            图片地址
	 * @param file
	 *            要保存到的文件
	 * @return
	 */
	public boolean downloadImage(String url, File file) {
		if (url == null || file == null) {
			return false;
		}
		HttpGet httpGet = null;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			httpGet = new HttpGet(url);
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, DEFAULT_IMAGE_CONNECT_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParameters, DEFAULT_IMAGE_READ_TIMEOUT);
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			BasicHttpResponse httpResponse = (BasicHttpResponse) httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			int responseCode = httpResponse.getStatusLine().getStatusCode();
			if (responseCode == HttpStatus.SC_NOT_FOUND) {
				return false;
			} else if (responseCode != HttpStatus.SC_OK) {
				return false;
			}
			is = entity.getContent();
			fos = new FileOutputStream(file);
			byte[] buff = new byte[4096];
			int n = -1;
			while ((n = is.read(buff)) != -1) {
				fos.write(buff, 0, n);
			}
			fos.flush();
			buff = null;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
				if (httpGet != null) {
					httpGet.abort();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Log.i("pp", ""+file.length()/1000+"------"+url);
		return (file.exists() && file.length() > 0);
	}

}
