(function($,er){
	var _module = {};
	function estudy(){
		
	};
	estudy.prototype.init = function() {
		  console.log("Success to load js !");
          er.init();
	};
	_module.estudy = new estudy();
    return _module;
})($, er);
 