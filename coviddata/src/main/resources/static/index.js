urlBase = "http://127.0.0.1:8080";
var map;
//urlBase = "http://deti-engsoft-11.ua.pt:8081";

async function getData(url, type){
    const response = await fetch(url);
    var data = await response.json();
    console.log(data)
    var map = new Map(Object.entries(data));
    
    if (type=="city"){
        if (map.get("status")=="500"){
            document.getElementById("city-div").innerHTML = "<h1>Not a valid entry!</h1>";
        }
        else{
            show_data_city(data,url);
        }
    }
    else if(type=="countries"){
        show_data_countries(data);
    }
    else if(type=="cities"){
        show_data_cities(data);
    }
}

function show_data_cities(data){
    var arr = Object.entries(data);
    var tab = ``;
    var province = "";
    if (arr.length==1){
        tab += `<a href="#">Can not show cities for this country.</a>`;
    }
    else{
        for (let i=1; i < arr.length; i++){
            let cls = arr[i];
            province = cls[1];
            tab += `<a href="#">${province}</a>`;
        }
    }
    document.getElementById("myDropdown2").innerHTML = tab;
}

function show_data_countries(data){
    map = new Map(Object.entries(data));
    var arr =Array.from(map.keys());
    var tab = ``;
    var name = ``;
    for (let i=0; i < arr.length; i++){
        let cls = arr[i];
        name = cls;
        tab += `<a href="#">${name}</a>`;
    }
    document.getElementById("myDropdown").innerHTML = tab;
}

function show_data_city(data){
    var arr = Object.entries(data);
    var type = [];
    var name ="";
    var lat="";
    var active ="";
    var confirmed="";
    var deaths ="";
    var recovered="";
    var lon="";
    var fat = "";
    var tab = ``;
    for (let i = 0; i < arr.length; i++) {
        let cls = arr[i];
        if (cls[0]=="name"){
            name = cls[1];
        }
        else if(cls[0]=="lat"){
            lat = cls[1];
        }
        else if(cls[0]=="lon"){
            lon = cls[1];
        }
        else if(cls[0]=="active"){
            active = cls[1];
        }
        else if(cls[0]=="deaths"){
            deaths = cls[1];
        }
        else if(cls[0]=="confirmed"){
            confirmed = cls[1];
        }
        else if(cls[0]=="recovered"){
            recovered = cls[1];
        }
        else if(cls[0]=="fatality_rate"){
            fat = cls[1];
        }
    }
    tab += `<div class="content">
    <div class="recov">
        <h2>${recovered}</h2>
        <h3>Recovered</h3>
    </div>
    <div class="deaths">
        <h2>${deaths}</h2>
        <h3>Deaths</h3>
    </div>
    <div class="activ">
        <h2>${active}</h2>
        <h3>Active</h3>
    </div>
</div>
<div>
    <h2 id="city-name">City: ${name}</h2>
    <span>Fatality rate: ${fat}</span>
    <span style="margin-left: 25px;">Latitude:${lat}   Longitude:${lon}</span>
</div>`;
    document.getElementById("city-div").innerHTML = tab;
}

function getCity(city){
    getData(urlBase + "/city/" + city, "city");
}

function getCountries(){
    getData(urlBase + "/countries", "countries");
}

function getCities(country){
    var iso = map.get(country);
    console.log(iso);
    getData(urlBase + "/cities/" + iso, "cities");
}
