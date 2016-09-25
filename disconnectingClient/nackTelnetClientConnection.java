//package com.thalesifec.avant.security.tools;

import java.io.*;
import java.lang.Thread;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;
//

public class TelnetClientConnection implements Runnable{

    //ReentrantLock recvLock = new ReentrantLock();
    Object recvLock = new Object();
    static TelnetClient clientConnection = null;
    static String strCommandReceived = null;
    private static final TelnetClientConnection INSTANCE = new TelnetClientConnection();
    boolean ready = false;

    /* A Constructor
    */

    public void TelnetClientConnection(){
        System.out.println("TelnetClientConnection");
    }


    // returns a instance of the class

    public static TelnetClientConnection getInstance(){
        return INSTANCE;
    }


    // Connects to the telnet server on the ip address

    public void ConnectToTelnetServer(String strHostIp, int intPortNumber) throws Exception{
        System.out.println( "host = " + strHostIp + ", Port = " + intPortNumber);
        clientConnection = new TelnetClient();
        setTelnetClientOptionHandlers();
        clientConnection.connect(strHostIp, intPortNumber);
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

    public void waitTillConnectionIsReady(){
        boolean connectionIsReady = false;
        do{
            connectionIsReady = !getReponseForCommand("test1 5").matches("ERROR: unknown command.*");
            try{
                //Thread.sleep(10);
                System.out.println("waiting");
            }
            catch (Exception e){
                System.out.println("Error "+ e.getMessage());
            }
        } while (!connectionIsReady);
        System.out.println("I'm out");
    }


    // This method starts a thread to receive output from the telnet server

    public void startReceivingIncomingMessages(){
        Thread reader = new Thread (new TelnetClientConnection());
        reader.start();
    }


    // Receive Method to be run on a separate therad

    public void run(){
        System.out.println("will try to recieve !");
        boolean end_loop = false;
        InputStream instr = clientConnection.getInputStream();
        try{
            byte[] buff = new byte[30*1024];
            int ret_read = 0;
            do{
                //recvLock.lock();
                ready = false;
                ret_read = instr.read(buff);
                if(ret_read > 0){

                    synchronized(recvLock){
                    ready = true;
                    System.out.print("received");
                    strCommandReceived = new String(buff, 0, ret_read);
                    recvLock.notifyAll();
                    try{
                    //Thread.sleep(100);
                    }catch (Exception e){}
                    }
                    //System.out.print("received :"+new String(buff, 0, ret_read));
                }
            } while (ret_read >= 0);
        }
        catch(IOException e){
            System.err.println("Exception while reading socket:" + e.getMessage());
        }
    }


    // This method sends a string message to the telnet server

    public void send(String strMessageToSend){
        //System.out.println("will try to send !");
        strMessageToSend = strMessageToSend + "\n";
        OutputStream outStr = clientConnection.getOutputStream();
        try{
            byte[] buff = strMessageToSend.getBytes();
            //System.out.println("sending : "+ buff[0] + "which is : " + new String(buff) );
            //System.out.println("length1 :" + buff.length );
            outStr.write(buff, 0, buff.length );
            outStr.flush();
        }
        catch(IOException e){
            System.err.println("Exception while reading socket:" + e.getMessage());
        }
    }


    //

    public String getReponseForCommand(String strCommand){
        try{
            send(strCommand);
            synchronized(recvLock){
                while (!ready){
                    System.out.println("waiting");
                    recvLock.wait();
                    System.out.println("done waiting1");
                 }
             }
            //Thread.wait();
            System.out.println("done waiting");
        }
        catch (Exception e){}
        //System.out.println("output : " + strCommandReceived);

        return strCommandReceived;
    }

    // Main Method

    public static void main(String[] args){
        try {
            // OpenVpnManagementTool tool = OpenVpnManagementTool.getInstance();
            // tool.ConnectToTelnetServer("127.0.0.1", 28516);
            TelnetClientConnection client = TelnetClientConnection.getInstance();
            client.ConnectToTelnetServer("127.0.0.1", 28516);
            client.startReceivingIncomingMessages();
            //client.send("status");
            //client.send("status");
            //client.send("status");
            //client.send("status");
            System.out.println("here1");
            System.out.println(client.getReponseForCommand("status") );
            System.out.println("here2");
            System.out.println(client.getReponseForCommand("status") );
            System.out.println(client.getReponseForCommand("status") );
            System.out.println(client.getReponseForCommand("status") );
            System.out.println(client.getReponseForCommand("status") );
            System.out.println(client.getReponseForCommand("status") );
            //System.out.println("here11");
            // Thread reader = new Thread (new TelnetClientConnection());
            // reader.start();
            //while (!client.isTheConnectionIsReady());
            //Thread.sleep(10);
            //client.getReponseForCommand("test 1");
            //client.waitTillConnectionIsReady();
            //client.receive();
            //Thread.sleep(5000);
            //System.out.println(client.getAStringReponseForCommand("status") );
            //System.out.println("here2");
            // client.send("exit");
            //Thread.sleep(5000);
            //String a = client.getReponseForCommand("status");
            //System.out.println("\na : " + a + "\n\n matches : " + a.matches(".*OpenVPN CLIENT LIST.*"));
            // client.send("exit");
            // Thread.sleep(3000);
            // client.send("exit");
            //clientConnection.sendAyt("status");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
