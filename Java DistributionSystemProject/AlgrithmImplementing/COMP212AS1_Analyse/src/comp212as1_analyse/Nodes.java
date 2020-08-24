/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp212as1_analyse;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author KSK
 */
public class Nodes {

    //these overal variable used to check the answer of excute of algorism
    Node head = null;//head is the first node in the network
    int Rounds = 0;// used to record rounds to get leader
    int MessageNum = 0;
    int counter = 0;//used to record rounds
    int NodeCount=0;
    int leaderID;
    int CorrectleaderID;
    int Phase=0;
    
    //==========================================================================
    //basic linklist methods
    public void addNode(int d) {
        NodeCount++;
        Node newNode = new Node(d);// generate a node
        if (head == null) {//if it is the first node make it to head
            head = newNode;
            return;
        }
        Node tmp = head;
        while (tmp.listnext != null) {//to get the tail of the nodes
            tmp = tmp.listnext;
        }
        tmp.listnext = newNode;
        newNode.previous = tmp;
        tmp.next = newNode;
    }

    public void addNode(int d, int k) {
        NodeCount++;
        Node newNode = new Node(d, k);// generate a node
        if (head == null) {//if it is the first node make it to head
            head = newNode;
            return;
        }
        Node tmp = head;
        while (tmp.listnext != null) {//to get the tail of the nodes
            tmp = tmp.listnext;
        }
        tmp.listnext = newNode;
        newNode.previous = tmp;
        tmp.next = newNode;
    }

    public void connectNode() {
        Node tmp = this.head;
        while (tmp.listnext != null) {//to get the tail of the nodes
            tmp = tmp.listnext;
        }
        tmp.next = head;
        head.previous = tmp;
    }

    public int length() {
        int length = 0;
        Node tmp = head;
        while (tmp != null) {
            length++;
            tmp = tmp.listnext;
        }
        return length;
    }

    public void printListData() {
        Node tmp = head;
        while (tmp != null) {
            System.out.println(tmp.data);
            tmp = tmp.listnext;
        }
    }

    public void printListNum() {
        Node tmp = head;
        while (tmp != null) {
            System.out.println(tmp.myID);
            tmp = tmp.listnext;
        }
    }

    public void printListState() {
        Node tmp = head;
        while (tmp != null) {
            System.out.println(tmp.status);
            tmp = tmp.listnext;
        }
    }

    public void printList() {
        Node tmp = head;
        while (tmp != null) {
            //System.out.println("num:" + tmp.number + "  ID:" + tmp.myID + "  states:" + tmp.status + "  data:" + tmp.data + "  NextNode:" + tmp.next.number + "  PreviousNode:" + tmp.previous.number + "  Terminate:" + tmp.terminate + "  Leader in momery:" + tmp.leaderID);
            tmp = tmp.listnext;
        }
    }

    public void chooseLeaderBiggestTask1() 
    {
        Node tmp = head;
        Node count = head;

        //first round
        //prepare
        while (count != null) {
            tmp.NodeActTask1Start();
            tmp = tmp.next;
            count = count.listnext;
        }
        
        tmp = head;
        count = head;
        //send
        while (count != null) {
            if(tmp.send){
            tmp.NodeActTask1Send();}
            tmp = tmp.next;
            count = count.listnext;
        }
        counter++;
        //System.out.println("Parallel execute " + counter + " finish");
        
        //the round then
        while (!allTerminate()) {
            //all node recieve and process pack
            tmp = head;
            count = head;
            while (count != null) {
                if (!tmp.terminate) {
                    tmp.NodeActTask1Read();
                }
                tmp = tmp.next;
                count = count.listnext;
            }
            //all node send packs
            tmp = head;
            count = head;
            while (count != null) {
                if (!tmp.terminate) {
                    if(tmp.send){
                    tmp.NodeActTask1Send();
                    }
                }
                tmp = tmp.next;
                count = count.listnext;
            }
            //all node check terminate
            tmp = head;
            count = head;
            while (count != null) {
                if (!tmp.terminate) {
                    tmp.sendTerminate();
                }
                tmp = tmp.next;
                count = count.listnext;
            }
            counter++;
            //System.out.println("Parallel execute " + counter + " finish");
        }
    }

    public void chooseLeaderBiggestTask2() {
        //head.NodeActTask2_start();
        //head.next.NodeActTask2_start();

        Node tmp = head;
        Node count = head;

        //first round
        while (count != null) {
            tmp.NodeActTask2_start();
            tmp = tmp.next;
            count = count.listnext;
        }
        counter++;
        //System.out.println("Parallel execute " + counter + " finish");

        //the round then
        while (!allTerminate()) 
        {
            //all node recieve and process pack
            tmp = head;
            count = head;
            while (count != null) {
                if (!tmp.terminate) {
                    tmp.NodeActTask2_read();
                }
                tmp = tmp.next;
                count = count.listnext;
            }
            //all node send packs
            tmp = head;
            count = head;
            while (count != null) {
                if (!tmp.terminate) {
                    tmp.NodeActTask2_send();
                }
                tmp = tmp.next;
                count = count.listnext;
            }
            counter++;
            //System.out.println("Parallel execute " + counter + " finish");
        }
    }

    boolean allTerminate() {
        boolean allTerminate = true;
        Node tmp = head;
        while (tmp != null) {
            if (tmp.terminate == false) {
                return false;
            }
            tmp = tmp.listnext;
        }
        return allTerminate;
    }

    int ReadBiggestID() {
        int ID=0;
        Node tmp = head;
        while (tmp != null) {
            if(tmp.myID>=ID){
                ID=tmp.myID;
            }
            tmp = tmp.listnext;
        }
        return ID;
    }
    class Node {

    //basic attributes
    String dataIn; //recieve data
    String data; //save data
    String status = "unknown";
    int myID = 0; //ID of node
    int number = 0;//number of node
    int leaderID;//to store the leader ID
    boolean terminate = false;

    //attributes for Task1
    int numIn = 0;//number in this round
    int numTosend = 0;
    boolean terminateTosend = false;
    boolean terminateIn = false;
    boolean send = false;

    //attributes for TASK2
    int RecieveCounter = 0; 
    int Myphase=0;
    Queue<NodePackage> PackageRecievedCounterClockwise = new LinkedList<>();
    Queue<NodePackage> PackageRecievedClockwise = new LinkedList<>();
    Queue<NodePackage> PackagePrepareToSendCounterClockwise = new LinkedList<>();
    Queue<NodePackage> PackagePrepareToSendClockwise = new LinkedList<>();

    int Distance = 1;

    //Nodes relation
    Node listnext;   //save next node of the list (open loop)
    Node next;// save next node of the network (close loop)
    Node previous;   //save previous node

    Node() {
    }

    Node(int num, int num2) {
        this.myID = num;
        number = num2;
    }

    Node(int num) {
        this.myID = num;
    }

    public void NodeActTask1Start() {
        numTosend = myID;//pass id of itself to the next node
        leaderID = myID;
        send = true;
    }

    public void NodeActTask1Read() {
        //check terminate
        checkTerminateIn() ;
        
        //read ID get
        if (numIn > myID && numIn != this.leaderID) {
            numTosend = numIn;//pass the number if the input number is bigger than the number it self
            send = true;
            leaderID = numIn;
            this.status = "normal";
            this.leaderID = numIn;
        } else if (numIn == myID) {
            becomeLeader();
        } else {
        }
    }

    public void NodeActTask1Send() {
        //send ID
        next.numIn = this.numTosend;
        send = false;
        //System.out.println("Node:<num:" + number + " ID:" + myID + "> pass " + numIn + " to next node <num:" + next.number + " ID:" + next.myID + ">");
        //count the message num
        MessageNum++;
    }
    
    public void NodeActTask2_start() {
        NodePackage a = new NodePackage(this.myID, this.Distance, "out");
        NodePackage b = new NodePackage(this.myID, this.Distance, "out");
        this.PackagePrepareToSendClockwise.offer(a);
        this.PackagePrepareToSendCounterClockwise.offer(b);
        //send counter clockwise
        NodeActTask2_send();
    }

    public void NodeActTask2_read() {
        //check terminate message get
        checkTerminateIn();
        
        //read counter clockwise message
        for (NodePackage readTmp : this.PackageRecievedCounterClockwise) {
            //System.out.println("Node:" + this.number + " read<" + readTmp.ID + "," + readTmp.hopCount + "," + readTmp.status + ">");
            if ("leader".equals(readTmp.status)) {
                this.leaderID = readTmp.ID;
                this.status = "normal";
                this.PackagePrepareToSendClockwise.offer(readTmp);
                this.PackagePrepareToSendCounterClockwise.offer(readTmp);
                continue;
            }
            if (readTmp.ID > this.myID) {
                this.leaderID=readTmp.ID;
                //System.out.println("=================================");
                if (readTmp.hopCount == 0) {
                    //change direction when counter is 1(the package has got to the finial distance of this time)
                    readTmp.changeState("in");
                    this.PackagePrepareToSendClockwise.offer(readTmp);
                } else {
                    this.PackagePrepareToSendCounterClockwise.offer(readTmp);
                }
            } else if (readTmp.ID == this.myID) {
                //System.out.println("=================================");
                if (null != readTmp.status) {
                    switch (readTmp.status){
                        case "out": {
                            this.becomeLeader();
                            break;
                        }
                        case "in": {
                            //prepare next package
                            RecieveCounter++;
                            break;
                        }
                    }
                }
            }
        }
        //clear the queue
        this.PackageRecievedCounterClockwise.clear();
        //read clockwise message
        for (NodePackage readTmp : this.PackageRecievedClockwise) {
            //System.out.println("Node:" + this.number + " read<" + readTmp.ID + "," + readTmp.hopCount + "," + readTmp.status + ">");
            if ("leader".equals(readTmp.status)) {
                this.leaderID = readTmp.ID;
                this.status = "normal";
                this.PackagePrepareToSendClockwise.offer(readTmp);
                this.PackagePrepareToSendCounterClockwise.offer(readTmp);
                continue;
            }
            if (readTmp.ID > this.myID) {
                if (readTmp.hopCount == 0) {
                    //change direction when counter is 1(the package has got to the finial distance of this time)
                    readTmp.changeState("in");
                    this.PackagePrepareToSendCounterClockwise.offer(readTmp);
                } else {
                    this.PackagePrepareToSendClockwise.offer(readTmp);
                }
            } //System.out.println(readTmp.ID+"  "+myID);
            //readTmp.ID == this.myID
            else if (readTmp.ID == this.myID) {
                switch (readTmp.status) {
                    case "out": {
                        this.becomeLeader();
                    }
                    case "in": {
                        //prepare next package
                        RecieveCounter++;
                    }
                }
            } else {
            }
        }
        //clear the queue
        this.PackageRecievedClockwise.clear();

        //prepare the next test for those node succeed in both feedback
        if (RecieveCounter == 2) {
            this.prepareNextPackage();
        }
        this.RecieveCounter = 0;
    }

    public void NodeActTask2_send() {
        //send counter clockwise
        for (NodePackage sendTmp : this.PackagePrepareToSendCounterClockwise){
            sendTmp.hopCount--;
            //System.out.println("Node:" + this.number + " sends<" + sendTmp.ID + "," + sendTmp.hopCount + "," + sendTmp.status + "> to Node:" + previous.number);
            sendPackageCounterClockWise(sendTmp);
        }
        //send clockwise
        for (NodePackage sendTmp2 : this.PackagePrepareToSendClockwise){
            sendTmp2.hopCount--;
            //System.out.println("Node:" + this.number + " sends<" + sendTmp2.ID + "," + sendTmp2.hopCount + "," + sendTmp2.status + "> to Node:" + next.number);
            sendPackageClockWise(sendTmp2);
        }

        this.PackagePrepareToSendCounterClockwise.clear();
        this.PackagePrepareToSendClockwise.clear();
        sendTerminate();
    }

    private void sendPackageClockWise(NodePackage get) {
        this.next.PackageRecievedClockwise.offer(get);
        //count the message num
        MessageNum++;
    }

    private void sendPackageCounterClockWise(NodePackage get) {
        this.previous.PackageRecievedCounterClockwise.offer(get);
        //count the message num
        MessageNum++;
    }

    private void prepareNextPackage() {
        this.Distance *= 2;
        Myphase++;
        NodePackage a = new NodePackage(this.myID, this.Distance, "out");
        NodePackage b = new NodePackage(this.myID, this.Distance, "out");
        this.PackagePrepareToSendClockwise.offer(a);
        this.PackagePrepareToSendCounterClockwise.offer(b);
    }

    private void becomeLeader() {
        this.leaderID=this.myID;
        int k=this.Distance;
        Phase=this.Myphase;
        status = "Leader";//if get the number of the node it self so the node is the biggest node in the network
        this.terminateTosend=true;
        this.PackagePrepareToSendClockwise.clear();
        this.PackagePrepareToSendCounterClockwise.clear();
        //System.out.println("Node:<num:" + number + " ID:" + myID + "> chosen as the leader");
        Rounds=counter+1;
    }

    void checkTerminateIn() {
        if (this.terminateIn) //ensure terminate once
        {
            this.terminateTosend=true;
        }
    }

    public void sendTerminate() {
        if(this.terminateTosend){
            this.next.terminateIn=this.terminateTosend;
            this.terminate=true;
            //System.out.println("Node:<num:" + number + " ID:" + myID + "> told Node:<num:" + next.number + " ID:" + next.myID + "> to terminate");
        }
    }
    }
}