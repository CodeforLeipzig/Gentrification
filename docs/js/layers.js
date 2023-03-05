define(["jquery", "leaflet", "leaflet.ajax", "map", "constants"], ($, leaflet, leafletAjax, map, constants) => ({
  handleGeoJsonLayers: (state, dataList) => {
    var years = constants.years;
    var colors = constants.colors;
    var overlayMaps = {};
    for (var j = 0; j < dataList.length; j++) {
      var entry = dataList[j]
      overlayMaps[map.layerLegendKey(years, colors, entry.year)] = entry.geoJsonLayer;
    }
    // https://gis-portal.de.tl/WMS_Dienste.htm
    // https://www.landesvermessung.sachsen.de/slider-historische-luftbilder-6020.html
    var createBaseMaps = function() {
      var webatlasOptions = {
        layers: 'Siedlung,Vegetation,Gewaesser,Verkehr,Adminstrative_Einheiten,Beschriftung'
      };
      var webatlasLayer = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_webatlas-sn/guest', webatlasOptions);
      var rohdopOptions = {
        layers: 'sn_rohdop_020'
      };
      var rohdopLayer = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_rohdop-rgb/guest', rohdopOptions);
      var rohdopOptions2018 = {
        layers: 'dop_2018_2020_rgb'
      };
      var rohdopLayer2018 = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_dop_2018_2020/guest', rohdopOptions2018);
      var rohdopOptions2015 = {
        layers: 'dop_2015_2017_rgb'
      };
      var rohdopLayer2015 = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_dop_2015_2017/guest', rohdopOptions2015);
      var rohdopOptions2014 = {
        layers: 'dop_2012_2014_rgb'
      };
      var rohdopLayer2014 = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_dop_2012_2014/guest', rohdopOptions2014);
      var rohdopOptions2013 = {
        layers: 'sn_dop_sb_rgb'
      };
      var rohdopLayer2013 = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_dopsb2013/guest', rohdopOptions2013);
      var rohdopOptions2009 = {
        layers: 'dop_2009_2011_rgb'
      };
      var rohdopLayer2009 = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_dop_2009_2011/guest', rohdopOptions2009);
      var rohdopOptions2006 = {
        layers: 'dop_2006_2008_rgb'
      };
      var rohdopLayer2006 = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_dop_2006_2008/guest', rohdopOptions2006);
      var rohdopOptions2005 = {
        layers: 'dop_2005'
      };
      var rohdopLayer2005 = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_dop-2005/guest', rohdopOptions2005);
      var rohdopOptions2000 = {
        layers: 'dop_2000_2004'
      };
      var rohdopLayer2000 = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_dop_1995_2004/guest', rohdopOptions2000);
      var rohdopOptions1995 = {
        layers: 'dop_1995_2000'
      };
      var rohdopLayer1995 = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_dop_1995_2004/guest', rohdopOptions1995);
      return {
        "OSM": state.getBaseMap(),
        "geodienste.sachsen.de webatlas WMS": webatlasLayer,
        "geodienste.sachsen.de rohdop WMS 1995-2000": rohdopLayer1995,
        "geodienste.sachsen.de rohdop WMS 2000-2004": rohdopLayer2000,
        "geodienste.sachsen.de rohdop WMS 2005": rohdopLayer2005,
        "geodienste.sachsen.de rohdop WMS 2006-2008": rohdopLayer2006,
        "geodienste.sachsen.de rohdop WMS 2009-2011": rohdopLayer2009,
        "geodienste.sachsen.de rohdop WMS 2013": rohdopLayer2013,
        "geodienste.sachsen.de rohdop WMS 2012-2014": rohdopLayer2014,
        "geodienste.sachsen.de rohdop WMS 2015-2017": rohdopLayer2015,
        "geodienste.sachsen.de rohdop WMS 2018-2020": rohdopLayer2018,
        "geodienste.sachsen.de rohdop WMS aktuell": rohdopLayer
      };
    }
    if (!state.getBaseMapJson()) {
      state.setBaseMapJson(createBaseMaps());
    }
    const buildingsMap = state.getBuildingMap();
    const layersControl = leaflet.control.layers(state.getBaseMapJson(), overlayMaps);
    state.setLayersControl(layersControl);
    layersControl.addTo(buildingsMap);
    state.getBaseMapJson()["geodienste.sachsen.de rohdop WMS aktuell"].addTo(buildingsMap);
    const selectedIndexes = [ 0, years.length ];
    if (!state.getLastDistrictLayer()) {
      state.setLastSelectedDistrict("Zentrum-Sued");
    }
    if (state.getLastOverlayLayers().length == 0) {
      selectedIndexes.forEach(selectedIndex => state.getLastOverlayLayers().push(dataList[selectedIndex].geoJsonLayer));
    }
    if (state.getLastBuildingLayers().length == 0) {
      selectedIndexes.forEach(selectedIndex => overlayMaps[map.layerLegendKey(years, colors, years[selectedIndex])].addTo(buildingsMap));
    }
    state.getInfo().update();
  },
}));