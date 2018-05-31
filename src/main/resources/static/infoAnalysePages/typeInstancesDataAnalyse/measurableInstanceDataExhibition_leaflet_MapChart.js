$(document).ready(function() {
    var discoverSpaceName=getQueryString("discoverSpace");
    var measurableIds=getQueryString("measurableIds");
    var graphHeight=getQueryString("graphHeight");
    var latProperty=getQueryString("latProperty");
    var lngProperty=getQueryString("lngProperty");
    var titleProperty=getQueryString("titleProperty");
    var descProperty=getQueryString("descProperty");

    if(!discoverSpaceName){return;}
    if(!measurableIds){return;}
    if(!latProperty){return;}
    if(!lngProperty){return;}
    if(!titleProperty){return;}

    var restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"typeInstanceAnalyseService/typeInstancesDetailInfo/";
    if(graphHeight){
        document.getElementById('mapid').style.height=""+graphHeight+"px";
    }
    measurableIds=measurableIds.replace(/#/g, "%23");
    measurableIds=measurableIds.replace(/:/g, "%3a");
    var restURL=restBaseURL+discoverSpaceName+"/"+measurableIds+"/";
    $.ajax({
        url: restURL
    }).then(function(data) {
        var centerLocation=data[0];
        var centerLocationCoordinate=[
            getMeasurablePropertyNumberValue(centerLocation,latProperty),
            getMeasurablePropertyNumberValue(centerLocation,lngProperty)
        ];
        var mymap = L.map('mapid').setView(centerLocationCoordinate, 15);
        L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
            maxZoom: 18,
            /*
             attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
             '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
             'Imagery © <a href="http://mapbox.com">Mapbox</a>',
             */
            id: 'mapbox.streets'
        }).addTo(mymap);

        $.each(data,function(index,currentData){
            var currentLocationCoordinate=[
                getMeasurablePropertyNumberValue(currentData,latProperty),
                getMeasurablePropertyNumberValue(currentData,lngProperty)
            ];
            var mainDesc=getMeasurablePropertyOriginalValue(currentData,titleProperty);

            var secondDesc="";
            if(descProperty){
                secondDesc="<br/>"+getMeasurablePropertyOriginalValue(currentData,descProperty);
            }
            L.marker(currentLocationCoordinate).addTo(mymap).bindPopup("<b>"+mainDesc+"</b>"+secondDesc);
        });

        // L.marker([39.9788, 116.30226]).addTo(mymap).bindPopup("<b>Hello world!</b><br />I am a popup.").openPopup();
        L.marker([39.9788, 116.30226]).addTo(mymap).bindPopup("<b>Hello world!</b><br />I am a popup.");
        L.marker([39.9760, 116.30298]).addTo(mymap).bindPopup("<b>Hello world!</b><br />I am a popup.");
        L.circle([39.9788, 116.30226], 50, {
            color: 'red',
            fillColor: '#f03',
            fillOpacity: 0.4
        }).addTo(mymap).bindPopup("I am a circle.");

        L.polygon([
             [39.98447, 116.33461],
             [39.97983, 116.33496],
             [39.97747, 116.33579],
             [39.9773,  116.33938],
             [39.97641, 116.33944],
             [39.97577, 116.34635],
             [39.97786, 116.34612],
             [39.97802, 116.34721],
             [39.98496, 116.34695]
        ]).addTo(mymap).bindPopup("I am a polygon.");
        /*
        var popup = L.popup();
        function onMapClick(e) {
            popup
                .setLatLng(e.latlng)
                .setContent("You clicked the map at " + e.latlng.toString())
                .openOn(mymap);
        }
         mymap.on('click', onMapClick);
        */
    });
});















































/*
 Highcharts.chart('container', {
 chart: {
 plotBackgroundColor: null,
 plotBorderWidth: null,
 plotShadow: false,
 type: 'pie'
 },
 title: {
 text: 'Browser market shares January, 2015 to May, 2015'
 },
 tooltip: {
 pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
 },
 plotOptions: {
 pie: {
 allowPointSelect: true,
 cursor: 'pointer',
 dataLabels: {
 enabled: true,
 format: '<b>{point.name}</b>: {point.percentage:.1f} %',
 style: {
 color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
 }
 }
 }
 },
 series: [{
 name: 'Brands',
 colorByPoint: true,
 data: [{
 name: 'Microsoft Internet Explorer',
 y: 56.33,
 color:'#CE0000'
 }, {
 name: 'Chrome',
 y: 24.03,
 sliced: true,
 selected: true
 }, {
 name: 'Firefox',
 y: 10.38
 }, {
 name: 'Safari',
 y: 4.77
 }, {
 name: 'Opera',
 y: 0.91
 }, {
 name: 'Proprietary or Undetectable',
 y: 0.2
 }]
 }]
 });

 */