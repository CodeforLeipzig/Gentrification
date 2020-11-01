define(["jquery", "leaflet", "leaflet.ajax", "district"], ($, leaflet, leafletAjax, district) => {
  return {
    configureInfo: (state, data) => {
      // control that shows state info on hover
      var info = leaflet.control({
        position : 'topright'
      });
      info.onAdd = function (map) {
        this._div = leaflet.DomUtil.create('div', 'info');
        this.update();
        return this._div;
      };
      info.update = function (id, props) {
        var htmlInner = '<div style="width: 300px;">';
        htmlInner += "<b>Ortsteil:</b> "
        htmlInner += district.districtSelectionBox(state);
        htmlInner += '</div>';
        this._div.innerHTML = htmlInner;
        district.setDistrictInSelectionBox(state);
        $("#districtSelection").off('change');
        $("#districtSelection").on('change', function(e) {
          district.handleDistrictChange(document, data, state);
        });
      }
      state.setInfo(info);
      return info;
    },
  };
});