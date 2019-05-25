
package onlineradio.zeronebits.com.freeradio;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIDatum  {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("fms")
    @Expose
    private List<Fm> fms=null;

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
