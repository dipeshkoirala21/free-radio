package onlineradio.zeronebits.com.freeradio.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import onlineradio.zeronebits.com.freeradio.Fm;

public class FMCategory implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("fms")
    @Expose
    private List<Fm> fms;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Fm> getFms() {
        return fms;
    }

    public void setFms(List<Fm> fms) {
        this.fms = fms;
    }
}
