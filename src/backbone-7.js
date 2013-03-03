    initialize : function () {
        this.listenTo(webSocket, "order:created", this.update);
        this.listenTo(webSocket, "order:updated", this.update);
    },

    update : function (data) { … }
});
