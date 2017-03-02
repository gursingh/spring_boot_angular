app.factory('emailStatusService', function($scope, $http) {
	this.getEmailStatusList = function() {
		return $http({
			method : 'GET',
			url : 'http://gold.tkc.firm.in/responsestatus?page='
					+ $scope.currentPage + '&size=' + $scope.itemsPerPage
		});
	};
});

app.controller('emailStatusCtrl', function($scope, $filter, $http) {
	$scope.data = [];

	var temp = {
		"applicantName" : "Bell",
		"emailId" : "abc@gmail.com",
		"email_status" : true,
		"clicked" : false,
		"replied" : false
	};
	$scope.viewby = 10;
	$scope.totalItems = 0;
	$scope.currentPage = 1
	$scope.itemsPerPage = $scope.viewby;
	$scope.maxSize = 5; // Number of pager buttons to show

	$scope.setPage = function(pageNo) {
		$scope.currentPage = pageNo;
	};
	$scope.formatDate = function(date) {
		var dateOut = new Date(date);
		return dateOut;
	};
	$scope.getEmailStatusList = function() {
		return $http({
			method : 'GET',
			url : 'http://gold.tkc.firm.in/responsestatus?page='
					+ $scope.currentPage + '&size=' + $scope.itemsPerPage
		});
	};
	$scope.pageChanged = function() {
		var a = $scope.getEmailStatusList();
		a.success(function(response) {
			$scope.data = response.emailStatus;
			$scope.totalItems = response.totalCount;
		}).error(function(response) {
			logger.logError("Error in fetching inoculation data");
		});

	};
	$scope.setItemsPerPage = function(num) {
		$scope.itemsPerPage = num;
		$scope.currentPage = 1; // reset to first paghe
		$scope.pageChanged();
	}

	$scope.pageChanged();
});
