package com.robintegg.htmx.demo.contacts;

import java.util.List;

public record PagedList<T>(List<T> items, boolean hasPrevious, boolean hasNext, int count) {
}
