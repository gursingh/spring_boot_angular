app.controller('emailStatusCtrl', function($scope, $filter,$http) {
 $scope.data = [
    {"applicantName":"Bell","emailId":"abc@gmail.com","email_status":true,"clicked": false,
        "replied": false}
];
  $scope.viewby = 10;
  $scope.totalItems = $scope.data.length;
  $scope.currentPage = 1
  $scope.itemsPerPage = $scope.viewby;
  $scope.maxSize = 5; //Number of pager buttons to show

  $scope.setPage = function (pageNo) {
    $scope.currentPage = pageNo;
  };

  $scope.pageChanged = function() {
    console.log('Page changed to: ' + $scope.currentPage);
  };

$scope.setItemsPerPage = function(num) {
  $scope.itemsPerPage = num;
  $scope.currentPage = 1; //reset to first paghe
}
$http({
	  method: 'GET',
	  url: 'http://gold.tkc.firm.in/responsestatus?page='+$scope.currentPage+'&size='+$scope.itemsPerPage
	}).then(function successCallback(response) {
	    $scope.data=response.data;
	  }, function errorCallback(response) {
	    // called asynchronously if an error occurs
	    // or server returns response with an error status.
	  });
});