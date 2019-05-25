package onlineradio.zeronebits.com.freeradio;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import onlineradio.zeronebits.com.freeradio.model.PlayableLinkModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/* interface class for adapter click*/

interface AdapterCallback{
    void itemClicked(Fm fm);
}


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    View view ;
    private Context mContext ;
    private List<Fm> mData ;
    private AdapterCallback adapterCallback;

    RecyclerViewAdapter(Context mContext, List<Fm> fmList, AdapterCallback adapterCallback) {
        this.mContext = mContext;
        this.mData = fmList;
        this.adapterCallback = adapterCallback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.radiocard_view,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.radioName.setText(mData.get(position).getName());
        Picasso.with(mContext)
                .load(mData.get(position).getImage())
                .into(holder.radioImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "fms ID = " + mData.get(position).getId(), Toast.LENGTH_SHORT).show();
                int fmId = mData.get(position).getId();
                //call interface method on item click
                adapterCallback.itemClicked(mData.get(position));
//                playableLink(fmId);


            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView radioName;
        ImageView radioImage;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            radioName = (TextView) itemView.findViewById(R.id.radioTextView) ;
            radioImage = (ImageView) itemView.findViewById(R.id.radioImageView);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "inside viewholder position = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }


}
