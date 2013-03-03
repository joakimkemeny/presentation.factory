var Order = {};

Order.Model = Backbone.Model.extend({
    urlRoot : "/api/order",

    defaults : {
        customer : null,
        quantity : 100
    }
});

Order.Collection = Backbone.Collection.extend({
    model : Order.Model,
    url : "/api/order"
});
