//var siteUrl = window.location.protocol + "//" + window.location.host + "/";
var siteUrl = "https://codeforleipzig.github.io/Gentrification/";
requirejs.config({
  baseUrl: siteUrl + "js/",
  paths: {
    "leaflet": "https://unpkg.com/leaflet@1.7.1/dist/leaflet",
    "leaflet.ajax": "https://cdnjs.cloudflare.com/ajax/libs/leaflet-ajax/2.1.0/leaflet.ajax.min",
    "jquery": "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min",
  }
});
require(["jquery", "map", "data", "shared_state", "info", "district", "leaflet"], function ($, map, data, state, info, district, leaflet) {
  $.ajaxSetup({
    scriptCharset: "utf-8",
    contentType: "application/json; charset=utf-8"
  });

  const globalState = state.state();
  const buildingMap = map.create(globalState);
  globalState.setBuildingMap(buildingMap);
  const infoBox = info.configureInfo(globalState, data);
  data.loadData(globalState, district.allDistricts[0]);
  infoBox.addTo(buildingMap);
});