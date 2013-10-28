window.require(["SHARED/juzu-ajax","SHARED/jquery"], function(jzAjax,jq) {
var require = eXo.require, requirejs = eXo.require,define = eXo.define;
eXo.define.names=["jzAjax","jq"];
eXo.define.deps=[jzAjax,jq];

(function($) {

  var ExamplePortlet = {
    parentId: '#examplePortlet',
    apply : function(e) {
      var portlet = $(ExamplePortlet.parentId);
      var inputValue = portlet.find('#location_input').val();
      portlet.jzAjax({
        url : "ExampleControll.updateLocation()",
        data : {
          "location" : inputValue
        },
        success : function(data) {
          var content = $('<div></div>').html(data).find('div.examplePortlet:first').html();
          $(ExamplePortlet.parentId).html(content);
          ExamplePortlet.onload();
        }
      });
    },
    onload : function() {
      var parent = $(ExamplePortlet.parentId);
      parent.find('button#Save').click(ExamplePortlet.apply);
    }
  };
  ExamplePortlet.onload();
  return ExamplePortlet;
})(jq);

});
