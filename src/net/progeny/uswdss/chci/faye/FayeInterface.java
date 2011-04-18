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

import net.progeny.uswdss.chci.NavFormatter;

public class FayeInterface {

  public static void main(String args[]) {
    FayeInterface faye = new FayeInterface();
    faye.setFayeServer("localhost", "9292");
    faye.setHeading("1");
    faye.setSpeed("1");
    faye.setCourse("1");
    faye.setLatitude("1");
    faye.setLongitude("1");
    faye.setStale("false");
    System.exit(0);
  }

  private String host;
  private String port;
  private NavFormatter nav;

  public FayeInterface(String host, String port){
    setFayeServer(host, port);
    nav = new NavFormatter();
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
    String data = "{\"hdg\":\"" + nav.formatHeading(value) + "\"}";
    return (sendHTTPRequest(channel, data));
  }
  public boolean setCourse(String value) {
    String channel = "/chci/nav";
    String data = "{\"crs\":\"" + nav.formatCourse(value) + "\"}";
    return (sendHTTPRequest(channel, data));
  }
  public boolean setSpeed(String value) {
    String channel = "/chci/nav";
    String data = "{\"spd\":\"" + nav.formatSpeed(value) + "\"}";
    return (sendHTTPRequest(channel, data));
  }
  public boolean setLatitude(String value) {
    String channel = "/chci/nav";
    String data = "{\"lat\":\"" + nav.formatLatitude(value) + "\"}";
    return (sendHTTPRequest(channel, data));
  }
  public boolean setLongitude(String value) {
    String channel = "/chci/nav";
    String data = "{\"lon\":\"" + nav.formatLongitude(value) + "\"}";
    return (sendHTTPRequest(channel, data));
  }
  public boolean setNav(String hdg, String crs, String spd, String lat, String lon) {
    String channel = "/chci/nav";
    String data =  "{";
    data +=          "\"hdg\":\"" + nav.formatHeading(hdg) + "\",";
    data +=          "\"crs\":\"" + nav.formatCourse(crs) + "\",";
    data +=          "\"spd\":\"" + nav.formatSpeed(spd) + "\",";
    data +=          "\"lat\":\"" + nav.formatLatitude(lat) + "\",";
    data +=          "\"lon\":\"" + nav.formatLongitude(lon) + "\"";
    data +=        "}";
    return (sendHTTPRequest(channel, data));
  }
  public boolean setStale(String value) {
    if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")){
      value = "false";
    }
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
