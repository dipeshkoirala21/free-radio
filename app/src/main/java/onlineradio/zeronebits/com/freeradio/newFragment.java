package onlineradio.zeronebits.com.freeradio;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import onlineradio.zeronebits.com.freeradio.model.FMCategory;
import onlineradio.zeronebits.com.freeradio.model.PlayableLinkModel;
import onlineradio.zeronebits.com.freeradio.player.PlaybackStatus;
import onlineradio.zeronebits.com.freeradio.player.RadioManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


public class newFragment extends Fragment {


    List<Fm> fmList;
    RadioManager radioManager;
    String streamURL;

    ImageButton trigger1;

    @BindView(R.id.playTrigger)
    ImageButton trigger;

    @BindView(R.id.cardview_id)
    CardView cardView;

    @BindView(R.id.name)
    TextView textView;

    View view;


    private OnFragmentInteractionListener mListener;

    public newFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_new, container, false);

        trigger = view.findViewById(R.id.playTrigger);
        Log.d("TAG", streamURL + "");
        ButterKnife.bind(getActivity());
        radioManager = RadioManager.with(getActivity());
//        cardView.setOnClickListener(new RecyclerViewAdapter.MyViewHolder(this,streamURL));
//        trigger.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                radioManager.playOrPause(streamURL);
//
//            }
//        });

        //getting fm model from radio list fragment
        Fm fm = (Fm) getArguments().getSerializable("fm");
        playableLink(fm.getId());
        return view;
    }


    private void playableLink(final int selectedCard) {
        RadioInterface apiInterface = APIClient.getClient().create(RadioInterface.class);
        Call<PlayableLinkModel> call = apiInterface.getId(selectedCard + "");
        call.enqueue(new Callback<PlayableLinkModel>() {
            @Override
            public void onResponse(Call<PlayableLinkModel> call, Response<PlayableLinkModel> response) {
//                Log.d("TAG", response.body().getPlayableUrl() + "gj");
                PlayableLinkModel playableLinkModel = response.body();
//                Toast.makeText(view.getContext(),playableLinkModel.getPlayableUrl(),Toast.LENGTH_SHORT);
                final String link = playableLinkModel.getPlayableUrl().toString();
                trigger.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radioManager.playOrPause(link);
                    }
                });
            }

            @Override
            public void onFailure(Call<PlayableLinkModel> call, Throwable t) {
                Toast.makeText(view.getContext(), "Api Calling Failed!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onStart() {

        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {

        EventBus.getDefault().unregister(this);

        super.onStop();
    }


    @Override
    public void onDestroy() {

        radioManager.unbind();

        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        radioManager.bind();
    }


    @Subscribe
    public void onEvent(String status) {

        switch (status) {

            case PlaybackStatus.LOADING:

                // loading

                break;

            case PlaybackStatus.ERROR:

                Toast.makeText(getActivity(), R.string.no_stream, Toast.LENGTH_SHORT).show();

                break;

        }

        trigger.setImageResource(status.equals(PlaybackStatus.PLAYING)
                ? R.drawable.ic_pause_black
                : R.drawable.ic_play_arrow_black);

    }


    @OnClick(R.id.playTrigger)
    public void onClicked() {

        if (TextUtils.isEmpty(streamURL)) return;

        radioManager.playOrPause(streamURL);
    }

    @OnItemClick(R.id.cardview_id)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        PlayableLinkModel shoutcast = (PlayableLinkModel) parent.getItemAtPosition(position);
        if (shoutcast == null) {

            return;

        }

        textView.setText(fmList.get(position).getName());


        streamURL = shoutcast.getPlayableUrl();

        radioManager.playOrPause(streamURL);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
