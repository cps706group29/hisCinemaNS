import java.io.*;
import java.net.*;
import java.util.*;

public class HisCinemaNS{
  public static final String HER_CDN_IP = "127.0.0.1";
  public static final int HIS_CINEMA_NS_LISTENING_PORT = 40282;

  public static ArrayList<ResourceRecord> records;

  public static void main(String argv[]) throws Exception{
    records =  new ArrayList<ResourceRecord>();
    records.add(new ResourceRecord("video.hiscinema.com", "herCDN.com",     "R"));

    DatagramSocket serverSocket = new DatagramSocket(HIS_CINEMA_NS_LISTENING_PORT);
    byte[] receiveData = new byte[1024];
    byte[] sendData = new byte[1024];

    // HisCinemaNS runs infinitely, listens for any incoming DNS queries
    System.out.println("Listening for Requests...");
    while(true){
      // incoming packet arrives at the socket
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      serverSocket.receive(receivePacket);

      String requestURL = new String(receivePacket.getData());
      System.out.println("Incoming Message: " + requestURL);

      // Prepare a response
      InetAddress IPAddress = receivePacket.getAddress();
      int port = receivePacket.getPort();

      ///////////////////////// RESOLVE REQUEST ///////////////////////
      String response = resolve(requestURL);
      ////////////////////////////////////////////////////////////////

      sendData = response.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

      // Send the Response
      serverSocket.send(sendPacket);
    }
  }

  /**
    The resource record must take
      video.hiscinema.com
    and return the recirection for
      herCDN.com
  **/
  private static String resolve(String url){
    System.out.println("--------------------------------------------");
    System.out.println("Resolving: " + url);
    ResourceRecord record = records.get(0);
    if(url.trim().equals(record.name)){
      String redirect = record.value;
      System.out.println("Resolved:  " + redirect);
      System.out.println("--------------------------------------------");
      return redirect;
    }
    System.out.println("Could not resolve - No record for: " + url);
    System.out.println("--------------------------------------------");
    return "";


    // System.out.println("Resolving: " + url);
    // String current = url.trim();
    // for(int i = 0; i < records.size(); i++){
    //   ResourceRecord record = records.get(i);
    //   if(current.equals(record.name)){
    //     current = record.value;
    //     // Translations always end in an A type
    //     if(record.type.equals("A")){
    //       System.out.println("Resolved: " + current);
    //       return current;
    //     }
    //     // Reset the counter
    //     i = i - i - 1;
    //   }
    // }
    // System.out.println("Could not resolve");
    // return "";
  }
}
