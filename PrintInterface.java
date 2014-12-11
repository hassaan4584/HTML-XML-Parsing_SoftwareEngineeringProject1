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
public interface PrintInterface {

    /**
     * Write a HTML representation of #receiver on the given #buf.
     * <p>
     * <strong>Precondition:</strong> isInitialized();
     * </p>
     */
    void printHTMLOn(StringBuffer buf);

    /**
     * Write a printable representation of #receiver on the given #buf.
     * <p>
     * <strong>Precondition:</strong> isInitialized();
     * </p>
     */
    void printOn(StringBuffer buf);

    /**
     * Write an XML representation of #receiver on the given #buf.
     * <p>
     * <strong>Precondition:</strong> isInitialized();
     * </p>
     */
    void printXMLOn(StringBuffer buf);
    
}
