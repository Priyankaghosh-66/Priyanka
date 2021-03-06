app.controller('UserController',function($scope,$rootScope,$cookieStore,$location,UserService){
	$scope.user={id:'',username:'',password:'',email:'',role:'',isOnline:'',enabled:''};
	$scope.message;
	$scope.submit=function(){
		console.log('entering -submit function in controller')
		UserService.authenticate($scope.user)
		.then(function(response)
		    {
				$scope.user=response.data;
				$rootScope.currentUser=$scope.user;
				$cookieStore.put('currentUser',$rootScope.currentUser)
				console.log('currentUser in rootscope' + $rootScope.currentUser.id)
				$location.path("/home")
			},
			function(response){
				console.log('invalid username and password!!')
				$scope.message="Invalid Username and Password";
				$location.path("/login")
			})
	}
	
	$scope.registerUser=function(){
		console.log('entering registerUser')
		UserService.registerUser($scope.user)
		.then(function(response){
			console.log("registration success"+ response.status)
			$scope.message="Registration successfull.. login using username and password.."
			$location.path("/login");
		},function(response){
			console.log("registration failed" + response.status)
			console.log(response.data)
			$scope.errorMessage="Registration failed....."+ response.data.message
			$location.path("/register")
		})
	}
	/*$rootScope.logout=function(){
		console.log('logout function')
		delete $rootScope.currentUser;
		
		UserService.logout()
		.then(function(response){
			console.log("Logged out successfully");
			$scope.message="Logged out successfully";
			$location.path('/login')
		},
		function(response){
			console.log(response.status);
		})
	}
	$rootScope.hasRole=function(role){
		if($rootScope.currentUser.role==undefined)
			return false;
		return $rootScope.currentUser.role==role;
	}*/
	
	$scope.friendRequest=function(username){
		alert('friendRequest in userController')
		console.log('friendRequest function')
		UserService.friendRequest(username)
		.then(function(response){
			console.log('response.status')
			alert('friendRequest sent')
			getAllUsers();
			$location.path('/getAllUsers')
		},
		function(response){
			console.log(response.status);
		})
	}
	
	
	function getAllUsers(){
		console.log('entering get al users')
		UserService.getAllUsers()
		.then(function(response){
			console.log(response.status)
			console.log(response.data)
			$scope.users=response.data
		},function(response){
			console.log(response.status)
		})
	}
	getAllUsers()
})