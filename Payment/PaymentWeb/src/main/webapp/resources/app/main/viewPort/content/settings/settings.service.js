"use strict";
{

    function settingsService($http) {

        const
            me = this;

        me.changePassword = function (currentPassword,newPassword) {

            const formDataString = $.param({ currentPassword: currentPassword, newpassword: newPassword }, true);

            return $http({
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
                },
                method: 'POST',
                url: 'changePassword',
                data: formDataString
            });

        };

    }
    ;

    angular.module('payment').service('settingsService', settingsService);

};
