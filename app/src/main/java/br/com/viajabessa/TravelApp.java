package br.com.viajabessa;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import br.com.viajabessa.model.Pacote;

/**
 * Created by gabrielbernardopereira on 3/14/15.
 */
public class TravelApp extends Application {
    private List<Pacote> pacoteList = new ArrayList<>(0);
    private Pacote pacoteSelecionado;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public List<Pacote> getPacotes(){
        return pacoteList;
    }

    public void setPacoteSelecionado(Pacote pacote){
        this.pacoteSelecionado = pacote;
    }
    public Pacote getPacoteSelecionado(){
        return pacoteSelecionado;
    }

}
