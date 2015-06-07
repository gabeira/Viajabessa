package br.com.viajabessa.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.viajabessa.MainActivity;
import br.com.viajabessa.R;
import br.com.viajabessa.adapter.PacoteAdapter;
import br.com.viajabessa.model.Pacote;

/**
 * Created by gabrielbernardopereira on 3/14/15.
 */
public class ListaPacotesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        ListView.OnItemClickListener {

    private ListView pacotesListView;
    private PacoteAdapter adapter;
    private SwipeRefreshLayout swipe;
    private MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.activity = ((MainActivity)this.getActivity());
        this.swipe = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_main, container, false);
        this.swipe.setOnRefreshListener(this);
        this.swipe.setColorSchemeResources(
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light
        );
        this.pacotesListView = (ListView) swipe.findViewById(R.id.pacotes_list);
        this.pacotesListView.setOnItemClickListener(this);

        this.adapter = new PacoteAdapter(getActivity(), activity.getApp().getPacotes());
        this.pacotesListView.setAdapter(this.adapter);
        return this.swipe;
    }

    @Override
    public void onRefresh() {
        activity.alteraEstadoEExecuta(MainActivity.EstadoMainActivity.REFRESH_REQUISITADO);
    }

    public void notifyRefreshFinished(){
        if (null != this.swipe)
            this.swipe.setRefreshing(false);

        if (null != this.adapter)
            this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != parent) {
            activity.getApp().setPacoteSelecionado((Pacote) parent.getItemAtPosition(position));
            activity.alteraEstadoEExecuta(MainActivity.EstadoMainActivity.PACOTE_DETAIL_SELECIONADO);
        }
    }
}
