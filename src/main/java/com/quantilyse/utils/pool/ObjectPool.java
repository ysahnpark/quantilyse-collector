package com.quantilyse.utils.pool;

import java.util.concurrent.LinkedBlockingDeque;

public class ObjectPool<T> {

	LinkedBlockingDeque<T> pooledObjects = new LinkedBlockingDeque<>();
}
