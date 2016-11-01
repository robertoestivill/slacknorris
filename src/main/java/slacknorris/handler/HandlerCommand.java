package slacknorris.handler;

import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.Map;
import okhttp3.OkHttpClient;
import slacknorris.model.icndb.IcndbResponse;
import slacknorris.model.lambda.LambdaRequest;
import slacknorris.model.lambda.LambdaResponse;
import slacknorris.model.slack.SlackAttachment;
import slacknorris.model.slack.SlackField;
import slacknorris.model.slack.SlackResponse;

import static com.amazonaws.util.StringUtils.isNullOrEmpty;
import static slacknorris.config.Constants.SLACK_ORIGIN_TOKEN;

public class HandlerCommand extends HandlerBase {

  @Override public LambdaResponse handle(LambdaRequest req) throws Exception {
    if (req == null || isNullOrEmpty(req.body)) {
      return badRequest();
    }

    Map<String, String> formParams = parseForm(req.body);

    if (formParams == null ||
        !formParams.containsKey("text") ||
        !formParams.containsKey("token") ||
        !SLACK_ORIGIN_TOKEN.equals(formParams.get("token"))) {
      return unauthorized();
    }

    String text = formParams.get("text");

    if (isNullOrEmpty(text)) {
      return ok(generate("random", null));
    }

    text = text.trim();

    if (text.equals("help")) {
      return ok(gson.toJson(help()));
    }
    if (text.equals("about")) {
      return ok(gson.toJson(about()));
    }

    text = URLDecoder.decode(text, "UTF-8");
    String[] params = text.trim().split(" ");

    if (params.length == 1) {
      if (isId(params[0])) {
        return ok(generate(params[0], null));
      } else {
        return ok(generate("random", params[0]));
      }
    }

    if (params.length == 2) {
      if (isId(params[0])) {
        return ok(generate(params[0], params[1]));
      }
    }
    return ok(gson.toJson(help()));
  }

  private String generate(String path, String name) {
    logger.log("Generating quote for: [" + path + "," + name + "]");

    String uri = "/jokes/" + path + "?escape=javascript";
    if (name != null) {
      uri = uri.concat("&firstName=" + name + "&lastName=");
    }

    OkHttpClient client = new OkHttpClient();
    okhttp3.Request request = new okhttp3.Request.Builder()
        .url("http://api.icndb.com" + uri)
        .build();

    okhttp3.Response response;
    try {
      response = client.newCall(request).execute();
    } catch (Exception e) {
      logger.log("Request to IDNDB unsuccessful " + e.getMessage());
      return gson.toJson(error());
    }

    if (!response.isSuccessful()) {
      logger.log("Request to IDNDB unsuccessful");
      return gson.toJson(error());
    }

    logger.log("Request to IDNDB successful");

    IcndbResponse icndb = gson.fromJson(response.body().charStream(), IcndbResponse.class);
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

      return gson.toJson(res);
    }
    return gson.toJson(error());
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

  boolean isId(String string) {
    try {
      Integer.parseInt(string);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}