package com.example.displayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class IUTAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = IUTAsyncTask.class.getCanonicalName();

    public static final String URL_API = "https://randomuser.me/api/";
    public HttpURLConnection huc;
    public InputStream responseStream;

    private Context context;
    private ImageView imageView;

    public IUTAsyncTask(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(TAG, "onPreExecute IUT");
    }

    @Override
    protected Bitmap doInBackground(String... imageURLS) {
        Log.i(TAG, "doInBackground IUT");

        if (imageURLS != null && imageURLS.length > 0) {
            String imageUrl = imageURLS[0];

            InputStream inputStream = doSimpleGetRequest(this.context, imageUrl);

            return BitmapFactory.decodeStream(inputStream);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        Log.i(TAG, "onPostExecute IUT");

        Log.i(TAG, "bitmap : " + bitmap);

        this.imageView.setImageBitmap(bitmap);
        this.imageView.setVisibility(View.VISIBLE);
    }

    private InputStream doSimpleGetRequest(Context context, String url) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnected()) {
            Uri uri = Uri.parse(url).buildUpon().build();

            try {
                java.net.URL requestURL = new URL(uri.toString());

                huc = (HttpURLConnection) requestURL.openConnection();
                huc.setRequestMethod("GET");
                huc.connect();

                int responseCode = huc.getResponseCode();
                responseStream = huc.getInputStream();

//                String res = IOUtils.toString(responseStream, "UTF-8");
//                Log.i(TAG, "Response : " + res);

                return responseStream;
            } catch(IOException e) {
                Log.e(TAG, "Error while connecting to " + url, e);
            }
        } else {
            Log.e(TAG, "Error ");
        }

        return null;
    }

}
