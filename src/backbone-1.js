var Order = {};

Order.Model = Backbone.Model.extend({
    idAttribute : "id"
});

Order.Collection = Backbone.Collection.extend({
    model : Order.Model,
    url : "/api/order"
});
