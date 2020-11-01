define(["jquery", "leaflet", "leaflet.ajax", "show_district_border_layer", "show_building_layer"], function ($, leaflet, leafletAjax, showDistrictBorderLayer, showBuildingLayers) {
  return {
    loadData: (state, district) => {
      $.getJSON(`geojsons/districts/${district}.geojson`, data => {
        showDistrictBorderLayer(state, data);
        showBuildingLayers(state, district);
      })
    }
  }
});