define(["leaflet", "leaflet.ajax", "map"], (leaflet, leafletAjax, map) => {
  return (state, data) => {
    const style = {
      weight: 2,
      color: 'blue',
      fillOpacity: 0.0
    }
    const geoJsonLayer = leaflet.geoJson(data, style);
    state.setLastDistrictLayer(geoJsonLayer);
    const buildingMap = state.getBuildingMap();
    buildingMap.addLayer(geoJsonLayer);
    geoJsonLayer.eachLayer(layer => {
      var coords = layer.feature.geometry.coordinates[0];
      var centroid = map.districtCenter(coords);
      var zoomLevel = 16;
      buildingMap.setView(centroid, zoomLevel);
    });
  }
});