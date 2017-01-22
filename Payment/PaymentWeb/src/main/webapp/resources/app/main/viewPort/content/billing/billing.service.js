(function () {

    var billingService = function ($http) {

        var me = this;

        me.generateBill = function (bill) {

            var billData = { soldItems: [] };
            var totalAmount = 0;
            billData.customer = bill.customer;
            billData.generatedDate = bill.generatedDate;
            // billData.netAmount = 
            for (var index = 0; index < bill.items.length; index++) {
                var itemObj = { itemDetails: { id: null }, quantity: bill.items[index].quantity, soldPrice: bill.items[index].price };
                if (bill.items[index].selectedItem == null) {
                    itemObj.itemName = bill.items[index].searchItemText;
                    itemObj.itemDetails = { id:null, capacity:bill.items[index].capacity,price: bill.items[index].price, itemCompany: null , item: null };
                    itemObj.itemDetails.itemCompany = { companyName: bill.items[index].searchCompanyText };
                    itemObj.itemDetails.item = { itemName : bill.items[index].searchItemText };

                } else {
                    itemObj.itemDetails.id = bill.items[index].selectedCompany.id;
                }

                billData.soldItems.push(itemObj);
                totalAmount += bill.items[index].price;
            }
            billData.netAmount = totalAmount;
             console.log("final bill object prepared: ");
            console.log(billData);

            $http({
                url: 'saveBill',
                method: "POST",
                data: billData
            })
                .then(function (response) {
                    console.log("successfully saved");
                },
                function (response) {
                    console.log("failed to saved");
                });
            // events displaying from json 
            // $http.get('javax.faces.resource/assets/events.json').success(function (data) {
            //    me.events = data;
            // });
        };

    };

    angular.module('payment').service('billingService', billingService);

} ());