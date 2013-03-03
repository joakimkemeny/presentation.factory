define([
    "backbone",
    "knockback",
    "knockout",
    "modules/order/Order",
    "text!modules/order/OrderForm.html"
], function (Backbone, Knockback, Knockout, Order, orderFormTemplate) {
    "use strict";

    var OrderForm = Backbone.View.extend({

        render : function () {
            this.$el.html(orderFormTemplate);

            // Create the view model for the model.
            var vm = new Knockback.ViewModel(this.model, {
                requires : ["customer", "quantity"]
            });

            // Attach event handlers to the view model.
            var view = this;
            vm.close = function () {
                Backbone.history.navigate("order", true);
            };
            vm.save = function () {
                view.model.save();
                Backbone.history.navigate("order", true);
            };

            Knockout.applyBindings(vm, this.el);
        }
    });

    return OrderForm;
});
