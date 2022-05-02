package com.example.coviddata.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class CountryCache {
    
    private int hits = 0;
    private int misses = 0;
    private int requests = 0;

    private Map<String, ArrayList<String>> cacheCountryMap = new HashMap<>();
    private Map<String, Long> timeToLive = new HashMap<>();

    private long timeToClean;


    public CountryCache(int timeToClean) {
        this.timeToClean = timeToClean;
        cleaningByTime();
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    public int getRequests() {
        return requests;
    }

    public boolean addValue(String key, ArrayList<String> arr){
        long maxTime = System.currentTimeMillis() + this.timeToClean * 1000;
        timeToLive.put(key, maxTime);
        cacheCountryMap.put(key, arr);
        return true;
    }

    public boolean deleteValue(String key){
        if(cacheCountryMap.containsKey(key)){
            cacheCountryMap.remove(key);
            timeToLive.remove(key);
            return true;
        }
        return false;
    }

    public ArrayList<String> getArrayFromCache(String key){
        if(cacheCountryMap.containsKey(key) && timeToLive.get(key) > System.currentTimeMillis()){
            this.hits++;
            this.requests++;
            return cacheCountryMap.get(key);
        }
        this.requests++;
        this.misses++;
        return null;
    }

    public Thread cleaningByTime(){
        Thread thread = new Thread(){
            @Override
            public void run(){
                while (true){
                    for(String key: cacheCountryMap.keySet()){
                        if(timeToLive.get(key) < System.currentTimeMillis()){
                            deleteValue(key);
                        }
                    }
                    try {
                        Thread.sleep(timeToClean * 1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }


                }
            }
        };
        thread.start();
        return thread;
    }

    public void cacheTimer(String key, long time){
        this.timeToLive.remove(key);
        this.timeToLive.put(key, time);
    }

    public void clearCache(){
        this.cacheCountryMap.clear();
        this.timeToLive.clear();
    }

    public int getCacheSize(){
       return this.cacheCountryMap.size();
    }

    public boolean containsCity(String key){
        return this.cacheCountryMap.containsKey(key);
    }

}
