package pgdp.datastructures;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;

public class QuarternarySearchTree<T extends Comparable<T>> implements Iterable<T> {
	private QuarternaryNode<T> root;

	public QuarternarySearchTree() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return root == null ? 0 : root.size();
	}

	public boolean contains(T value) {
		if (isEmpty()) {
			return false;
		}
		return root.contains(value);
	}

	public void insert(T value) {
		if (value == null) {
			return;
		}
		if (isEmpty()) {
			root = new QuarternaryNode<T>(value);
		} else {
			root.insert(value);
		}
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "[]";
		} else {
			return root.toString();
		}
	}

	public String toGraphvizString() {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph G {\n");
		sb.append(root == null ? "" : root.toGraphvizStringHelper());
		sb.append("}");
		return sb.toString();
	}

	public QuarternaryNode<T> getRoot() {
		return root;
	}

	public void setRoot(QuarternaryNode<T> root) {
		this.root = root;
	}

	//@Override
	public Iterator<T> iterator() {
		// TODO
		// Throw exception using the following line of code
		// throw new NoSuchElementException("Trying to call next on an empty QuarternarySearchTreeIterator");
		return new TreeIterator<>();
	}

	public class TreeIterator<T extends Comparable<T>> implements Iterator<T> {
		private static int counter;
		private ArrayList<T> out_list;
		public TreeIterator(){
			counter = 0;
			out_list = (ArrayList<T>) calculate_out_list();
		}
		@Override
		public boolean hasNext() {
			//solange counter nicht am Ende der Liste angelangt ist
			if (counter < out_list.size())
				return true;
			return false;
		}

		@Override
		public T next() {
			if (hasNext())
				return out_list.get(counter++);
			throw new NoSuchElementException("Trying to call next on an empty QuarternarySearchTreeIterator");
		}
	}

	public ArrayList<?> calculate_out_list() {
		//current_nodes sind aktuelle nodes einer ebene, current_nodes_new sind die der nächsten Ebene
		ArrayList<QuarternaryNode> current_nodes = new ArrayList();
		ArrayList<QuarternaryNode> current_nodes_new = new ArrayList();
		ArrayList out_list = new ArrayList();
		QuarternaryNode current_node;

		current_nodes.add(root);

		if (root == null) {
			return out_list;
		} else {
			while (current_nodes != null) {
				for (int i = 0; i < current_nodes.size(); i++) {
					//Für jeden node aus current_nodes ausführen
				//for (Iterator<QuarternaryNode> node_it = current_nodes.iterator(); node_it.hasNext();) {
					current_node = current_nodes.get(i);

					//Werte von aktuellem node zu liste hinzufügen
					for (Object wert : current_node.getValues()) {
					//for (Iterator<T> t_it = current_node.getValues().iterator(); t_it.hasNext();) {
						//falls wert existiert
						if (wert != null)
							out_list.add(wert);
					}

					//jedes Kind des aktuellen nodes in neuen current_nodes legen
					for (Object kind : current_node.getChildren()) {
					//for (Iterator<QuarternaryNode> qn2_it = current_node.getChildren().iterator(); qn2_it.hasNext(); ) {
						//falls kind existiert
						if (kind != null)
							current_nodes_new.add((QuarternaryNode) kind);
					}
				}
				//verschiebe eine Ebene nach unten
				if (current_nodes_new.size() > 0) {
					current_nodes.clear();
					current_nodes = current_nodes_new;
				} else {
					current_nodes = null;
				}
			}
			//System.out.println(out_list);
			//unsortierte Liste mit java comparator nach natürlicher Ordnung sortieren
			Collections.sort(out_list, Comparator.naturalOrder());
			//System.out.println(out_list);
		}
		return out_list;
	}

	public static void main(String[] args) {
		//int[] values = new int[] { 8, 4, 12, 1, 5, 9, 13, 3, 7, 11, 15, 2, 6, 10, 14 };
		int[] values = new int[] { 8, 4, 12, 1, 5, 9, 13, 3, 7, 11, 15, 2, 6, 10, 14 };

		//int[] values = new int[] {};
		QuarternarySearchTree<Integer> n = new QuarternarySearchTree<Integer>();
		for (int i : values) {
			n.insert(i);
		}
		//System.out.println(n.toGraphvizString());
		//System.out.println(n.toString());
		/*System.out.println("gesamter Baum: " + n.toString());
		System.out.println("root: " + n.root);
		System.out.println("size: " + n.size());
		System.out.println("qn values: " + n.root.getValues());
		System.out.println("qn height: " + n.root.height());
		System.out.println("qn getchild size: " + n.root.getChild(0).getNodeSize());
		System.out.println("qn children: " + n.root.getChildren());
		System.out.println("qn nodesize: " + n.root.getNodeSize());
		System.out.println(it.next());
		System.out.println(it.next());
		System.out.println(it.next());*/
		Iterator it = n.iterator();
		//System.out.println(it.next());

		// uncomment after implementing the iterator for testing the large example
				//for (int i : n) {
				//	System.out.print(i + " - ");
				//}
	}
}
