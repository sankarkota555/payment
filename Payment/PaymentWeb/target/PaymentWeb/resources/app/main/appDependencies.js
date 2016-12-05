// Plugin Styles
import 'angular-material/angular-material.min.css';
import 'lato-font/css/lato-font.css';

//Plugins
import 'jquery/dist/jquery.min.js';
import 'bootstrap/dist/js/bootstrap.min.js';
import 'angular-auto-validate/dist/jcs-auto-validate.min.js';

//Routes
import './viewPort/routes/menuRoutes.js';

// Controllers
import "./viewPort/view-port.controller.js";
import "./viewPort/menu/menu.controller.js";
import "./viewPort/content/content.controller.js";
import "./viewPort/content/home/home.controller.js";
import "./viewPort/content/billing/billing.controller.js";
import "./viewPort/content/viewBills/viewBills.controller.js";
import "./viewPort/content/items/items.controller.js";

//import "./viewPort/content/feed/feed.controller.js";

// Directives
import "./viewPort/view-port.directive.js";
import "./viewPort/content/home/home.directive.js";
import "./viewPort/menu/menu.directive.js";
import "./viewPort/content/content.directive.js";
//import "./viewPort/common/document-hide.directive.js";

// Services
import "./viewPort/content/billing/billing.service.js";
import "./viewPort/content/items/items.service.js";

// Filters
import "./viewPort/filters/matchResults.js";
