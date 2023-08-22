package ru.mail.zinovev_dv.udp_test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {
    public final static int SERVICE_PORT = 5001;
    public final static int BUFFER_SIZE = 1024;

    public final static String SERVER_NAME = "localhost";

    public final static String TEST_MESSAGE = "Hello from UDP client";

    public static void main(String[] args) throws IOException {
        try(DatagramSocket clientSocket = new DatagramSocket()){

            // получаем адрес сервера
            InetAddress IPAddress = InetAddress.getByName(SERVER_NAME);
            // создаем буферы
            byte[] sendingDataBuffer = new byte[BUFFER_SIZE];
            byte[] receivingDataBuffer = new byte[BUFFER_SIZE];

            // сообщение преобразуем в байты и размещаем в буфере
            sendingDataBuffer = TEST_MESSAGE.getBytes();

            // создаем UDP пакет
            DatagramPacket sendingPacket = new DatagramPacket(
                            sendingDataBuffer, sendingDataBuffer.length,
                            IPAddress, SERVICE_PORT);

            // отправляем пакет
            clientSocket.send(sendingPacket);

            // получаем ответ от сервера
            DatagramPacket receivingPacket = new DatagramPacket(
                            receivingDataBuffer, receivingDataBuffer.length);
            clientSocket.receive(receivingPacket);

            String receivedData = new String(receivingPacket.getData());
            System.out.println("Sent from the server: " + receivedData);
            // закрыть соединение
            //clientSocket.close();
        }
        catch (SocketException e){
            e.printStackTrace();
        }
    }
}
