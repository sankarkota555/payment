import angular from 'angular';
import bootstrap from 'angular-ui-bootstrap';
import angularAria from 'angular-aria';
import angularAnimate from 'angular-animate';
import angularMaterial from 'angular-material';
import tabs from 'angular-ui-bootstrap/src/tabs';
import accordion from 'angular-ui-bootstrap/src/accordion';
import datepicker from 'angular-ui-bootstrap/src/datepicker';
import timepicker from 'angular-ui-bootstrap/src/timepicker';
import modal from 'angular-ui-bootstrap/src/modal';
import uiRouter from 'angular-ui-router';

angular.module("payment", [
	bootstrap, 
	angularAria,
	angularAnimate,
	angularMaterial,
	tabs,
	uiRouter,
	accordion,
	datepicker,
	timepicker,
	modal,	
	'jcs-autoValidate'
]);

setTimeout(
  function asyncBootstrap() {
     angular.bootstrap( document, [ "payment" ] );
},( 2 * 1000 ));