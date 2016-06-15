package com.kun.station.network;

public class NetworkApi {
//    public static final String VERSION_MARK = "/v1";
//    public static final String REGISTER_URL = Constants.PROD_SERVER + VERSION_MARK + "/app/register";
//    public static final String CHECK_INVITATION_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/user/checkinvitation";
//    public static final String LOGIN_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/user/login";
//    public static final String GET_PROFILE_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/user/profile";
//    public static final String GET_TAG_LIST_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/tags/list";
//    public static final String GET_QI_NIU_TOKEN_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/app/qiniu_token";
//    public static final String GET_VERIFICATION_CODE_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/user/invitation";
//    public static final String GET_VERSION = Constants.PROD_SERVER + VERSION_MARK + "/%s/app/version";
//    public static final String REPORT_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/user/report";
//    public static final String CHECK_USER_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/user/check";
//    public static final String CHECK_TAG_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/user/check_tags";
//    public static final String Get_BY_Like_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/user/likees";
//    public static final String Get_Like_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/user/likers";
//    public static final String Relation_Chat_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/relationship/chat";
//    public static final String Tag_Aggregation_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/tags/show";
//    public static final String GET_MATCH_URL = Constants.PROD_SERVER + VERSION_MARK + "/%s/relationship/search";
//    public static final String EXCUTE_LIKE = Constants.PROD_SERVER + VERSION_MARK + "/%s/relationship/alllike";
//    public static final String GET_FRIEND = Constants.PROD_SERVER + VERSION_MARK + "/%s/user/friends";
//    public static final String DELETE_FRIEND = Constants.PROD_SERVER + VERSION_MARK + "/%s/user/delfriend";
//
//    public static void register(Response.Listener listener){
//        Application.getNetworkManager().getPostResultString(REGISTER_URL, listener);
//    }
//
//    public static void checkVerificationCode(String code, Response.Listener listener){
//        String url = String.format(CHECK_INVITATION_URL, Application.appId);
//        Map<String, String> params = new HashMap<>();
//        params.put("invitation_code", code);
//        Application.getNetworkManager().getResultString(url, params, listener);
//    }
//
//    public static void login(String type, String openId, String accessToken,
//                             Response.Listener listener, Response.ErrorListener errorListener){
//        String url = String.format(LOGIN_URL, Application.appId);
//        Map<String, String> params = new HashMap<>();
//        params.put("sso_vendor", type);
//        params.put("open_id", openId);
//        params.put("access_token", accessToken);
////        params.put("invitation_code", "hahahaha");
////        if(!TextUtils.isEmpty(Application.verificationCode)){
////            params.put("invitation_code", Application.verificationCode);
////        }
//        Application.getNetworkManager().getPostResultClass(url, params, LoginResultModel.class, listener, errorListener);
//    }
//
//    public static void getProfile(long userId, boolean addMeData, Response.Listener<PersonalInfoModel> listener){
//        String url = String.format(GET_PROFILE_URL, Application.appId);
//        Map<String, String> params = new HashMap<>();
//        params.put("user_id", String.valueOf(userId));
//        if(addMeData){
//            params.put("me", "1");
//        }
//        Application.getNetworkManager().getResultClass(url, params, PersonalInfoModel.class, listener);
//    }
//
//    public static void getSimpleProfile(long userId, Response.Listener<PersonalInfoModel> listener){
//        String url = String.format(GET_PROFILE_URL, Application.appId);
//        Map<String, String> params = new HashMap<>();
//        params.put("user_id", String.valueOf(userId));
//        params.put("brief", "1");
//        params.put("me", "0");
//        Application.getNetworkManager().getResultClass(url, params, PersonalInfoModel.class, listener);
//    }
//
//    public static void getBasicInfo(long userId, Response.Listener<PersonalInfoModel> listener){
//        String url = String.format(GET_PROFILE_URL, Application.appId);
//        Map<String, String> params = new HashMap<>();
//        params.put("user_id", String.valueOf(userId));
//        params.put("brief", "1");
//        params.put("me", "0");
//        Application.getNetworkManager().getResultClass(url, params, PersonalInfoModel.class, listener);
//    }
//
//    public static void updateProfile(Map<String, String> params, Response.Listener listener){
//        if(params == null){
//            return;
//        }
//        String url = String.format(GET_PROFILE_URL, Application.appId);
//        params.put("user_id", String.valueOf(Application.userId));
//        Application.getNetworkManager().getPostResultClass(url, params, UpdateProfileMode.class, listener, null);
//    }
//
//    public static void getTagList(String category, long time, Response.Listener listener){
//        String url = String.format(GET_TAG_LIST_URL, Application.appId);
//        Map<String, String> params = new HashMap<>();
//        params.put("category", category);
//        params.put("timestamp", String.valueOf(time));
//        params.put("all", "1");
//        Application.getNetworkManager().getResultClass(url, params, TagListModel.class, listener);
//    }
//
//    public static void getQiNiuToken(Response.Listener listener){
//        String url = String.format(GET_QI_NIU_TOKEN_URL, Application.appId);
//        Application.getNetworkManager().getResultString(url, listener);
//    }
//
//    public static void getVerificationCode(Response.Listener listener){
//        String url = String.format(GET_VERIFICATION_CODE_URL, Application.appId);
//        Application.getNetworkManager().getResultString(url, listener);
//    }
//
//    public static void getVersion(Response.Listener listener){
//        String url = String.format(GET_VERSION, Application.appId);
//        Application.getNetworkManager().getResultString(url, listener);
//    }
//
//    public static void report(long userId, String content, String misc,
//                              String imageUrl, Response.Listener listener){
//        String url = String.format(REPORT_URL, Application.appId);
//        Map<String, String> params = new HashMap<>();
//        params.put("user_id", String.valueOf(userId));
//        if(!TextUtils.isEmpty(content)){
//            params.put("content", content);
//        }
//        if(!TextUtils.isEmpty(misc)){
//            params.put("misc", misc);
//        }
//        if(!TextUtils.isEmpty(imageUrl)){
//            params.put("screenshot_url", imageUrl);
//        }
//        Application.getNetworkManager().getPostResultString(url, params, listener);
//    }
//
//    public static void checkUser(Response.Listener listener, Response.ErrorListener errorListener){
//        String url = String.format(CHECK_USER_URL, Application.appId);
//        Application.getNetworkManager().getResultString(url, null, listener, errorListener);
//    }
//
//    public static void getCheckedTag(Response.Listener<CheckTagModel> listener){
//        String url = String.format(CHECK_TAG_URL, Application.appId);
//        Application.getNetworkManager().getResultClass(url, CheckTagModel.class, listener);
//    }
//
//    public static  void getMatchList(Serach serach,Response.Listener listener,Response.ErrorListener errorListener){
//         Map<String,String> params=new HashMap<>();
//        if(serach.getGender()!=null){
//            params.put("target_gender",String.valueOf(serach.getGender()));
//        }
//        if(!"-1".equals(serach.getMax_distance()) && Application.getInstance().getLocation()[0] < 0.01
//                && Application.getInstance().getLocation()[1] < 0.01){
//            Toast.makeText(Application.getInstance(), R.string.location_fail, Toast.LENGTH_SHORT).show();
//        }
//        params.put("latitude", String.valueOf(Application.getInstance().getLocation()[0]));
//        params.put("longitude", String.valueOf(Application.getInstance().getLocation()[1]));
//        if(!TextUtils.isEmpty(serach.getMin_age())){
//
//            params.put("min_age", Integer.parseInt(serach.getMin_age())+"");
//        }
//        if(!TextUtils.isEmpty(serach.getMax_age())){
//            params.put("max_age",Integer.parseInt(serach.getMax_age())+"");
//        }
//        if(!TextUtils.isEmpty(serach.getMin_distance())){
//            params.put("min_distance",serach.getMin_distance());
//        }
//        if(!TextUtils.isEmpty(serach.getMax_distance())){
//            params.put("max_distance",serach.getMax_distance());
//        }
//        String url = String.format(GET_MATCH_URL, Application.appId);
//        Application.getNetworkManager().getResultClass(url, params, MatchModel.class, listener, errorListener);
//    }
//
//    public static void excuteLike(String result_batch_id, long ids,boolean islike, Response.Listener<String> listener){
//        Map<String,String> params=new HashMap<>();
//        params.put("result_batch_id",result_batch_id);
//        if(islike){
//            params.put("likes",String.valueOf(ids));
//            params.put("dislikes","");
//        }else {
//            params.put("dislikes",String.valueOf(ids));
//            params.put("likes","");
//        }
//        String url = String.format(EXCUTE_LIKE, Application.appId);
//        Application.getNetworkManager().getPostResultString(url, params, listener);
//    }
//
//    public static void addChatRelation(long to_user_id, String result_batch_id, Response.Listener<String> listener){
//        String url = String.format(Relation_Chat_URL, Application.appId);
//        Map<String, String> params = new HashMap<>();
//        params.put("result_batch_id", result_batch_id);
//        params.put("to_user_id", String.valueOf(to_user_id));
//        Application.getNetworkManager().getPostResultString(url, params, listener);
//    }
//
//    public static void getLikeList(Response.Listener<FriendListModel> listener){
//        String url = String.format(Get_Like_URL, Application.appId);
//        Application.getNetworkManager().getResultClass(url, FriendListModel.class, listener);
//    }
//
//    public static void getByLikeList(Response.Listener<FriendListModel> listener){
//        String url = String.format(Get_BY_Like_URL, Application.appId);
//        Application.getNetworkManager().getResultClass(url, FriendListModel.class, listener);
//    }
//
//    public static void getTagAggregationList(long tagId, int pageIndex, boolean isNear,
//             Response.Listener<ArrayList<TagAggregationModel>> listener, Response.ErrorListener mErrorListener){
//        String url = String.format(Tag_Aggregation_URL, Application.appId);
//        Map<String, String> mParams = new HashMap<>();
//        mParams.put("tag_id", String.valueOf(tagId));
//        mParams.put("p", String.valueOf(pageIndex));
//        mParams.put("type", isNear ? "nearest" : "hottest");
//        if(isNear){
//            LocalShared mShared = LocalShared.getInstance();
//            mParams.put("latitude", mShared.getLatitude());
//            mParams.put("longitude", mShared.getLongitude());
//        }
//        Application.getNetworkManager().getResultClass(url, mParams,
//                new TypeToken<ArrayList<TagAggregationModel>>(){}.getType(), listener, mErrorListener);
//    }
//
//    public static void getFriends(Response.Listener<FriendListModel> listener){
//        String url = String.format(GET_FRIEND, Application.appId);
//        Application.getNetworkManager().getResultClass(url, FriendListModel.class, listener);
//    }
//
//    public static void delFriends(String to_user_id,Response.Listener<String> listener){
//        Map<String,String> params=new HashMap<>();
//        params.put("to_user_id",to_user_id);
//        String url = String.format(DELETE_FRIEND, Application.appId);
//        Application.getNetworkManager().getPostResultString(url, params, listener);
//    }
}
