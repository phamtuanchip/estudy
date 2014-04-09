(function($, Reminder){
	var _module = {};
	function estudy(){
		
	};
	estudy.prototype.init = function() {
		  console.log("Success to load js !");
	};
	_module.estudy = new estudy();
	_module.estudy.Reminder = Reminder;
    return _module;
})($, Reminder);
 