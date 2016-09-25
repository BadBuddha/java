//package com.thalesifec.avant.security.tools;

import java.io.*;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;
//

public class TelnetClientConnection{

  static TelnetClient clientConnection = null;

  private static final TelnetClientConnection INSTANCE = new TelnetClientConnection();
  /* A Constructor
  */
  public void TelnetClientConnection(){
    System.out.println("TelnetClientConnection");
  }

  public static TelnetClientConnection getInstance(){
    return INSTANCE;
  }


  // Connects to the telnet server on the ip address

  public void ConnectToTelnetServer(String strHostIp, int intPortNumber) throws Exception{
    System.out.println( "host = " + strHostIp + ", Port = " + intPortNumber);
    clientConnection = new TelnetClient();
    setTelnetClientOptionHandlers();
    //client.connect(strHostIp, intPortNumber);
  }


  // Sets the the Option Handlers

  public void setTelnetClientOptionHandlers(){
    TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler("VT100", false, false, true, false);
    EchoOptionHandler echoopt = new EchoOptionHandler(true, false, true, false);
    SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);

    try{
        clientConnection.addOptionHandler(ttopt);
        clientConnection.addOptionHandler(echoopt);
        clientConnection.addOptionHandler(gaopt);
    }
    catch (InvalidTelnetOptionException e){
      System.err.println("Error registering option handlers: " + e.getMessage());
    }
    catch (IOException ex) {
     System.err.println("Error registering option handlers: " + ex.getMessage());
    }
  }

  public static void main(String[] args){
    try {
          // OpenVpnManagementTool tool = OpenVpnManagementTool.getInstance();
          // tool.ConnectToTelnetServer("127.0.0.1", 28516);
          TelnetClientConnection client = TelnetClientConnection.getInstance();
          client.ConnectToTelnetServer("127.0.0.1", 28516);


    } catch(Exception e){
      e.printStackTrace();
    }
  }


}
