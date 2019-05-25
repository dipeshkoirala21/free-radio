package onlineradio.zeronebits.com.freeradio;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import onlineradio.zeronebits.com.freeradio.model.FMCategory;
import onlineradio.zeronebits.com.freeradio.player.RadioManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RadioListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RadioListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RadioListFragment extends Fragment implements AdapterCallback{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    RecyclerView recyclerView;
    View view;
    List<Fm> fmList=new ArrayList<>();
    List<FMCategory>fms;
    int currentPosition;
    RecyclerViewAdapter adapter;
    RadioManager radioManager;
    private ViewPager viewPager;
    ProgressDialog progressDialog;

    private OnFragmentInteractionListener mListener;

    public RadioListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RadioListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RadioListFragment newInstance(String param1, String param2) {
        RadioListFragment fragment = new RadioListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        radioManager = RadioManager.with(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_radio_list, container, false);
        recyclerView= view.findViewById(R.id.recyclerview_id);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
//        recyclerView.setAdapter(new RecyclerViewAdapter(new ArrayList<Fm>()));
//        loadFMLIST();
        fmList = (List<Fm>) getArguments().getSerializable("fms");
        adapter = new RecyclerViewAdapter(getActivity(),fmList,this);
        recyclerView.setAdapter(adapter);
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    //override method will auto called on item clicked in adapter call
    @Override
    public void itemClicked(Fm fmRadio) {

        newFragment fragment = new newFragment();
        Bundle args = new Bundle();
        args.putSerializable("fm", fmRadio);
        fragment.setArguments(args);
        FragmentManager fm = ((MainActivity) getActivity()).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.flFrame, fragment).addToBackStack("Tag")
                .commit();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
