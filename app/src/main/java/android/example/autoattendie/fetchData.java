package android.example.autoattendie;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchData extends AsyncTask<Void, Void, Void> {
    String data;
    public static String name;
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://18.212.176.13:443");
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                data = bufferedReader.readLine();
            } catch(Exception e) {
                Log.i("Exception", e.toString());
            }
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0;i<jsonArray.length();i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                name = jsonObject.get("name").toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Home.loginname.setText(this.name);
    }
}
