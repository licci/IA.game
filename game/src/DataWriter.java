import java.util.Vector;

public class DataWriter {

	private node[] nodes;

	public DataWriter(node[] map) {

		nodes = map;
	}

	public String getXMLfromGraph() {

		String xmlString = "<graph>\n";

		for (int i = 0; i < nodes.length; i++) { // this for goes through the
													// array's nodes

			xmlString += "\t<node";

			node node = nodes[i];
			xmlString += " IDnumber=\"" + node.get_id() + "\" "; // In JAVA
																	// strings,
																	// '\"' ==
																	// '"'
			xmlString += " belongsTo=\"" + node.belongs() + "\" ";
			xmlString += " squares=\"" + node.numberOfSquares() + "\" ";
			xmlString += " circles=\"" + node.numberOfCircles() + "\" ";
			xmlString += " triangles=\"" + node.numberOfTriangles() + "\" ";
			xmlString += " unitCreationType=\"" + node.creationType() + "\" ";
			xmlString += " unitCreationSpeed=\"" + node.speed() + "\" ";
			xmlString += " special=\"" + node.getSpecial() + "\" >\n";

			xmlString += "\t\t<adjacentNodes ";

			int[] adjacents = node.adjacentNodes;

			for (int j = 1; j <= adjacents.length; j++) { // this for goes
															// through the
															// adjacents' array

				xmlString += "number" + j + "=\"" + adjacents[j - 1] + "\" ";
			}

			xmlString += ">\t\t</adjacentNodes>\n";

			xmlString += "\t\t<availableAdjacentNodes ";
			
			int[] availableAdjacentNodes = node.availableAdjacentNodes;

			for (int j = 1; j <= availableAdjacentNodes.length; j++) { // this
																		// for
																		// goes
																		// through
																		// the
																		// adjacents'
																		// array

				xmlString += "number" + j + "=\"" + availableAdjacentNodes[j - 1]
						+ "\" ";
			}

			xmlString += ">\t\t</availableAdjacentNodes>\n";

			xmlString += "\t</node>\n";
		}

		return xmlString += "</graph>\n";

	}

	public static void main(String[] args) {

		graph graaph = new graph();

		graaph.initialization();

		DataWriter writer = new DataWriter(graph.map);

		String xml = writer.getXMLfromGraph();

		DataReader parser = new DataReader(xml);

		Vector<node> nodes = parser.getData();

		for (int i = 0; i < nodes.size(); i++) {
			System.out.println("node " + (i + 1) + "\n\tIDnumber= "
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
 * String xml = "<graph>\n" + "\t<node" + " IDnumber=\"0\" " +
 * "belongsTo=\"0\" " + "squares=\"3\" " + "circles=\"7\" " + "triangles=\"0\" "
 * + "unitCreationType=\"2\" " + "unitCreationSpeed=\"1\" " + "special=\"0\">\n"
 * + "\t\t<adjacentNodes " + "number1=\"3\" " + "number2=\"4\" " +
 * "number3=\"1\" " + "number4=\"7\" " + "number5=\"9\">" +
 * "\t\t</adjacentNodes>\n" + "\t\t<availableAdjacentNodes " + "number1=\"3\" "
 * + "number2=\"4\" " + "number3=\"1\" " + "number4=\"7\" " + "number5=\"9\">" +
 * "\t\t</availableAdjacentNodes>\n" + "\t</node>\n" + "</graph>";
 * 
 * 
 * // Maybe with more nodes, it's only an example
 */