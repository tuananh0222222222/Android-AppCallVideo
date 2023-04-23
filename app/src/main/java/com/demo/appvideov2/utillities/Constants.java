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
    public  static  final  String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public  static  final  String REMOTE_MSG_CONTENT_TYPE = "Content-Type";

    public  static  final  String REMOTE_MSG_TYPE ="type";
    public  static  final  String REMOTE_MSG_MEETING_TYPE ="meetingType";
    public  static  final  String REMOTE_MSG_INVITATION ="invitation";
    public  static  final  String REMOTE_MSG_INVITER_TOKEN ="inviterToken";
    public  static  final  String REMOTE_MSG_DATA ="data";
    public  static  final  String REMOTE_MSG_REGISTRATION_IDS ="registration_ids";

    public  static HashMap<String,String> getRemoteMessageHeader(){
        HashMap<String,String> headers  = new HashMap<>();
        headers.put(
                Constants.REMOTE_MSG_AUTHORIZATION,
                "key=AAAA1Dr4gh0:APA91bFJ5rvMzQ3HIU1LxKJ9xwl_T1ifEUpAZw4tlA1nxiGX4wnyhf_z3ehUI5PWApGYANp08yWvhBQy0FugDCmJwh3cdnyHBV1EGHBhxjyz-evkTy0TpNzPsiDW73QB9sWNQ4doVswd"
        );
        headers.put(Constants.REMOTE_MSG_CONTENT_TYPE,"application/json");
        return  headers;
    }



}

