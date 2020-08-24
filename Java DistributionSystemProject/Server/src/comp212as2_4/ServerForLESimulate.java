package comp212as2_4;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerForLESimulate {

    public static void main(String[] args) throws IOException {
        
        int portNumber;
        if (args.length < 1) {
            System.out.println("Warning: You have provided no arguments\nTrying to connect to the default port 8080...");
            portNumber = 8080;
        } else if (args.length == 1) {
            portNumber = Integer.parseInt(args[0]);
        } else {
            System.out.println("Warning: You have provided > 1 arguments\nTrying with the first argument to connect to a port...");
            portNumber = Integer.parseInt(args[0]);
        }
        while (true) {
            //in order to serve multiple clients but sequentially, one after the other
            try (
                    ServerSocket myServerSocket = new ServerSocket(portNumber);
                    Socket aClientSocket = myServerSocket.accept();
                    PrintWriter output = new PrintWriter(aClientSocket.getOutputStream(), true);
                    BufferedReader input = new BufferedReader(new InputStreamReader(aClientSocket.getInputStream()));) {
                //simulating service
                
                //ask for algrithm choose
                System.out.println("Connection established with a new client with IP address: " + aClientSocket.getInetAddress() + "\n");
                output.println("Server says: Hello Client " + aClientSocket.getInetAddress() + ". This is server " + myServerSocket.getInetAddress()
                        + " speaking. Our connection has been successfully established!");
                output.println("Server says: This is a LCR/HR simulator. Please input which algorithm you would like to simulate, \"1\" for LCR and \"0\" for HS");
                String inputLine1 = input.readLine();
                System.out.println("Received a new message from client " + aClientSocket.getInetAddress());
                System.out.println("Client says: " + inputLine1);

                //ask for node choose
                output.println("Server says: Please input the node number in the network");
                String inputLine2 = input.readLine();
                System.out.println("Received a new message from client " + aClientSocket.getInetAddress());
                System.out.println("Client says: " + inputLine2);

                //ask for alpha choose
                output.println("Server says: Please input which alpha you would like to genarate ID (alpha is a small interger)");
                String inputLine4 = input.readLine();
                System.out.println("Received a new message from client " + aClientSocket.getInetAddress());
                System.out.println("Client says: " + inputLine4);

                //ask for Id genaration choose
                output.println("Server says: Please select how to generate ID for nodes \"1\" for random and \"2\" for clock wise, \"3\" for counter clockwise");
                String inputLine3 = input.readLine();
                System.out.println("Received a new message from client " + aClientSocket.getInetAddress());
                System.out.println("Client says: " + inputLine3);

                int nodeNum = 3;
                nodeNum = Integer.parseInt(inputLine2);

                int alpha = 3;
                alpha = Integer.parseInt(inputLine4);

                int IDGenereatOrder = 1;
                if (null != inputLine3) {
                    switch (inputLine3) {
                        case "2":
                            IDGenereatOrder = 2;
                            break;
                        case "3":
                            IDGenereatOrder = 3;
                            break;
                        default:
                            IDGenereatOrder = 1;
                            break;
                    }
                }

                boolean algrithm = false;
                if ("1".equals(inputLine1)) {
                    algrithm = true;
                }

                String[] result = AS2SimulatingService(nodeNum, alpha, IDGenereatOrder, algrithm);
                //out put the results
                output.println("Server says: " + result[0] );//+ " <The query is completed and Connection with client " + aClientSocket.getInetAddress() + " is now closing...\\n>");
                System.out.println("Connection with client " + aClientSocket.getInetAddress());// + " is now closing...\n");
                
                //ask for Id genaration choose
                output.println("Server says: If you'd like to get while process log, input \"Y\". If you would like to end, input any other character");
                String inputLine5 = input.readLine();
                if ("Y".equals(inputLine5)) {
                    //run out put whole log
                    System.out.println(result[1]);
                    output.println(result[1]);
                    
                    //send message at the end
                    output.println(" <The query is completed and Connection with client " + aClientSocket.getInetAddress() + " is now closing...\n>");
                } else {
                    //send message at the end
                    output.println(" <The query is completed and Connection with client " + aClientSocket.getInetAddress() + " is now closing...\n>");
                }
                System.out.println("Received a new message from client " + aClientSocket.getInetAddress());
                System.out.println("Client says: " + inputLine5);

            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port "
                        + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static Nodes GrnerateNodesRandomID(int n, int a) {
        Nodes network = new Nodes();
        //Generate the ID pool from 1 to a*n
        int[] IDpool = new int[a * n];
        for (int i = 0; i < a * n; i++) {
            IDpool[i] = i + 1;
        }
        List list = new ArrayList();
        for (int i = 0; i < IDpool.length; i++) {
            list.add(IDpool[i]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < n; i++) {
            network.addNode((int) list.get(i), i + 1);
        }
        network.CorrectleaderID=network.ReadBiggestID();
        return network;
    }
    public static Nodes GrnerateNodesClockwiseID(int n, int a) {
        Nodes network = new Nodes();
        //Generate the ID pool from 1 to a*n
        int[] IDpool = new int[a * n];
        for (int i = 0; i < a * n; i++) {
            IDpool[i] = i + 1;
        }
        List list = new ArrayList();
        for (int i = 0; i < IDpool.length; i++) {
            list.add(IDpool[i]);
        }
        //System.out.println();
        Collections.shuffle(list);
        List list2 = list.subList(0,n);
        list2.sort(null);
        for (int i = 0; i < n; i++) {
            network.addNode((int) list2.get(i), i + 1);
        }
        network.CorrectleaderID=network.ReadBiggestID();
        return network;
    }
    public static Nodes GrnerateNodesCounterclockwiseID(int n, int a) {
        Nodes network = new Nodes();
        //Generate the ID pool from 1 to a*n
        int[] IDpool = new int[a * n];
        for (int i = 0; i < a * n; i++) {
            IDpool[i] = i + 1;
        }
        List list = new ArrayList();
        for (int i = 0; i < IDpool.length; i++) {
            list.add(IDpool[i]);
        }
        //System.out.println();
        Collections.shuffle(list);
        List list2 = list.subList(0,n);
        list2.sort(null);
        for (int i = n-1; i >=0; i--) {
            network.addNode((int) list2.get(i), i + 1);
        }
        network.CorrectleaderID=network.ReadBiggestID();
        return network;
    }

    public static int safeLongToInt(long l) {
        int i = (int) l;
        if ((long) i != l) {
            throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
        }
        return i;
    }

    public static void Task1LCR(Nodes network1) {
        network1.connectNode();
        network1.logList();
        network1.chooseLeaderBiggestTask1();
        network1.logList();
    }

    public static void Task2HS(Nodes network1) {
        network1.connectNode();
        network1.logList();
        network1.chooseLeaderBiggestTask2();
        network1.logList();
    }

    private static String[] AS2SimulatingService(int nodeNum, int alpha,int IDGenereatOrder,boolean algrithm) {
        String[] s = new String[2];
        //genarate network
        Nodes network;
        if (IDGenereatOrder == 1) {
            Nodes network1 = GrnerateNodesRandomID(nodeNum, alpha);
            network = network1;
        } else if (IDGenereatOrder == 2) {
            Nodes network2 = GrnerateNodesClockwiseID(nodeNum, alpha);//GrnerateNodes(n,a)
            network = network2;
        } else {
            Nodes network3 = GrnerateNodesCounterclockwiseID(nodeNum, alpha);//GrnerateNodes(n,a)
            network = network3;
        }
        if (algrithm) {
            Task1LCR(network);
            ResultLCR(network);
        } else {
            Task2HS(network);
            ResultHS(network);
        }
        s[0]="The network runs "+network.counter+" rounds and send "+network.MessageNum+" messages";
        s[1]=network.getlog();
        //String result="The network runs "+network.counter+" rounds and send "+network.MessageNum+" messages. Leader ID is:"+network.leaderID;
        return s;
    }

    private static void ResultLCR(Nodes network) {
        System.out.println("This is the altomatic static of run result of such network");
        System.out.println("There are "+network.NodeCount+" nodes in the network");
        System.out.println("Algorithm used "+network.Rounds+" rounds to elect the leader");
        System.out.println("Algorithm used "+network.counter+" parall execute rounds to make all nodes terminate");
        System.out.println("Algorithm passed "+network.MessageNum+" message in total");
        network.leaderID=network.head.leaderID;
        System.out.println("Leader ID: "+network.leaderID+"   Correct ID from sort of all ID:"+network.CorrectleaderID);
        
        if(network.leaderID==network.CorrectleaderID){
            System.out.println("The algorithm succeed to get the right leader");
        }else{
            System.out.println("The algorithm failed to get the right leader");
        }
    }
    private static void ResultHS(Nodes network) {
        System.out.println("This is the altomatic static of run result of such network");
        System.out.println("There are "+network.NodeCount+" nodes in the network");
        System.out.println("Algorithm used "+network.Phase+" phases to elect the leader");
        System.out.println("Algorithm used "+network.counter+" parall execute rounds to make all nodes terminate");
        System.out.println("Algorithm passed "+network.MessageNum+" message in total");
        network.leaderID=network.head.leaderID;
        System.out.println("Leader ID: "+network.leaderID+"   Correct ID from sort of all ID:"+network.CorrectleaderID);
        
        if(network.leaderID==network.CorrectleaderID){
            System.out.println("The algorithm succeed to get the right leader");
        }else{
            System.out.println("The algorithm failed to get the right leader");
        }
    }
}
