/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lanSimulation;

import org.w3c.dom.Element;

/**
 *
 * @author mian
 */
public class WorkStation extends Node{
    
    public WorkStation(String name){
        super(name);
    }
    public WorkStation getWorkstation(Element el)
    {
        
        return this;
        
    }
    public void printOn(StringBuffer buf){

        buf.append("Workstation ");
        buf.append(this.name_);
        buf.append(" [Workstation]");

    }

    public void printXmlOn(StringBuffer buf){
        buf.append("<workstation>");
        buf.append(this.name_);
        buf.append("</workstation>");
    }

   
}
