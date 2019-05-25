package onlineradio.zeronebits.com.freeradio;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import onlineradio.zeronebits.com.freeradio.model.FMCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements newFragment.OnFragmentInteractionListener {
    List<FMCategory> apiData = new ArrayList<>();
//    List<Fm> fmList;
    private Toolbar toolbar;
//    private TabLayout tabLayout;
//    private ViewPager viewPager;
//    ProgressDialog progressDialog;
//    RadioManager radioManager;
//    public RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        progressDialog = new ProgressDialog(MainActivity.this);
//        progressDialog.setMessage("Loading....");
//        progressDialog.show();
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        viewPager =  findViewById(R.id.viewpager);
//        tabLayout =  findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
//        radioManager = RadioManager.with(this);
        initViews();

    }



    private void initViews() {
//        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_id);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(adapter);
        loadFMLIST();
    }



    private void loadFMLIST() {
        RadioInterface apiInterface = APIClient.getClient().create(RadioInterface.class);
        Call<List<FMCategory>> call = apiInterface.getFms();
        call.enqueue(new Callback<List<FMCategory>>() {
            @Override
            public void onResponse(Call<List<FMCategory>> call, Response<List<FMCategory>> response) {
                Log.d("fsfslfdfkdsf", response.code() + "gj");
                apiData = response.body();
//                setupViewPager(viewPager);
                Fragment fragment = new OneFragment();
                Bundle b = new Bundle();
                b.putSerializable("apiData", (Serializable) apiData);
                fragment.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.flFrame, fragment)
                        .addToBackStack("list").commit();



            }

            @Override
            public void onFailure(Call<List<FMCategory>> call, Throwable t) {
                Log.d("fsfslfdfkdsf",  "failed");
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }






     /*   private void loadJSON() {
        RadioInterface apiInterface=APIClient.getClient().create(RadioInterface.class);
       Call<ResponseBody> call = apiInterface.getAPI();
       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               progressDialog.dismiss();
               try {
                   JSONArray jsonArray = new JSONArray(response.body().string());
                   for(int i=0; i < jsonArray.length(); i++){
                       JSONObject jsonObject = jsonArray.getJSONObject(i);
                       FMCategory apiDatum = new FMCategory();
                       apiDatum.setCategoryName(jsonObject.getString("category_name"));
                       apiDatum.setCategoryName(jsonObject.getString("category_name"));
                       apiData.add(apiDatum);

                   }
                   setupViewPager(viewPager);

               } catch (IOException e) {
                   e.printStackTrace();
               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {
               progressDialog.dismiss();
               Log.d("Error",t.getMessage());
           }
       });
*/
//       call.enqueue(new Callback<List<APIDatum>>() {
//           @Override
//           public void onResponse(Call<List<APIDatum>> call, Response<List<APIDatum>> response) {
//               Log.d("TAG", response.code() + "hajkfhkjaskjfakjsdf");
//               fmList = response.body();
//               Log.d("TAG", fmList.size()+"  ");
//                System.out.println(postDataList.get(0).getTitle());
//               data = new ArrayList<>(Arrays.asList(jsonResponse.getDatas()));
//               adapter = new RecyclerViewAdapter(getApplicationContext(),fmList);
//               recyclerView.setAdapter(adapter);
//           }
//
//           @Override
//           public void onFailure(Call<List<APIDatum>> call, Throwable t) {
//               Log.d("Error",t.getMessage());
//           }
//       });

    }

