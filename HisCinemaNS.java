import java.io.*;
import java.net.*;
import java.util.*;

public class HisCinemaNS{
  public static final String HER_CDN_IP = "127.0.0.1";
  public static final int SERVER_LISTENING_PORT = 40282;

  public static ArrayList<ResourceRecord> records;

  public static void main(String argv[]) throws Exception{
    records =  new ArrayList<ResourceRecord>();
    records.add(new ResourceRecord("video.hiscinema.com", "herCDN.com",     "R"));
    records.add(new ResourceRecord("herCDN.com",          "www.herCDN.com", "CN"));
    records.add(new ResourceRecord("www.herCDN.com",      "127.0.0.1",      "A"));

    DatagramSocket serverSocket = new DatagramSocket(SERVER_LISTENING_PORT);
    byte[] receiveData = new byte[1024];
    byte[] sendData = new byte[1024];

    // HisCinemaNS runs infinitely, listens for any incoming DNS queries
    while(true){
      // incoming packet arrives at the socket
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      serverSocket.receive(receivePacket);

      String requestURL = new String(receivePacket.getData());
      System.out.println("Incoming Message: " + requestURL);

      // Prepare a response
      InetAddress IPAddress = receivePacket.getAddress();
      int port = receivePacket.getPort();

      ///////////////////////// Take requestURL from UDP message, and resolve it ///////////////////////
      String response = resolve(requestURL);
      //////////////////////////////////////////////////////////////////////////////////////

      sendData = response.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

      // Send the Response
      serverSocket.send(sendPacket);
    }
  }

  private static String resolve(String url){
    System.out.println("Starting resolution...");
    String current = url;
    for(int i = 0; i < records.size(); i++){
      ResourceRecord record = records.get(i);
      if(current.trim().equals(record.name.trim())){
        current = record.value;
        // Translations always end in an A type
        if(record.type.equals("A")){
          System.out.println("Resolved: " + url + " -> " + current);
          return current;
        }
        // Reset the counter
        i = i - i - 1;
      }
    }
    System.out.println("Unable to resolve");
    return "";
  }
}
