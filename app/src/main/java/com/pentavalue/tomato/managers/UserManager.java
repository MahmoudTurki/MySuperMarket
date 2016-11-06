package com.pentavalue.tomato.managers;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.pentavalue.tomato.R;
import com.pentavalue.tomato.business.BusinessManager;
import com.pentavalue.tomato.data.constants.Params;
import com.pentavalue.tomato.data.exception.AppException;
import com.pentavalue.tomato.data.helper.DataHelper;
import com.pentavalue.tomato.data.parsing.BuildingRequestParams;
import com.pentavalue.tomato.data.parsing.ParsingManager;
import com.pentavalue.tomato.data.response.BaseResponse;
import com.pentavalue.tomato.listeneres.OnBrowseSupermarketsListener;
import com.pentavalue.tomato.listeneres.OnChangePasswordListener;
import com.pentavalue.tomato.listeneres.OnForgotPasswordListener;
import com.pentavalue.tomato.listeneres.OnLocationsListener;
import com.pentavalue.tomato.listeneres.OnLoginListener;
import com.pentavalue.tomato.listeneres.OnProfileListener;
import com.pentavalue.tomato.listeneres.OnSignUpListener;
import com.pentavalue.tomato.model.Location;
import com.pentavalue.tomato.model.Supermarket;
import com.pentavalue.tomato.model.User;
import com.pentavalue.tomato.network.NetworkChecker;
import com.pentavalue.tomato.network.NetworkConstants;
import com.pentavalue.tomato.network.VolleyNetworkHelper;
import com.pentavalue.tomato.storage.SharedPrefConstants;
import com.pentavalue.tomato.storage.SharedPrefrencesDataLayer;
import com.pentavalue.tomato.utils.DateUtils;
import com.pentavalue.tomato.utils.LanguageUtils;
import com.pentavalue.tomato.utils.ValidatorUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Mahmoud Turki
 */
public class UserManager extends BusinessManager<User> {

    private static UserManager sInstance;
    private Context context;
    private List<Supermarket> supermarketsList = new ArrayList<Supermarket>();
    private Map<Integer/* Supermarket ID */, List<Supermarket>> mSupermarketMap;
    private long mLastIndexChecked;

    public static UserManager getInstance(Context context) {
        if (sInstance == null)
            sInstance = new UserManager(context);
        return sInstance;
    }

    private UserManager(Context context) {
        super(User.class);
        this.context = context;
    }

    public User getUser() {
        String userJson = SharedPrefrencesDataLayer.getStringPreferences(context, SharedPrefConstants.USER_OBJECT, null);
        if (userJson == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(userJson, User.class);
    }

    public User getUserTokens() {
        String userJson = SharedPrefrencesDataLayer.getStringPreferences(context, SharedPrefConstants.USER_TOKEN, null);
        if (userJson == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(userJson, User.class);
    }

    public void saveUserLoginTokens(User user) {
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        SharedPrefrencesDataLayer.saveStringPreferences(context, SharedPrefConstants.USER_TOKEN, userJson);
    }

    public void saveUserProfile(User user) {
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        SharedPrefrencesDataLayer.saveStringPreferences(context, SharedPrefConstants.USER_OBJECT, userJson);
    }

    public boolean logout() {
        return SharedPrefrencesDataLayer.clearSharedPreferences(context);
    }

    public List<Supermarket> getSupermarketsList() {
        return supermarketsList;
    }

    public void setSupermarketsList(List<Supermarket> supermarketsList) {
        this.supermarketsList.addAll(supermarketsList);
    }

    private void addSupermarketsToMap(List<Supermarket> list) {
        if (list != null && list.size() > 0) {
            for (Supermarket obj : list) {
                // False 0 = messages received, True 1 = messages sent
                if (mSupermarketMap.containsKey(obj.getId())) {
                    mSupermarketMap.get(obj.getId()).add(obj);
                } else {
                    List<Supermarket> tmpList = new ArrayList<Supermarket>();
                    tmpList.add(obj);
                    mSupermarketMap.put(obj.getId(), tmpList);
                }
            }
        }
    }

    private void addSupermarketsToMap(Supermarket obj) {
        // False 0 = messages received, True 1 = messages sent
        if (mSupermarketMap.containsKey(obj.getId())) {
            mSupermarketMap.get(obj.getId()).add(obj);
        } else {
            List<Supermarket> tmpList = new ArrayList<Supermarket>();
            tmpList.add(obj);
            mSupermarketMap.put(obj.getId(), tmpList);
        }
    }

    private long getLastIndexChecked() {
        if (mLastIndexChecked == 0)
            mLastIndexChecked = SharedPrefrencesDataLayer.getLongPerferences(context, SharedPrefConstants.LAST_PAGE_INDEX_CHECKED_KEY, 0L);
        return mLastIndexChecked;
    }

    public void saveLastIndexChecked(long pageIndex) {
        mLastIndexChecked = pageIndex;
        SharedPrefrencesDataLayer.saveLongPreferences(context, SharedPrefConstants.LAST_PAGE_INDEX_CHECKED_KEY,
                mLastIndexChecked);
    }

    /**
     * Login API
     *
     * @param user
     */
    private void asyncRequestLogin(final User user) {
        // pass third argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, NetworkConstants.LOGIN_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseSpecificServerResponse(response.toString());
                    User entity = DataHelper.deserialize(baseResponse.getData(), User.class);
                    if (!ValidatorUtils.isEmpty(entity.getAccessToken())) {
                        saveUserLoginTokens(entity);
                        notifyEntityReceviedSuccess(entity, OnLoginListener.class);
                    } else {
                        notifyRetrievalException(new AppException(AppException.WB_LOGIN_INVALID_GRANT_EXCEPTION), OnLoginListener.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    notifyRetrievalException(AppException.getAppException(e), OnLoginListener.class);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnLoginListener.class);
            }
        }) {
            @Override
            public byte[] getBody() {
                String httpPostBody = BuildingRequestParams.getLoginBody(user);
                // usually you'd have a field with some values you'd want to escape, you need to do it yourself if overriding getBody. here's how you do it
                try {
                    httpPostBody = httpPostBody + "&randomFieldFilledWithAwkwardCharacters=" + URLEncoder.encode("{{%stuffToBe Escaped/", "UTF-8");
                } catch (UnsupportedEncodingException exception) {
                    Log.e("ERROR", "exception", exception);
                    // return null and don't pass any POST string if you encounter encoding error
                    return null;
                }
                return httpPostBody.getBytes();
            }
        };
        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);
    }

    /**
     * SignUp API
     *
     * @param user
     */
    private void asyncRequestSignUp(final User user) {
        // pass third argument as "null" for GET requests
        final JSONObject obj = new JSONObject(BuildingRequestParams.test(user));
        System.out.println("JSON1: " + obj.toString());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, NetworkConstants.REGISTRATION_URL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response1: " + response);
                System.out.println("Response2: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        notifyEntityReceviedSuccess(baseResponse.getData(), OnSignUpListener.class);
                    } else if (statusResponse == BaseResponse.STATUS_WEBSERVICE_VALIDATION_RULE_ERROR) {
                        notifyRetrievalException(new AppException(AppException.WB_REGISTRATION_VALIDATION_EXCEPTION), OnSignUpListener.class);
                    } else if (statusResponse == BaseResponse.STATUS_WEBSERVICE_PARAM_ERROR) {
                        notifyRetrievalException(new AppException(AppException.WB_PARAM_EXCEPTION), OnSignUpListener.class);
                    } else {
                        notifyRetrievalException(new AppException(AppException.UNKNOWN_EXCEPTION), OnSignUpListener.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    notifyRetrievalException(AppException.getAppException(e), OnSignUpListener.class);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnSignUpListener.class);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);
    }

    /**
     * Change Password API
     *
     * @param user
     */
    private void asyncRequestChangePassword(final User user) {
        // pass third argument as "null" for GET requests
        final JSONObject obj = new JSONObject(BuildingRequestParams.getChangePasswordParams(user));
        System.out.println("JSON1: " + obj.toString());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, NetworkConstants.CHANGE_PASSWORD_URL, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        User cachedUser = getUser();
                        cachedUser.setPassword(user.getNewPassword());
                        cachedUser.setConfirmPassword(user.getConfirmPassword());
                        saveUserProfile(cachedUser);
                        notifyEntityReceviedSuccess(baseResponse.getData(), OnChangePasswordListener.class);
                    } else if (statusResponse == BaseResponse.STATUS_WEBSERVICE_VALIDATION_RULE_ERROR) {
                        notifyRetrievalException(new AppException(AppException.WB_CHANGE_PASSWORD_VALIDATION_EXCEPTION), OnChangePasswordListener.class);
                    } else if (statusResponse == BaseResponse.STATUS_WEBSERVICE_PARAM_ERROR) {
                        notifyRetrievalException(new AppException(AppException.WB_PARAM_EXCEPTION), OnChangePasswordListener.class);
                    } else {
                        notifyRetrievalException(new AppException(AppException.UNKNOWN_EXCEPTION), OnChangePasswordListener.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    notifyRetrievalException(AppException.getAppException(e), OnChangePasswordListener.class);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnSignUpListener.class);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization", "bearer " + getUserTokens().getAccessToken());
                return headerMap;
            }
        };
        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);
    }

    /**
     * Get Profile API
     */
    private void asyncRequestProfile() {
        // pass third argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, NetworkConstants.PROFILE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        User entity = DataHelper.deserialize(baseResponse.getData(), User.class);
                        String birthDate = DateUtils.getDateStr(DateUtils.truncateTime(entity.getBirthDate()));
                        entity.setBirthDate(birthDate);
                        saveUserProfile(entity);
                        System.out.println("List Size: " + entity.toString());
                        notifyEntityReceviedSuccess(entity, OnProfileListener.class);
                    } else {
                        notifyRetrievalException(new AppException(AppException.UNKNOWN_EXCEPTION), OnChangePasswordListener.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    notifyRetrievalException(AppException.getAppException(e), OnProfileListener.class);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnProfileListener.class);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization", "bearer " + getUserTokens().getAccessToken());
                return headerMap;
            }
        };
        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);
    }

    /**
     * Forget Password API
     */
    private void asyncRequestForgotPassword() {
        // pass third argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, NetworkConstants.FORGOT_PASSWORD_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    notifyVoidSuccess(OnForgotPasswordListener.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    if (e.getMessage().equalsIgnoreCase(context.getString(R.string.no_data)))
                        notifyRetrievalException(new AppException(AppException.SENDING_SUCCESSFUL), OnForgotPasswordListener.class);
                    else
                        notifyRetrievalException(new AppException(AppException.DATE_PARSING_EXCEPTION), OnForgotPasswordListener.class);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnForgotPasswordListener.class);
            }
        });
        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);
    }

    private void asyncRequestLocation() {
        // pass third argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, NetworkConstants.LOCATION_URL + "?" + Params.LANGUAGE + LanguageUtils.getInstance().getDeviceLanguage(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    List<Location> list = handleObjectListServerResponse(baseResponse, Location.class);
                    System.out.println("List Size: " + list.size());
                    notifyEntityListReceviedSuccess(list, OnLocationsListener.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    notifyRetrievalException(new AppException(AppException.DATE_PARSING_EXCEPTION), OnLocationsListener.class);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnLocationsListener.class);
            }
        });
        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);
    }


    private void asyncRequestEditProfile(User user) {
        // pass third argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, NetworkConstants.PROFILE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        User entity = DataHelper.deserialize(baseResponse.getData(), User.class);
                        String birthDate = DateUtils.getDateStr(DateUtils.truncateTime(entity.getBirthDate()));
                        entity.setBirthDate(birthDate);
                        saveUserProfile(entity);
                        System.out.println("List Size: " + entity.toString());
                        notifyEntityReceviedSuccess(entity, OnProfileListener.class);
                    } else {
                        notifyRetrievalException(new AppException(AppException.UNKNOWN_EXCEPTION), OnChangePasswordListener.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    notifyRetrievalException(AppException.getAppException(e), OnProfileListener.class);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnProfileListener.class);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization", "bearer " + getUserTokens().getAccessToken());
                return headerMap;
            }
        };
        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);
    }

    private void asyncRequestBrowseSupermarkets(Location location) {
        if (ValidatorUtils.isEmpty(location)) {
            notifyRetrievalException(new AppException(AppException.DATE_PARSING_EXCEPTION), OnBrowseSupermarketsListener.class);
            return;
        }
        // pass third argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, NetworkConstants.BROWSE_SUPERMARKETS_URL + "?"
                + Params.BrowseSupermarket.PAGE_INDEX + getLastIndexChecked()
                + "&" + Params.BrowseSupermarket.PAGE_SIZE + Params.BrowseSupermarket.PAGE_SIZE_VALUE
                + "&" + Params.BrowseSupermarket.CITY_ID + location.getId()
                + "&" + Params.BrowseSupermarket.AREA_ID + location.getAreaList().get(0).getId()
                + "&" + Params.LANGUAGE + LanguageUtils.getInstance().getDeviceLanguage(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    List<Supermarket> list = handleObjectListServerResponse(baseResponse, Supermarket.class);
                    if (!ValidatorUtils.isEmpty(list))
                        saveLastIndexChecked(getLastIndexChecked() + list.size());
                    setSupermarketsList(list);
                    addSupermarketsToMap(list);
                    System.out.println("List Size: " + list.size());
                    notifyVoidSuccess(OnBrowseSupermarketsListener.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    notifyRetrievalException(new AppException(AppException.DATE_PARSING_EXCEPTION), OnBrowseSupermarketsListener.class);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnBrowseSupermarketsListener.class);
            }
        });
        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);
    }

    public void performLoginRequest(User user) {
        if (NetworkChecker.isNetworkAvailable(context)) asyncRequestLogin(user);
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnLoginListener.class);
    }

    public void performProfileRequest() {
        if (NetworkChecker.isNetworkAvailable(context)) asyncRequestProfile();
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnProfileListener.class);
    }

    public void performEditProfileRequest(User user) {
        if (NetworkChecker.isNetworkAvailable(context)) asyncRequestEditProfile(user);
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnProfileListener.class);
    }

    public void performSignUpRequest(User user) {
        if (NetworkChecker.isNetworkAvailable(context)) asyncRequestSignUp(user);
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnSignUpListener.class);
    }

    public void performChangePasswordRequest(User user) {
        if (NetworkChecker.isNetworkAvailable(context)) asyncRequestChangePassword(user);
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnChangePasswordListener.class);
    }

    public void performLocationRequest() {
        if (NetworkChecker.isNetworkAvailable(context)) asyncRequestLocation();
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnLocationsListener.class);
    }

    public void performForgotPasswordRequest() {
        if (NetworkChecker.isNetworkAvailable(context)) asyncRequestForgotPassword();
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnForgotPasswordListener.class);
    }

    public void performBrowseSupermarketsRequest(Location location) {
        if (NetworkChecker.isNetworkAvailable(context)) asyncRequestBrowseSupermarkets(location);
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnBrowseSupermarketsListener.class);
    }

}
