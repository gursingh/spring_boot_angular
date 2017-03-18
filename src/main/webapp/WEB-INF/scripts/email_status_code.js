app.factory('emailStatusService', function($scope, $http) {
	this.getEmailStatusList = function() {
		return $http({
			method : 'GET',
			url : 'http://gold.tkc.firm.in/responsestatus?page='
					+ $scope.currentPage + '&size=' + $scope.itemsPerPage
		});
	};
});

app
		.controller(
				'emailStatusCtrl',
				function($scope, $filter, $http) {
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
					$scope.fromDate = new Date();
					$scope.isOpen = false;
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
									+ $scope.currentPage
									+ '&size='
									+ $scope.itemsPerPage
						});
					};
					$scope.pageChanged = function() {
						var a = $scope.getEmailStatusList();
						a
								.success(function(response) {
									$scope.data = response.emailStatus;
									$scope.totalItems = response.totalCount;
								})
								.error(
										function(response) {
											logger
													.logError("Error in fetching inoculation data");
										});

					};
					$scope.setItemsPerPage = function(num) {
						$scope.itemsPerPage = num;
						$scope.currentPage = 1; // reset to first paghe
						$scope.pageChanged();
					}

					$scope.pageChanged();
					$scope.today = function() {
						$scope.dt = new Date();
					};
					$scope.today();

					$scope.clear = function() {
						$scope.dt = null;
					};

					$scope.inlineOptions = {
						customClass : getDayClass,
						minDate : new Date(),
						showWeeks : true
					};

					$scope.dateOptions = {
						dateDisabled : disabled,
						formatYear : 'yy',
						maxDate : new Date(2020, 5, 22),
						minDate : new Date(),
						startingDay : 1
					};

					// Disable weekend selection
					function disabled(data) {
						var date = data.date, mode = data.mode;
						return mode === 'day'
								&& (date.getDay() === 0 || date.getDay() === 6);
					}

					$scope.toggleMin = function() {
						$scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null
								: new Date();
						$scope.dateOptions.minDate = $scope.inlineOptions.minDate;
					};

					$scope.toggleMin();

					$scope.open1 = function() {
						$scope.popup1.opened = true;
					};

					$scope.open2 = function() {
						$scope.popup2.opened = true;
					};

					$scope.setDate = function(year, month, day) {
						$scope.dt = new Date(year, month, day);
					};

					$scope.formats = [ 'dd-MMMM-yyyy', 'yyyy/MM/dd',
							'dd.MM.yyyy', 'shortDate' ];
					$scope.format = $scope.formats[0];
					$scope.altInputFormats = [ 'M!/d!/yyyy' ];

					$scope.popup1 = {
						opened : false
					};

					$scope.popup2 = {
						opened : false
					};

					var tomorrow = new Date();
					tomorrow.setDate(tomorrow.getDate() + 1);
					var afterTomorrow = new Date();
					afterTomorrow.setDate(tomorrow.getDate() + 1);
					$scope.events = [ {
						date : tomorrow,
						status : 'full'
					}, {
						date : afterTomorrow,
						status : 'partially'
					} ];

					function getDayClass(data) {
						var date = data.date, mode = data.mode;
						if (mode === 'day') {
							var dayToCheck = new Date(date)
									.setHours(0, 0, 0, 0);

							for (var i = 0; i < $scope.events.length; i++) {
								var currentDay = new Date($scope.events[i].date)
										.setHours(0, 0, 0, 0);

								if (dayToCheck === currentDay) {
									return $scope.events[i].status;
								}
							}
						}

						return '';
					}
				});
