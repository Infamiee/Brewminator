package pl.nzi.brewminator.service;

import android.content.Context;


import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.simple.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;



public class ApiConnector {
    private static final String URL = "https://brewminator-api.herokuapp.com";
    private RequestQueue queue;
    public ApiConnector(Context context) {
        queue = Volley.newRequestQueue(context);
    }


    public void get(String endpoint,JSONObject bodyObj, int method, Response.Listener<String> response, Response.ErrorListener error) {

        if (bodyObj==null){
            bodyObj = new JSONObject();
        }
        final String body = bodyObj.toString();
        String url = URL;
        if (endpoint != null){
            url = URL +endpoint;
        }

        StringRequest request = new StringRequest(method, url,response , error) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {
                try {
                    return body.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            body, "utf-8");
                    return null;
                }
            }
        };
        queue.add(request);

    }
}
