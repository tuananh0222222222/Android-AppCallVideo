package com.demo.appvideov2.utillities;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static  final  String KEY_COLLECTION_USER = "users";
    public static  final  String KEY_FIRST_NAME = "first_name";
    public static  final  String KEY_LAST_NAME = "last_name";
    public static  final  String KEY_EMAIL= "email";
    public static  final  String KEY_PASSWORD = "password";
    public  static  final  String KEY_PREFERENCE_NAME = "videoMeettingPreference";
    public  static  final  String KEY_IS_SIGNED_IN = "isSignedIn";
    public  static  final  String KEY_USER_ID = "user_id";
    public  static  final  String KEY_FCM_TOKEN = "fcm_token";
    public  static  final  String REMOTE_MSG_AUTHORIZATION = "authorization";
    public  static  final  String REMOTE_MSG_CONTENT_TYPE = "Content-type";
    public  static  final  String REMOTE_MSG_TYPE ="type";
    public  static  final  String REMOTE_MSG_MEETING_TYPE ="meetingtype";
    public  static  final  String REMOTE_MSG_INVITATION ="invitation";
    public  static  final  String REMOTE_MSG_INVITER_TOKEN ="inviterToken";

    public  static  final  String REMOTE_MSG_DATA ="data";
    public  static  final  String REMOTE_MSG_REGISTRATION_IDS ="registration_ids";

    public  static HashMap<String,String> getRemoteMessageHeader(){
        HashMap<String,String> headers  = new HashMap<>();
        headers.put(
                Constants.REMOTE_MSG_AUTHORIZATION,
                "key=BJS71Cn7znNw01rLPi-J5IWMNZkugRrkmkLWrnsLLPrXOMm1LgtEc3h4Wi1dAFStjHZODDBvkVQF3NSy5UiNyxU"
        );
        headers.put(Constants.REMOTE_MSG_CONTENT_TYPE,"application/json");
        return  headers;
    }



}

