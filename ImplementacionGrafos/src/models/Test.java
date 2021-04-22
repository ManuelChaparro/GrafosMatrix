package models;

import structures.Node;

public class Test {

	public static void main(String[] args) {
		ComparatorString comparator = new ComparatorString();
		GraphsMatrix<String> graphs = new GraphsMatrix<String>(comparator);
		
		//crear nodos de ciudades
		Node<String> tunjaNode = new Node<String>("Tunja");
		Node<String> paipaNode = new Node<String>("Paipa");
		Node<String> tutaNode = new Node<String>("Tuta");
		Node<String> duitamaNode = new Node<String>("Duitama");
		Node<String> tibasosaNode = new Node<String>("Tibaso");
		Node<String> nobsaNode = new Node<String>("Nobsa");
		Node<String> sogamosoNode = new Node<String>("Sogamo");

		//Agregar vertices/nodos
		graphs.newVertex(tunjaNode);
		graphs.newVertex(paipaNode);
		graphs.newVertex(tutaNode);
		graphs.newVertex(duitamaNode);
		graphs.newVertex(tibasosaNode);
		graphs.newVertex(nobsaNode);
		graphs.newVertex(sogamosoNode);

		//Agregar arcos entre vertices/nodos
		graphs.insertArc(tunjaNode, paipaNode, 28);
		graphs.insertArc(tunjaNode, tutaNode, 15);
		graphs.insertArc(paipaNode, tutaNode, 15);
		graphs.insertArc(paipaNode, duitamaNode, 10);
		graphs.insertArc(duitamaNode, tibasosaNode, 15);
		graphs.insertArc(duitamaNode, nobsaNode, 20);
		graphs.insertArc(tibasosaNode, sogamosoNode, 15);
		graphs.insertArc(nobsaNode, sogamosoNode, 15);
		
		//mostrar matriz
		graphs.show();
		
		//mostrar recorrido en anchura con dos datos distintos
		System.out.println("______________________________________________________________________");
		System.out.println("\nRecorrido en anchura prueba 1...\n");
		graphs.toStringBFS(tunjaNode);
		
		//mostrar alogritmo dijkstra
		System.out.println("\nAlgoritmo de Dijkstra (Devuelve un Array de Strings con: La ciudad y su ditancia desde el vertice inicial.\n");
		for (String data : graphs.dijkstra(tunjaNode)) {
			System.out.println(data);
		}
	}
}
