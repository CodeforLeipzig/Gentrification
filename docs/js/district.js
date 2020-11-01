const districtDict = {
  "Althen-Kleinpoesna": "Althen-Kleinpösna",
  "Altlindenau": "Altlindenau",
  "Anger-Crottendorf": "Anger-Crottendorf",
  "Baalsdorf": "Baalsdorf",
  "Burghausen-Rueckmarsdorf": "Burghausen-Rückmarsdorf",
  "Boehlitz-Ehrenberg": "Böhlitz-Ehrenberg",
  "Connewitz": "Connewitz",
  "Doelitz-Doesen": "Dölitz-Dösen",
  "Engelsdorf": "Engelsdorf",
  "Eutritzsch": "Eutritzsch",
  "Gohlis-Mitte": "Gohlis-Mitte",
  "Gohlis-Nord": "Gohlis-Nord",
  "Gohlis-Sued": "Gohlis-Süd",
  "Grosszschocher": "Großzschocher",
  "Gruenau-Mitte": "Grünau-Mitte",
  "Gruenau-Nord": "Grünau-Nord",
  "Gruenau-Ost": "Grünau-Ost",
  "Gruenau-Siedlung": "Grünau-Siedlung",
  "Hartmannsdorf-Knautnaundorf": "Hartmannsdorf-Knautnaundorf",
  "Heiterblick": "Heiterblick",
  "Holzhausen": "Holzhausen",
  "Kleinzschocher": "Kleinzschocher",
  "Knautkleeberg-Knauthain": "Knautkleeberg-Knauthain",
  "Lausen-Gruenau": "Lausen-Grünau",
  "Leutzsch": "Leutzsch",
  "Liebertwolkwitz": "Liebertwolkwitz",
  "Lindenau": "Lindenau",
  "Lindenthal": "Lindenthal",
  "Loessnig": "Lößnig",
  "Luetzschena-Stahmeln": "Lützschena-Stahmeln",
  "Marienbrunn": "Marienbrunn",
  "Meusdorf": "Meusdorf",
  "Miltitz": "Miltitz",
  "Mockau-Nord": "Mockau-Nord",
  "Mockau-Sued": "Mockau-Süd",
  "Moeckern": "Möckern",
  "Moelkau": "Mölkau",
  "Neulindenau": "Neulindenau",
  "Neustadt-Neuschoenefeld": "Neustadt-Neuschönefeld",
  "Paunsdorf": "Paunsdorf",
  "Plagwitz": "Plagwitz",
  "Plaussig-Portitz": "Plaußig-Portitz",
  "Probstheida": "Probstheida",
  "Reudnitz-Thonberg": "Reudnitz-Thonberg",
  "Schleussig": "Schleußig",
  "Schoenau": "Schönau",
  "Schoenefeld-Abtnaundorf": "Schönefeld-Abtnaundorf",
  "Schoenefeld-Ost": "Schönefeld-Ost",
  "Seehausen": "Seehausen",
  "Sellerhausen-Stuenz": "Sellerhausen-Stünz",
  "Stoetteritz": "Stötteritz",
  "Suedvorstadt": "Südvorstadt",
  "Thekla": "Thekla",
  "Volkmarsdorf": "Volkmarsdorf",
  "Wahren": "Wahren",
  "Wiederitzsch": "Wiederitzsch",
  "Zentrum": "Zentrum",
  "Zentrum-Nord": "Zentrum-Nord",
  "Zentrum-Nordwest": "Zentrum-Nordwest",
  "Zentrum-Ost": "Zentrum-Ost",
  "Zentrum-Sued": "Zentrum-Süd",
  "Zentrum-Suedost": "Zentrum-Südost",
  "Zentrum-West": "Zentrum-West",
};
const districts = Object.keys(districtDict);

define(({
  allDistricts: districts,
  districtSelectionBox: (state) => {
    var htmlCode = '<select id="districtSelection">';
    for (var index in districts) {
      var district = districts[index];
      if (district) {
        htmlCode += '<option id="' + district + '">' + districtDict[district] + '</option>';
      }
    }
    htmlCode += '</select>';
    return htmlCode;
  },

  selectedDistrict: () => {
    var selectionBox = document.getElementById("districtSelection");
    if (selectionBox && selectionBox.selectedIndex != -1) {
      var option = selectionBox.options[selectionBox.selectedIndex];
      if (option) {
        return option.attributes["id"].value;
      } else {
        ''
      }
    } else {
      ''
    }
  },
  setDistrictInSelectionBox: (state) => {
    var selectionBox = document.getElementById('districtSelection');
    if (selectionBox) {
      for (var option, index = 0; option = selectionBox.options[index]; index++) {
        if (option.attributes["id"].value == state.getLastSelectedDistrict()) {
          selectionBox.selectedIndex = index;
          break;
        }
      }
    }
  },
  handleDistrictChange: (document, data, state) => {
    var selectionBox = document.getElementById("districtSelection");
    if (selectionBox && selectionBox.selectedIndex != -1) {
      var selectedDistrict = selectionBox.options[selectionBox.selectedIndex].attributes["id"].value;
      state.setLastSelectedDistrict(selectedDistrict);
      if (selectedDistrict != "") {
        state.setOldLayer(undefined);
        state.getLastOverlayLayers().forEach(layer => state.getBuildingMap().removeLayer(layer));
        state.getLastBuildingLayers().forEach(layer => state.getBuildingMap().removeLayer(layer));
        state.getBuildingMap().removeLayer(state.getLastDistrictLayer());
        state.getBuildingMap().removeControl(state.getLayersControl());
        data.loadData(state, selectedDistrict);
      }
    }
  }
}));