package com.civicaid.service.impl;

import com.civicaid.dto.request.SchemeRequest;
import com.civicaid.dto.response.SchemeResponse;
import com.civicaid.entity.Program;
import com.civicaid.entity.Scheme;
import com.civicaid.exception.ResourceNotFoundException;
import com.civicaid.repository.ProgramRepository;
import com.civicaid.repository.SchemeRepository;
import com.civicaid.service.SchemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchemeServiceImpl implements SchemeService {

    private final SchemeRepository schemeRepository;
    private final ProgramRepository programRepository;

    @Override
    @Transactional
    public SchemeResponse createScheme(SchemeRequest request) {
        Program program = programRepository.findById(request.getProgramId())
                .orElseThrow(() -> new ResourceNotFoundException("Program", request.getProgramId()));
        Scheme scheme = Scheme.builder()
                .program(program)
                .title(request.getTitle())
                .description(request.getDescription())
                .eligibilityCriteria(request.getEligibilityCriteria())
                .status(request.getStatus() != null ? request.getStatus() : Scheme.SchemeStatus.ACTIVE)
                .build();
        return mapToResponse(schemeRepository.save(scheme));
    }

    @Override
    public SchemeResponse getSchemeById(Long id) {
        return mapToResponse(schemeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scheme", id)));
    }

    @Override
    public List<SchemeResponse> getSchemesByProgram(Long programId) {
        return schemeRepository.findByProgram_ProgramId(programId)
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public Page<SchemeResponse> getAllSchemes(Pageable pageable) {
        return schemeRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional
    public SchemeResponse updateScheme(Long id, SchemeRequest request) {
        Scheme scheme = schemeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scheme", id));
        Program program = programRepository.findById(request.getProgramId())
                .orElseThrow(() -> new ResourceNotFoundException("Program", request.getProgramId()));
        scheme.setProgram(program);
        scheme.setTitle(request.getTitle());
        scheme.setDescription(request.getDescription());
        scheme.setEligibilityCriteria(request.getEligibilityCriteria());
        if (request.getStatus() != null) scheme.setStatus(request.getStatus());
        return mapToResponse(schemeRepository.save(scheme));
    }

    @Override
    @Transactional
    public void updateSchemeStatus(Long id, Scheme.SchemeStatus status) {
        Scheme scheme = schemeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scheme", id));
        scheme.setStatus(status);
        schemeRepository.save(scheme);
    }

    @Override
    @Transactional
    public void deleteScheme(Long id) {
        if (!schemeRepository.existsById(id)) throw new ResourceNotFoundException("Scheme", id);
        schemeRepository.deleteById(id);
    }

    private SchemeResponse mapToResponse(Scheme s) {
        return SchemeResponse.builder()
                .schemeId(s.getSchemeId())
                .programId(s.getProgram().getProgramId())
                .programTitle(s.getProgram().getTitle())
                .title(s.getTitle())
                .description(s.getDescription())
                .eligibilityCriteria(s.getEligibilityCriteria())
                .status(s.getStatus())
                .createdAt(s.getCreatedAt())
                .updatedAt(s.getUpdatedAt())
                .build();
    }
}
