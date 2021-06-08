package jenkins.plugins.logstash.persistence;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class LogstashDao extends HostBasedLogstashIndexerDao {

  public LogstashDao(String logstashHostString, int logstashPortInt) {
    super(logstashHostString, logstashPortInt);
  }

  @Override
  public void push(String data) throws IOException {
    DatagramSocket logstashClientSocket = new DatagramSocket();
    byte[] buffer = data.getBytes(StandardCharsets.UTF_8);
    InetAddress address = InetAddress.getByAddress(getHost().getBytes(StandardCharsets.UTF_8));
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, getPort());
    logstashClientSocket.send(packet);
    logstashClientSocket.close();
  }
}