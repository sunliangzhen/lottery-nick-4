package com.example.machenike.myapplication.net;



import com.example.machenike.myapplication.a.JSONUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;


/**
 * @author Zero
 * @date 2016/4/13 8:18
 */
public class ApiTool {
    public ApiTool() {
    }

    RequestParams params;
    String type;

    public Callback.Cancelable getApi(RequestParams params, ApiListener apiListener, String type) {
        this.params = params;
        this.type = type;
        params.setConnectTimeout(15000);
        return x.http().get(params, new DefaultRequestCallBack(apiListener));
    }


    public Callback.Cancelable postApi(RequestParams params, ApiListener apiListener, String type) {
        this.params = params;
        this.type = type;
        params.setConnectTimeout(15000);
        return x.http().post(params, new DefaultRequestCallBack(apiListener));
    }

    public Callback.Cancelable postApiTime(RequestParams params, ApiListener apiListener, String type, int time) {
        this.params = params;
        this.type = type;
        params.setConnectTimeout(time);
        return x.http().post(params, new DefaultRequestCallBack(apiListener));
    }


    private class DefaultRequestCallBack implements Callback.ProgressCallback<String> {
        private ApiListener apiListener;

        public DefaultRequestCallBack(ApiListener apiListener) {
            this.apiListener = apiListener;
        }

        @Override
        public void onSuccess(String result) {
            try {
                Map e = parseError(result);
                if (e == null || e.size() == 0) {
                    if (this.apiListener != null) {
                        this.apiListener.onComplete(params, result, type);
                    }
                } else if (this.apiListener != null) {
                    this.apiListener.onError(e, params);
                }
            } catch (Exception var4) {
                if (this.apiListener != null) {
//                    this.apiListener.onExceptionType(var4, params, type);
                }
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            this.apiListener.onExceptionType(ex, params, type);
        }
        @Override
        public void onCancelled(CancelledException cex) {
            this.apiListener.onCancelled(cex);
        }
        @Override
        public void onFinished() {

        }

        @Override
        public void onWaiting() {

        }

        @Override
        public void onStarted() {

        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading) {

        }
    }

    public static Map<String, String> parseError(String json) {
        JSONObject jsonObject = null;
        if (json.startsWith("[") && json.endsWith("]")) {
            return null;
        } else {
            try {
                jsonObject = new JSONObject(json);
            } catch (JSONException var4) {
                return null;
            }
            String flag = jsonObject.optString("flag");
            return flag != null && flag.equals("error") ? JSONUtils.parseKeyAndValueToMap(json) : null;
        }
    }
}

