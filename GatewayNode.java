/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanSimulation;

import java.util.ArrayList;

/**
 *
 * @author hega
 */
public class GatewayNode extends Node {

     public ArrayList<String> names;

    /**
     *
     */
    public GatewayNode(){  
        super("node");
    names=new ArrayList<String>();
    }

/*    public GatewayNode(byte type, String name, Node nextNode) {
//        super(type, name, nextNode);
    }
    public GatewayNode(byte type, String name) {
//        super(type, name);
    }
*/
//    public GatewayNode(int size) {
//        super(size);
//    }
    
    private String destination;

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
     public void fillgateway(String n){
        names.add(n);
    
    }
    public boolean shouldDeffer(String packetDestination){
        if(packetDestination.charAt(0) == 'Y'){
            if(packetDestination.charAt(1) == 'E'){
                if(packetDestination.charAt(2) == 'S'){
                    return true;
                }
            }

        }
        
        return false;
    }

    boolean checkgatway(String n) {
      String sub1="",sub2="";
        for(int i=0;i<names.size();i++){
            
            sub1=names.get(i).substring(0,0);
            sub2=n.substring(0,0);
            if(names.get(i).length()==0 && n.length()==0 && sub1.equals(sub2))
                return true;
            if(names.get(i).length()==1 && n.length()!=1)
                continue;
             sub1=names.get(i).substring(0,1);
            sub2=n.substring(0,1);
            if(names.get(i).length()==1 && n.length()==1 && sub1.equals(sub2))
                return true;
             sub1=names.get(i).substring(0,2);
            sub2=n.substring(0,2);
            if(sub1.equals(sub2))
                return true;
            
            
    }
    
    
 
    return true;
    }
}
