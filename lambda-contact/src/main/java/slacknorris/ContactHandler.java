package slacknorris;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.net.URLDecoder;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ContactHandler {

  static final String EMAIL_USERNAME = "YOUR ACCOUNT EMAIL ADDRESS";
  static final String EMAIL_PASSWORD = "YOUR ACCOUNT PASSWORD";

  static final String REDIRECT_HOME = "http://robertoestivill.com/slacknorris";
  static final String REDIRECT_ERROR = REDIRECT_HOME + "/error.html";
  static final String REDIRECT_SUCCESS = REDIRECT_HOME + "/contacted.html";

  private LambdaLogger logger;

  public static class ContactRequest {
    public String email;
    public String name;
    public String message;
  }

  public static class ContactRedirect {
    public String location;

    public ContactRedirect(String s) {
      this.location = s;
    }
  }

  public ContactRedirect handle(ContactRequest req, Context context) throws Exception {
    logger = context.getLogger();
    logger.log( "#############################" );
    if (req == null || req.name == null || req.email == null || req.message == null) {
      return new ContactRedirect(REDIRECT_ERROR);
    }

    req.email = URLDecoder.decode(req.email, "UTF-8");
    req.name = URLDecoder.decode(req.name, "UTF-8");
    req.message = URLDecoder.decode(req.message, "UTF-8");

    try {
      sendEmail(req);
    } catch (MessagingException e) {
      return new ContactRedirect(REDIRECT_ERROR);
    }
    return new ContactRedirect(REDIRECT_SUCCESS);
  }

  private void sendEmail(ContactRequest req) throws Exception {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
          }
        });

    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress("SlackNorris API <api@slacknorris.com>"));
    message.setRecipients(
        Message.RecipientType.TO,
        InternetAddress.parse("robertoestivill@gmail.com"));
    message.setSubject("[SlackNorris] Contact from '" + req.name + "'");
    message.setText("Email: " + req.email + "\n\n" +
        "Name: " + req.name + "\n\n" +
        "Message: \n" + req.message);

    Transport.send(message);
  }
}