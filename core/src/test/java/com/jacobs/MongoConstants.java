package com.jacobs;

/**
 * Created by lichao on 2017/5/12.
 */
public class MongoConstants {

  public final static String LIKE_COLLECTION = "like";
  public final static String NOTICE_STATE_COLLECTION = "notice_state";
  public final static String TIC_MON_COLLECTION = "tic_mon_collection";
  public final static String TIC_MON_CLICK_COLLECTION = "tic_mon_click";
  public final static String WATCH_GEO_COLLECTION = "watch_geo";


  public final static String STEPS = "steps";
  public final static String ADDRESS = "address";

  //notice_state columns
  public final static String NOTICE_WWID = "wwid";
  public final static String NOTICE_STATE = "state";
  public final static String NOTICE_TIMESTAMP = "timestamp";

  //TicMon columns
  public final static String TIC_MON_WWID = "wwid";
  public final static String TIC_MON_NAME = "name";
  public final static String TIC_MON_URL = "img_url";
  public final static String TIC_MON_LEVEL = "level";
  public final static String TIC_MON_MINSTEPS = "min_steps";
  public final static String TIC_MON_WATCH_FACE_ID = "watch_face_id";

  //tic_mon_click columns
  public final static String TIC_MON_CLICK_WWID = "wwid";
  public final static String TIC_MON_CLICK_TIC_MON_ID = "tic_mon_id";
  public final static String TIC_MON_CLICK_TIMESTAMP = "timestamp";

  //steps
  public final static String STEPS_WWID = "wwid";
  public final static String STEPS_COUNT = "count";
  public final static String STEPS_IDENTITY = "identity";
  public final static String STEPS_TIMESTAMP = "timestamp";

  //watch_geo
  public final static double GEO_MAX_DISTANCE = 5.0 / 6371.0;//5 Km
  public final static String GEO_NEARBY_USERS_QUERY = "{'coordinate':{$near: [%f, %f],spherical: true, $maxDistance: %f}}";
  public final static String GEO_WWID = "wwid";
  public final static String GEO_WATCH_DEVICE_ID = "watch_device_id";
  public final static String GEO_STEPS = "steps";
  public final static String GEO_IDENTITY = "identity";
  public final static String GEO_TIMESTAMP = "timestamp";
  public final static String GEO_COORDINATE = "coordinate";
  public final static String GEO_COORDINATE_LAT = "latitude";
  public final static String GEO_COORDINATE_LNG = "longitude";
}
