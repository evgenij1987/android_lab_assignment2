package com.lab.android.comsys.evgenijavstein.mensaviewer.request;

import android.os.AsyncTask;

import com.lab.android.comsys.evgenijavstein.mensaviewer.model.WeekPlan;
import com.lab.android.comsys.evgenijavstein.mensaviewer.parser.WeekPlanXmlPullParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by evgenijavstein on 29/04/15.
 */


/**
 * Simple class encapsulating use of @HttpURLConnection
 */
public class WeekPlanGetRequest {

    public interface OnResponseListener{
        public void onResponse(WeekPlan weekPlan);
        public void onError(String errorMessage);
    }

    private OnResponseListener onResponseListener;
    private String remoteUrl;

    public WeekPlanGetRequest(String url){
        remoteUrl=url;
    }

    /**
     * Set OnResponseListener before calling this method, to receive the response
     */
    public void send(){
        new NetworkWorker().execute(remoteUrl);
    }


    class NetworkWorker extends AsyncTask<String, Void,WeekPlan> {


        @Override
        protected WeekPlan doInBackground(String... params) {

            URL url = null;
            try {
                url = new URL(params[0]);
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                WeekPlanXmlPullParser parser=new WeekPlanXmlPullParser(conn.getInputStream());

                return parser.parse();


            } catch (MalformedURLException e) {
                e.printStackTrace();
                onResponseListener.onError(e.getMessage());
                return null;
            } catch (ProtocolException e) {
                e.printStackTrace();
                onResponseListener.onError(e.getMessage());
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                onResponseListener.onError(e.getMessage());
                return null;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                onResponseListener.onError(e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(WeekPlan weekPlan) {
            super.onPostExecute(weekPlan);
            onResponseListener.onResponse(weekPlan);
        }
    }

    public void setOnResponseListener(OnResponseListener onResponseListener){
        this.onResponseListener=onResponseListener;
    }
}
