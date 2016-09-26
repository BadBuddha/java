package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.Socket;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;

public class TelnetCreator(){
    public Thread getThread(Runnable runnable) {
        return new Thread(runnable);
    }

    @VisibleForTesting
    protected Socket doGetSocket(String hostName, int port) throws IOException {
        return new Socket(hostName, port);
    }

    public Scanner getSocketReader(Socket socket) {
        return new Scanner(getSocketInputStream(socket));
    }


    /*  Sets the telnet optionHandlers
        @return None
    */

    public void setOptionHandlers(){
        TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler("VT100", false, false, true, false);
        EchoOptionHandler echoopt = new EchoOptionHandler(true, false, true, false);
        SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);
        try{
            clientConnection.addOptionHandler(ttopt);
            clientConnection.addOptionHandler(echoopt);
            clientConnection.addOptionHandler(gaopt);
        }catch (InvalidTelnetOptionException e){
            System.err.println("Error registering option handlers: " + e.getMessage());
        }catch (IOException ex) {
            System.err.println("Error registering option handlers: " + ex.getMessage());
        }
    }

    public InputStream getSocketInputStream(Socket socket) {
        try {
            return socket.getInputStream();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public PrintWriter getSocketWriter(Socket socket) {
        return new PrintWriter(getSocketOutputStream(socket), true);
    }

    public OutputStream getSocketOutputStream(Socket socket) {
        try {
            return socket.getOutputStream();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
