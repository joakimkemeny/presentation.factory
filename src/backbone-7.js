    resetFromData : function (data) {
        this.reset(data);
    },

    updateFromData : function (data) {
        var model = this.get(data.id);
        if (model) {
            model.set(data);
        } else {
            this.add(data);
        }
    }
});
