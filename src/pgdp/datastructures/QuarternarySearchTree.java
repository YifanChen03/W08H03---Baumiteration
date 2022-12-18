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
		int round = 0;
		QuarternaryNode<T> current_node = root;
		QuarternaryNode<T> current_parent_node = root;
		QuarternaryNode<T> current_child_node;
		ArrayList<T> out_list = new ArrayList<>();

		//falls root == null
		if (root != null) {
			current_child_node = root.getChild(0);
			if (current_child_node == null) {
				for (int i = 0; i < 3; i++) {
					out_list.add(current_node.getValue(i));
				}
			}
			//Alle Werte aus dem Baum auslesen und in out_list füllen
			while (current_child_node != null) {
				//solange Eltern noch Kinder mit Werten haben haben
				for (int i = 0; i < Math.pow(4, round); i++) {
					//4 hoch round mal ausführen
					if (round == 0) {

					} else {
						current_node = current_parent_node.getChild(i);
					}
					for (int i2 = 0; i2 < 3; i2++) {
						out_list.add(current_node.getValue(i2));
					}
				}
				if (round != 0) {
					current_parent_node = current_child_node;
					current_child_node = current_parent_node.getChild(0);
				}
				round++;
			}
			//System.out.println(out_list);
			Collections.sort(out_list, Comparator.naturalOrder());
			//System.out.println(out_list.size());
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
		//Iterator it = n.iterator();
		//System.out.println(it.next());

		// uncomment after implementing the iterator for testing the large example
				for (int i : n) {
					System.out.print(i + " - ");
				}
	}
}
