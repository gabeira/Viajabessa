package br.com.viajabessa.task;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.viajabessa.model.Pacote;

/**
 * Created by gabrielbernardopereira on 3/14/15.
 */
public class BuscaPacotesTask extends AsyncTask<String, Void, List<Pacote>> {
    private static final String TAG = "BuscaPacotesTask";

    //TODO Change server URL, this is just for test purpose
    private static final String API_URL = "http://private-5ae24-viajabessa21.apiary-mock.com/pacotes";

    public interface Delegate {
        public void onSuccessLoadPacotes(List<Pacote> pacoteList);
        public void onErrorLoadingPacotes(Exception e);
    }

    private Delegate _d;

    public BuscaPacotesTask(Delegate d) {
        this._d = d;
    }

    @Override
    protected List<Pacote> doInBackground(String... params) {
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(API_URL);
            sb.append("?android="+ Build.VERSION.RELEASE );
            sb.append("&brand=" + Build.BRAND.toUpperCase());
            sb.append("&model=" + Build.MODEL.toUpperCase());

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
//            Log.d(TAG, "JSON Result:"+jsonResults.toString());

            JSONArray predsJsonArray = new JSONArray(jsonResults.toString());

            List<Pacote> pacotes = new ArrayList<>(predsJsonArray.length());
            for (int i=0; i<predsJsonArray.length();i++) {
                JSONObject pacoteJson = predsJsonArray.getJSONObject(i);
                Pacote p = new Pacote();
                p.setId(pacoteJson.getInt("id"));
                p.setNome(""+pacoteJson.getString("nome"));
                p.setValor(pacoteJson.getDouble("valor"));
                p.setDescricao(""+pacoteJson.getString("descricao"));
                p.setFoto(""+pacoteJson.getString("foto"));
                p.setFoto_thumb(""+pacoteJson.getString("foto_thumb"));
                pacotes.add(p);
            }
            return pacotes;
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error processing URL", e);
            _d.onErrorLoadingPacotes(e);
            return new ArrayList<>(0);
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to API", e);
            _d.onErrorLoadingPacotes(e);
            return new ArrayList<>(0);
        } catch (Exception e) {
            e.printStackTrace();
            _d.onErrorLoadingPacotes(e);
            return new ArrayList<>(0);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(List<Pacote> result) {
        if (result != null)
            _d.onSuccessLoadPacotes(result);
    }
}