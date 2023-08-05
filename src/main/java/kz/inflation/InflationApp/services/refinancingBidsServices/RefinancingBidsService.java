package kz.inflation.InflationApp.services.refinancingBidsServices;

import kz.inflation.InflationApp.models.refinancingBids.RefinancingBids;
import kz.inflation.InflationApp.repositories.refinancingBidsRepository.RefinancingBidsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RefinancingBidsService {
    private final RefinancingBidsRepository repository;

    @Autowired
    public RefinancingBidsService(RefinancingBidsRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void updateData(HashMap<LocalDate, Float> data){
        List<RefinancingBids> bids = new ArrayList<>();
        for (LocalDate date : data.keySet().stream().toList()) {
            bids.add(new RefinancingBids(date, data.get(date)));
        }
        repository.saveAll(bids);
    }

    public List<RefinancingBids> getAll(){
        List<RefinancingBids> bids = new ArrayList<>();
        for (LocalDate date : repository.getDistinctYear()) {
            bids.add(repository.findDistinctFirstByYearOrderByYearAsc(date));
        }
        Collections.reverse(bids);
        return bids;
    }

}
