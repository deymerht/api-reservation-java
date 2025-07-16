package com.api_reservation.api_reservation.connector;

import com.api_reservation.api_reservation.connector.configuration.EndpointConfiguration;
import com.api_reservation.api_reservation.connector.configuration.HostConfiguration;
import com.api_reservation.api_reservation.connector.configuration.HttpConnectorConfiguration;
import com.api_reservation.api_reservation.connector.response.CityDTO;
import com.api_reservation.api_reservation.exception.ReservationExeption;
import com.api_reservation.api_reservation.num.APIError;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Component
public class CatalogConnector {
  private final String HOST = "api-catalog";
  private final String ENDPOINT = "get-city";

  private HttpConnectorConfiguration configuration;

  @Autowired
  public CatalogConnector(HttpConnectorConfiguration configuration) {
    this.configuration = configuration;
  }

  final String URL = "http://localhost:6070/api/flights/catalog/city/";

  @CircuitBreaker(name = "api-catalog", fallbackMethod = "fallbackMethodGetCity")
  public CityDTO getCity(String code){

    HostConfiguration hostConfiguration = configuration.getHosts().get(HOST);
    EndpointConfiguration endpointConfiguration = hostConfiguration.getEndpoints().get(ENDPOINT);

    HttpClient httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Math.toIntExact(endpointConfiguration.getConnectionTimeout()))
        .doOnConnected(conn -> conn
            .addHandler(new ReadTimeoutHandler(endpointConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS))
            .addHandler(new WriteTimeoutHandler(endpointConfiguration.getWriteTimeout(), TimeUnit.MILLISECONDS)));

    WebClient client = WebClient.builder()
        .baseUrl("http://" + hostConfiguration.getHost() + ":" + hostConfiguration.getPort() + endpointConfiguration.getUrl())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();

    return client.get()
        .uri(uriBuilder -> uriBuilder.build(code))
        .retrieve()
        .bodyToMono(CityDTO.class)
        .share()
        .block();
  }

  private CityDTO fallbackMethodGetCity(String code, CallNotPermittedException callNotPermittedException){
    System.out.println("Calling fallbackMethodGetCity-1");
    return new CityDTO();
  }

  private CityDTO fallbackMethodGetCity(String code, Exception exception){
    System.out.println("Calling fallbackMethodGetCity-1");
    throw  new ReservationExeption(APIError.VALIDATION_ERROR);
  }

}
