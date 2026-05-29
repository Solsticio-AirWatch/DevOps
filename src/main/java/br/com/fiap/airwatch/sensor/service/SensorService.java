package br.com.fiap.airwatch.sensor.service;
import br.com.fiap.airwatch.city.service.CityService;
import br.com.fiap.airwatch.exception.ResourceNotFoundException;
import br.com.fiap.airwatch.sensor.dto.*;
import br.com.fiap.airwatch.sensor.model.Sensor;
import br.com.fiap.airwatch.sensor.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class SensorService {
    private final SensorRepository repo;
    private final CityService cityService;
    public Page<SensorResponse> findAll(Pageable p) { return repo.findAll(p).map(SensorResponse::from); }
    public Page<SensorResponse> findByCity(Long cityId,Pageable p) { return repo.findByCity_Id(cityId,p).map(SensorResponse::from); }
    public SensorResponse findById(Long id) { return SensorResponse.from(findOrThrow(id)); }
    public SensorResponse create(SensorRequest req) {
        var city=cityService.findOrThrow(req.cityId());
        return SensorResponse.from(repo.save(Sensor.builder().city(city).name(req.name()).type(req.type())
            .location(req.location()).latitude(req.latitude()).longitude(req.longitude())
            .source(req.source()!=null?req.source():"IOT").status(req.status()!=null?req.status():"ACTIVE").build()));
    }
    public SensorResponse update(Long id,SensorRequest req) {
        var s=findOrThrow(id); var city=cityService.findOrThrow(req.cityId());
        s.setCity(city); s.setName(req.name()); s.setType(req.type()); s.setLocation(req.location());
        s.setLatitude(req.latitude()); s.setLongitude(req.longitude());
        if(req.source()!=null) s.setSource(req.source());
        if(req.status()!=null) s.setStatus(req.status());
        return SensorResponse.from(repo.save(s));
    }
    public void delete(Long id) { findOrThrow(id); repo.deleteById(id); }
    public Sensor findOrThrow(Long id) { return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Sensor not found: "+id)); }
}
