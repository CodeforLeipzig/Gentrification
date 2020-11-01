define(["jquery", "leaflet", "leaflet.ajax", "map", "constants", "layers"], ($, leaflet, leafletAjax, map, constants, layers) => {
  const handleGeoJsonLayers = layers.handleGeoJsonLayers
  return (state, district) => {
    const years = constants.years;
    const colors = constants.colors;
    var promises = []
    for (var i = 0; i < years.length; i++) {
      var year = years[i]
      var path = `geojsons/buildings/${district}-${year}-buildings.geojson`;
      var promise = Promise.all([$.getJSON(path), Promise.resolve(colors[i]), Promise.resolve(year)])
      promises.push(promise.then(data => {
        const layer = map.createGeoJsonLayer(data[0], data[1]);
        state.getLastBuildingLayers().push(layer)
        return {
          geoJsonLayer: layer,
          year: data[2]
        }
      }));
    }
    Promise.all(promises).then(dataList => handleGeoJsonLayers(state, dataList));
  }
});