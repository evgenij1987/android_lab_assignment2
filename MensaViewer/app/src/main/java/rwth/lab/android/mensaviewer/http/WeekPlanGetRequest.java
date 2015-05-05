package rwth.lab.android.mensaviewer.http;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import rwth.lab.android.mensaviewer.model.MensaListItem;
import rwth.lab.android.mensaviewer.model.WeekPlan;
import rwth.lab.android.mensaviewer.parser.WeekPlanXmlPullParser;

/**
 * Created by evgenijavstein on 29/04/15.
 */
public class WeekPlanGetRequest {
    private static final String TAG = "Mensa-Viewer";
    private static final int READ_TIMEOUT = 10000; //milliseconds
    private static final int CONNECT_TIMEOUT = 15000;  //milliseconds
    private OnResponseListener onResponseListener;
    private MensaListItem mensaItem;

    public WeekPlanGetRequest(MensaListItem mensaItem) {
        this.mensaItem = mensaItem;
    }

    /**
     * Set OnResponseListener before calling this method, to receive the response
     */
    public void send() {
        new HttpGetTask().execute(mensaItem);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpGetTask extends AsyncTask<MensaListItem, Void, WeekPlan> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onResponseListener.onPreExecute();
        }

        @Override
        protected WeekPlan doInBackground(MensaListItem... params) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(params[0].getMensaURL());
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Encoding", "gzip"); // leverage gzip compression to save bandwidth.
                conn.setDoInput(true);
                conn.connect();

                /*decompress gzip stream*/
                GZIPInputStream gzipInputStream = new GZIPInputStream(new BufferedInputStream(conn.getInputStream()));
                WeekPlanXmlPullParser parser = new WeekPlanXmlPullParser(gzipInputStream);

                return parser.parse();
            } catch (MalformedURLException e) {
                Log.e(TAG, "Error with URL: " + e.getMessage());
                onResponseListener.onError(e.getMessage());
                return null;
            } catch (ProtocolException e) {
                Log.e(TAG, "Error with protocol: " + e.getMessage());
                onResponseListener.onError(e.getMessage());
                return null;
            } catch (IOException e) {
                Log.e(TAG, "Error while working with a stream: " + e.getMessage());
                onResponseListener.onError(e.getMessage());
                return null;
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error while parsing: " + e.getMessage());
                onResponseListener.onError(e.getMessage());
                return null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(WeekPlan weekPlan) {
            super.onPostExecute(weekPlan);
            onResponseListener.onResponse(weekPlan);
        }
    }
}
