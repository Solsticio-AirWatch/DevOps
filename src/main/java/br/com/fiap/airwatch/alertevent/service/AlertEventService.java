package br.com.fiap.airwatch.alertevent.service;
import br.com.fiap.airwatch.airreading.service.AirReadingService;
import br.com.fiap.airwatch.alertconfig.service.AlertConfigService;
import br.com.fiap.airwatch.alertevent.dto.*;
import br.com.fiap.airwatch.alertevent.model.AlertEvent;
import br.com.fiap.airwatch.alertevent.repository.AlertEventRepository;
import br.com.fiap.airwatch.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
@Service @RequiredArgsConstructor
public class AlertEventService {
    private final AlertEventRepository repo;
    private final AlertConfigService alertConfigService;
    private final AirReadingService airReadingService;
    public Page<AlertEventResponse> findAll(Pageable p) { return repo.findAll(p).map(AlertEventResponse::from); }
    public Page<AlertEventResponse> findByCity(Long cityId,Pageable p) { return repo.findByCityId(cityId,p).map(AlertEventResponse::from); }
    public Page<AlertEventResponse> findByUser(Long userId,Pageable p) { return repo.findByUserId(userId,p).map(AlertEventResponse::from); }
    public Page<AlertEventResponse> findByStatus(String status,Pageable p) { return repo.findByStatusOrderByEventAtDesc(status,p).map(AlertEventResponse::from); }
    public AlertEventResponse findById(Long id) { return AlertEventResponse.from(findOrThrow(id)); }
    public AlertEventResponse create(AlertEventRequest req) {
        var config=alertConfigService.findOrThrow(req.alertConfigId());
        var reading=airReadingService.findOrThrow(req.readingId());
        return AlertEventResponse.from(repo.save(AlertEvent.builder().alertConfig(config).reading(reading)
            .measuredValue(req.measuredValue()).message(req.message())
            .status(req.status()!=null?req.status():"PENDING").build()));
    }
    public AlertEventResponse markAsSent(Long id) {
        var e=findOrThrow(id); e.setStatus("SENT"); e.setNotificationSent("Y"); e.setNotifiedAt(LocalDateTime.now());
        return AlertEventResponse.from(repo.save(e));
    }
    public AlertEventResponse markAsIgnored(Long id) {
        var e=findOrThrow(id); e.setStatus("IGNORED"); return AlertEventResponse.from(repo.save(e));
    }
    public void delete(Long id) { findOrThrow(id); repo.deleteById(id); }
    public AlertEvent findOrThrow(Long id) { return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("AlertEvent not found: "+id)); }
}
