package onlineradio.zeronebits.com.freeradio;

import java.util.List;

import okhttp3.ResponseBody;
import onlineradio.zeronebits.com.freeradio.model.FMCategory;
import onlineradio.zeronebits.com.freeradio.model.PlayableLinkModel;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RadioInterface {

    String BASE_URL="http://51.75.23.214/tv/public/api/v1/";

    @GET("fms")
    Call<ResponseBody> getAPI();

    @GET("fms")
    Call<List<FMCategory>> getFms();

    @GET("fms/{id}/playable")
    Call<PlayableLinkModel> getId(@Path("id") String id);






}
