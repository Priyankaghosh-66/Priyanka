app.factory('FriendService',function($http){
	var friendService=this;
	var BASE_URL="http://localhost:8034/proj2backend"
		
	
	friendService.getAllFriends=function(){
		console.log('service-----friendRequest');
		return $http.get(BASE_URL +"/getAllFriends");
		
	}
	friendService.pendingRequest=function(){
		console.log('service------pending request');
		return $http.get(BASE_URL +"/pendingRequest")
	}
	friendService.updateFriendRequest=function(friendStatus,fromId){
		console.log('service------update friend Request')
		return $http.put(BASE_URL +"/updateFriendRequest/" +friendStatus+"/"+fromId)
	}
	return friendService;
})