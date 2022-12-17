package pgdp.datastructures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
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

	@Override
	public Iterator<T> iterator() {
		// TODO
		QuarternaryNode<T> current_node = root;
		QuarternaryNode<T> current_parent_node = root;
		QuarternaryNode<T> current_child_node = root.getChild(0);
		ArrayList<T> out_list = new ArrayList<>();

		//fülle out_list mit Werten in sortierter Reihenfolge
		for (int i = 0; i < root.height(); i++) {
			//für die Höhe wird auf jeder Ebene einmal ausgeführt
			for (int i2 = 0; i < (int) Math.pow(4, i); i2++) {
				//für jeden Knoten wird diese Loop einmal ausgeführt
				if (current_node == root) {
					current_node = root.getChild(0);;
				} else {
					out_list.add(current_parent_node.getValue(i2 - 1));
					current_node = current_parent_node.getChild(i2);
				}
				if (current_node == null) {
					break;
					//System.out.println(out_list);
				}
				for (int i3 = 0; i3 < current_node.getNodeSize(); i3++) {
					//für jeden Wert in diesem Node
					out_list.add(current_node.getValue(i3));
				}
			}
		}
		// Throw exception using the following line of code
		// throw new NoSuchElementException("Trying to call next on an empty QuarternarySearchTreeIterator");
		return new Iterator<>() {
			private static int count = 0;

			@Override
			public boolean hasNext() {
				if (count < size())
					return true;
				return false;
			}

			@Override
			public T next() {
				if (hasNext())
					return out_list.get(count++);
				throw new NoSuchElementException("Trying to call next on an empty QuarternarySearchTreeIterator");
			}
		};
	}

	public static void main(String[] args) {
		//int[] values = new int[] { 8, 4, 12, 1, 5, 9, 13, 3, 7, 11, 15, 2, 6, 10, 14 };

		int[] values = new int[] { 8, 4, 12, 1, 5, 9, 13, 3, 7, 11, 15, 2, 6, 10, 14 };
		QuarternarySearchTree<Integer> n = new QuarternarySearchTree<Integer>();
		for (int i : values) {
			n.insert(i);
		}
		//System.out.println(n.toGraphvizString());
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

		// uncomment after implementing the iterator for testing the large example
				for (int i : n) {
					System.out.print(i + " - ");
				}
	}
}
