/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanSimulation;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hega
 */
public class Document {

    private String text;
    private String documentName;

    public Document(String text){
        this.text = text;
    }

    public String getText(){

        return text;

    }
    public Document(String text, String Name){
        this.text = text;
        this.documentName = Name;
    }

    public String getDocumentName(){

        return this.documentName;

    }

    public void print(String text){
        Writer report = new Writer() {

            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void flush() throws IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void close() throws IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        try {
            report.write(text);
        } catch (IOException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            report.flush();
        } catch (IOException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
