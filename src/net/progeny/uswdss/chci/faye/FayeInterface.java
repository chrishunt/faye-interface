package net.progeny.uswdss.chci.faye;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class FayeInterface {

  public static void main(String args[]) {
    FayeInterface faye = new FayeInterface();
    faye.setFayeServer("localhost", "9292");

    faye.setHeading("800");
    faye.setCourse("200");
    faye.setSpeed("300");
    faye.setLatitude("400");
    faye.setLongitude("500");

    faye.setStale("true");

    System.out.println(faye.setNav("1","2","3","4","5"));

    faye.setStale("false");
    faye.setStale("true");
    faye.setStale("false");

    System.exit(0);
  }

  private String host;
  private String port;

  public FayeInterface(String host, String port){
    setFayeServer(host, port);
  }

  public FayeInterface () {
    this("localhost", "9292");
  }

  public void setFayeServer(String host, String port){
    this.host = host;
    this.port = port;
  }

  // Update CHCI
  public boolean setHeading(String value) {
    String channel = "/chci/nav";
    String data = "{\"hdg\":\"" + value + "\"}";
    return (sendHTTPRequest(channel, data));
  }
  public boolean setCourse(String value) {
    String channel = "/chci/nav";
    String data = "{\"crs\":\"" + value + "\"}";
    return (sendHTTPRequest(channel, data));
  }
  public boolean setSpeed(String value) {
    String channel = "/chci/nav";
    String data = "{\"spd\":\"" + value + "\"}";
    return (sendHTTPRequest(channel, data));
  }
  public boolean setLatitude(String value) {
    String channel = "/chci/nav";
    String data = "{\"lat\":\"" + value + "\"}";
    return (sendHTTPRequest(channel, data));
  }
  public boolean setLongitude(String value) {
    String channel = "/chci/nav";
    String data = "{\"lon\":\"" + value + "\"}";
    return (sendHTTPRequest(channel, data));
  }
  public boolean setNav(String hdg, String crs, String spd, String lat, String lon) {
    String channel = "/chci/nav";
    String data =  "{";
    data +=          "\"hdg\":\"" + hdg + "\",";
    data +=          "\"crs\":\"" + crs + "\",";
    data +=          "\"spd\":\"" + spd + "\",";
    data +=          "\"lat\":\"" + lat + "\",";
    data +=          "\"lon\":\"" + lon + "\"";
    data +=        "}";
    return (sendHTTPRequest(channel, data));
  }
  public boolean setStale(String value) {
    String channel = "/chci/nav/stale";
    String data = "\"" + value + "\"";
    return (sendHTTPRequest(channel, data));
  }

  private boolean sendHTTPRequest(String channel, String data) {
    try{
      String url = "http://" + host + ":" + port + "/faye";
      String fayeData = "{\"channel\":\"" + channel + "\",\"data\":" + data + "}";

      List <NameValuePair> payload = new ArrayList <NameValuePair>();
      payload.add(new BasicNameValuePair("message", fayeData));

      HttpPost httppost = new HttpPost(url);
      httppost.setEntity(new UrlEncodedFormEntity(payload, HTTP.UTF_8));
      HttpClient httpclient = new DefaultHttpClient();
      HttpResponse response = httpclient.execute(httppost);
      HttpEntity entity = response.getEntity();

      int returnCode = response.getStatusLine().getStatusCode();
      return (returnCode == 200);
    } catch (Exception e) {
      return false;
    }
  }
}
