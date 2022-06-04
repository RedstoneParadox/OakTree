package io.github.redstoneparadox.oaktree.util;

import io.github.redstoneparadox.oaktree.control.Control;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ZIndexedControls implements Iterable<ZIndexedControls.Entry> {
	private final List<Entry> entries = new ArrayList<>();
	private int offset = 0;

	public int size() {
		return entries.size();
	}

	public Entry get(int index) {
		return entries.get(index);
	}

	public void add(Control control) {
		entries.add(new Entry(control, offset));
	}

	public void addOffset(int offset) {
		this.offset += offset;
	}

	public void clear() {
		entries.clear();
	}

	@NotNull
	@Override
	public Iterator<Entry> iterator() {
		return entries.listIterator();
	}

	@Override
	public void forEach(Consumer<? super Entry> action) {
		Iterable.super.forEach(action);
	}

	@Override
	public Spliterator<Entry> spliterator() {
		return Iterable.super.spliterator();
	}

	public record Entry(Control control, int zOffset) { }
}
