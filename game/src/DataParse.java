
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


public class DataParse extends DefaultHandler {

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

	public DataParse(String xml) {

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

			nodes.add(new node());
			
			//	nodes.add(new node(idNumber, belongsTo, squares, circles,
			//triangles, unitCreationType, unitCreationSpeed,
			//adjacentNodes, availableAdjacentNodes, special));
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


		// Maybe with more nodes, it's only an example

		DataParse parser = new DataParse(xml);

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





/*
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
 
public class DataParse extends node {
 
	public static void main(String argv[]) {
 
	  try {
 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("graph");
		doc.appendChild(rootElement);
 

		Element node = doc.createElement("node");
		rootElement.appendChild(node);
 
		Attr idnum = doc.createAttribute("IDnumber");
		idnum.setValue(identificationNumber);
		node.setAttributeNode(idnum);
                
                Attr belongs = doc.createAttribute("belongsTo");
		belongs.setValue("0");
		node.setAttributeNode(belongs);
                
                Attr squares = doc.createAttribute("squares");
		squares.setValue("3");
		node.setAttributeNode(squares);
                
                Attr circles = doc.createAttribute("circles");
		circles.setValue("7");
		node.setAttributeNode(circles);
                
                Attr triangles = doc.createAttribute("triangles");
		triangles.setValue("0");
		node.setAttributeNode(triangles);
                
                Attr creationtype = doc.createAttribute("unitCreationType");
		creationtype.setValue("2");
		node.setAttributeNode(creationtype);
                
                Attr creationspeed = doc.createAttribute("unitCreationSpeed");
		creationspeed.setValue("1");
		node.setAttributeNode(creationspeed);
                
                Attr special = doc.createAttribute("special");
		special.setValue("0");
		node.setAttributeNode(special);
 
 
		
		Element adjacent = doc.createElement("adjacentNodes");
		node.appendChild(adjacent);
 
		Attr number1 = doc.createAttribute("number1");
		number1.setValue("3");
		adjacent.setAttributeNode(number1);
                
                Attr number2 = doc.createAttribute("number2");
		number2.setValue("4");
		adjacent.setAttributeNode(number2);
                
                Attr number3 = doc.createAttribute("number3");
		number3.setValue("1");
		adjacent.setAttributeNode(number3);
                
                Attr number4 = doc.createAttribute("number4");
		number4.setValue("7");
		adjacent.setAttributeNode(number4);
                
                Attr number5 = doc.createAttribute("number5");
		number5.setValue("9");
		adjacent.setAttributeNode(number5);
                
                
                
                Element available = doc.createElement("availableAdjacentNodes");
		node.appendChild(available);
 
		Attr num1 = doc.createAttribute("number1");
		num1.setValue("3");
		available.setAttributeNode(num1);
                
                Attr num2 = doc.createAttribute("number2");
		num2.setValue("4");
		available.setAttributeNode(num2);
                
                Attr num3 = doc.createAttribute("number3");
		num3.setValue("1");
		available.setAttributeNode(num3);
                
                Attr num4 = doc.createAttribute("number4");
		num4.setValue("7");
		available.setAttributeNode(num4);
                
                Attr num5 = doc.createAttribute("number5");
		num5.setValue("9");
		available.setAttributeNode(num5);
                
 
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		
                //StreamResult result = new StreamResult(new File("C:\\blabla.xml"));
                StreamResult result =  new StreamResult(System.out);
                transformer.transform(source, result);
 
		
 
		System.out.println("File saved!");

 
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
}
*/