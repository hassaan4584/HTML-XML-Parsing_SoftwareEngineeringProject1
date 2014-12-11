/*   This file is part of lanSimulation.
 *
 *   lanSimulation is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   lanSimulation is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with lanSimulation; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *   Copyright original Java version: 2004 Bart Du Bois, Serge Demeyer
 *   Copyright C++ version: 2006 Matthias Rieger, Bart Van Rompaey
 */
package lanSimulation;

import java.io.File;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;


public class LANSimulation {

        @SuppressWarnings("UseOfSystemOutOrSystemErr")
	public static void doRegressionTests() {
            System.out.println("In doRegistrationTests");
		//junit.textui.TestRunner.run(LANTests.suite());
	}

        @SuppressWarnings("UseOfSystemOutOrSystemErr")
	public static void simulate() {
		Network network = Network.DefaultExample();
		StringWriter report = new StringWriter(100);
		StringBuffer buf = new StringBuffer(100);

		System.out.print("siumlate on Network: ");
		System.out.println(network);
		System.out.println();

		network.printHTMLOn(buf);
		System.out
				.println("---------------------------------HTML------------------------------------------");
		System.out.println(buf.toString());
		System.out.println();

		buf.setLength(0);
		network.printXMLOn(buf);
		System.out
				.println("---------------------------------XML------------------------------------------");
		System.out.println(buf.toString());
		System.out.println();

		System.out
				.println("---------------------------------SCENARIOS------------------------------------------");
		String document = "author: FILIP   Hello World";
		System.out.print("'Filip' prints '" + document + "' on 'Andy': ");
		System.out.print(network.requestWorkstationPrintsDocument("Filip",
				document, "Andy", report));
		System.out.println(" (expects true);");

		System.out.print("'Filip' prints '" + document
				+ "' on 'UnknownPrinter': ");
		System.out.print(network.requestWorkstationPrintsDocument("Filip",
				document, "UnknownPrinter", report));
		System.out.println(" (expects false);");

		System.out.print("'Filip' prints '" + document + "' on 'Hans': ");
		System.out.print(network.requestWorkstationPrintsDocument("Filip",
				document, "Hans", report));
		System.out.println(" (expects false);");

		System.out.print("'Filip' prints '" + document + "' on 'n1': ");
		System.out.print(network.requestWorkstationPrintsDocument("Filip",
				document, "n1", report));
		System.out.println(" (expects false);");

		document = "Hello World";
		System.out.print("'Filip' prints '" + document + "' on 'Andy': ");
		System.out.print(network.requestWorkstationPrintsDocument("Filip",
				document, "Andy", report));
		System.out.println(" (expects true);");

		document = "!PS Hello World in postscript.author:Filip.title:Hello.";
		System.out.print("'Filip' prints '" + document + "' on 'Andy': ");
		System.out.print(network.requestWorkstationPrintsDocument("Filip",
				document, "Andy", report));
		System.out.println(" (expects true);");

		System.out.print("'Filip' prints '" + document + "' on 'Hans': ");
		System.out.print(network.requestWorkstationPrintsDocument("Filip",
				document, "Hans", report));
		System.out.println(" (expects false);");

		document = "!PS Hello World in postscript.Author:Filip.Title:Hello.";
		System.out.print("'Filip' prints '" + document + "' on 'Andy': ");
		System.out.print(network.requestWorkstationPrintsDocument("Filip",
				document, "Andy", report));
		System.out.println(" (expects true);");

		document = "!PS Hello World in postscript.author:Filip;title:Hello;";
		System.out.print("'Filip' prints '" + document + "' on 'Andy': ");
		System.out.print(network.requestWorkstationPrintsDocument("Filip",
				document, "Andy", report));
		System.out.println(" (expects true);");

		document = "!PS Hello World in postscript.author:.title:.";
		System.out.print("'Filip' prints '" + document + "' on 'Andy': ");
		System.out.print(network.requestWorkstationPrintsDocument("Filip",
				document, "Andy", report));
		System.out.println(" (expects true);");

		try {
			System.out
					.print("'UnknownWorkstation' prints 'does not matter' on 'does not matter': ");
			System.out.print(network.requestWorkstationPrintsDocument(
					"UnknownWorkstation", "does not matter", "does not matter",
					report));
			System.out.println(" (??? no exception);");
		} catch (AssertionError e1) {
			System.out.println("exception (as expected);");
		}

		System.out.print("BROADCAST REQUEST: ");
		System.out.print(network.requestBroadcast(report));
		System.out.println(" (expects true);");

		System.out.println();
		System.out.println();
		System.out.println();
		System.out
				.println("---------------------------------REPORT------------------------------------------");
		System.out.println(report.toString());
	}

        public static void xmlread()
        {
             try {
  File file = new File("configuration.xml");
  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
  DocumentBuilder db = dbf.newDocumentBuilder();
                 org.w3c.dom.Document doc = db.parse(file);
  doc.getDocumentElement().normalize();
  System.out.println("Root element " + doc.getDocumentElement().getNodeName());
  NodeList nodeLst = doc.getElementsByTagName("jobs");
  System.out.println("Information of all jobs");

  
            System.out.println("<network>");
  
  for (int s = 0; s < nodeLst.getLength(); s++) {

      Node fstNode = nodeLst.item(s);
    
    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
  
            System.out.println("<jobs>");
           Element fstElmnt = (Element) fstNode;
      NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("workstation");
      Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
      NodeList fstNm = fstNmElmnt.getChildNodes();
      System.out.println("<workstation>"  + ((Node) fstNm.item(0)).getNodeValue()+ "</workstation>");
      NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("job");
      Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
      NodeList lstNm = lstNmElmnt.getChildNodes();
      System.out.println("<job>" + ((Node) lstNm.item(0)).getNodeValue() + "</job>");
       NodeList NmElmntLst = fstElmnt.getElementsByTagName("printer");
      Element NmElmnt = (Element) NmElmntLst.item(0);
      NodeList Nm = NmElmnt.getChildNodes();
      System.out.println("<printer>" + ((Node) Nm.item(0)).getNodeValue() + "</printer>");
      System.out.println("</jobs>");
    }

  }
            System.out.println("</network>");
  
  } catch (Exception e) {
    e.printStackTrace();
  }
        }
        

        @SuppressWarnings({"UseOfSystemOutOrSystemErr", "UnnecessaryUnboxing"})
	public static void main(String args[]) {

		if (args.length <= 0) {
			System.out.println("Usage: t(est) | s(imulate) nrOfIterations '");
		} else if (args[0].equals("t")) {// 'test' command
			doRegressionTests();
		} else if (args[0].equals("s")) {// 'simulate' command
                        @SuppressWarnings("UnnecessaryBoxing")
			Integer nrOfIters = new Integer(1);
			if (args.length > 1) {
				nrOfIters = new Integer(args[1]);
			}

			for (int i = 0; i < nrOfIters.intValue(); i++) {
				simulate();
			}
		} else {// unknown commaND
			System.out.print("Unknown command to LANSimulation: '");
			System.out.print(args[0]);
			System.out.println("'");
		}
                xmlread();
	}
}
