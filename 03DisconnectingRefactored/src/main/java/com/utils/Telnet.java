/*
 * NOTICE - THE INFORMATION CONTAINED HEREIN IS PROPRIETARY AND CONFIDENTIAL
 * TO THALES AVIONICS, INC. (THALES) IN WHOLE OR IN PART AND SHALL NOT BE
 * USED OR DISCLOSED IN WHOLE OR IN PART WITHOUT FIRST OBTAINING THE WRITTEN
 * PERMISSION OF THALES.  Copyright © 2016, All Rights Reserved.
 */
package com.utils;

import java.io.*;
import org.apache.commons.net.telnet.TelnetClient;

public class TelnetClientConnection{

    private String host = "127.0.0.1";
    private String port = "28516";
    private InputStream reader;
    private OutputStream writer;
    private Telnetclient telnetClient = null;
    private static final int READ_TIMEOUT = 300000;


    public void setHostAndPort(String host, String port) {
        this.host = host;
        this.port = port;
    }


    public void connect() throws Exception{
        // Create a new telnet client
        telnetClient = new TelnetClient();
        TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler("VT100", false, false, true, false);
        SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);
        EchoOptionHandler echoopt = new EchoOptionHandler();
        try {
            // set telnet client options
            telnetClient.addOptionHandler(ttopt);
            telnetClient.addOptionHandler(gaopt);
            telnetClient.addOptionHandler(echoopt);
            telnetClient.connect(host, Integer.parseInt(port));
            telnetClient.setSoTimeout(READ_TIMEOUT);
            // Initialize the reader writer
            writer = telnetClient.getOutputStream();
            reader = telnetClient.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not connect, unable to open telnet session " + e.getMessage());
        }
    }

    public void disconnect() throws TransportException {
        try {
            telnetClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            throw new TransportException("Problems when disconnecting: " + e.getMessage());
        }
    }


    // static TelnetClient clientConnection = null;
    // private static final TelnetClientConnection INSTANCE = new TelnetClientConnection();
    //
    //
    // /*  Returns a instance of the class
    //     @return TelnetClientConnection instance
    // */
    //
    // public static TelnetClientConnection getInstance(){
    //     return INSTANCE;
    // }
    //
    //
    // /*  Removes the the line feed and new carriage return from a string
    //     @return String without '\r\n'
    // */
    //
    // public String ConvertTelnetToNormalString(String telnetString){
    //     return telnetString.replace("\n","").replace("\r","");
    // }
    //
    //
    // /*  Connects to the telnet server on the ip address
    //     @return boolean : whether the connection is ready
    // */
    //
    // public boolean ConnectToTelnetServer(String strHostIp, int intPortNumber) throws Exception{
    //     clientConnection = new TelnetClient();
    //     setTelnetClientOptionHandlers();
    //     clientConnection.connect(strHostIp, intPortNumber);
    //     clientConnection.setSoTimeout(500);
    //     return isConnectionReady();
    // }
    //
    //
    // /*  Sets the telnet optionHandlers
    //     @return None
    // */
    //
    // public void setTelnetClientOptionHandlers(){
    //     TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler("VT100", false, false, true, false);
    //     EchoOptionHandler echoopt = new EchoOptionHandler(true, false, true, false);
    //     SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);
    //     try{
    //         clientConnection.addOptionHandler(ttopt);
    //         clientConnection.addOptionHandler(echoopt);
    //         clientConnection.addOptionHandler(gaopt);
    //     }catch (InvalidTelnetOptionException e){
    //         System.err.println("Error registering option handlers: " + e.getMessage());
    //     }catch (IOException ex) {
    //         System.err.println("Error registering option handlers: " + ex.getMessage());
    //     }
    // }
    //
    //
    // /*  Checks if the connection to the server is ready and turns off the logs.
    //     For some reason the first command sent is appended with some garbage
    //     value, the server replies by saying the command
    //     not found. Until further debugging, sending a bogus command (test), whose
    //     response isn't important to check
    //     @return Boolean
    // */
    //
    // public boolean isConnectionReady(){
    //     if (ConvertTelnetToNormalString( receive() ).matches(".*INFO.*") ){
    //         getReponseForCommand("test");
    //         getReponseForCommand("log off");
    //         return true;
    //     }
    //     return false;
    // }
    //
    //
    // /*  Receives messages till a particular timeout
    //     The timeout is set in ConnectToTelnetServer method
    //     @return string MessageReceived
    // */
    //
    // public String receive(){
    //     String MessageReceived = "";
    //     InputStream instr = clientConnection.getInputStream();
    //     int buff;
    //     try{
    //         do{
    //             buff = instr.read();
    //             MessageReceived += (char)buff;
    //         }while(buff>=0);
    //     }catch(IOException e){
    //         return MessageReceived;
    //     }
    //     return null;
    // }
    //
    //
    // /*  Sends a string message to the telnet server
    //     @return None
    // */
    //
    // public void send(String strMessageToSend){
    //     strMessageToSend = strMessageToSend + "\n";
    //     OutputStream outStr = clientConnection.getOutputStream();
    //     try{
    //         byte[] buff = strMessageToSend.getBytes();
    //         outStr.write(buff, 0, buff.length );
    //         outStr.flush();
    //     }catch(IOException e){
    //         System.err.println("Exception while reading socket:" + e.getMessage());
    //     }
    // }
    //
    //
    // /*  Gets the reponse for a command
    //     Sends a message to the telnet connection and then waits to receive the
    //     message
    //     @return string message received
    // */
    //
    // public String getReponseForCommand(String strCommand){
    //     send(strCommand);
    //     return receive();
    // }
    //
    //
    // /*  Closes the telnet connection
    //     @reutrn None
    // */
    //
    // public void closeConnection() throws Exception{
    //     clientConnection.disconnect();
    // }
    //
    //
    // /*  Returns if the connection to the servver is made
    //     @return boolean connection status
    // */
    // public boolean isAlive(){
    //     return clientConnection.isConnected();
    // }
}
