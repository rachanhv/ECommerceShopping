package vinformax.vinmart.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vinformax.vinmart.R;


public class thirdfragment extends Fragment {



    public static thirdfragment newInstance(String param1, String param2) {
        thirdfragment fragment = new thirdfragment();
               return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_thirdfragment, container, false);
    }

}
