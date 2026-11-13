package com.ctrlimpact.clientes.web;

import com.ctrlimpact.clientes.config.ContractProperties;
import com.ctrlimpact.clientes.contract.ContractLoader;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Único endpoint: listado de clientes mockeado con el ejemplo del contrato. */
@RestController
public class ClienteController {

    private final ContractLoader contract;
    private final ContractProperties props;

    public ClienteController(ContractLoader contract, ContractProperties props) {
        this.contract = contract;
        this.props = props;
    }

    @GetMapping(value = "${contract.endpoint:/clientes}", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode listarClientes() {
        return contract.exampleResponse(props.endpoint(), "get", props.status());
    }
}
