package ru.mail.zinovev_dv.udp_test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    public final static int SERVICE_PORT = 5001;
    public final static int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        try{

            // Новый экземпляр сокета для ответов от клиента
            DatagramSocket serverSoket = new DatagramSocket(SERVICE_PORT);
            // буферы для хранения отправляемых и получаемых данных
            byte[] receivingDataBuffer = new byte[BUFFER_SIZE];
            byte[] sendingDataBuffer = new byte[BUFFER_SIZE];

            // экземпляр UDP пакета для хранения клиентских данных
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            System.out.println("Waiting for a client to connect...");
            while(true) {
                // получить данные от клиента и сохранить их
                serverSoket.receive(inputPacket);

                // вывести на экран отправленные клиентом данные
                String receivedData = new String(inputPacket.getData());
                System.out.println("Sent from the client: " + receivedData);

                // преобразуем в верхний регистр и преобразуем в байты
                sendingDataBuffer = receivedData.toUpperCase().getBytes();

                // получить IP адрес и порт клиента
                InetAddress senderAddress = inputPacket.getAddress();
                int senderPort = inputPacket.getPort();

                // создать UDP-пакет с данными, чтоб отправить их клиенту
                DatagramPacket outputPacket = new DatagramPacket(
                        sendingDataBuffer, sendingDataBuffer.length,
                        senderAddress, senderPort);
                // отправить пакет клиенту
                serverSoket.send(outputPacket);
            }
            // закрыть соединение
            //serverSoket.close();

        } catch (SocketException e){
            e.printStackTrace();
        }
    }
}
