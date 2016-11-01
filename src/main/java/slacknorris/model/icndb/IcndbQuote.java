package slacknorris.model.icndb;

import com.google.gson.annotations.SerializedName;

public class IcndbQuote {

  @SerializedName("id")
  public int id;

  @SerializedName("joke")
  public String joke;
}
