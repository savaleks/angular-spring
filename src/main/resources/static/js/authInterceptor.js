app.factory('AuthInterceptor', [function () {
    return {
        'request': function (config) {
            config.header = config.header || {};
            // btoa() function to get Base64 encoded string from user
             var encodeString = btoa("admin:password");
             config.header.Authorization = 'Basic ' + encodeString;
             return config;
        }
    }
}]);