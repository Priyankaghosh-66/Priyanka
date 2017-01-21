app.controller('JobController',function($scope,$location,$routeParams,JobService){
	$scope.job={jobTitle:'',jobDescription:'',skillsRequired:'',salary:'',location:''}
	$scope.jobs={}
	$scope.saveJob=function(){
		console.log('entering save job in jobcontroller')
		JobService.saveJob($scope.job)
		.then(function(response){
			console.log("successfully inserted job details");
			alert("Posted job successfully");
			$location.path('/home')
		},function(response){
			console.log("failure "+response.status);
			if(response.status==401){
				$location.path('/login')
			}
			else{
				console.log(response.data.message);
				$location.path('/postJob')
				
			}
			
		})
	}
	function getAllJobs(){
		console.log('entering get All jobs')
		JobService.getAllJobs()
		.then(function(response){
			console.log(response.status);
			$scope.jobs=response.data;
		},function(response){
			console.log(response.status)
		})
	}
	getAllJobs();
	
	$scope.getJobDetail=function(jobId){
		JobService.getJobDetail(jobId)
		.then(function(response){
			$scope.jobDetail=response.data;
			console.log(response.status)
		},function(response){
			console.log(response.status)
		})
	}
})