package br.com.fiap.airwatch.integrationlog.service;
import br.com.fiap.airwatch.city.service.CityService;
import br.com.fiap.airwatch.exception.ResourceNotFoundException;
import br.com.fiap.airwatch.integrationlog.dto.*;
import br.com.fiap.airwatch.integrationlog.model.IntegrationLog;
import br.com.fiap.airwatch.integrationlog.repository.IntegrationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class IntegrationLogService {
    private final IntegrationLogRepository repo;
    private final CityService cityService;
    public Page<IntegrationLogResponse> findAll(Pageable p) { return repo.findAll(p).map(IntegrationLogResponse::from); }
    public Page<IntegrationLogResponse> findByApiName(String apiName,Pageable p) { return repo.findByApiNameOrderByRequestedAtDesc(apiName,p).map(IntegrationLogResponse::from); }
    public Page<IntegrationLogResponse> findByResult(String result,Pageable p) { return repo.findByResultOrderByRequestedAtDesc(result,p).map(IntegrationLogResponse::from); }
    public Page<IntegrationLogResponse> findByCity(Long cityId,Pageable p) { return repo.findByCity_IdOrderByRequestedAtDesc(cityId,p).map(IntegrationLogResponse::from); }
    public Page<IntegrationLogResponse> findAllErrors(Pageable p) { return repo.findAllErrors(p).map(IntegrationLogResponse::from); }
    public IntegrationLogResponse findById(Long id) { return IntegrationLogResponse.from(findOrThrow(id)); }
    public IntegrationLogResponse create(IntegrationLogRequest req) {
        var city=req.cityId()!=null?cityService.findOrThrow(req.cityId()):null;
        return IntegrationLogResponse.from(repo.save(IntegrationLog.builder().city(city).apiName(req.apiName())
            .endpoint(req.endpoint()).httpMethod(req.httpMethod()!=null?req.httpMethod():"GET")
            .httpStatus(req.httpStatus()).recordsCount(req.recordsCount()).result(req.result())
            .errorMessage(req.errorMessage()).responseMs(req.responseMs()).requestedAt(req.requestedAt()).build()));
    }
    public void delete(Long id) { findOrThrow(id); repo.deleteById(id); }
    public IntegrationLog findOrThrow(Long id) { return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("IntegrationLog not found: "+id)); }
}
