package dev.cnpe.inventoryappapi.controllers;

import dev.cnpe.inventoryappapi.domain.dtos.InfoResponse;
import dev.cnpe.inventoryappapi.services.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class InformationController {

  private final InfoService infoService;

  @GetMapping
  public ResponseEntity<InfoResponse> getInfo() {
    InfoResponse info = infoService.getInfo();
    return new ResponseEntity<>(info, HttpStatus.OK);
  }


}
