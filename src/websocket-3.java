    public void onClose(int closeCode, String message) {
        webSocketManager.deregisterClient(this);
    }

    public void onMessage(String message) {
        webSocketManager.onCommand(message, this);
    }

    public void sendMessage(String message) throws IOException {
        if (connection != null) {
            connection.sendMessage(message);
        }
    }
}
