package com.ajou.op.service;

import com.ajou.op.domain.Part;
import com.ajou.op.exception.ErrorCode;
import com.ajou.op.exception.OpApplicationException;
import com.ajou.op.repositoty.PartRepository;
import com.ajou.op.request.PartCreateRequest;
import com.ajou.op.response.PartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PartService {

    private final PartRepository partRepository;

    public void save(PartCreateRequest request) {
        partRepository.findByName(request.getName())
                .ifPresent(it->new OpApplicationException(ErrorCode.DUPLICATED_PART));

        Part entity =  Part.builder()
                .name(request.getName())
                .build();

        partRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public void get(){

    }

    @Transactional(readOnly = true)
    public List<PartResponse> getAll(){

        return partRepository.findAllByOrderByName().stream()
                .map(en-> PartResponse.builder()
                        .id(en.getId())
                        .name(en.getName())
                        .build())
                .toList();
    }

    public void change(PartCreateRequest request) {

    }

    public void delete(Long id) {}

}
