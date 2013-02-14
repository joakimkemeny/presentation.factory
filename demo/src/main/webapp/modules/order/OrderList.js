define([
    "jquery",
    "backbone",
    "knockback",
    "knockout",
    "factory/WebSocket",
    "modules/order/Order",
    "text!modules/order/OrderList.html"
], function ($, Backbone, Knockback, Knockout, webSocket, Order, orderListTemplate) {
    "use strict";

    var OrderList = Backbone.View.extend({

        render : function () {
            this.$el.html(orderListTemplate);

            Knockout.applyBindings({

                // Create the view model fo the collection.
                collection : Knockback.collectionObservable(this.collection, {
                    view_model : function (model) {

                        // Create the view model for each model.
                        return new Knockback.ViewModel(model);
                    }
                }),

                // Attach event handlers to the view model.
                toggleScales : function () {
                    $.ajax("api/scale/toggle");
                },
                add : function () {
                    Backbone.history.navigate("order/add", true);
                },
                edit : function (model) {
                    Backbone.history.navigate("order/edit/" + model.id(), true);
                },
                queue : function (model) {
                    webSocket.send("order:queue", { id : model.id() });
                },
                remove : function (model) {
                    webSocket.send("order:delete", { id : model.id() });
                }
            }, this.el);
        }
    });

    return OrderList;
});
