package com.example.coviddata.service;

//import airquality.cache.CityTTLCache;
import com.example.coviddata.model.Country;
import com.example.coviddata.model.City;
import com.example.coviddata.cache.CityCache;
import com.example.coviddata.cache.CountryCache;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class CountryServiceImpl implements CountryService{

    private static final String PRIVATEKEY = "dbde23c25fmshdc3716b5c0be28bp1a55e4jsn87e202b8bd9c";
    private static final String URL = "https://covid-19-statistics.p.rapidapi.com/";

    private CloseableHttpClient client;

    private CityCache cacheCity = new CityCache(60);

    private CountryCache cacheCountry= new CountryCache(60);

    public CountryServiceImpl() throws InterruptedException {
        this.client = HttpClients.createDefault();
    }

    @Override
    public City getCityByName(String name) throws IOException, URISyntaxException {
        return getCityFromAPI(URL + "reports?region_province=" + name, name);
    }

    @Override
    public HashMap<String, String> getCountries() throws IOException, URISyntaxException {
        return getCountriesFromAPI(URL + "regions");
    }

    @Override
    public ArrayList<String> getCities(String iso) throws IOException, URISyntaxException {
        return getCitiesFromAPI(URL + "provinces?iso=" + iso, iso);
    }

    @Override
    public HashMap<String, HashMap<String, Integer>> getCacheDetails(){
        HashMap<String, HashMap<String, Integer>> response = new HashMap<>();
        HashMap<String, Integer> city = new HashMap<>();
        HashMap<String, Integer> country = new HashMap<>();
        city.put("hits", this.cacheCity.getHits());
        city.put("misses", this.cacheCity.getMisses());
        city.put("requests", this.cacheCity.getRequests());
        country.put("hits", this.cacheCountry.getHits());
        country.put("misses", this.cacheCountry.getMisses());
        country.put("requests", this.cacheCountry.getRequests());
        response.put("City Cache Details", city);
        response.put("Country Cache Details",country);
        return response;
    }

    public ArrayList<String> getCitiesFromAPI(String url, String iso) throws IOException, URISyntaxException {

        ArrayList<String> arrFromCache = cacheCountry.getArrayFromCache(iso);

        if(arrFromCache != null){
            return arrFromCache;
        }

        ArrayList<String> values = new ArrayList<>();

        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString());

        JSONObject responseJson =  new JSONObject(response);
        System.out.println(responseJson);

        if(responseJson.get("data").toString() == ""){
            return null;
        }

        JSONArray data = new JSONArray(responseJson.get("data").toString());

        for (Object obj: data){
            System.out.println(obj);
            JSONObject jsonObj = (JSONObject) obj;
            values.add(jsonObj.get("province").toString());
        }

        cacheCountry.addValue(iso, values);

        return values;
    }

    public HashMap<String, String> getCountriesFromAPI(String url) throws IOException, URISyntaxException{

        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString());

        JSONObject responseJson =  new JSONObject(response);
        System.out.println(responseJson);

        if(responseJson.get("data").toString() == ""){
            return null;
        }

        HashMap<String, String> values = new HashMap<>();

        JSONArray data = new JSONArray(responseJson.get("data").toString());

        System.out.println(data);

        for (Object obj: data){
            System.out.println(obj);
            JSONObject jsonObj = (JSONObject) obj;
            values.put(jsonObj.get("name").toString(), jsonObj.get("iso").toString());
        }

        return values;
    }

    public City getCityFromAPI(String url, String name) throws IOException, URISyntaxException {
        //Logger logger = Logger.getLogger()

        City cityFromCache = cacheCity.getCityFromCache(name);

        if(cityFromCache != null){
            return cityFromCache;
        }

        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString());

        JSONObject responseJson =  new JSONObject(response);

        if(responseJson.get("data").toString() == ""){
            return null;
        }

        JSONArray data = new JSONArray(responseJson.get("data").toString());
        JSONObject content = new JSONObject(data.get(0).toString());

        JSONObject region = new JSONObject(content.get("region").toString());
        Double lat = Double.parseDouble(region.get("lat").toString());
        Double lon = Double.parseDouble(region.get("long").toString());
        int confirmed = Integer.parseInt(content.get("confirmed").toString());
        int deaths = Integer.parseInt(content.get("deaths").toString());
        int recovered = Integer.parseInt(content.get("recovered").toString());
        int active = Integer.parseInt(content.get("active").toString());
        Double fat = Double.parseDouble(content.get("fatality_rate").toString());

        City city = new City(name, lat, lon, confirmed, deaths, recovered, active, fat);

        cacheCity.addValue(name, city);

        return city;
    }

    public String constructUrlRequest(String url) throws IOException {
        if (url.contains("?")){
            url = url + "&rapidapi-key=" + PRIVATEKEY;
        }
        else{
            url = url + "?rapidapi-key=" + PRIVATEKEY;
        }
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = this.client.execute(get);

        if (response != null) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
        else{
            return null;
        }

    }
    
}
