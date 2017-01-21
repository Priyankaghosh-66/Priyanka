app.factory('UserService',function($http){
	var userService=this;
	var BASE_URL="http://localhost:8034/proj2backend"
		userService.authenticate=function(user){
		return $http.post(BASE_URL +"/login",user);
	}
	userService.registerUser=function(user){
		console.log('entering service for registration')
		return $http.post(BASE_URL +"/register",user)
	};
	userService.logout=function(){
		console.log('entering logout service')
		return $http.put(BASE_URL + "/logout")
	};
	userService.getAllUsers=function(){
		console.log('entering getallusers in userService')
		return $http.get(BASE_URL + "/getAllUsers")
	}
	userService.friendRequest=function(username){
		console.log('service------friendRequest')
		return $http.post(BASE_URL +"/friendRequest",username);
	}
	return userService;
})