/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp212as1;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author KSK
 */
//this page is used to save backups
public class Savetmp {
    //        private void NodeActTask2_start(){
//            NodePackage a=new NodePackage(this.myID,this.Distance,"out");
//            this.PackagePrepareToSendClockwise.add(a);
//            this.PackagePrepareToSendCounterClockwise.add(a);
//            //send counter clockwise
//            while(!this.PackagePrepareToSendCounterClockwise.isEmpty()){
//                //process the first pack
//                NodePackage np=this.PackagePrepareToSendCounterClockwise.get(0);
//                sendPackageCounterClockWise(np);
//                //print out the pass result
//                System.out.println("Node:" + this.number + " pass<"+np.ID+","+np.hopCount+","+np.status+"> to Node:"+previous.number);
//                this.PackagePrepareToSendCounterClockwise.remove(0);
//            }
//            //send clockwise
//            while(!this.PackagePrepareToSendClockwise.isEmpty()){
//                //process the first pack
//                NodePackage np=this.PackagePrepareToSendClockwise.get(0);
//                System.out.println("Node:" + this.number + " pass<"+np.ID+","+np.hopCount+","+np.status+"> to Node:"+next.number);
//                sendPackageClockWise(this.PackagePrepareToSendClockwise.get(0));
//                this.PackagePrepareToSendClockwise.remove(0);
//            }
//        }
        
//        private void NodeActTask2_read() {
//            NodePackage readTmp;
//            boolean c=this.PackageRecievedCounterClockwise.isEmpty();
//            boolean cc=this.PackageRecievedClockwise.isEmpty();
//            //conterClockwise
//            while(!c){
//                //process the first pack
//                readTmp=this.PackageRecievedCounterClockwise.get(0);
//                //used for test========================================================================================
//                System.out.println("start read");
//                System.out.println("Node:" + this.number + " read<"+readTmp.ID+","+readTmp.hopCount+","+readTmp.status+">");
//                
//                if(readTmp.status=="leader"){
//                    this.leaderID=readTmp.ID;
//                    this.status="normal";
//                    this.PackagePrepareToSendClockwise.add(readTmp);
//                    this.PackagePrepareToSendCounterClockwise.add(readTmp);
//                    this.PackageRecievedCounterClockwise.remove(0);
//                    continue;
//                }
//                if(readTmp.ID>this.myID){
//                    if(readTmp.hopCount==1){
//                        //change direction when counter is 1(the package has got to the finial distance of this time)
//                        readTmp.hopCount--;
//                        readTmp.changeState("in");
//                        this.PackagePrepareToSendClockwise.add(readTmp);
//                        this.PackageRecievedCounterClockwise.remove(0);
//                        continue;
//                    }
//                    else{
//                        readTmp.hopCount--;
//                        this.PackagePrepareToSendCounterClockwise.add(readTmp);
//                        
//                        this.PackageRecievedCounterClockwise.remove(0);
//                        continue;
//                    }
//                }
//                else if (readTmp.ID == this.myID) {
//                    if (readTmp.status == "out") {
//                        this.status = "leader";
//                        //speradLeader leade message
//                        NodePackage a=new NodePackage(this.myID,-1,"leader");
//                        //send terminate massage to both side nodes
//                        this.PackagePrepareToSendClockwise.add(a);
//                        this.PackagePrepareToSendCounterClockwise.add(a);
//                        this.PackageRecievedCounterClockwise.remove(0);
//                        continue;
//                    }
//                    else if (readTmp.status == "in") {
//                        //prepare next package
//                        this.Distance*=2;
//                        NodePackage a=new NodePackage(this.myID,this.Distance,"out");
//                        this.PackagePrepareToSendClockwise.add(a);
//                        this.PackageRecievedCounterClockwise.remove(0);
//                        continue;
//                    }
//                }
//                this.PackageRecievedCounterClockwise.remove(0);
//            }
//            //clockwise
//            while(!cc){
//                //process the first pack
//                readTmp=this.PackageRecievedCounterClockwise.get(0);
//                //used for test========================================================================================
//                System.out.println("start read");
//                System.out.println("Node:" + this.number + " read<"+readTmp.ID+","+readTmp.hopCount+","+readTmp.status+">");
//                
//                if(readTmp.status=="leader"){
//                    this.leaderID=readTmp.ID;
//                    this.status="normal";
//                    this.PackagePrepareToSendClockwise.add(readTmp);
//                    this.PackagePrepareToSendCounterClockwise.add(readTmp);
//                    this.PackageRecievedClockwise.remove(0);
//                    continue;
//                }
//                if(readTmp.ID>this.myID){
//                    if(readTmp.hopCount==1){
//                        //change direction when counter is 1(the package has got to the finial distance of this time)
//                        readTmp.hopCount--;
//                        readTmp.changeState("in");
//                        this.PackagePrepareToSendCounterClockwise.add(readTmp);
//                        
//                        this.PackageRecievedClockwise.remove(0);
//                        continue;
//                    }
//                    else{
//                        readTmp.hopCount--;
//                        this.PackagePrepareToSendClockwise.add(readTmp);
//                        
//                        this.PackageRecievedClockwise.remove(0);
//                        continue;
//                    }
//                }
//                else if (readTmp.ID == this.myID) {
//                    if (readTmp.status == "out") {
//                        this.status = "leader";
//                        //speradLeader leade message
//                        NodePackage a=new NodePackage(this.myID,-1,"leader");
//                        //send terminate massage to both side nodes
//                        this.PackagePrepareToSendClockwise.add(readTmp);
//                        this.PackagePrepareToSendCounterClockwise.add(readTmp);
//                        this.PackageRecievedClockwise.remove(0);
//                        continue;
//                    }
//                    else if (readTmp.status == "in") {
//                        //prepare next package
//                        this.Distance*=2;
//                        NodePackage a=new NodePackage(this.myID,this.Distance,"out");
//                        this.PackagePrepareToSendCounterClockwise.add(a);
//                        this.PackageRecievedClockwise.remove(0);
//                        continue;
//                    }
//                }
//                this.PackageRecievedClockwise.remove(0);
//            }
//        }
//        
//        private void NodeActTask2_read2() {
//            NodePackage readTmp;
//            //conterClockwise
//            while(!this.PackageRecievedCounterClockwise.isEmpty()){
//                //process the first pack
//                readTmp=this.PackageRecievedCounterClockwise.get(0);
//                //used for test========================================================================================
//                System.out.println("start read");
//                System.out.println("Node:" + this.number + " read<"+readTmp.ID+","+readTmp.hopCount+","+readTmp.status+">");
//                
//                if(readTmp.ID>this.myID){
//                    if(readTmp.status=="leader"){
//                        this.leaderID=readTmp.ID;
//                        this.status="normal";
//                        this.terminate=true;
//                    }
//                    else if(readTmp.hopCount==1){
//                        //change direction when counter is 1(the package has got to the finial distance of this time)
//                        readTmp.hopCount--;
//                        readTmp.changeState("in");
//                        this.PackagePrepareToSendClockwise.add(readTmp);
//                    }
//                    else{
//                        readTmp.hopCount--;
//                        this.PackagePrepareToSendCounterClockwise.add(readTmp);
//                    }
//                }
//                else if (readTmp.ID == this.myID) {
//                    if (readTmp.status == "out") {
//                        this.status = "leader";
//                        //speradLeader leade message
//                        NodePackage a=new NodePackage(this.myID,-1,"leader");
//                        //send terminate massage to both side nodes
//                        sendPackageCounterClockWise(a);
//                        sendPackageClockWise(a);
//                        //finally terminate
//                        this.terminate=true;
//                    }
//                    else if (readTmp.status == "in") {
//                        //prepare next package
//                        this.Distance*=2;
//                        NodePackage a=new NodePackage(this.myID,this.Distance,"out");
//                        this.PackagePrepareToSendClockwise.add(a);
//                    }
//                }
//                else if (readTmp.ID < this.myID) {
//                    //do nothing
//                }
//                this.PackageRecievedCounterClockwise.remove(0);
//            }
//            //clockwise
//            while(!this.PackageRecievedClockwise.isEmpty()){
//                //process the first pack
//                readTmp=this.PackageRecievedClockwise.get(0);
//                //used for test
//                System.out.println("start read");
//                readTmp.Print();
//                
//                System.out.println("Node:" + this.number + " read<"+readTmp.ID+","+readTmp.hopCount+","+readTmp.status+">");
//                if(readTmp.ID>this.myID){
//                    if(readTmp.hopCount==1){
//                        //change direction when counter is 1(the package has got to the finial distance of this time)
//                        readTmp.hopCount--;
//                        readTmp.changeState("in");
//                        this.PackagePrepareToSendCounterClockwise.add(readTmp);
//                    }
//                    else{
//                        readTmp.hopCount--;
//                        this.PackagePrepareToSendClockwise.add(readTmp);
//                    }
//                }
//                else if (readTmp.ID == this.myID) {
//                    if (readTmp.status == "out") {
//                        this.status = "leader";
//                        //speradLeader leade message
//                        NodePackage a=new NodePackage(this.myID,-1,"leader");
//                        //send terminate massage to both side nodes
//                        sendPackageCounterClockWise(a);
//                        sendPackageClockWise(a);
//                        //finally terminate
//                        this.terminate=true;
//                    }
//                    else if (readTmp.status == "in") {
//                        //prepare next package
//                        this.Distance*=2;
//                        NodePackage a=new NodePackage(this.myID,this.Distance,"out");
//                        this.PackagePrepareToSendClockwise.add(a);
//                    }
//                }
//                else if (readTmp.ID < this.myID) {
//                    //do nothing
//                }
//                this.PackageRecievedClockwise.remove(0);
//            }
//        }
    
    //                private void NodeActTask2_send() {
//            //send counter clockwise
//            while(!this.PackagePrepareToSendCounterClockwise.isEmpty()){
//                //process the first pack
//                NodePackage np=this.PackagePrepareToSendCounterClockwise.get(0);
//                if(np.status=="leader"){
//                    //terminate after send leader message
//                    this.terminate=true;
//                }
//                System.out.println("Node:" + this.number + " sends<"+np.ID+","+np.hopCount+","+np.status+"> to Node:"+previous.number);
//                sendPackageCounterClockWise(this.PackagePrepareToSendCounterClockwise.get(0));
//                this.PackagePrepareToSendCounterClockwise.remove(0);
//            }
//            //send clockwise
//            while(!this.PackagePrepareToSendClockwise.isEmpty()){
//                //process the first pack
//                NodePackage np=this.PackagePrepareToSendClockwise.get(0);
//                if(np.status=="leader"){
//                    this.terminate=true;
//                }
//                System.out.println("Node:" + this.number + " pass<"+np.ID+","+np.hopCount+","+np.status+"> to Node:"+next.number);
//                sendPackageClockWise(this.PackagePrepareToSendClockwise.get(0));
//                this.PackagePrepareToSendClockwise.remove(0);
//            }
//        }
    
        //==========================================================================
    //management methods
//    public void chooseLeaderBiggestTask1() {
//        Node tmp = head;
//        Node tmp2 = head;
//        int counter01=0;
//        while (checkNumIn(tmp) == false) {
//            checkNumTmp(tmp);
//            if(tmp.next==tmp2){
//                counter01++;
//                System.out.println("Round " + counter01+" finish");
//            }
//            
//            tmp = tmp.next;
//        }
//    }
    
        private static void tmptry() {
        Queue<String> queue = new LinkedList<>();
        //添加元素
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        for (String q : queue) {
            System.out.println(q);
        }
        //queue.clear();
        for (String q : queue) {
            if ("b".equals(q)) {
                System.out.println("hhh");
            } else {
                System.out.println("ooo");
            }
        }
//        System.out.println("===");
//        System.out.println("poll="+queue.poll()); //返回第一个元素，并在队列中删除
//        for(String q : queue){
//            System.out.println(q);
//        }
//        System.out.println("===");
//        System.out.println("element="+queue.element()); //返回第一个元素 
//        for(String q : queue){
//            System.out.println(q);
//        }
//        System.out.println("===");
//        System.out.println("peek="+queue.peek()); //返回第一个元素 
//        for(String q : queue){
//            System.out.println(q);
//            
//        }
    }
}


