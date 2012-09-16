package com.fb.util;

import java.util.concurrent.atomic.AtomicLong;

public enum IDGenerator {
    INSTANCE;

    private AtomicLong nextIdPre = new AtomicLong(System.nanoTime());
    private AtomicLong nextIdPost = new AtomicLong(System.nanoTime());

    public String nextID() {
	nextIdPost.incrementAndGet();
	nextIdPre.incrementAndGet();
	return nextIdPre.toString() + "-" + nextIdPost.toString();
    }

}
