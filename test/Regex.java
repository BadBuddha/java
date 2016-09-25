import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Regex{
    public static void main(String args[]){

        String Input =  "OpenVPN CLIENT LIST\r\n"+
                        "Updated,Tue Sep  6 22:05:30 2016\r\n"+
                        "Common Name,Real Address,Bytes Received,Connected Since,Bytes Sent\r\n"+
                        "wca client,172.16.63.2:11111,10504,Tue Sep  6 21:57:47 2016,10871\r\n"+
                        "wca clent1,232.36.38.8:22222,10504,Tue Sep  6 21:57:47 2016,10871\r\n"+
                        "wca clent1,232.36.38.8:33333,10504,Tue Sep  6 21:57:47 2016,10871\r\n"+
                        "ROUTING TABLE\r\n"+
                        "Virtual Address,Common Name,Real Address,Last Ref\r\n"+
                        "10.8.0.6,wca client,172.16.63.2:43394,Tue Sep  6 22:05:12 2016\r\n"+
                        "GLOBAL STATS\r\n"+
                        "Max bcast/mcast queue length,0\r\n"+
                        "END\r\n";
        //HashMap map = new HashMap();
        List<HashMap<String, String>> client = new ArrayList<HashMap<String, String>>();
        String regex = "OpenVPN client list.*(Common name.*)routing table.*";
        Pattern r = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = r.matcher(Input);
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



//     private static final String REGEX = "foo(.*)";
// private static final String INPUT = "fooooooooooooooooo";
// private static Pattern r;
// private static Matcher matcher;
//
// public static void main( String args[] ){
//    r = Pattern.compile(REGEX);
//    matcher = r.matcher(INPUT);
//    if (matcher.find( )) {
//        System.out.println("Found value: " + matcher.group(1) );
//        //System.out.println("Found value: " + matcher.group(1) );
//        //System.out.println("Found value: " + matcher.group(2) );
//    } else {
//         System.out.println("NO MATCH");
//     }
// }


}
