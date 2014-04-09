package org.estudy.notification;

public interface EventLifeCycle<T> {
	void preSave(T t);
	void postSave(T t);
	
	void preDelete(T t);
	void postDelete(T t);
}
