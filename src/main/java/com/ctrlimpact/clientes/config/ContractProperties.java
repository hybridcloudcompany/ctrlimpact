package com.ctrlimpact.clientes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Configuración del contrato (application.yml, prefijo "contract").
 *
 * @param path     ubicación del contrato OpenAPI (classpath: o file:)
 * @param endpoint ruta del endpoint dentro del contrato
 * @param status   código de respuesta del cual se toma el ejemplo
 */
@ConfigurationProperties(prefix = "contract")
public record ContractProperties(
        @DefaultValue("classpath:contract/clientes-api.yaml") String path,
        @DefaultValue("/clientes") String endpoint,
        @DefaultValue("200") String status) {
}
