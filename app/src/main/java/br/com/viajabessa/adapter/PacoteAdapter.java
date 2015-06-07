package br.com.viajabessa.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.viajabessa.R;
import br.com.viajabessa.model.Pacote;

/**
 * Created by gabrielbernardopereira on 3/14/15.
 */
public class PacoteAdapter extends BaseAdapter {
    private final List<Pacote> pacotesList;
    private Context context;

    public PacoteAdapter(Context context, List<Pacote> pacotesList) {
//        super(context, R.layout.pacote_list_row, pacotesList);
        this.context = context;
        this.pacotesList = pacotesList;
    }

    @Override
    public int getCount() {
        return pacotesList.size();
    }

    @Override
    public Object getItem(int i) {
        return pacotesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View rowView = convertView;
        ViewHolder holder;
        // reuse views
        if (rowView == null) {
            rowView = LayoutInflater.from(context).inflate(R.layout.pacote_list_row, viewGroup, false);
            // configure view holder
            holder = new ViewHolder(rowView);
            rowView.setTag(holder);
        }else {
            holder = (ViewHolder) rowView.getTag();
        }

        Pacote pacote = pacotesList.get(position);

        holder.nomePacote.setText(pacote.getNome());
        holder.valorPacote.setText(String.format( "R$ %.2f", pacote.getValor()));
        if (null != pacote.getFoto_thumb() && !pacote.getFoto_thumb().isEmpty()) {
            Picasso.with(this.context)
                    .load(pacote.getFoto_thumb())
                    .placeholder(R.drawable.placeholder_thumb)
                    .error(R.drawable.placeholder_thumb)
                    .fit()
                    .into(holder.thumbnail);
        }else{
            holder.thumbnail.setImageResource(R.drawable.placeholder_thumb);
        }
        return rowView;
    }

    static class ViewHolder {
        public TextView nomePacote;
        public TextView valorPacote;
        public ImageView thumbnail;

        ViewHolder(View view){
            this.nomePacote = (TextView) view.findViewById(R.id.title);
            this.valorPacote = (TextView) view.findViewById(R.id.price);
            this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }
}