package com;


public interface Telnet {
    void connect();
    void disconnect();
    void send(String text);
}
