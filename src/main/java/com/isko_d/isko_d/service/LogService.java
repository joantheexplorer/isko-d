package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Log;
import com.isko_d.isko_d.repository.LogRepository;
import com.isko_d.isko_d.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LogService {
    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<Log> findAll() {
        return logRepository.findAll();
    }

    public Log findById(Integer id) {
        return logRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Log.class, id));
    }

    public Log save(Log log) {
        return logRepository.save(log);
    }

    public Log update(Integer id, Log log) {
        Log existingLog = logRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Log.class, id));

        existingLog.setActionType(log.getActionType());
        existingLog.setLocation(log.getLocation());
        existingLog.setDeviceId(log.getDeviceId());

        return logRepository.save(existingLog);
    }

    public Log delete(Integer id) {
        Log log = logRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Log.class, id));

        logRepository.deleteById(id);

        return log;
    }
}
