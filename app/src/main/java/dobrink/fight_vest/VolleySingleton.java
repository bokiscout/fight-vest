package dobrink.fight_vest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import dobrink.fight_vest.models.Fight;

/**
 * Created by Dobrin on 13-Jun-17.
 */

@SuppressWarnings({"DefaultFileTemplate", "CanBeFinal"})
public class VolleySingleton {
    private final static String URL = "http://www.fv.pdtransverzalec.org.mk";
    private final static String GET_FIGHTS_URL = URL+"/api/Fights";
    private final static String POST_START_FIGHT_URL = URL+"/api/Fight/Start/"; //+MatchID
    private final static String POST_START_NEXT_ROUND_URL = URL+"/api/Fight/Round/Start/"; //+MatchID
    private final static String POST_END_LAST_ROUND_URL = URL+"/api/Fight/Round/End/"; //+MatchID
    private final static String POST_END_FIGHt_URL = URL+"/api/Fight/End/"; //+MatchID


    private static VolleySingleton mVolleySingletonInstance;
    private FightLogicHelper fightLogic;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mContext;



    private VolleySingleton(Context context){
        mContext = context;
        mRequestQueue = getRequestQueue();
        fightLogic = FightLogicHelper.getInstance();
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(20);
                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }
                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
        }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mVolleySingletonInstance == null) {
            mVolleySingletonInstance = new VolleySingleton(context);
        }
        return mVolleySingletonInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    private <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void volleyJsonArrayRequest(String url){
        String REQUEST_TAG = "volleyJsonArrayRequest"; //used to cancel the request
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("yyyy-mm-dd'T'hh:mm:ss");
                        Gson gson = gsonBuilder.create();
                        ArrayList<Fight> fights =  new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(),Fight[].class)));
                        FightLogicHelper.setFights(fights);
                        sendFightListUpdate();
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", "Error: " + error.getMessage());
                FightLogicHelper.getInstance().makeFakeFights(5);
                sendFightListUpdate();
            }
        });

        // Adding JsonArray request to request queue
        addToRequestQueue(jsonArrayReq,REQUEST_TAG);
    }
    private void volleyPostRequest(String url){
        String REQUEST_TAG = "volleyPostRequest"; //used to cancel the request
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int  statusCode = error.networkResponse.statusCode;
                        NetworkResponse response = error.networkResponse;
                        Log.d("ERROR",""+statusCode+" "+response.toString());
                    }
                }){
        };
        // Adding JsonArray request to request queue
        addToRequestQueue(postRequest,REQUEST_TAG);
    }
    public void fetchFightsOnline() {
        volleyJsonArrayRequest(GET_FIGHTS_URL);
    }

    private void sendFightListUpdate() {
        Intent intent = new Intent("fightListUpdated");
        LocalBroadcastManager.getInstance(null).sendBroadcast(intent);
    }

    public void postStartFight() {
        Log.d("VOLLEY", "postStartFight(): "+fightLogic.getMatchID());
        volleyPostRequest(POST_START_FIGHT_URL+fightLogic.getMatchID());
    }

    public void postStartNextRound() {
        Log.d("VOLLEY", "postStartNextRound(): "+fightLogic.getMatchID());
        volleyPostRequest(POST_START_NEXT_ROUND_URL+(fightLogic.getMatchID()));
    }

    public void postEndLastRound() {
        Log.d("VOLLEY", "postEndLastRound(): "+fightLogic.getMatchID());
        volleyPostRequest(POST_END_LAST_ROUND_URL+(fightLogic.getMatchID()));
    }

    public void postEndFight() {
        Log.d("VOLLEY", "postEndFight()");
        volleyPostRequest(POST_END_FIGHt_URL+fightLogic.getMatchID());
    }

    public void postHitEvent(int hitFighterID, int strength) {
        String str;
        if (strength==1){
            str="weak";
        }else{
            str="strong";
        }
        Log.d("VOLLEY", "postHitEvent hitFighterID(): "+hitFighterID);
        String HIT_URL = URL+"/api/Fight/"+fightLogic.getMatchID()+"/Hit/"+hitFighterID+"/"+str;
        volleyPostRequest(HIT_URL);
    }
}
