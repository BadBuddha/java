package com.thalesifec.
import com.thalesifec.avant.security.tools.TelnetClientConnection;
//

public class OpenVpnManagementTool{



  private static final OpenVpnManagementTool INSTANCE = new OpenVpnManagementTool();


  //

  private OpenVpnManagementTool(){
    System.out.println("in OpenVpnManagementTool");
  }


  //

  public static OpenVpnManagementTool getInstance(){
    return INSTANCE;
  }


  //

  public void runCommand(String[] args) throws Exception{
    System.out.println(args);
  }


  //





// main execution of the tool

public static void main(String[] args) {
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
