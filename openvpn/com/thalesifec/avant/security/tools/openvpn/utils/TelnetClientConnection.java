package com.thalesifec.avant.security.tools.vpncontroller.tools;

import java.io.*;
import java.lang.Thread;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;

//

public class TelnetClientConnection{

    static TelnetClient clientConnection = null;
    private static final TelnetClientConnection INSTANCE = new TelnetClientConnection();

    // returns a instance of the class

    public static TelnetClientConnection getInstance(){
        return INSTANCE;
    }


    //

    public String ConvertTelnetToNormalString(String telnetString){
        return telnetString.replace("\n","").replace("\r","");
    }

    // Connects to the telnet server on the ip address

    public boolean ConnectToTelnetServer(String strHostIp, int intPortNumber) throws Exception{
        clientConnection = new TelnetClient();
        setTelnetClientOptionHandlers();
        clientConnection.connect(strHostIp, intPortNumber);
        clientConnection.setSoTimeout(500);
        return waitTillConnectionIsReady();
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


    // Method to check if the connection to the server is ready for exchange of
    // commands

    public boolean waitTillConnectionIsReady(){
        if (ConvertTelnetToNormalString( recieve() ).matches(".*INFO.*") ){
            getReponseForCommand("test 4");
            return true;
        }
        return false;
    }


    // Receive Method to be run on a separate therad

    public String recieve(){
        String MessageReceived = "";
        InputStream instr = clientConnection.getInputStream();
        int buff;
        try{
            do{
                buff = instr.read();
                MessageReceived += (char)buff;
            }while(buff>=0);
        }
        catch(IOException e){
            return MessageReceived;
        }
        return null;
    }


    // This method sends a string message to the telnet server

    public void send(String strMessageToSend){
        strMessageToSend = strMessageToSend + "\n";
        OutputStream outStr = clientConnection.getOutputStream();
        try{
            byte[] buff = strMessageToSend.getBytes();
            outStr.write(buff, 0, buff.length );
            outStr.flush();
        }
        catch(IOException e){
            System.err.println("Exception while reading socket:" + e.getMessage());
        }
    }


    // Get reponse for a command

    public String getReponseForCommand(String strCommand){
        send(strCommand);
        return recieve();
    }


    //

    public void getListOfConnections(){
        String statusResponse = getReponseForCommand("status");
        String regex = "OpenVPN client list.*(Common name.*)routing table.*";
        List<HashMap<String, String>> client = new ArrayList<HashMap<String, String>>();
        Pattern r = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = r.matcher(statusResponse);
        int numberOfClients;
        if (matcher.find()){
            String[] clientInfoNewLines = ( matcher.group(1).split("\r\n") );
            numberOfClients = clientInfoNewLines.length-1;
            String[] keys = clientInfoNewLines[0].split(",");
            for (int i = 1; i < numberOfClients+1; i++){
                HashMap<String, String> map = new HashMap<String, String>();
                String[] values = clientInfoNewLines[i].split(",");
                for (int j = 0; j < keys.length; j++){
                    map.put(keys[j], values[j]);
                }
                client.add(map);
            }
            for (int i = 0; i < client.size(); i++){
                System.out.println(client.get(i).get("Real Address"));
            }
        }
        else{
            System.out.println("No value Found ! ");
        }
    }

    // // Main Method
    //
    // public static void main(String[] args){
    //     try {
    //         TelnetClientConnection client = TelnetClientConnection.getInstance();
    //         if (!client.ConnectToTelnetServer("127.0.0.1", 28516)){
    //             System.out.println("Could not connect to the Openvpn management interface");
    //         }
    //         client.getListOfConnections();
    //     }
    //     catch(Exception e){
    //         e.printStackTrace();
    //     }
    // }

}
