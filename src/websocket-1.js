var webSocket = new WebSocket("ws://localhost:8080/ws");

webSocket.onopen = function () { …
}
webSocket.onclose = function () { …
}

webSocket.onmessage = function (e) {
    var command = JSON.parse(e.data);

    console.log(command.protocol);
    console.log(command.command);
    console.log(command.data);
};

webSocket.send(JSON.stringify(command));
