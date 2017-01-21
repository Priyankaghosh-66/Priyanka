app.controller('BlogDetailController',function($routeParams,$scope,BlogService){
	alert('entering blogDetailController')
	var id=$routeParams.blogId
	console.log("Blog ID:"+id);
	$scope.blogPost={}
	$scope.comment={body:'',blogPost:{}}
	$scope.blogPost=BlogService.getBlogDetail(id)
	.then(function(response){
		console.log(response.data)
		console.log(response.status)
		$scope.blogPost=response.data;
		$scope.showComments=false;
	},function(response){
		console.log(response.status)
	})
	$scope.editPost=function(){
		$scope.isEditPost=true;
		BlogService.editPost()
	}
	$scope.getComments=function(blogId){
		$scope.showComments=true;
		console.log('getComments'+blogId)
		$scope.comments=BlogService.getComments(blogId)
		.then(function(response){
			$scope.comments=response.data;
			console.log($scope.comments);
			console.log('comments'+response.status);
		},function(response){
			console.log('comments'+response.status);
		})
	}
	
	$scope.addComment=function(){
		alert($scope.blogPost.id)
		$scope.comment.blogPost.id=$scope.blogPost.id;
		$scope.comment.blogPost=$scope.blogPost;
		alert($scope.comment.body)
		alert($scope.comment.blogPost)
		BlogService.addComment($scope.comment)
		.then(function(response){
			console.log(response.data)
			console.log(response.status)
		},function(response){
			console.log(response.status)
		})
	}
})