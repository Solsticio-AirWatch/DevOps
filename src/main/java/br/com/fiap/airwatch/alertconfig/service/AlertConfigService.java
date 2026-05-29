package br.com.fiap.airwatch.alertconfig.service;
import br.com.fiap.airwatch.alertconfig.dto.*;
import br.com.fiap.airwatch.alertconfig.model.AlertConfig;
import br.com.fiap.airwatch.alertconfig.repository.AlertConfigRepository;
import br.com.fiap.airwatch.city.service.CityService;
import br.com.fiap.airwatch.exception.*;
import br.com.fiap.airwatch.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class AlertConfigService {
    private final AlertConfigRepository repo;
    private final UsersService usersService;
    private final CityService cityService;
    public Page<AlertConfigResponse> findAll(Pageable p) { return repo.findAll(p).map(AlertConfigResponse::from); }
    public Page<AlertConfigResponse> findByUser(Long userId,Pageable p) { return repo.findByUser_Id(userId,p).map(AlertConfigResponse::from); }
    public Page<AlertConfigResponse> findByCity(Long cityId,Pageable p) { return repo.findByCity_Id(cityId,p).map(AlertConfigResponse::from); }
    public AlertConfigResponse findById(Long id) { return AlertConfigResponse.from(findOrThrow(id)); }
    public AlertConfigResponse create(AlertConfigRequest req) {
        String op=req.operator()!=null?req.operator():">=";
        if(repo.existsByUser_IdAndCity_IdAndPollutantAndOperator(req.userId(),req.cityId(),req.pollutant(),op))
            throw new BusinessException("Alert config already exists for this combination.");
        var user=usersService.findOrThrow(req.userId());
        var city=cityService.findOrThrow(req.cityId());
        return AlertConfigResponse.from(repo.save(AlertConfig.builder().user(user).city(city)
            .pollutant(req.pollutant()).threshold(req.threshold()).operator(op)
            .severity(req.severity()!=null?req.severity():"WARNING").build()));
    }
    public AlertConfigResponse update(Long id,AlertConfigRequest req) {
        var a=findOrThrow(id);
        a.setUser(usersService.findOrThrow(req.userId())); a.setCity(cityService.findOrThrow(req.cityId()));
        a.setPollutant(req.pollutant()); a.setThreshold(req.threshold());
        if(req.operator()!=null) a.setOperator(req.operator());
        if(req.severity()!=null) a.setSeverity(req.severity());
        return AlertConfigResponse.from(repo.save(a));
    }
    public AlertConfigResponse toggle(Long id) {
        var a=findOrThrow(id); a.setIsActive("Y".equals(a.getIsActive())?"N":"Y");
        return AlertConfigResponse.from(repo.save(a));
    }
    public void delete(Long id) { findOrThrow(id); repo.deleteById(id); }
    public AlertConfig findOrThrow(Long id) { return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("AlertConfig not found: "+id)); }
}
