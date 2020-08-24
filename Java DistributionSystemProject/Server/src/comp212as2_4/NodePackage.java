/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp212as2_4;

/**
 *
 * @author KSK
 */
public class NodePackage {

    int ID;
    int hopCount;
    String status;

    public NodePackage(int ID, int hopCount, String status) {
        this.ID = ID;
        this.hopCount = hopCount;
        this.status = status;
    }
    public void Print(){
        System.out.println("<"+this.ID+","+this.hopCount+","+this.status+">");
    }
    public void changeState(String status) {
        this.status = status;
    }
}
