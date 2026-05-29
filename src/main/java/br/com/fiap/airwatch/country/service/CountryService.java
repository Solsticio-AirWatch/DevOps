package br.com.fiap.airwatch.country.service;
import br.com.fiap.airwatch.country.dto.*;
import br.com.fiap.airwatch.country.model.Country;
import br.com.fiap.airwatch.country.repository.CountryRepository;
import br.com.fiap.airwatch.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class CountryService {
    private final CountryRepository repo;
    public Page<CountryResponse> findAll(Pageable p){return repo.findAll(p).map(CountryResponse::from);}
    public CountryResponse findById(Long id){return CountryResponse.from(findOrThrow(id));}
    public CountryResponse create(CountryRequest req){
        if(repo.existsByIsoCode(req.isoCode())) throw new BusinessException("ISO already exists: "+req.isoCode());
        return CountryResponse.from(repo.save(Country.builder().name(req.name()).isoCode(req.isoCode().toUpperCase()).continent(req.continent()).build()));
    }
    public CountryResponse update(Long id,CountryRequest req){
        Country c=findOrThrow(id); c.setName(req.name()); c.setIsoCode(req.isoCode().toUpperCase()); c.setContinent(req.continent());
        return CountryResponse.from(repo.save(c));
    }
    public void delete(Long id){findOrThrow(id);repo.deleteById(id);}
    public Country findOrThrow(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Country not found: "+id));}
}
