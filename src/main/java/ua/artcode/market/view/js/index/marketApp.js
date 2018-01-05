var app = angular.module('marketApp', []);
app.controller('marketAppCtrl', function($scope, $http) {
    $scope.loginTo = $http({
        method : "POST",
        url : "/login".concat(function(){
            var loginElement = angular.element(document.querySelector('#inputLogin'));

            return 'asd';

        }).concat(function(){
            return 'asd';
        })
    }).then(function mySuccess(response) {
        $scope.myWelcome = response.data;
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });

    alert("OK");
});