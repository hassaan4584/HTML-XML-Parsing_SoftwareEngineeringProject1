/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanSimulation;

import java.io.Writer;

/**
 *
 * @author hega
 */
public interface RequestInterface {

    /**
     * The #receiver is requested to broadcast a message to all nodes. Therefore
     * #receiver sends a special broadcast packet across the token ring network,
     * which should be treated by all nodes.
     * <p>
     * <strong>Precondition:</strong> consistentNetwork();
     * </p>
     *
     * @param report
     *            Stream that will hold a report about what happened when
     *            handling the request.
     * @return Anwer #true when the broadcast operation was succesful and #false
     *         otherwise
     */
    boolean requestBroadcast(Writer report);

    /**
     * The #receiver is requested by #workstation to print #document on
     * #printer. Therefore #receiver sends a packet across the token ring
     * network, until either (1) #printer is reached or (2) the packet travelled
     * complete token ring.
     * <p>
     * <strong>Precondition:</strong> consistentNetwork() &
     * hasWorkstation(workstation);
     * </p>
     *
     * @param workstation
     *            Name of the workstation requesting the service.
     * @param document
     *            Contents that should be printed on the printer.
     * @param printer
     *            Name of the printer that should receive the document.
     * @param report
     *            Stream that will hold a report about what happened when
     *            handling the request.
     * @return Anwer #true when the print operation was succesful and #false
     *         otherwise
     */
    boolean requestWorkstationPrintsDocument(String workstation, String document, String printer, Writer report);
    
}
