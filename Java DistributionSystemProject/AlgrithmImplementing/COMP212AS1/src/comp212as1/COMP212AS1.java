/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp212as1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author KSK
 */
public class COMP212AS1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AS1Demmo();
        //AnalyseAlgorithm();
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
        System.out.println("===================================================");
        System.out.println("Start Task1========================================");
        System.out.println("===================================================");
        //======================================================================
        //Make the network a close loop
        System.out.println("===================================================");
        System.out.println("Inicial Stata");
        network1.connectNode();
        System.out.println("Nodes in total:" + network1.length());
        network1.printList();
        System.out.println("===================================================");

        System.out.println("===================================================");
        System.out.println("That is the test of Task1 LCR algorithm");
        network1.chooseLeaderBiggestTask1();
        network1.printList();
        System.out.println("===================================================");
    }

    public static void Task2HS(Nodes network1) {
        System.out.println("===================================================");
        System.out.println("Start Task2========================================");
        System.out.println("===================================================");
        //======================================================================
        //Make the network a close loop
        System.out.println("===================================================");
        System.out.println("Inicial Stata");
        network1.connectNode();
        System.out.println("Nodes in total:" + network1.length());
        network1.printList();
        System.out.println("===================================================");

        System.out.println("===================================================");
        System.out.println("That is the test of Task2 HS algorithm");
        network1.chooseLeaderBiggestTask2();
        network1.printList();
        System.out.println("===================================================");
    }

    private static void AS1Demmo() {
        //input n and a
        System.out.println("Please input the number of node \"n\" you want to add");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println("Plaese select the \"a\"");
        Scanner sc2 = new Scanner(System.in);
        int n2 = sc2.nextInt();
        System.out.println("Plaese select the ID gererate order");
        System.out.println("1: Random");
        System.out.println("2: Clockwise");
        System.out.println("3: CounterClockwise");
        Scanner sc3 = new Scanner(System.in);
        int n3 = sc3.nextInt();

        //genarate network
        switch (n3) {
            case 1:
                Nodes network = GrnerateNodesRandomID(n, n2);//GrnerateNodes(n,a)
                Nodes network2 = GrnerateNodesRandomID(n, n2);
                //Task1
                Task1LCR(network);

                //Task2
                Task2HS(network2);

                //machine test
                ResultLCR(network);
                ResultHS(network2);
                break;
            case 2:
                Nodes network3 = GrnerateNodesClockwiseID(n, n2);//GrnerateNodes(n,a)
                Nodes network4 = GrnerateNodesClockwiseID(n, n2);
                //Task1
                Task1LCR(network3);

                //Task2
                Task2HS(network4);

                //machine test
                ResultLCR(network3);
                ResultHS(network4);
                break;
            case 3:
                Nodes network6 = GrnerateNodesCounterclockwiseID(n, n2);//GrnerateNodes(n,a)
                Nodes network5 = GrnerateNodesCounterclockwiseID(n, n2);
                //Task1
                Task1LCR(network6);

                //Task2
                Task2HS(network5);

                //machine test
                ResultLCR(network6);
                ResultHS(network5);
                break;
        }
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
    private static void BriefResultLCR(Nodes network) {
        System.out.println("NODE  Rounds  Counter  MessageNum");
        System.out.println(network.NodeCount+" "+network.Rounds+" "+network.counter+" "+network.MessageNum);
    }
    private static void BriefResultHS(Nodes network) {
        System.out.println("NODE  Phase Rounds  MessageNum");
        System.out.println(network.NodeCount+" "+network.Phase+" "+network.counter+" "+network.MessageNum);
    }

    private static void AnalyseAlgorithm() {
        //genarate network
        int n=10;
        int n2=3;
        
        //LCR
        Nodes network1 = GrnerateNodesRandomID(n, n2);//GrnerateNodes(n,a)
        Nodes network1_1 = GrnerateNodesRandomID(n, n2);
        Nodes network1_2 = GrnerateNodesRandomID(n, n2);
        
        Nodes network1_3 = GrnerateNodesCounterclockwiseID(n, n2);
        Nodes network1_4 = GrnerateNodesClockwiseID(n, n2);
        
        //HS
        Nodes network2 = GrnerateNodesRandomID(n, n2);
        Nodes network2_1 = GrnerateNodesRandomID(n, n2);
        Nodes network2_2 = GrnerateNodesRandomID(n, n2);
        
        Nodes network2_3 = GrnerateNodesCounterclockwiseID(n, n2);
        Nodes network2_4 = GrnerateNodesClockwiseID(n, n2);

        //Task1
        Task1LCR(network1);
        Task1LCR(network1_1);
        Task1LCR(network1_2);
        Task1LCR(network1_3);
        Task1LCR(network1_4);
        
        //Task2
        Task2HS(network2);
        Task2HS(network2_1);
        Task2HS(network2_2);
        Task2HS(network2_3);
        Task2HS(network2_4);
        
        
        
        //machine test
        BriefResultLCR(network1);
        BriefResultLCR(network1_1);
        BriefResultLCR(network1_2);
        BriefResultLCR(network1_3);
        BriefResultLCR(network1_4);
        
        BriefResultHS(network2);
        BriefResultHS(network2_1);
        BriefResultHS(network2_2);
        BriefResultHS(network2_3);
        BriefResultHS(network2_4);
        
    }
}
