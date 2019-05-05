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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class fetchData extends AsyncTask<Void, Void, Void> {
    String data, id;
    public static String name;
    public static ArrayList<String> attendance = new ArrayList<String>();
    public static JSONObject a;
    fetchData(String id) {
        this.id = id;
        Log.i("id", this.id);
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://18.212.176.13:443/" + id);
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
                Log.i("json", "yes");
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                name = jsonObject.get("name").toString();
                System.out.println(jsonObject.get("attended").getClass().getSimpleName());
                a = (JSONObject) jsonObject.get("attended");
                System.out.println(a);
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
//        Home.loginname.setText(this.name);
        try {
            SubjectInfo.initJsonObject(a);
            Home.initJsonObject(a);
            AttendanceChart.AddValuesToBARENTRY(a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
