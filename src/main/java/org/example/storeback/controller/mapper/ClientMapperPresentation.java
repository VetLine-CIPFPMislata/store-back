package org.example.storeback.controller.mapper;

import org.example.storeback.controller.webmodel.request.ClientInsertRequest;
import org.example.storeback.controller.webmodel.request.ClientUpdateRequest;
import org.example.storeback.controller.webmodel.response.ClientResponse;
import org.example.storeback.domain.service.dto.ClientDto;

public class ClientMapperPresentation {
    private static ClientMapperPresentation INSTANCE;

    public static ClientMapperPresentation getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientMapperPresentation();
        }
        return INSTANCE;
    }
    public static ClientResponse fromClientDtoToClientResponse(ClientDto clientDto){
        if (clientDto == null){
            return null;
        }
        return new ClientResponse(
                clientDto.id(),
                clientDto.name(),
                clientDto.phone(),
                clientDto.email(),
                clientDto.password(),
                clientDto.cartId(),
                clientDto.role()
        );

    }
    public static ClientDto fromClientInsertToClientDto(ClientInsertRequest clientInsert){
        if (clientInsert == null){
            return null;
        }
        return new ClientDto(
                null,
                clientInsert.name(),
                clientInsert.phone(),
                clientInsert.email(),
                clientInsert.password(),
                clientInsert.cartId(),
                clientInsert.role()
        );
    }

    public static ClientDto fromClientUpdateToClientDto(Long id, ClientUpdateRequest clientUpdate){
        if (clientUpdate == null){
            return null;
        }
        return new ClientDto(
                clientUpdate.id(),
                clientUpdate.name(),
                clientUpdate.phone(),
                clientUpdate.email(),
                clientUpdate.password(),
                clientUpdate.cartId(),
                null
        );
    }
}
