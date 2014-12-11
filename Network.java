package lanSimulation;
import java.io.IOException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Iterator;

public class Network implements PrintInterface, RequestInterface {
	private Network initPtr_;
	private Node firstNode_;
	@SuppressWarnings("unchecked")
	private Hashtable workstations_;
        private static GatewayNode gateway_;        
	@SuppressWarnings("unchecked")
	public Network(int size) {
		assert size > 0;
		initPtr_ = this;
		firstNode_ = null;
		workstations_ = new Hashtable(size, 1.0f);
                gateway_ = new GatewayNode();
		assert isInitialized();
		assert !consistentNetwork();
	}

         public static void setNodes(Network network)
        {
            Node wsFilip = new WorkStation("Filip");
		Node n1 = new Node(Node.NODE, "n1");
		Node wsHans = new WorkStation("Hans");
		Node prAndy = new Printer("Andy");

		wsFilip.nextNode_ = n1;
		n1.nextNode_ = wsHans;
		wsHans.nextNode_ = prAndy;
		prAndy.nextNode_ = wsFilip;
                
                
		network.workstations_.put(wsFilip.name_, wsFilip);
		network.workstations_.put(wsHans.name_, wsHans);
		network.firstNode_ = wsFilip;
                
                
gateway_.fillgateway(wsFilip.name_);
gateway_.fillgateway(n1.name_);
gateway_.fillgateway(wsHans.name_);
gateway_.fillgateway(prAndy.name_);




        }
	@SuppressWarnings("unchecked")
	public static Network DefaultExample() {
		Network network = new Network(2);

		   setNodes(network); 
		//assert network.isInitialized();
		assert network.consistentNetwork();
		return network;
	}

	public boolean isInitialized() {
		return (initPtr_ == this);
	};

	public boolean hasWorkstation(String ws) {
		Node n;

		assert isInitialized();
		n = (Node) workstations_.get(ws);
		if (n == null) {
			return false;
		} else {
			return n.type_ == Node.WORKSTATION;
		}
	};

	@SuppressWarnings("unchecked")
	public boolean consistentNetwork() {
		assert isInitialized();
		Node currentNode;
		int printersFound = 0, workstationsFound = 0;
		Hashtable encountered = new Hashtable(workstations_.size() * 2, 1.0f);

		if (workstations_.isEmpty()) {
			return false;
		}

		if (firstNode_ == null) {
			return false;
		}

		// verify whether all registered workstations are indeed workstations
		for (Iterator workstationIter = workstations_.values().iterator(); workstationIter
				.hasNext();) {
			currentNode = (Node) workstationIter.next();
			if (currentNode.type_ != Node.WORKSTATION) {
				return false;
			}
		}

		// enumerate the token ring, verifying whether all workstations are
		// registered
		// also count the number of printers and see whether the ring is
		// circular
		currentNode = firstNode_;
		while (!encountered.containsKey(currentNode.name_)) {
			encountered.put(currentNode.name_, currentNode);
			if (currentNode.type_ == Node.WORKSTATION) {
				workstationsFound++;
			}

			if (currentNode.type_ == Node.PRINTER) {
				printersFound++;
			}

			currentNode = currentNode.nextNode_;
		}

		if (currentNode != firstNode_) {
			return false;
		}

		// not circular
		if (printersFound == 0) {
			return false;
		}

		// does not contain a printer
		if (workstationsFound != workstations_.size()) {
			return false;
		}

		// not all workstations are registered
		// all verifications succeedeed
		return true;
	}

    @Override
	public boolean requestBroadcast(Writer report) {
		assert consistentNetwork();

		try {
			report.write("Broadcast Request\n");
		} catch (IOException exc) {
			// just ignore
		}

		Node currentNode = firstNode_;
		Packet packet = new Packet("BROADCAST", firstNode_.name_,
				firstNode_.name_);
		do {
			try {
				report.write("\tNode '");
				report.write(currentNode.name_);
				report.write("' accepts broadcase packet.\n");
				report.write("\tNode '");
				report.write(currentNode.name_);
				report.write("' passes packet on.\n");
				report.flush();
			} catch (IOException exc) {
				// just ignore
			}

			currentNode = currentNode.nextNode_;
		} while (!packet.destination_.equals(currentNode.name_));

		try {
			report.write(">>> Broadcast travelled whole token ring.\n\n");
		} catch (IOException exc) {
			// just ignore
		}

		return true;
	}


        @Override
	public boolean requestWorkstationPrintsDocument(String workstation,
			String document, String printer, Writer report) {
		assert consistentNetwork() & hasWorkstation(workstation);

		try {
			report.write("'");
			report.write(workstation);
			report.write("' requests printing of '");
			report.write(document);
			report.write("' on '");
			report.write(printer);
			report.write("' ...\n");
		} catch (IOException exc) {
			// just ignore
		}

		boolean result = false;
		Node startNode, currentNode = null;
		Packet packet = new Packet(document, workstation, printer);

		startNode = (Node) workstations_.get(workstation);

		try {
			report.write("\tNode '");
                        if(startNode != null)
                            report.write(startNode.name_);
			report.write("' passes packet on.\n");
			report.flush();
		} catch (IOException exc) {
		}
                if(startNode != null)
                    currentNode = startNode.nextNode_;
                if(gateway_.checkgatway(packet.destination_))
                {
		while ((currentNode != null) &&(!packet.destination_.equals(currentNode.name_))
				& (!packet.origin_.equals(currentNode.name_))) {
			try {
				report.write("\tNode '");
				report.write(currentNode.name_);
				report.write("' passes packet on.\n");
				report.flush();
			} catch (IOException exc) {
				// just ignore
			}

			currentNode = currentNode.nextNode_;
		}
                }
		if ((currentNode != null) && packet.destination_.equals(currentNode.name_)) {
			result = printDocument(currentNode, packet, report);
		} else {
			try {
				report
						.write(">>> Destinition not found, print job cancelled.\n\n");
				report.flush();
			} catch (IOException exc) {
				// just ignore
			}
			result = false;
		}
		return result;
	}
	private boolean printDocument(Node printer, Packet document, Writer report) {
		String author = "Unknown";
		String title = "Untitled";
		int startPos = 0, endPos = 0;

		if (printer.type_ == Node.PRINTER) {
			try {
				if (document.message_.startsWith("!PS")) {
					startPos = document.message_.indexOf("author:");
					if (startPos >= 0) {
						endPos = document.message_.indexOf(".", startPos + 7);
						if (endPos < 0) {
							endPos = document.message_.length();
						}

						author = document.message_.substring(startPos + 7,
								endPos);
					}

					startPos = document.message_.indexOf("title:");
					if (startPos >= 0) {
						endPos = document.message_.indexOf(".", startPos + 6);
						if (endPos < 0) {
							endPos = document.message_.length();
						}

						title = document.message_.substring(startPos + 6,
								endPos);
					}
					report.write("\tAccounting -- author = '");
					report.write(author);
					report.write("' -- title = '");
					report.write(title);
					report.write("'\n");
					report.write(">>> Postscript job delivered.\n\n");
					report.flush();
				} else {
					title = "ASCII DOCUMENT";
					if (document.message_.length() >= 16) {
						author = document.message_.substring(8, 16);
					}
					report.write("\tAccounting -- author = '");
					report.write(author);
					report.write("' -- title = '");
					report.write(title);
					report.write("'\n");
					report.write(">>> ASCII Print job delivered.\n\n");
					report.flush();
				}

			} catch (IOException exc) {
			}
			return true;
		} else {
			try {
				report
						.write(">>> Destinition is not a printer, print job cancelled.\n\n");
				report.flush();
			} catch (IOException exc) {
			}
			return false;
		}
	}

	public String toString() {
		assert isInitialized();
		StringBuffer buf = new StringBuffer(30 * workstations_.size());
		printOn(buf);
		return buf.toString();
	}

	public void printOn(StringBuffer buf) {
		assert isInitialized();
		Node currentNode = firstNode_;
		do {
			currentNode.printOn(buf);
			buf.append(" -> ");
			currentNode = currentNode.nextNode_;
		} while (currentNode != firstNode_);
		buf.append(" ... ");
	}

	public void printHTMLOn(StringBuffer buf) {
		assert isInitialized();

		buf.append("<HTML>\n<HEAD>\n<TITLE>LAN Simulation</TITLE>\n</HEAD>\n<BODY>\n<H1>LAN SIMULATION</H1>");
		Node currentNode = firstNode_;
		buf.append("\n\n<UL>");
		do {
			buf.append("\n\t<LI> ");
			currentNode.printOn(buf);
			buf.append(" </LI>");
			currentNode = currentNode.nextNode_;
		} while (currentNode != firstNode_);
		buf.append("\n\t<LI>...</LI>\n</UL>\n\n</BODY>\n</HTML>\n");
	}
	public void printXMLOn(StringBuffer buf) {
		assert isInitialized();

		Node currentNode = firstNode_;
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<network>");
		do {
			buf.append("\n\t");
			currentNode.printXmlOn(buf);
			currentNode = currentNode.nextNode_;
		} while (currentNode != firstNode_);
		buf.append("\n</network>");
	}
}