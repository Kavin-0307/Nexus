package com.nexus.services;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nexus.configuration.RedisConfig;
import com.nexus.events.DocumentUploadEvent;

import redis.clients.jedis.RedisClient;
import redis.clients.jedis.StreamEntryID;
@Service
public class RedisStreamService {
	private final RedisConfig redisConfig;
	public RedisStreamService(RedisConfig redisConfig) {
		this.redisConfig=redisConfig;
	}
	public StreamEntryID publishUpload(DocumentUploadEvent documentUploadEvent) {
		RedisClient jedis=redisConfig.redisClient();
		
		Map<String,String> map=new HashMap<>();
		map.put("documentId",documentUploadEvent.getDocumentId().toString());
		map.put("userId",documentUploadEvent.getUserId().toString());
		map.put("filePath",documentUploadEvent.getFilePath());
		map.put("timestamp",String.valueOf(System.currentTimeMillis()));
		String streamKey="stream:document:uploads";
		StreamEntryID res=jedis.xadd(streamKey,StreamEntryID.NEW_ENTRY,map);
		return res;
	}
}
