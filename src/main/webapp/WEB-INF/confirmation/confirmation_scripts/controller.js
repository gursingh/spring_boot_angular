app.controller('confirmationFormCtrl', function($scope, $http, $routeParams) {
	$scope.cxo = [ {
		text : "Become a CXO",
		value : "Become a CXO"
	}, {
		text : "Assist a CXO",
		value : "Assist a CXO"
	} ];
	$scope.salary = [ {
		text : "More than a Crore",
		value : "More than a Crore"
	}, {
		text : "Between 50 lakhs and 1 Cr",
		value : "Between 50 lakhs and 1 Cr"
	}, {
		text : "Less than 50 Lakhs",
		value : "Less than 50 Lakhs"
	} ];
	$scope.time = [ {
		text : "2 Hr/ week",
		value : "2 Hr/ week"
	}, {
		text : "2 Hr/ week",
		value : "2 Hr/ week"
	}, {
		text : "1 hr per day + 4 hrs on a weekend",
		value : "1 hr per day + 4 hrs on a weekend"
	} ];

	$scope.cxoresult = {};
	$scope.salaryresult = {};
	$scope.timeresult = {};
	var userUniqueId = $routeParams.id;
	$scope.applicant = {};
	var applicantData = {};
	applicantData.name = $scope.name;
	applicantData.time = $scope.timeresult.value;
	applicantData.salary = $scope.salaryresult.value;
	applicantData.id=userUniqueId;
	$scope.applicant=applicantData;
	$scope.submitStudnetForm = function() {
		// send $http request to save student

	};
});

app.controller('notFoundCtrl', function($scope, $filter, $http) {

});

app.controller('successStatusCtrl', function($scope, $filter, $http) {

});