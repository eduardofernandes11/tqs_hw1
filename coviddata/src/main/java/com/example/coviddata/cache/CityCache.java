package com.example.coviddata.cache;

import java.util.HashMap;
import java.util.Map;
import com.example.coviddata.model.*;

public class CityCache {
    
    private int hits = 0;
    private int misses = 0;
    private int requests = 0;

    private Map<String, City> cacheCityMap = new HashMap<>();
    private Map<String, Long> timeToLive = new HashMap<>();

    private long timeToClean;


    public CityCache(int timeToClean) {
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

    public boolean addValue(String key, City city){
        long maxTime = System.currentTimeMillis() + this.timeToClean * 1000;
        timeToLive.put(key, maxTime);
        cacheCityMap.put(key, city);
        return true;
    }

    public boolean deleteValue(String key){
        if(cacheCityMap.containsKey(key)){
            cacheCityMap.remove(key);
            timeToLive.remove(key);
            return true;
        }
        return false;
    }

    public City getCityFromCache(String key){
        if(cacheCityMap.containsKey(key) && timeToLive.get(key) > System.currentTimeMillis()){
            this.hits++;
            this.requests++;
            return cacheCityMap.get(key);
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

                    for(String key: cacheCityMap.keySet()){
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

    public void clearCache(){
        this.cacheCityMap.clear();
        this.timeToLive.clear();
    }

    public int getCacheSize(){
       return this.cacheCityMap.size();
    }

    public boolean containsCity(String key){
        return this.cacheCityMap.containsKey(key);
    }

}
