package com.auditory.RepositoryService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auditory.RepositoryService.repository.AudioRepository;
import com.auditory.RepositoryService.repository.ChangeLogRepository;
import com.auditory.RepositoryService.repository.RepositoryManagerRepository;
import com.auditory.RepositoryService.model.*;

@RestController
@RequestMapping(value = "changelog")
public class ChangeLogController {
	@Autowired
	ChangeLogRepository clRepository;
	
	@Autowired
	AudioRepository audRepository;
	
	@Autowired
	RepositoryManagerRepository rmRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<ChangeLog> getAllChangeLogs()
	{
		List<ChangeLog> changeLogs = clRepository.findAll();
		return changeLogs;
	}
	
	@RequestMapping(value = "/{managerId}", method = RequestMethod.GET)
	public List<ChangeLog> findChangeLogsByManger(@PathVariable("managerId") String managerId)
	{
		RepositoryManager manager = rmRepository.findOne(managerId);
		List<ChangeLog> changelogs = clRepository.findByManager(manager);
		return changelogs;
	}
	
	@RequestMapping(value = "/{audioId}", method = RequestMethod.GET)
	public List<ChangeLog> findChangeLogsByAudio(@PathVariable("audioId") long audioId)
	{
		Audio audio = audRepository.findOne(audioId);
		List<ChangeLog> changeLogs = clRepository.findByAudio(audio);
		return changeLogs;
	}
	
	@RequestMapping(value = "/{managerId}/{audioId}")
	public ChangeLog saveChangeLog(@PathVariable("managerId") String managerId, 
			@PathVariable("audioId") long audioId, @RequestBody String changeType)
	{
		RepositoryManager manager = rmRepository.findOne(managerId);
		Audio audio = audRepository.findOne(audioId);
		ChangeLog changeLog = new ChangeLog(changeType, audio, manager);
		clRepository.save(changeLog);
		return changeLog;
	}
}
