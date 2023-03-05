define(["jquery", "leaflet", "leaflet.ajax"], ($, leaflet, leafletAjax) => ({
	create: (state) => {
    var buildingMap = leaflet.map('BuildingsMap');
    var baseMap = leaflet.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    })
    state.setBaseMap(baseMap);
    baseMap.addTo(buildingMap);
    return buildingMap;
  },
  createGeoJsonLayer: (data, colorName) => {
    var style = {
      weight: 2,
      color: colorName,
      fillOpacity: 0.5
    }
    return leaflet.geoJson(data, style);
  },
  districtCenter: (arr) => {
    var arrToUse;
    if (arr.length == 1) {
      arrToUse = arr[0];
    } else {
      arrToUse = arr;
    }
    var center = arrToUse.reduce(function (x, y) {
      return [x[0] + y[0] / arrToUse.length, x[1] + y[1] / arrToUse.length]
    }, [0, 0])
    return [center[1], center[0]];
  },
  layerLegendKey: (years, colors, year) => {
    var color = colors[years.indexOf(year)];
    return "<span style='color: " + color + "'>&#9724; Buildings " + year + "</span>";
  }
}));