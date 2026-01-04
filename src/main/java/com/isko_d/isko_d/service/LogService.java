package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Action;
import com.isko_d.isko_d.model.Device;
import com.isko_d.isko_d.model.Log;
import com.isko_d.isko_d.dto.log.LogRequestDTO;
import com.isko_d.isko_d.dto.log.LogResponseDTO;
import com.isko_d.isko_d.repository.ActionRepository;
import com.isko_d.isko_d.repository.DeviceRepository;
import com.isko_d.isko_d.repository.LogRepository;
import com.isko_d.isko_d.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LogService {
    private final LogRepository logRepository;
    private final DeviceRepository deviceRepository;
    private final ActionRepository actionRepository;

    public LogService(
        LogRepository logRepository,
        DeviceRepository deviceRepository,
        ActionRepository actionRepository
    ) {
        this.logRepository = logRepository;
        this.deviceRepository = deviceRepository;
        this.actionRepository = actionRepository;
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
            deviceRepository.findById(request.getDeviceId())
                .orElseThrow(() -> new NotFoundException(Device.class, request.getDeviceId())),
            actionRepository.findById(request.getActionId())
                .orElseThrow(() -> new NotFoundException(Action.class, request.getActionId()))
        ));
        
        return new LogResponseDTO(saved);
    }

    public LogResponseDTO update(Long id, LogRequestDTO request) {
        Log existing = logRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Log.class, id));

        if (request.getDeviceId() != null) {
            Device device = deviceRepository.findById(request.getDeviceId())
                .orElseThrow(() -> new NotFoundException(Device.class, request.getDeviceId()));
            existing.setDevice(device);
        }

        if (request.getActionId() != null) {
            Action action = actionRepository.findById(request.getActionId())
                .orElseThrow(() -> new NotFoundException(Action.class, request.getActionId()));
            existing.setAction(action);
        }

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
