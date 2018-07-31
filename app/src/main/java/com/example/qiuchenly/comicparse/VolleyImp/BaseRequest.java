package com.example.qiuchenly.comicparse.VolleyImp;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.qiuchenly.comicparse.App;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 本类用于网络请求接口集成
 */
public abstract class BaseRequest {

    public interface RequestCallback {
        void onSuccess(String RetStr);

        void onFailed(String ReasonStr);
    }

    public void SendRequest(String url, RequestCallback ret) {
        SendRequest(url, "", ret);
    }

    public void SendRequest(String url, String data, RequestCallback ret) {
        BaseRequestHandle(url, data, ret);
    }

    private void BaseRequestHandle(String url, final String data, final RequestCallback ret) {
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ret.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ret.onFailed(volleyError.getMessage());
            }
        }) {
            @Override
            public byte[] getPostBody() throws AuthFailureError {
                if (data.isEmpty()) return null;
                return data.getBytes();
            }

            @Override
            public int getMethod() {
                if (data.length() > 0) {
                    return Method.POST;
                } else {
                    return Method.GET;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String cook = response.headers.get("Set-Cookie");
                if (cook != null) {
                    String[] c = cook.split(";");
                    if (c.length == 2)
                        App.getCookieMaster().addCookie(c[0].trim(), c[1].trim());
                }
                String ret = "";
                try {
                    ret = new String(response.data, "gb2312");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(ret, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> head = new HashMap<>();
                head.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                head.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
                head.put("Cookie", App.getCookieMaster().toString());
                head.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
                return head;
            }
        };
        App.getQueue().add(req);
    }
}
