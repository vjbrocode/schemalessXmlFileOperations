package com.db.ems.service;

public interface RecordService {
	public String getAllRecords();

	public boolean addRecord(String record);

	public boolean deleteRecordById(String id);
}
