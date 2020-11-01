define({
  state: () => {
    var baseMap;
    var buildingMap;
    var lastSelectedDistrict;
    var oldLayer;
    var lastBuildingLayers = [];
    var lastOverlayLayers = [];
    var lastDistrictLayer;
    var info;
    var layersControl;

    return {
      getBaseMap: () => { return baseMap },
      getBuildingMap: () => { return buildingMap },
      getLastSelectedDistrict: () => { return lastSelectedDistrict },
      getOldLayer: () => { return oldLayer },
      getLastOverlayLayers: () => { return lastOverlayLayers },
      getLastBuildingLayers: () => { return lastBuildingLayers },
      getLastDistrictLayer: () => { return lastDistrictLayer },
      getInfo: () => { return info },
      getLayersControl: () => { return layersControl },

      setBaseMap: (newBaseMap) => { baseMap = newBaseMap },
      setBuildingMap: (newBuildingMap) => { buildingMap = newBuildingMap },
      setLastSelectedDistrict: (newLastSelectedDistrict) => { lastSelectedDistrict = newLastSelectedDistrict },
      setOldLayer: (newOldLayer) => { oldLayer = newOldLayer },
      setLastDistrictLayer: (newLastDistrictLayer) => { lastDistrictLayer = newLastDistrictLayer },
      setInfo: (newInfo) => { info = newInfo },
      setLayersControl: (newLayersControl) => { layersControl = newLayersControl }
    }
  }
});


