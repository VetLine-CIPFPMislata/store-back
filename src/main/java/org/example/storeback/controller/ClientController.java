package org.example.storeback.controller;

import lombok.RequiredArgsConstructor;
import org.example.storeback.controller.mapper.ClientMapperPresentation;
import org.example.storeback.controller.webmodel.request.ClientInsertRequest;
import org.example.storeback.controller.webmodel.request.ClientUpdateRequest;
import org.example.storeback.controller.webmodel.response.ClientResponse;
import org.example.storeback.domain.service.ClientService;
import org.example.storeback.domain.service.dto.ClientDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable Long id) {
        return clientService.findById(id)
                .map(ClientMapperPresentation::fromClientDtoToClientResponse)
                .map(clientResponse -> new ResponseEntity<>(clientResponse, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody ClientInsertRequest clientInsertRequest) {
        ClientDto clientDto = ClientMapperPresentation.fromClientInsertToClientDto(clientInsertRequest);
        ClientDto savedClientDto = clientService.save(clientDto);
        ClientResponse clientResponse = ClientMapperPresentation.fromClientDtoToClientResponse(savedClientDto);
        return new ResponseEntity<>(clientResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id, @RequestBody ClientUpdateRequest clientUpdateRequest) {
        ClientDto clientDto = ClientMapperPresentation.fromClientUpdateToClientDto(clientUpdateRequest);
        clientDto = new ClientDto(id, clientDto.name(), clientDto.phone(), clientDto.email(), clientDto.password(), clientDto.cartId(), clientDto.role());
        ClientDto updatedClientDto = clientService.save(clientDto);
        ClientResponse clientResponse = ClientMapperPresentation.fromClientDtoToClientResponse(updatedClientDto);
        return new ResponseEntity<>(clientResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
