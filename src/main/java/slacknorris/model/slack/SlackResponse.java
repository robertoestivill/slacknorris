package slacknorris.model.slack;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SlackResponse {

  @SerializedName("text")
  public String text;

  @SerializedName("response_type")
  public String type;

  @SerializedName("attachments")
  public List<SlackAttachment> attachments;
}
