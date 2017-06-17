"use strict";
{

    function changePasswordController($scope,settingsService, utilsService) {

        const me = this;

        me.password = {};
        me.passwordNotMatched = false;
        
        me.resetMsg = function(){
        	if(me.passwordNotMatched == true)
        		me.passwordNotMatched = false;
        };

        me.chagePassword = function () {
            if (me.password.newPassword != me.password.confirmPassword) {
                me.passwordNotMatched = true;
            } else {
                settingsService.changePassword(me.password.currentPassword, me.password.newPassword).then(function (response) {
                    const message = {};
                    if (response.data == 1) {
                        message.title = "Password changed successfully!!";
                        message.description = "Please logout and loging again.!";
                    } else if (response.data == -1) {
                        message.title = "Failed to change password!!";
                        message.description = "Your current password is wrong, Please try again.!";
                    }
                    utilsService.alertPopup(message.title, message.description, null);
                },
                    function (response) {
                        // show error opoup
                        processError(response);
                    });
                //reset form
                me.resetForm();
            }

        };
        
        /**
         * function to reset form details
         */
        me.resetForm = function () {
            // reset form
        	me.password = {};
            $scope.changePasswordFrom.$setPristine();
            $scope.changePasswordFrom.$setUntouched();
        };

        function processError(response) {
            utilsService.processError(response);
        }

    };

    angular.module('payment').controller('changePasswordController', changePasswordController);
};
