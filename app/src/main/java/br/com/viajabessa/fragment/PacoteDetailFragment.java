package br.com.viajabessa.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.com.viajabessa.MainActivity;
import br.com.viajabessa.R;

public class PacoteDetailFragment extends Fragment {

    private TextView nome;
    private TextView valor;
    private TextView description;
    private ImageView imageView;
    private Button botaoComprar;
    private MainActivity activity;

    public PacoteDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pacote_detail, container, false);

        nome = (TextView) view.findViewById(R.id.nome);
        valor = (TextView) view.findViewById(R.id.valor);
        description = (TextView) view.findViewById(R.id.descricao);
        imageView = (ImageView) view.findViewById(R.id.foto);
        botaoComprar = (Button) view.findViewById(R.id.comprar);
        botaoComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                        getResources().getString(R.string.compra_efetuada) + String.format("R$ %.2f", activity.getApp().getPacoteSelecionado().getValor()),
                        Toast.LENGTH_LONG).show();
            }
        });
        loadPacote();
        return view;
    }

    private void loadPacote(){
        if (null != activity.getApp().getPacoteSelecionado()) {
            nome.setText(activity.getApp().getPacoteSelecionado().getNome());
            valor.setText(String.format("R$ %.2f", activity.getApp().getPacoteSelecionado().getValor()));
            description.setText(activity.getApp().getPacoteSelecionado().getDescricao());
            if (null != activity.getApp().getPacoteSelecionado().getFoto() && !activity.getApp().getPacoteSelecionado().getFoto().isEmpty()) {
                Picasso.with(getActivity())
                        .load(activity.getApp().getPacoteSelecionado().getFoto())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .fit()
                        .centerInside()
                        .into(imageView);
            }
        }
    }
}
