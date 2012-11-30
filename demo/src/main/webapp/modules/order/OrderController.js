define([
    "jquery",
    "backbone",
    "modules/order/Order",
    "modules/order/OrderForm",
    "modules/order/OrderList"
], function ($, Backbone, Order, OrderForm, OrderList) {
    "use strict";

    var OrderRouter = Backbone.Router.extend({

        routes : {
            "" : "listOrders",
            "order" : "listOrders",
            "order/add" : "addOrder",
            "order/edit/:id" : "editOrder"
        },

        listOrders : function (ignoreForm) {

            var orderList = new OrderList({
                collection : Order.orders,
                el : $("#order-list")
            });
            orderList.render();

            if (!ignoreForm) {
                $("#order-form").hide("slide");
            }
        },

        addOrder : function () {

            this.listOrders(true);

            var orderForm = new OrderForm({
                collection : Order.orders,
                model : new Order.Model(),
                el : $("#order-form")
            });
            orderForm.render();

            $("#order-form").show("slide");
        },

        editOrder : function (id) {

            this.listOrders(true);

            Order.orders.promiseReset().done(function (orders) {
                var orderForm = new OrderForm({
                    collection : Order.orders,
                    model : orders.get(id).clone(),
                    el : $("#order-form")
                });
                orderForm.render();

                $("#order-form").show("slide");
            });
        }
    });

    return new OrderRouter();
});
