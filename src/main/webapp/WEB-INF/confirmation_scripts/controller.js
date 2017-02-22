app.controller('confirmationFormCtrl', function($scope, $http, 
		$location) {
	$scope.cxoopts = [ {
		"text" : "Become a CXO",
		"value" : "Become a CXO"
	}, {
		"text" : "Assist a CXO",
		"value" : "Assist a CXO"
	} ];
	$scope.salaryopts = [ {
		"text" : "More than a Crore",
		"value" : "More than a Crore"
	}, {
		"text" : "Between 50 lakhs and 1 Cr",
		"value" : "Between 50 lakhs and 1 Cr"
	}, {
		"text" : "Less than 50 Lakhs",
		"value" : "Less than 50 Lakhs"
	} ];
	$scope.choice = {};
	$scope.timeopts = [ {
		"text" : "2 Hr/ week",
		"value" : "2 Hr/ week"
	}, {
		"text" : "1 hr a day",
		"value" : "1 hr a day"
	}, {
		"text" : "1 hr per day + 4 hrs on a weekend",
		"value" : "1 hr per day + 4 hrs on a weekend"
	} ];

	$scope.submitStudnetForm = function() {
		$scope.applicant = $scope.choice;
		var request = $http({
            method: "post",
            url: "http://gold.tkc.firm.in/responsedetail",
            data: $scope.applicant
        });
        // Store the data-dump of the FORM scope.
        request.success(
            function( html ) {
            	$location.path('/success');
	            $scope.$apply();
            }
        );

	};
});

app.controller('notFoundCtrl', function($scope, $filter, $http) {

});

app.controller('successStatusCtrl', function($scope, $filter, $http) {

});