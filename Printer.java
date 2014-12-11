/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanSimulation;

/**
 *
 * @author hega
 */
public class Printer extends Node{

    public Printer(byte type, String name, Node nextNode) {
        super(type, name, nextNode);
    }

    public Printer(String name){
        super(name);
    }
    
    public boolean equalsType(Class type){
        return type.equals(Printer.class);
    }


    public void printOn(StringBuffer buf){

        buf.append("Printer ");
        buf.append(this.name_);
        buf.append(" [Printer]");

    }

    public void printXmlOn(StringBuffer buf){
        buf.append("<Printer>");
        buf.append(this.name_);
        buf.append("</Printer>");
    }
    
}
