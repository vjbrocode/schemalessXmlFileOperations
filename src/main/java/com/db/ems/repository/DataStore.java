package com.db.ems.repository;

/**
 * 
 * @author vijaysingh
 * 
 * Generic data store interface that can be implemented to support different type of storage in future like Databases, SFTP, S3 etc
 * 
 * @param <T>
 */
public interface DataStore<T> {
	
	public void write(T t);
	
	public T read();
}
