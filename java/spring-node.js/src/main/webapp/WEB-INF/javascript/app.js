(function() {
	var app = angular.module('pyramid', []);
	app.controller('TriangleController', triangleController);
	triangleController.$inject = [ '$scope', '$http' ];
	function triangleController($scope, $http) {
		var triangle = {
				sideA:0,
				sideB:0,
				sideC:0
		};
		var controller = this;

		$http.get('/test').then(function(response) {
			console.log("success");
			console.log(response);
		}, function(response) {
			console.log("error");
			console.log(response)
		});

		this.identify = function() {
			controller.triangle.triangleType="";
			controller.triangle.feedback="";
			console.log("Sending triangle:");
			console.log(controller.triangle);
			$http.post('/identify', controller.triangle).then(
					function(response) {
						console.log("success")
						console.log(response);
						controller.triangle.triangleType = response.data.triangleType;
					}, function(response) {
						console.log("error");
						console.log(response);
						controller.triangle.feedback = response.data.message;
					});
		};

	}
	;
})();
