app.controller('emailStatusCtrl', function($scope, $filter,$http) {
 $scope.data = [
    {"name":"Bell","email_id":"abc@gmail.com","email_status":true,"email_response":true,"email_date":"25-12-2016"},
    {"name":"Octavius","email_id":"abc@gmail.com","email_status":true,"email_response":true,"email_date":"25-12-2016"}
];
  $scope.viewby = 10;
  $scope.totalItems = $scope.data.length;
  $scope.currentPage = 4;
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
});