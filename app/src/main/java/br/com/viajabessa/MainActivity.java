package br.com.viajabessa;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import br.com.viajabessa.fragment.ListaPacotesFragment;
import br.com.viajabessa.fragment.PacoteDetailFragment;
import br.com.viajabessa.fragment.ProgressFragment;
import br.com.viajabessa.model.Pacote;
import br.com.viajabessa.task.BuscaPacoteTask;
import br.com.viajabessa.task.BuscaPacotesTask;

public class MainActivity
        extends ActionBarActivity
        implements BuscaPacotesTask.Delegate, BuscaPacoteTask.Delegate {

    public enum EstadoMainActivity {INICIO, PACOTES_RECEBIDOS, REFRESH_REQUISITADO, PACOTE_DETAIL_SELECIONADO, MOSTRANDO}

    ;
    private static String ESTADO_ATUAL = "ESTADO_ATUAL";
    private static String LISTA_FRAGMENT = "LISTA_FRAGMENT";
    private static String DETAIL_FRAGMENT = "DETAIL_FRAGMENT";
    private EstadoMainActivity estadoAtual;
    ListaPacotesFragment listaPacotesFragment;
    boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "oncreate ");

        if (findViewById(R.id.pacote_detail_container) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }

        if (savedInstanceState == null) {
            estadoAtual = EstadoMainActivity.INICIO;
            listaPacotesFragment = new ListaPacotesFragment();
        } else {
            this.estadoAtual = (EstadoMainActivity) savedInstanceState.get(ESTADO_ATUAL);
        }
    }

    @Override
    public void onSuccessLoadPacote(Pacote pacote) {
        Log.i("PacoteDetail", "Carregou pacote inteiro:" + pacote.getId());
        getApp().setPacoteSelecionado(pacote);
    }

    @Override
    public void onErrorLoadingPacote(Exception e) {

    }

    @Override
    public void onSuccessLoadPacotes(List<Pacote> pacoteList) {
        Log.d("MainActivity", "Retornou " + pacoteList.size() + " pacotes");
        if (null == getApp().getPacoteSelecionado() && pacoteList.size() > 0) {
            getApp().setPacoteSelecionado(pacoteList.get(0));
        }
        getApp().getPacotes().clear();
        getApp().getPacotes().addAll(pacoteList);
        alteraEstadoEExecuta(EstadoMainActivity.PACOTES_RECEBIDOS);
    }

    @Override
    public void onErrorLoadingPacotes(Exception e) {
    }

    public void alteraEstadoEExecuta(EstadoMainActivity estado) {
        this.estadoAtual = estado;
        if (null == listaPacotesFragment)
            listaPacotesFragment = (ListaPacotesFragment) getSupportFragmentManager().findFragmentByTag(LISTA_FRAGMENT);
        Log.d("MainActivity", "Estado " + estadoAtual);
        if (estadoAtual == EstadoMainActivity.INICIO) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ProgressFragment())
                    .commit();
            new BuscaPacotesTask(this).execute();
        }
        if (estadoAtual == EstadoMainActivity.PACOTES_RECEBIDOS) {
            listaPacotesFragment.notifyRefreshFinished();
            if (mTwoPane) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.pacote_detail_container, new PacoteDetailFragment(), DETAIL_FRAGMENT)
                        .commit();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, listaPacotesFragment, LISTA_FRAGMENT)
                    .commit();
        }
        if (estadoAtual == EstadoMainActivity.REFRESH_REQUISITADO) {
            new BuscaPacotesTask(this).execute();
        }
        if (estadoAtual == EstadoMainActivity.PACOTE_DETAIL_SELECIONADO) {
            //TODO handle pacote //new BuscaPacoteTask(this).execute({pacote selected});
            PacoteDetailFragment pacoteDetailFragment = new PacoteDetailFragment();
            estadoAtual = EstadoMainActivity.MOSTRANDO;
            if (mTwoPane) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.pacote_detail_container, pacoteDetailFragment, DETAIL_FRAGMENT)
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .detach(listaPacotesFragment)
                        .attach(pacoteDetailFragment)
                        .replace(android.R.id.content, pacoteDetailFragment, DETAIL_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
            }
        }
        if (estadoAtual == EstadoMainActivity.MOSTRANDO) {
//            Log.d("MainActivity", "Show");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        alteraEstadoEExecuta(estadoAtual);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ESTADO_ATUAL, this.estadoAtual);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.estadoAtual = (EstadoMainActivity) savedInstanceState.get(ESTADO_ATUAL);
    }

    public TravelApp getApp() {
        return (TravelApp) getApplication();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
