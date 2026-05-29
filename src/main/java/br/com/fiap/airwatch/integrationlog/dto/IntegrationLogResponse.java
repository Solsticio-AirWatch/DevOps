package br.com.fiap.airwatch.integrationlog.dto;
import br.com.fiap.airwatch.integrationlog.model.IntegrationLog;
import java.time.LocalDateTime;
public record IntegrationLogResponse(Long id,Long cityId,String cityName,String apiName,String endpoint,
    String httpMethod,Integer httpStatus,Integer recordsCount,String result,String errorMessage,
    Integer responseMs,LocalDateTime requestedAt) {
    public static IntegrationLogResponse from(IntegrationLog l) {
        return new IntegrationLogResponse(l.getId(),
            l.getCity()!=null?l.getCity().getId():null,l.getCity()!=null?l.getCity().getName():null,
            l.getApiName(),l.getEndpoint(),l.getHttpMethod(),l.getHttpStatus(),l.getRecordsCount(),
            l.getResult(),l.getErrorMessage(),l.getResponseMs(),l.getRequestedAt());
    }
}
