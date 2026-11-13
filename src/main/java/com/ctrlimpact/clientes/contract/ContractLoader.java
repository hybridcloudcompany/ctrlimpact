package com.ctrlimpact.clientes.contract;

import com.ctrlimpact.clientes.config.ContractProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Lee el contrato OpenAPI configurado y entrega el ejemplo de respuesta
 * de una operación. Si el contrato no es válido, la app falla al iniciar.
 */
@Component
public class ContractLoader {

    private final JsonNode contract;

    public ContractLoader(ContractProperties props, ResourceLoader resourceLoader) throws IOException {
        Resource resource = resourceLoader.getResource(props.path());
        if (!resource.exists()) {
            throw new IllegalStateException("No se encontró el contrato en: " + props.path());
        }
        try (InputStream in = resource.getInputStream()) {
            this.contract = new ObjectMapper(new YAMLFactory()).readTree(in);
        }
        // Validación temprana: el contrato debe definir el endpoint y un ejemplo
        exampleResponse(props.endpoint(), "get", props.status());
    }

    public JsonNode exampleResponse(String path, String method, String status) {
        JsonNode json = contract.path("paths").path(path).path(method)
                .path("responses").path(status)
                .path("content").path("application/json");
        if (json.isMissingNode()) {
            throw new IllegalStateException("El contrato no define %s %s con respuesta %s (application/json)"
                    .formatted(method.toUpperCase(), path, status));
        }
        JsonNode example = json.path("example");
        if (example.isMissingNode()) {
            // Alternativa: primer elemento de "examples"
            Iterator<JsonNode> it = json.path("examples").elements();
            if (it.hasNext()) {
                example = it.next().path("value");
            }
        }
        if (example.isMissingNode() || example.isNull()) {
            throw new IllegalStateException("El contrato no define un 'example' para %s %s (%s)"
                    .formatted(method.toUpperCase(), path, status));
        }
        return example;
    }
}
