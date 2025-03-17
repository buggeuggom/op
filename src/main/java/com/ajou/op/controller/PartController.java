package com.ajou.op.controller;

import com.ajou.op.request.PartCreateRequest;
import com.ajou.op.response.PartResponse;
import com.ajou.op.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/parts")
@RequiredArgsConstructor
public class PartController {

    private final PartService partService;


    @PostMapping()
    public void create(@RequestBody PartCreateRequest request) {

        partService.save(request);
    }

    @GetMapping()
    public List<PartResponse> getAll(){
       return partService.getAll();
    }
}
