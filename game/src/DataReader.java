

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class DataReader extends DefaultHandler {

	private Vector<node> nodes;
	private String stringXML;
	private boolean readingNode;
	private boolean readingAdjacents;
	private boolean readingAvailables;

	int[] adjacentNodes = new int[5];
	int[] availableAdjacentNodes = new int[5];

	private int idNumber;
	private int belongsTo;
	private int squares;
	private int circles;
	private int triangles;
	private int unitCreationType;
	private int unitCreationSpeed;
	private int special;

	public DataReader(String xml) {

		super();

		nodes = new Vector<node>();
		stringXML = xml;
	}

	// start of the XML document
	public void startDocument() {

	}

	// end of the XML document
	public void endDocument() {

	}

	// opening element tag
	public void startElement(String uri, String name, String qName,
			Attributes atts) {
		// handle the start of an element

		// find out if the element is a brand
		if (qName.equals("node")) {
			
			idNumber = Integer.parseInt(atts.getValue("IDnumber"));
			belongsTo = Integer.parseInt(atts.getValue("belongsTo"));
			squares = Integer.parseInt(atts.getValue("squares"));
			circles = Integer.parseInt(atts.getValue("circles"));
			triangles = Integer.parseInt(atts.getValue("triangles"));
			unitCreationType = Integer.parseInt(atts
					.getValue("unitCreationType"));
			unitCreationSpeed = Integer.parseInt(atts
					.getValue("unitCreationSpeed"));
			special = Integer.parseInt(atts.getValue("special"));

			readingNode = true;
		}

		if (qName.equals("adjacentNodes")) {

			readingAdjacents = true;

			adjacentNodes[0] = Integer.parseInt(atts.getValue("number1"));
			adjacentNodes[1] = Integer.parseInt(atts.getValue("number2"));
			adjacentNodes[2] = Integer.parseInt(atts.getValue("number3"));
			adjacentNodes[3] = Integer.parseInt(atts.getValue("number4"));
			adjacentNodes[4] = Integer.parseInt(atts.getValue("number5"));
		}

		else if (qName.equals("availableAdjacentNodes")) {

			readingAvailables = true;

			availableAdjacentNodes[0] = Integer.parseInt(atts
					.getValue("number1"));
			availableAdjacentNodes[1] = Integer.parseInt(atts
					.getValue("number2"));
			availableAdjacentNodes[2] = Integer.parseInt(atts
					.getValue("number3"));
			availableAdjacentNodes[3] = Integer.parseInt(atts
					.getValue("number4"));
			availableAdjacentNodes[4] = Integer.parseInt(atts
					.getValue("number5"));
		}

		if (readingNode && readingAdjacents && readingAvailables) {

			readingAdjacents = false;
			readingAvailables = false;
			readingNode = false;

			nodes.add(new node(idNumber, belongsTo, squares, circles,
					triangles, unitCreationType, unitCreationSpeed,
					adjacentNodes, availableAdjacentNodes, special));
		}

	}

	// closing element tag
	public void endElement(String uri, String name, String qName) {
		// handle the end of an element

	}

	// element content
	public void characters(char ch[], int start, int length) {
		// process the element content

	}

	public Vector<node> getData() {
		// take care of SAX, input and parsing errors
		try {
			// set the parsing driver
			System.setProperty("org.xml.sax.driver",
					"org.xmlpull.v1.sax2.Driver");
			// create a parser
			SAXParserFactory parseFactory = SAXParserFactory.newInstance();
			SAXParser xmlParser = parseFactory.newSAXParser();
			// get an XML reader
			XMLReader xmlIn = xmlParser.getXMLReader();
			// instruct the app to use this object as the handler
			xmlIn.setContentHandler(this);

			ByteArrayInputStream xmlStream = new ByteArrayInputStream(
					stringXML.getBytes());

			// parse the data
			xmlIn.parse(new InputSource(xmlStream));
		} catch (SAXException se) {
			System.out.print("SAX Error " + se.getMessage());
		}

		catch (IOException ie) {
			System.out.print("Input Error " + ie.getMessage());
		}

		catch (Exception oe) {
			System.out.print("Unspecified Error " + oe.getMessage());
		}

		// return the parsed product list
		return nodes;
	}

	public static void main(String[] args) {

		String xml = "<graph>\n" + "\t<node"
				+ " IDnumber=\"0\" "
				+ "belongsTo=\"0\" "
				+ "squares=\"3\" "
				+ "circles=\"7\" "
				+ "triangles=\"0\" "
				+ "unitCreationType=\"2\" "
				+ "unitCreationSpeed=\"1\" "
				+ "special=\"0\">\n"
				+ "\t\t<adjacentNodes "
				+ "number1=\"3\" "
				+ "number2=\"4\" "
				+ "number3=\"1\" "
				+ "number4=\"7\" "
				+ "number5=\"9\">" 
				+ "\t\t</adjacentNodes>\n"
				+ "\t\t<availableAdjacentNodes "
				+ "number1=\"3\" "
				+ "number2=\"4\" "
				+ "number3=\"1\" "
				+ "number4=\"7\" "
				+ "number5=\"9\">"
				+ "\t\t</availableAdjacentNodes>\n" 
				+ "\t</node>\n"
				+ "</graph>";

		
		xml = "<graph>"
+ "	<node IDnumber=\"0\"  belongsTo=\"1\"  squares=\"10\"  circles=\"10\"  triangles=\"10\"  unitCreationType=\"1\"  unitCreationSpeed=\"1\"  special=\"1\" >"
//		<adjacentNodes number0="1" number1="2" number2="3" number3="-1" number4="-1" >		</adjacentNodes>
//		<availableAdjacentNodes number0="1" number1="2" number2="3" number3="-1" number4="-1" >		</availableAdjacentNodes>
+ "	</node>"
+ "</graph>";

		// Maybe with more nodes, it's only an example

		DataReader parser = new DataReader(xml);

		Vector<node> nodes = parser.getData();

		for (int i = 0; i < nodes.size(); i++) {
			System.out.println("node " + i + "\n\tIDnumber= "
					+ nodes.get(i).get_id() + "\n\tbelongsTo= "
					+ nodes.get(i).belongs() + "\n\tsquares= "
					+ nodes.get(i).numberOfSquares() + "\n\tcircles= "
					+ nodes.get(i).numberOfCircles() + "\n\ttriangles= "
					+ nodes.get(i).numberOfTriangles());

			int[] adjacentNodes = nodes.get(i).getAdjacentNodes();

			System.out.println("\tadjacentNodes:");

			for (int j = 0; j < adjacentNodes.length; j++) {

				System.out.println("\t\tnumber: " + adjacentNodes[j]);
			}

			int[] availableAdjacentNodes = nodes.get(i).getAvailableNodes();

			System.out.println("\tavailableAdjacentNodes:");

			for (int j = 0; j < availableAdjacentNodes.length; j++) {

				System.out.println("\t\tnumber: " + availableAdjacentNodes[j]);
			}

		}
	}
}
