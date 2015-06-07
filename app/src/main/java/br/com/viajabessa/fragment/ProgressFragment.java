package br.com.viajabessa.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.viajabessa.R;

/**
 * Created by gabrielbernardopereira on 3/14/15.
 */
public class ProgressFragment extends Fragment {

    public ProgressFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_bar, null);
        return view;
    }

}