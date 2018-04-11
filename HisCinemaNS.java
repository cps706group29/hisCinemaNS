import java.io.*;
import java.net.*;
import java.util.*;

public class HisCinemaNS{
  public static ArrayList<ResourceRecord> records;

  public static void main(String argv[]) throws Exception{
    records =  new ArrayList<ResourceRecord>();
    records.add(new ResourceRecord("video.hiscinema.com", "herCDN.com",     "R"));
    records.add(new ResourceRecord("herCDN.com",          "www.herCDN.com", "CN"));
    records.add(new ResourceRecord("www.herCDN.com",      "127.0.0.1",      "A"));


    DatagramSocket serverSocket = new DatagramSocket(40282);
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


      //////////////////////////////////////////////////////////////////////////////////////
      String response = requestURL;
      sendData = response.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

      // Send the Response
      serverSocket.send(sendPacket);
    }
  }

  private String resolve(String url){
    return "";
  }
}
