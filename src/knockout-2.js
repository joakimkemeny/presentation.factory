var OrderList = Backbone.View.extend({

    render : function () {
        this.$el.html(orderListTemplate);

        Knockout.applyBindings({
            orders : Knockback.
                collectionObservable(this.collection)
        }, this.el);
    }
});
