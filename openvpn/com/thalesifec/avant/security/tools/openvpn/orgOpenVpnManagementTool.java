package com.thalesifec.avant.security.tools.openvpn;

import com.thalesifec.avant.security.tools.openvpn.utils.TelnetClientConnection;
//

public class orgOpenVpnManagementTool{

  private static final orgOpenVpnManagementTool INSTANCE = new orgOpenVpnManagementTool();


  //

  private orgOpenVpnManagementTool(){
    System.out.println("in OpenVpnManagementTool");
  }


  //

  public static orgOpenVpnManagementTool getInstance(){
    return INSTANCE;
  }


  //

  public void runCommand(String[] args) throws Exception{
    System.out.println(args);
  }

  public static void showUsage(){
      System.out.println("orgOpenVpnManagementTool listClientConnections");
      System.out.println("orgOpenVpnManagementTool disconnectClientConnection [ip] [port]");
  }

// main execution of the tool
    public static void main(String[] args){
        try {
                if (args[0].equals("listClientConnections") || args[0].equals("disconnectClientConnection")){
                    TelnetClientConnection client = TelnetClientConnection.getInstance();
                    if (!client.ConnectToTelnetServer("127.0.0.1", 28516)){
                        System.out.println("Could not connect to the Openvpn management interface");
                    }
                    switch(args[0]){
                        case "listClientConnections":
                            client.getListOfConnections();
                            break;

                        case "disconnectClientConnection":
                            System.out.println(client.getReponseForCommand("kill " + args[1] + ":" + args[2]) );
                            break;
                    }
                }
                else{
                    showUsage();
                }
        }


        catch(Exception e){
            e.printStackTrace();
        }
    }
}
