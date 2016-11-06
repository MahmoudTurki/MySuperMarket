package com.pentavalue.tomato.network;

/**
 * Define all constant values that is related to network operation.
 *
 * @author Mahmoud Turki
 */
public class NetworkConstants {

    /**
     * The base url of the web service.
     */
    public static final String BASE_URL = "http://52.174.57.181/tomato/";
    /**
     * The Login url of the web service.
     */
    public static final String LOGIN_URL = BASE_URL + "token";
    /**
     * The Registration url of the web service.
     */
    public static final String REGISTRATION_URL = BASE_URL + "api/Account/Register/";
    /**
     * The Change password url of the web service.
     */
    public static final String CHANGE_PASSWORD_URL = BASE_URL + "api/Account/ChangePassword";
    /**
     * The Forgot password url of the web service.
     * http://52.174.57.181/api/Account/forgotPassword
     */
    public static final String FORGOT_PASSWORD_URL = BASE_URL + "api/Account/forgotPassword";
    /**
     * The Get Profile url of the web service.
     */
    public static final String PROFILE_URL = BASE_URL + "Api/Profile";
    /**
     * The Cities with areas listing url of the web service.
     * api/cities/?lang=en
     */
    public static final String LOCATION_URL = BASE_URL + "api/cities/";
    /**
     * The Browse supermarket url of the web service.
     * "api/supermarket/list/?pageIndex=0&pageSize=10&cityId=6&areaId=24&lang=en"
     */
    public static final String BROWSE_SUPERMARKETS_URL = BASE_URL + "api/supermarket/list/";
    /**
     * The Rate Supermarket url of the web service.
     */
    public static final String RATE_SUPERMARKETS_URL = BASE_URL + "api/Supermarket/rateSupermarket/?supermarketId={{supermarketId}}&rating={{rating value from 1 ->5}}";
    /**
     * The Browsing Special Categories & Specials sub categories url of the web service.
     */
    public static final String BROWSE_SPECIAL_CATEGORIES_URL = BASE_URL + "api/SpecialCategories/List/?supermarketId=1002&pageIndex=0&pageSize=10&lang=en";
    /**
     * The Browsing special items related to a specials sub category id url of the web service.
     */
    public static final String BROWSE_SPECIAL_ITEMS_URL = BASE_URL + "api/Items/ListSpecials/?subCategoryId=1&pageIndex=0&pageSize=10&lang=en";
    /**
     * The Browsing normal items for supermarket url of the web service.
     */
    public static final String BROWSE_NORMAL_ITEMS_URL = BASE_URL + "api/Items/ListNormal/?supermarketId=1002&pageIndex=0&pageSize=10&lang=en";
    /**
     * The Browsing deals items for supermarket url of the web service.
     */
    public static final String BROWSE_DEAL_ITEMS_URL = BASE_URL + "api/Items/ListNormal/?supermarketId=1002&pageIndex=0&pageSize=10&lang=en";

}
