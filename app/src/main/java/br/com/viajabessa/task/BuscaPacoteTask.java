package br.com.viajabessa.task;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
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
 * Created by gabrielbernardopereira on 3/15/15.
 */
public class BuscaPacoteTask extends AsyncTask<String, Void, Pacote> {
    private static final String TAG = "BuscaPacoteTask";

    //TODO Change server URL, this is just for test purpose
    private static final String API_URL = "http://private-5ae24-viajabessa21.apiary-mock.com/pacote";

    public interface Delegate {
        public void onSuccessLoadPacote(Pacote pacote);
        public void onErrorLoadingPacote(Exception e);
    }

    private Delegate _d;

    public BuscaPacoteTask(Delegate d) {
        this._d = d;
    }

    @Override
    protected Pacote doInBackground(String... params) {
        if (null == params || params.length < 1)
            return null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(API_URL);
            sb.append("/"+ params[0] );

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
            Log.d(TAG, "JSON Result:"+jsonResults.toString());

            //TODO Remover Thread Sleep ao alterar para o server verdadeiro
            Thread.sleep(2000);

            JSONObject pacoteJson = new JSONObject(jsonResults.toString());

            Pacote p = new Pacote();
            p.setNome(""+pacoteJson.getString("nome"));
            p.setValor(pacoteJson.getDouble("valor"));
            p.setDescricao(""+pacoteJson.getString("descricao"));
            p.setFoto(""+pacoteJson.getString("foto"));
            p.setFoto_thumb(""+pacoteJson.getString("foto_thumb"));
            return p;
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error processing URL", e);
            _d.onErrorLoadingPacote(e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to API", e);
            _d.onErrorLoadingPacote(e);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            _d.onErrorLoadingPacote(e);
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(Pacote result) {
        if (result != null)
            _d.onSuccessLoadPacote(result);
    }
}