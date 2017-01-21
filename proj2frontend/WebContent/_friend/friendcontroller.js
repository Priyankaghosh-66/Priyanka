app.controller('FriendController',function($scope,$location,FriendService){
	alert('entering friend Controller')
	$scope.friends=[]
	$scope.pendingRequest=[]
	$scope.friends=FriendService.getAllFriends()
	.then(function(response){
		console.log('get all friends controller')
		console.log(response.status)
		$scope.friends=response.data;
	},function(response){
		console.log(response.data)
	})
	$scope.pendingRequest=FriendService.pendingRequest()
	.then(function(response){
		console.log('pending request')
		console.log(response.status)
		$scope.pendingRequest=response.data
		console.log($scope.pendingRequest)
		alert($scope.pendingRequest);
	},function(response){
		console.log('response.status')
	})
	$scope.updatePendingRequest=function(fromId,friendStatus){
		alert('updateFriendRequest')
		FriendService.updateFriendRequest(friendStatus,fromId)
		.then(function(response){
			console.log(response.status)
			if(friendStatus=='A'){
				alert('You have accepted the Friend Request.you and ' +fromId+ "are friends");
				$location.path('/friendList')
			}
			else{
				alert('you have denied the friend request')
			}
		},function(response){
			console.log(response.log)
		})
	}
})