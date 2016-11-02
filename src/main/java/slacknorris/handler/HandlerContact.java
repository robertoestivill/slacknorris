package slacknorris.handler;

import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import slacknorris.model.lambda.LambdaRequest;
import slacknorris.model.lambda.LambdaResponse;

import static slacknorris.config.Redirect.CONTACTED;
import static slacknorris.config.Redirect.ERROR;

public class HandlerContact extends HandlerBase {

  @Override public LambdaResponse handle(LambdaRequest request) throws Exception {
    Map<String, String> result = parseForm(request.body);
    if (result == null ||
        isNullOrEmpty(result.get("name")) ||
        isNullOrEmpty(result.get("email")) ||
        isNullOrEmpty(result.get("message"))) {
      return redirect(ERROR);
    }
    sendEmail(result);
    return redirect(CONTACTED);
  }

  private void sendEmail(Map<String, String> params) throws Exception {
    Properties emailProperties = new Properties();
    emailProperties.put("mail.smtp.auth", "true");
    emailProperties.put("mail.smtp.starttls.enable", "true");
    emailProperties.put("mail.smtp.host", properties.get("email.server"));
    emailProperties.put("mail.smtp.port", properties.get("email.port"));

    Session session = Session.getInstance(emailProperties,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(
                String.valueOf(properties.get("email.username")),
                String.valueOf(properties.get("email.password")));
          }
        });

    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress("SlackNorris API <robertoestivill@gmail.com>"));
    message.setRecipients(
        Message.RecipientType.TO,
        InternetAddress.parse("robertoestivill@gmail.com"));
    message.setSubject("[SlackNorris] Contact from '" + params.get("name") + "'");
    message.setText("Email: " + params.get("email") + "\n\n" +
        "Name: " + params.get("name") + "\n\n" +
        "Message: \n" + params.get("message"));

    Transport.send(message);
  }
}