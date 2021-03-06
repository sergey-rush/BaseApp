package ru.baseapp.web;

import android.text.TextUtils;

import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.baseapp.core.AppMode;
import ru.baseapp.core.HttpMethod;
import ru.baseapp.core.Product;
import ru.baseapp.core.Section;
import ru.baseapp.core.UrlObject;
import ru.baseapp.core.UrlType;
import ru.baseapp.core.User;

/**
 * Created by rash on 06.02.2018.
 */
public class WebContext {

    private static WebContext current = new WebContext();
    public static WebContext getInstance(){
        return current;
    }

    public User User = new User();
    public String Imei;
    public List<Section> SectionList;
    public List<Product> ProductList;

    private java.net.CookieManager CookieManager = new CookieManager();

    private WebContext() {

        if (Mode != AppMode.Product) {
            User = new User(1, "Разработчик приложения", "79267016427", "user@mail.ru", true);
        }
        initUrls();
    }

    /*
     * Persists cookie in current session.
     */
    public void setCookie(Map<String, List<String>> headers)
    {
        final String COOKIES_HEADER = "Set-Cookie";
        List<String> cookiesHeaders = headers.get(COOKIES_HEADER);
        if (cookiesHeaders != null) {
            for (String cookie : cookiesHeaders) {
                CookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }
        }
    }

    public void attachCookieTo(HttpURLConnection connection)
    {
        if (CookieManager.getCookieStore().getCookies().size() > 0) {
            connection.setRequestProperty("Cookie", TextUtils.join(";",  CookieManager.getCookieStore().getCookies()));
        }
    }

    public AppMode Mode = AppMode.Develop;
    Map<AppMode, Map<UrlType, UrlObject>> urlMap = new HashMap<AppMode, Map<UrlType, UrlObject>>();

    private void initUrls()
    {
        Map<UrlType, UrlObject> developMap = new HashMap<UrlType, UrlObject>();
        developMap.put(UrlType.Sections, new UrlObject(HttpMethod.GET, "http://10.0.1.99/api/sections/"));
        developMap.put(UrlType.Products, new UrlObject(HttpMethod.GET, "http://10.0.1.99/api/products/"));
        urlMap.put(AppMode.Develop, developMap);

        Map<UrlType, UrlObject> testMap = new HashMap<UrlType, UrlObject>();
        testMap.put(UrlType.Sections, new UrlObject(HttpMethod.GET, "http://10.0.1.99/api/sections/"));
        testMap.put(UrlType.Products, new UrlObject(HttpMethod.GET, "http://10.0.1.99/api/products/"));
        urlMap.put(AppMode.Test, testMap);

        Map<UrlType, UrlObject> prodMap = new HashMap<UrlType, UrlObject>();
        prodMap.put(UrlType.Sections, new UrlObject(HttpMethod.GET, "http://10.0.1.99/api/sections/"));
        prodMap.put(UrlType.Products, new UrlObject(HttpMethod.GET, "http://10.0.1.99/api/products/"));
        urlMap.put(AppMode.Product, prodMap);
    }

    public UrlObject getUrl(UrlType urlType) {
        return urlMap.get(Mode).get(urlType);
    }
}


