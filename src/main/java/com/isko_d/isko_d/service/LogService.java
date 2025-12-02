package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Log;
import com.isko_d.isko_d.dto.LogRequestDTO;
import com.isko_d.isko_d.dto.LogResponseDTO;
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

    public List<LogResponseDTO> findAll() {
        return logRepository.findAll()
            .stream()
            .map((log) -> new LogResponseDTO(log))
            .toList();
    }

    public LogResponseDTO findById(Long id) {
        return logRepository.findById(id)
            .map((log) -> new LogResponseDTO(log))
            .orElseThrow(() -> new NotFoundException(Log.class, id));
    }

    public LogResponseDTO save(LogRequestDTO request) {
        Log saved = logRepository.save(new Log(
            request.getActionType(),
            request.getLocation(),
            request.getDeviceId()
        ));

        return new LogResponseDTO(saved);
    }

    public LogResponseDTO update(Long id, LogRequestDTO request) {
        Log existing = logRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Log.class, id));

        if (request.getActionType() != null && !request.getActionType().isBlank()) existing.setActionType(request.getActionType());
        if (request.getLocation() != null && !request.getLocation().isBlank()) existing.setLocation(request.getLocation());
        if (request.getDeviceId() != null && !request.getDeviceId().isBlank()) existing.setDeviceId(request.getDeviceId());

        Log saved = logRepository.save(existing);

        return new LogResponseDTO(saved);
    }

    public LogResponseDTO delete(Long id) {
        Log log = logRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Log.class, id));

        logRepository.deleteById(id);

        return new LogResponseDTO(log);
    }
}
