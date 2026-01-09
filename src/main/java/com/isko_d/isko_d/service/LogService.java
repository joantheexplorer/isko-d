package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Action;
import com.isko_d.isko_d.model.Device;
import com.isko_d.isko_d.model.Log;
import com.isko_d.isko_d.model.User;
import com.isko_d.isko_d.dto.log.LogRequestDTO;
import com.isko_d.isko_d.dto.log.LogResponseDTO;
import com.isko_d.isko_d.repository.ActionRepository;
import com.isko_d.isko_d.repository.DeviceRepository;
import com.isko_d.isko_d.repository.LogRepository;
import com.isko_d.isko_d.repository.UserRepository;
import com.isko_d.isko_d.exception.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LogService {
    private final LogRepository logRepository;
    private final DeviceRepository deviceRepository;
    private final ActionRepository actionRepository;
    private final UserRepository userRepository;

    public LogService(
        LogRepository logRepository,
        DeviceRepository deviceRepository,
        ActionRepository actionRepository,
        UserRepository userRepository
    ) {
        this.logRepository = logRepository;
        this.deviceRepository = deviceRepository;
        this.actionRepository = actionRepository;
        this.userRepository = userRepository;
    }

    public List<LogResponseDTO> findAll(
        String searchBy,
        String search,
        Sort sort
    ) {
        if (searchBy != null && search != null) {
            switch (searchBy.toLowerCase()) {
                case "barcode":
                    return logRepository
                        .findByUserBarcodeContaining(search, sort)
                        .stream()
                        .map(LogResponseDTO::new)
                        .toList();
                case "action":
                    return logRepository
                        .findByActionNameContaining(search, sort)
                        .stream()
                        .map(LogResponseDTO::new)
                        .toList();
                case "location":
                    return logRepository
                        .findByDeviceLocationNameContaining(search, sort)
                        .stream()
                        .map(LogResponseDTO::new)
                        .toList();
                case "device":
                    return logRepository
                        .findByDeviceNameContaining(search, sort)
                        .stream()
                        .map(LogResponseDTO::new)
                        .toList();
            }
        }

        return logRepository
            .findAll(sort)
            .stream()
            .map(LogResponseDTO::new)
            .toList();
    }

    public Page<LogResponseDTO> findPage(
        int page,
        int size,
        String searchBy,
        String search,
        Sort sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if (searchBy != null && search != null) {
            switch (searchBy.toLowerCase()) {
                case "barcode":
                    return logRepository
                        .findByUserBarcodeContaining(search, pageRequest)
                        .map(LogResponseDTO::new);
                case "action":
                    return logRepository
                        .findByActionNameContaining(search, pageRequest)
                        .map(LogResponseDTO::new);
                case "location":
                    return logRepository
                        .findByDeviceLocationNameContaining(search, pageRequest)
                        .map(LogResponseDTO::new);
                case "device":
                    return logRepository
                        .findByDeviceNameContaining(search, pageRequest)
                        .map(LogResponseDTO::new);
            }
        }

        return logRepository
            .findAll(pageRequest)
            .map(LogResponseDTO::new);
    }


    public LogResponseDTO findById(Long id) {
        return logRepository.findById(id)
            .map((log) -> new LogResponseDTO(log))
            .orElseThrow(() -> new NotFoundException(Log.class, id));
    }

    public LogResponseDTO save(LogRequestDTO request) {
        Log saved = logRepository.save(new Log(
            userRepository.findByBarcode(request.getBarcode())
                .orElseThrow(() -> new NotFoundException(User.class, request.getBarcode(), "barcode")),
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
