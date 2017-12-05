/* Only 3 methods implemented but this is just for the assignment.
 * 
 */
package model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyList<T> implements List<T> {

	private Object[] objects;
	private int count = 0, total;;

	public MyList(int i) {
		objects = new Object[i];
		total = i;
	}

	@Override
	public boolean add(T e) {
		if(count<total) {
			objects[count++] = e;
			return true;
		}
		return false;
	}

	@Override
	public void add(int index, T element) {

	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return false;
	}

	@Override
	public void clear() {

	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int index) {
		return (T) objects[index];
	}

	@Override
	public int indexOf(Object o) {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return null;
	}

	@Override
	public int lastIndexOf(Object o) {
		return 0;
	}

	@Override
	public ListIterator<T> listIterator() {
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return null;
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public T remove(int index) {
		return null;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public T set(int index, T element) {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return null;
	}

	@Override
	public Object[] toArray() {
		return objects;
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}
}