package com;

import org.apache.commons.net.telnet.TelnetClient;
import static com.google.common.base.Preconditions.checkState;
//import org.apache.commas.net.telnet ;
import java.util.Queue;
import java.util.logging.Logger;

public class SynchronousTelnetClient implements Telnet{
    public static final int TIME_TO_SLEEP = 200;
    private State currentState = State.NOT_CONNECTED;

//    private final Logger logger = Logger.getlogger("TelnetClient");

    public enum State {
        NOT_CONNECTED,
        CONNECTED,
        DISCONNECTED
    }

    public void connect() {
        checkIfNotConnected();
        System.out.println("is not connected, will connect");
        currentState = State.CONNECTED;
        willConnect();
    }

    private void willConnect() {
        socket = telnetCreator.getSocket(config.getHostName(), config.getHostPort());
        scanner = telnetCreator.getSocketReader(socket);
        writer = telnetCreator.getSocketWriter(socket);
        readerThread = telnetCreator.getThread(this);
        readerThread.start();
    }

    public void disconnect(){

    }
    public void send(String text){

    }


    private void checkIfNotConnected() {
        checkState(currentState != State.CONNECTED, "The client is already connected");
        checkState(currentState != State.DISCONNECTED, "The client has already been disconnected");
    }

    public void checkIfConnected() {
        checkState(currentState != State.NOT_CONNECTED, "The client has not yet been connected");
        checkState(currentState != State.DISCONNECTED, "The client has already been disconnected");
    }


    public static void main(String[] args){
        SynchronousTelnetClient client = new SynchronousTelnetClient();
        client.connect();
    }

}
