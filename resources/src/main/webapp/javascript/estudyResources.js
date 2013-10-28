(function($,player){
    var _module = {};
    function estudyResources(){

    };
    estudyResources.prototype.init = function() {
        console.log("Success to load from resource js !");
    };
    _module.estudyResources = new estudyResources();
    return _module;


})($,player);