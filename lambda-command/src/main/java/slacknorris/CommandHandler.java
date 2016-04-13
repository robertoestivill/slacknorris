package slacknorris;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import com.google.gson.Gson;

import java.net.URLDecoder;
import java.util.LinkedList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import slacknorris.model.icndb.*;
import slacknorris.model.slack.*;

public class CommandHandler {

  static final String SLACK_ORIGIN_TOKEN = "YOUR SLACK ORIGIN TOKEN";
  static final Gson GSON = new Gson();

  private LambdaLogger logger;

  public static class CommandRequest {
    public String token;
    public String text;
  }

  private boolean isNullOrEmpty(String s) {
    return s == null || s.trim().length() == 0;
  }

  boolean isId(String string) {
    try {
      Integer.parseInt(string);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Object handle(CommandRequest req, Context context) throws Exception {
    logger = context.getLogger();

    if (req == null || isNullOrEmpty(req.token) || !req.token.equals(SLACK_ORIGIN_TOKEN)) {
      logger.log("Unauthorized");
      return "Unauthorized";
    }

    logger.log( "text: " + req.text );

    if (isNullOrEmpty(req.text)) {
      return generate("random", null);
    }

    req.text = req.text.trim();

    if (req.text.equals("help")) {
      return GSON.toJson(help());
    }
    if (req.text.equals("about")) {
      return GSON.toJson(about());
    }

    req.text = URLDecoder.decode(req.text, "UTF-8");
    String[] params = req.text.trim().split(" ");

    if (params.length == 1) {
      if (isId(params[0])) {
        return generate(params[0], null);
      } else {
        return generate("random", params[0]);
      }
    }

    if (params.length == 2) {
      if (isId(params[0])) {
        return generate(params[0], params[1]);
      }
    }
    return GSON.toJson(help());
  }

  private String generate(String path, String name) {
    logger.log("Generating quote for: [" + path + "," + name + "]");

    String uri = "/jokes/" + path + "?escape=javascript";
    if (name != null) {
      uri = uri.concat("&firstName=" + name + "&lastName=");
    }

    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url("http://api.icndb.com" + uri)
        .build();

    Response response;
    try {
      response = client.newCall(request).execute();
    } catch (Exception e) {
      logger.log("Request to IDNDB unsuccessful " + e.getMessage());
      return GSON.toJson(error());
    }

    if (!response.isSuccessful()) {
      logger.log("Request to IDNDB unsuccessful");
      return GSON.toJson(error());
    }

    logger.log("Request to IDNDB successful");

    IcndbResponse icndb = GSON.fromJson(response.body().charStream(), IcndbResponse.class);
    if (icndb.type.equalsIgnoreCase("success")) {
      String quote = icndb.value.joke;
      quote = quote.replace("  ", " ");
      if (name != null) {
        quote = quote.replace(name, "*" + name + "*");
      }

      SlackAttachment attachment = new SlackAttachment();
      attachment.markdown = new LinkedList<>();
      attachment.markdown.add("text");
      attachment.title = "Quote #" + icndb.value.id;
      attachment.text = " \n" + quote + "\n ";

      SlackResponse res = new SlackResponse();
      res.attachments = new LinkedList<>();
      res.attachments.add(attachment);
      res.type = "in_channel";

      return GSON.toJson(res);
    }
    return GSON.toJson(error());
  }

  private SlackResponse error() {
    SlackResponse res = new SlackResponse();
    res.text = "Oops...\n"
        + "There was an error retrieving the quote for the given parameters.\n"
        + "Please double check and try again.";
    res.type = "ephemeral";
    return res;
  }

  private SlackResponse help() {
    String help = ""
        + "SlackNorris command help\n"
        + "\n"
        + "\t`/chucknorris`\n"
        + "\t\tPrints a random quote\n"
        + "\n"
        + "\t`/chucknorris [name]`\n"
        + "\t\tPrints a random quote and replaces *Chuck Norris* with *[name]*.\n"
        + "\n"
        + "\t`/chucknorris [id] `\n"
        + "\t\tPrints the quote for the given *[id]*\n"
        + "\n"
        + "\t`/chucknorris [id] [name]`\n"
        + "\t\tPrints the quote for the given *[id]* and replaces *Chuck Norris* with the given *[name]*\n"
        + "\n"
        + "\t`/chucknorris help`\n"
        + "\t\tPrints the help information\n"
        + "\n"
        + "\t`/chucknorris about`\n"
        + "\t\tPrints about information\n"
        + "\n";

    SlackResponse res = new SlackResponse();
    res.type = "ephemeral";
    res.text = help;
    return res;
  }

  private SlackResponse about() {
    SlackField f1 = new SlackField();
    f1.title = "Twitter";
    f1.value = "<http://twitter.com/robertoestivill|@robertoestivill>";
    f1.isShort = true;

    SlackField f2 = new SlackField();
    f2.title = "Email";
    f2.value = "<mailto:robertoestivill@gmail.com|robertoestivill@gmail.com>";
    f2.isShort = true;

    SlackAttachment a = new SlackAttachment();
    a.fields = new LinkedList<>();
    a.fields.add(f1);
    a.fields.add(f2);
    a.title = "About SlackNorris";
    a.text = "With ♥ by Roberto Estivill";

    SlackResponse res = new SlackResponse();
    res.attachments = new LinkedList<>();
    res.attachments.add(a);
    res.type = "ephemeral";

    return res;
  }

  public static void main(String[] args) throws Exception {
    CommandRequest req = new CommandRequest();
    req.token = SLACK_ORIGIN_TOKEN;
    req.text = null;
    System.out.println(new CommandHandler().handle(req, null));
  }
}