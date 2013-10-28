(function($) {

  var ExamplePortlet = {
    parentId: '#examplePortlet',
    apply : function(e) {
      var portlet = $(ExamplePortlet.parentId);
      var inputValue = portlet.find('#location_input').val();
      portlet.jzAjax({        
      url : "ExampleControll.updateLocation()",
      data : {
        "params" : inputValue
      });
    },
    onload : function() {
      var parent = $(Notification.parentId);
      parent.find('button#Save').click(ExamplePortlet.apply);
    }
  };
  ExamplePortlet.onload();
  return ExamplePortlet;
})(jq);
