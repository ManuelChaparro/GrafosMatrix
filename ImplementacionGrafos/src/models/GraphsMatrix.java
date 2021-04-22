package models;

import java.util.ArrayList;
import java.util.Comparator;
import structures.Node;
import structures.Queue;

public class GraphsMatrix<T> {

	public int n;
	private Queue<Node<T>> queue;
	private ArrayList<Node<T>> vertexList;
	private int[][] matrix;
	private Comparator<T> comparator;

	public GraphsMatrix(Comparator<T> comparator) {
		queue = new Queue<Node<T>>();
		matrix = new int[1][1];
		formatMatrix();
		vertexList = new ArrayList<Node<T>>();
		this.n = 0;
		this.comparator = comparator;
	}

	public void insertArc(Node<T> i, Node<T> j) {
		modifyMatrix(i, j, 0);
	}

	public void insertArc(Node<T> i, Node<T> j, int w) {
		modifyMatrix(i, j, w);
	}

	public void deleteArc(Node<T> i, Node<T> j) {
		modifyMatrix(i, j, -1);
	}

	public boolean adjacent(Node<T> i, Node<T> j) {
		if (i != null && j != null || i != null || j != null) {
			int positionX = 0;
			int positionY = 0;
			boolean isX = false;
			boolean isY = false;

			for (int k = 0; k < vertexList.size(); k++) {
				if (comparator.compare(i.getData(), j.getData()) != 0) {
					if (comparator.compare(vertexList.get(k).getData(), i.getData()) == 0) {
						positionX = k;
						isX = true;
					} else if (comparator.compare(vertexList.get(k).getData(), j.getData()) == 0) {
						positionY = k;
						isY = true;
					}
				} else {
					if (comparator.compare(vertexList.get(k).getData(), i.getData()) == 0) {
						positionX = k;
						positionY = k;
						isX = true;
						isY = true;
					}
				}
			}
			if (isX && isY && matrix[positionX][positionY] != -1) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new NullPointerException("El dato ingresado es nulo");
		}
	}

	public void newVertex(Node<T> u) {
		if (u != null) {
			if (!isExist(u)) {
				vertexList.add(u);
				n += 1;
				resizeMatrix();
			} else {
				throw new NullPointerException("El dato ingresado no existe");
			}
		} else {
			throw new NullPointerException("El dato ingresado es nulo");
		}
	}

	public void deleteVertex(Node<T> u) {
		if (u != null) {
			if (isExist(u)) {
				int position = 0;
				position = removeObject(u, position);
				int[][] auxMatrix = copyRows(position);
				copyColumns(position, auxMatrix);
			} else {
				throw new NullPointerException("El dato ingresado no existe");
			}
		} else {
			throw new NullPointerException("El dato ingresado es nulo");
		}
	}

	public void toStringBFS(Node<T> root) {
		if (root != null) {
			queue.push(root);
			while (!queue.isEmpty()) {
				Node<T> visit = queue.poll();
				if (visit.isVisit() == false) {
					visit.setVisit(true);
					setVertexTrue(visit);
					System.out.println("visitado -> " + visit.getData());
					setNodeAdjacent(visit);
				}
			}
			resetVisitList();
		} else {
			throw new NullPointerException("El dato ingresado es nulo");
		}
	}

	public ArrayList<String> dijkstra(Node<T> node) {
		if (node!=null) {
			int position = -1;
			for (int i = 0; i < vertexList.size(); i++) {
				if (comparator.compare(node.getData(), vertexList.get(i).getData()) == 0) {
					position = i;
				}
			}
			if (position!=-1) {
				return dijkistra(position);
			}else {
				throw new NullPointerException("No existe el vector ingresado");
			}
		}else {
			throw new NullPointerException("El vector ingresado es Nulo");
		}

	}

	private ArrayList<String> dijkistra(int src) {
		int[] allWeights = new int[n];
		boolean[] processVertex = new boolean[n];
		initVector(src, allWeights, processVertex);
		findShortWays(allWeights, processVertex);
		return showDijkstra(allWeights);
	}

	private void findShortWays(int[] allWeights, boolean[] processVertex) {
		for (int count = 0; count < n - 1; count++) {
			int u = minDistance(allWeights, processVertex);
			processVertex[u] = true;
			for (int v = 0; v < n; v++)
				if (!processVertex[v] && matrix[u][v] > 0 && allWeights[u] != Integer.MAX_VALUE
						&& allWeights[u] + matrix[u][v] < allWeights[v])
					allWeights[v] = allWeights[u] + matrix[u][v];
		}
	}

	private void initVector(int src, int[] dist, boolean[] verticeYaProcesado) {
		for (int i = 0; i < n; i++) {
			dist[i] = Integer.MAX_VALUE;
			verticeYaProcesado[i] = false;
		}
		dist[src] = 0;
	}

	private ArrayList<String> showDijkstra(int[] allWeights) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			result.add("DijkstraObj [weight="+allWeights[i] + ", data=" + vertexList.get(i).getData().toString()+"]");
		}
		return result;
	}

	private int minDistance(int[] dist, boolean[] verticeYaProcesado) {
		int min = Integer.MAX_VALUE;
		int min_index = 0;
		for (int v = 0; v < n; v++)
			if (verticeYaProcesado[v] == false && dist[v] <= min) {
				min = dist[v];
				min_index = v;
			}
		return min_index;
	}

	private void resetVisitList() {
		for (Node<T> vertex : vertexList) {
			vertex.setVisit(false);
		}
	}

	private void setVertexTrue(Node<T> visit) {
		for (Node<T> vertex : vertexList) {
			if (comparator.compare(vertex.getData(), visit.getData()) == 0) {
				vertex.setVisit(true);
			}
		}
	}

	private void setNodeAdjacent(Node<T> visit) {
		for (Node<T> vertex : vertexList) {
			if (adjacent(visit, vertex) && vertex.isVisit() == false) {
				queue.push(vertex);
			}
		}
	}

	private void formatMatrix() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = -1;
			}
		}
	}

	private void modifyMatrix(Node<T> i, Node<T> j, int w) {
		if (i != null && j != null || i != null || j != null) {
			int positionX = 0;
			int positionY = 0;
			boolean isX = false;
			boolean isY = false;

			for (int k = 0; k < vertexList.size(); k++) {
				if (comparator.compare(i.getData(), j.getData()) != 0) {
					if (comparator.compare(vertexList.get(k).getData(), i.getData()) == 0) {
						positionX = k;
						isX = true;
					} else if (comparator.compare(vertexList.get(k).getData(), j.getData()) == 0) {
						positionY = k;
						isY = true;
					}
				} else {
					if (comparator.compare(vertexList.get(k).getData(), i.getData()) == 0) {
						positionX = k;
						positionY = k;
						isX = true;
						isY = true;
					}
				}
			}
			if (isX && isY) {
				matrix[positionX][positionY] = w;
				matrix[positionY][positionX] = w;
			}
		} else {
			throw new NullPointerException("Uno de los datos ingresados es nulo");
		}
	}

	public int findWeightMatrix(Node<T> i, Node<T> j) {
		if (i != null && j != null || i != null || j != null) {
			int positionX = 0;
			int positionY = 0;
			boolean isX = false;
			boolean isY = false;

			for (int k = 0; k < vertexList.size(); k++) {
				if (comparator.compare(i.getData(), j.getData()) != 0) {
					if (comparator.compare(vertexList.get(k).getData(), i.getData()) == 0) {
						positionX = k;
						isX = true;
					} else if (comparator.compare(vertexList.get(k).getData(), j.getData()) == 0) {
						positionY = k;
						isY = true;
					}
				} else {
					if (comparator.compare(vertexList.get(k).getData(), i.getData()) == 0) {
						positionX = k;
						positionY = k;
						isX = true;
						isY = true;
					}
				}
			}
			if (isX && isY) {
				return matrix[positionX][positionY];
			} else {
				throw new NullPointerException("No existe arco entre los dos arcos ingresados");
			}
		} else {
			throw new NullPointerException("Uno de los datos ingresados es nulo");
		}
	}

	private boolean isExist(Node<T> u) {
		if (u != null) {
			boolean exist = false;
			for (Node<T> vertex : vertexList) {
				if (comparator.compare(u.getData(), vertex.getData()) == 0) {
					exist = true;
				}
			}
			return exist;
		} else {
			throw new NullPointerException("El dato ingresado es nulo");
		}
	}

	private void resizeMatrix() {
		int[][] auxMatrix = matrix;
		matrix = new int[n][n];
		formatMatrix();
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - 1; j++) {
				matrix[i][j] = auxMatrix[i][j];
			}
		}
	}

	private void copyColumns(int position, int[][] auxMatrix) {
		int k;
		matrix = new int[matrix.length - 1][matrix.length - 1];
		for (int i = 0; i < matrix.length; i++) {
			k = 0;
			for (int j = 0; j < matrix.length; j++) {
				if (position == j) {
					k++;
					matrix[i][j] = auxMatrix[i][k];
					k++;
				} else {
					matrix[i][j] = auxMatrix[i][k];
					k++;
				}
			}
		}
		n--;
	}

	private int[][] copyRows(int position) {
		int[][] auxMatrix = new int[matrix.length - 1][matrix.length];
		int k = 0;
		for (int i = 0; i < n - 1; i++) {
			if (position != i) {
				System.arraycopy(matrix, k, auxMatrix, i, 1);
				k++;
			} else {
				k++;
				System.arraycopy(matrix, k, auxMatrix, i, 1);
				k++;
			}
		}
		return auxMatrix;
	}

	private int removeObject(Node<T> u, int position) {
		for (int i = 0; i < vertexList.size(); i++) {
			if (comparator.compare(vertexList.get(i).getData(), u.getData()) == 0) {
				vertexList.remove(vertexList.remove(i));
				position = i;
			}
		}
		return position;
	}

	public void show() {
		System.out
				.println("-------------MATRIZ------------- (metodo unicamente para comprobar la organizacion de la matriz)");
		String msg = "\t\t";
		for (int i = 0; i < matrix.length; i++) {
			String student = (String) vertexList.get(i).getData();
			msg += student + "\t";
		}
		System.out.println(msg);

		for (int i = 0; i < matrix.length; i++) {
			String student = (String) vertexList.get(i).getData();
			String msj = student + "\t\t";
			for (int j = 0; j < matrix.length; j++) {
				msj += matrix[i][j] + "\t";
			}
			System.out.println(msj);
		}
		System.out.println("\n");
	}
}