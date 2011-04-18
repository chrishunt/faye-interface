package net.progeny.uswdss.chci;

import java.text.NumberFormat;
import java.text.DecimalFormat;

public class NavFormatter {
  private NumberFormat threeDOTone, oneDOTone, two;

  public NavFormatter(){
    threeDOTone = new DecimalFormat("000.0");
    oneDOTone = new DecimalFormat("0.0");
    two = new DecimalFormat("00");
  }

  public String formatHeading(String value){
    try{
      double hdg = rotateDegrees(Double.parseDouble(value));
      return threeDOTone.format(hdg);
    } catch (Exception e){
      return value;
    }
  }

  public String formatCourse(String value){
    try{
      double crs = rotateDegrees(Double.parseDouble(value));
      return threeDOTone.format(crs);
    } catch (Exception e){
      return value;
    }
  }

  public String formatSpeed(String value){
    try{
      return oneDOTone.format(Double.parseDouble(value));
    } catch (Exception e){
      return value;
    }
  }

  public String formatLatitude(String value){
    try{
      return buildNavString("latitude",Double.parseDouble(value));
    } catch (Exception e) {
      return value;
    }
  }

  public String formatLongitude(String value){
    try{
      return buildNavString("longitude",Double.parseDouble(value));
    } catch (Exception e) {
      return value;
    }
  }

  // PRIVATE METHODS
  
  private double rotateDegrees(double degrees){
    while (degrees < 0){
      degrees = degrees + 360;
    }
    while (degrees > 360){
      degrees = degrees - 360;
    }
    return degrees;
  }

  private String buildNavString(String type, double value){
    String pole = "";
    // deturmine polarity
    if (value < 0){
      value = value - (2.0 * value);
      if (type.equalsIgnoreCase("latitude") || 
          type.equalsIgnoreCase("lat")){
        pole = "S";
      } else {
        pole = "W";
      }
    } else {
      if (type.equalsIgnoreCase("latitude") || 
          type.equalsIgnoreCase("lat")){
        pole = "N";
      } else {
        pole = "E";
      }
    }
    // parse coordinates
    int degrees = (int) value;
    double temp = (value - degrees) * 60.0;
    int minutes = (int) temp;
    int seconds = (int) ((temp - minutes) * 60.0);
    // create string
    String degreesString = two.format(degrees);
    String minutesString = two.format(minutes);
    String secondsString = two.format(seconds);

    return degreesString + "&#176; " +
           minutesString + "&#39; " +
           secondsString + "&#34;" +
           pole;
  }
}
