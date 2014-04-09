(function(gj, cometd){
	function Reminder() {} ;

	Reminder.prototype.init = function(eXoUser, eXoToken, cometdContextName){
		if(!cometd) return;
		cometd.exoId = eXoUser;
		cometd.exoToken = eXoToken;
		if(cometdContextName)
			cometd.url = '/' + cometdContextName + '/cometd';
		cometd.subscribe('/estudy/notification/messages', function(eventObj) {		
			Reminder.alarm(eventObj) ;
		});
	} ;

	Reminder.prototype.initCometd = function() {
		cometd.subscribe('/estudy/notification/messages', function(eventObj) {		
			Reminder.alarm(eventObj) ;
		});
	}


    //display popup reminder
	Reminder.prototype.alarm = function(eventObj){
		console.log("reminder ........")
	}
})(gj,cometd);