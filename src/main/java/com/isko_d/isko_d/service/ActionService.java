package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Action;
import com.isko_d.isko_d.dto.action.ActionRequestDTO;
import com.isko_d.isko_d.dto.action.ActionResponseDTO;
import com.isko_d.isko_d.repository.ActionRepository;
import com.isko_d.isko_d.exception.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActionService {
    private final ActionRepository actionRepository;

    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public List<ActionResponseDTO> findAll(
        String searchBy,
        String search,
        Sort sort
    ) {
        if (searchBy != null && search != null) {
            switch (searchBy.toLowerCase()) {
                case "name":
                    return actionRepository
                        .findByNameContaining(search, sort)
                        .stream()
                        .map(ActionResponseDTO::new)
                        .toList();
            }
        }

        return actionRepository
            .findAll(sort)
            .stream()
            .map(ActionResponseDTO::new)
            .toList();
    }

    public Page<ActionResponseDTO> findPage(
        int page,
        int size,
        String searchBy,
        String search,
        Sort sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if (searchBy != null && search != null) {
            switch (searchBy.toLowerCase()) {
                case "name":
                    return actionRepository
                        .findByNameContaining(search, pageRequest)
                        .map(ActionResponseDTO::new);
            }
        }

        return actionRepository
            .findAll(pageRequest)
            .map(ActionResponseDTO::new);
    }

    public ActionResponseDTO findById(Long id) {
        return actionRepository.findById(id)
                .map((action) -> new ActionResponseDTO(action))
                .orElseThrow(() -> new NotFoundException(Action.class, id));
    }

    public ActionResponseDTO save(ActionRequestDTO request) {
        Action saved = actionRepository.save(new Action(
                request.getName()
        ));

        return new ActionResponseDTO(saved);
    }

    public ActionResponseDTO update(Long id, ActionRequestDTO request) {
        Action existing = actionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Action.class, id));

        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());
        Action saved = actionRepository.save(existing);

        return new ActionResponseDTO(saved);
    }

    public ActionResponseDTO delete(Long id) {
        Action deleted = actionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Action.class, id));

        actionRepository.deleteById(id);

        return new ActionResponseDTO(deleted);
    }
}
