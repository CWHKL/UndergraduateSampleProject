/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp212as2_clint2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 *
 * @author KSK
 */
public class ClientForLESimulate {
    private Socket clientSocket;
    private BufferedReader input;
    private PrintStream output;
    private Scanner scan=new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ClientForLESimulate client=new ClientForLESimulate();
        client.request();
        //client.test();
    }

    public void test() throws IOException {
        clientSocket = new Socket("localhost", 8080);
        output = new PrintStream(clientSocket.getOutputStream());
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //Stream<String> message3 = input.lines();
        //System.out.println(message3);
        
        String message3 = input.toString();
        System.out.println(message3);

        //input.lines();  // send to server
        //input.flush(); // here, it should get you going.
    }
    public void request() throws IOException{
        clientSocket=new Socket("localhost",8080);
        output=new PrintStream(clientSocket.getOutputStream());
        input=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //get the first message for connection
        String message0=input.readLine();
        System.out.println(message0);
        //while(clientSocket.isConnected()){
        for(int i=0;i<4;i++){
            //get message from server each round
            String message=input.readLine();
            System.out.println(message);
            //reply message to the server sach round
            String reply=scan.nextLine();
            output.println(reply);
        }
        //get message from server
        String message3 = input.readLine();
        System.out.println(message3);
        //get message from server
        String message = input.readLine();
        System.out.println(message);
        //reply message to the server sach round
        String reply = scan.nextLine();
        output.println(reply);

        if ("Y".equals(reply)) {
            String message7 = input.readLine();
            message7 = message7.replace("*"," \n");
            System.out.println(message7);
            
            String message8 = input.readLine();
            System.out.println(message8);
        }
        else{
            String message6 = input.readLine();
            System.out.println(message6);
        }
    }

}
