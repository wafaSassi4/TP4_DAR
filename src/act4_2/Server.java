package act4_2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    public static void main(String[] args) {
        try {
            // Définit le port sur lequel le serveur écoute
            int serverPort = 1234;

            // Crée une socket pour le serveur
            DatagramSocket serverSocket = new DatagramSocket(serverPort);

            // Affiche un message indiquant que le serveur est en attente de connexions
            System.out.println("Le serveur est en attente de connexions...");

            while (true) { // Boucle infinie pour gérer les connexions entrantes
                // Section pour recevoir le message du client
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                System.out.println("Message reçu du client " + clientAddress + ":" + clientPort + ": " + clientMessage);

                // Réponse au client
                String welcomeMessage = "Bienvenue " + clientMessage;
                byte[] sendData = welcomeMessage.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);

                // Section pour envoyer l'heure au client
                byte[] receiveHeure = new byte[1024];
                DatagramPacket receivePacketHeure = new DatagramPacket(receiveHeure, receiveHeure.length);
                serverSocket.receive(receivePacketHeure);

                String clientMessageHeure = new String(receivePacketHeure.getData(), 0, receivePacketHeure.getLength());
                System.out.println(clientMessageHeure);

                // Obtient l'heure actuelle
                String heureResponse = "Heure: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                byte[] sendHeure = heureResponse.getBytes();
                DatagramPacket sendPacket1 = new DatagramPacket(sendHeure, sendHeure.length, clientAddress, clientPort);
                serverSocket.send(sendPacket1);
            }
        } catch (Exception e) {
            e.printStackTrace(); // En cas d'erreur, affiche l'exception
        }
    }
}