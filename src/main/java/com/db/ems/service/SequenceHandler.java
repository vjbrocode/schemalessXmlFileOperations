package com.db.ems.service;

import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import com.db.ems.repository.FileStoreSeq;

@Component
public class SequenceHandler {

	private final ReentrantLock lock = new ReentrantLock(true);

	private FileStoreSeq fileStore;

	public SequenceHandler(FileStoreSeq fileStore) {
		this.fileStore = fileStore;
	}

	public String getNextSequenceNumber() {
		lock.lock();
		String data = "";
		try {
			data = fileStore.read();
			fileStore.write(data);
		} finally {
			lock.unlock();
		}
		return data;
	}
}
