// Plugin Styles
import 'angular-material/angular-material.min.css';
import 'lato-font/css/lato-font.css';
import 'angular-ui-notification/dist/angular-ui-notification.min.css';

//Plugins
import 'jquery/dist/jquery.min.js';
import 'bootstrap/dist/js/bootstrap.min.js';
import 'angular-auto-validate/dist/jcs-auto-validate.min.js';

//Routes
import './viewPort/routes/menuRoutes.js';

//interceptors
import "./viewPort/interceptors/csrfInterceptor.js";

// Controllers
import "./viewPort/view-port.controller.js";
import "./viewPort/menu/menu.controller.js";
import "./viewPort/content/content.controller.js";
import "./viewPort/content/home/home.controller.js";
import "./viewPort/content/billing/billing.controller.js";
import "./viewPort/content/view/view.controller.js";
import "./viewPort/content/view/viewBills/viewBills.controller.js";
import "./viewPort/content/view/viewItems/viewItems.controller.js";
import "./viewPort/content/manage/items/items.controller.js";
import "./viewPort/content/manage/manage.controller.js";
import "./viewPort/content/auth/userActicity.controller.js";
import "./viewPort/content/settings/changePassword/changePassword.controller.js";
import "./viewPort/content/systems/systems.controller.js";
//import "./viewPort/content/feed/feed.controller.js";

// Directives
import "./viewPort/view-port.directive.js";
import "./viewPort/content/home/home.directive.js";
import "./viewPort/menu/menu.directive.js";
import "./viewPort/content/content.directive.js";
import "./viewPort/directives/autoCompleteItemCompany.directive.js";
import "./viewPort/directives/autoCompleteItem.directive.js";
import "./viewPort/directives/systemStatusColor.directive.js";

// Services
import "./viewPort/content/billing/billing.service.js";
import "./viewPort/content/manage/items/items.service.js";
import "./viewPort/utils/util.service.js";
import "./viewPort/content/auth/userActivity.service.js";
import "./viewPort/content/settings/settings.service.js";
import "./viewPort/content/systems/systems.service.js";

// Filters
import "./viewPort/filters/matchResults.js";
import "./viewPort/filters/capacityFilter.js";

// socket configuration
import "./viewPort/content/socket/socketConfig.js";


