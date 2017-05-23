"use strict";
{
    /**
     * Filter to cretae capacity details array for one company
     */
    function capacityFilter() {

        return function (array) {

            if (array) {
                for (const object of array) {
                    if (object.capacity) {
                        return true;
                    }
                }
            }
            else {
                return false;
            }


            /*
                      const itemCompanies = new Array(1);
                      itemCompanies[0] = {};
                      // set company name
                      itemCompanies[0].companyName = array[0].itemCompany.companyName;
                      itemCompanies[0].hasCapacity = false;
                      itemCompanies[0].itemDetails = [];
          
                      for (let index in array) {
                          let detail = {};
                          detail.detailsId = array[index].id;
                          detail.capacity = array[index].capacity;
                          detail.price = array[index].price;
                          detail.quantity = array[index].quantity;
                          // push into array
                          itemCompanies[0].itemDetails.push(detail);
          
                          if (detail.capacity && !itemCompanies[0].hasCapacity) {
                              itemCompanies[0].hasCapacity = true;
                          }
          
                      }
          
                      return itemCompanies; */
        }
    };

    angular.module('payment').filter('capacityFilter', capacityFilter);

};
