package onlineradio.zeronebits.com.freeradio.model;

import com.google.gson.annotations.SerializedName;

public class PlayableLinkModel {

    @SerializedName("link")
    String playableUrl;


    public String getPlayableUrl() {
        return playableUrl;
    }
}
