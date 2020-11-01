define(["jquery", "leaflet", "leaflet.ajax", "map", "constants"], ($, leaflet, leafletAjax, map, constants) => ({
  handleGeoJsonLayers: (state, dataList) => {
    var years = constants.years;
    var colors = constants.colors;
    var overlayMaps = {};
    for (var j = 0; j < dataList.length; j++) {
      var entry = dataList[j]
      overlayMaps[map.layerLegendKey(years, colors, entry.year)] = entry.geoJsonLayer;
      state.getLastOverlayLayers().push(entry.geoJsonLayer);
    }
    var webatlasOptions = {
      layers: 'Siedlung,Vegetation,Gewaesser,Verkehr,Adminstrative_Einheiten,Beschriftung'
    };
    var webatlasLayer = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_webatlas-sn/guest', webatlasOptions);
    var rohdopOptions = {
      layers: 'sn_rohdop_020'
    };
    var rohdopLayer = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_rohdop-rgb/guest', rohdopOptions);
    var rohdopOptions2014 = {
      layers: 'dop_2012_2014_rgb'
    };
    var rohdopLayer2014 = leaflet.tileLayer.wms('https://geodienste.sachsen.de/wms_geosn_dop_2012_2014/guest', rohdopOptions2014);
    var baseMaps = {
      "OSM": state.getBaseMap(),
      "geodienste.sachsen.de webatlas WMS": webatlasLayer,
      "geodienste.sachsen.de rohdop WMS 2012 - 2014": rohdopLayer2014,
      "geodienste.sachsen.de rohdop WMS 2018": rohdopLayer
    };
    const buildingsMap = state.getBuildingMap();
    const layersControl = leaflet.control.layers(baseMaps, overlayMaps);
    state.setLayersControl(layersControl);
    layersControl.addTo(buildingsMap);
    rohdopLayer2014.addTo(buildingsMap);
    years.forEach(year => overlayMaps[map.layerLegendKey(years, colors, year)].addTo(buildingsMap));
    state.getInfo().update();
  },
}));