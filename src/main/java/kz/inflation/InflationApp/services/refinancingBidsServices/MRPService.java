package kz.inflation.InflationApp.services.refinancingBidsServices;

import kz.inflation.InflationApp.models.refinancingBids.MRP;
import kz.inflation.InflationApp.repositories.refinancingBidsRepository.MRPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MRPService {
    private final MRPRepository repository;

    @Autowired
    public MRPService(MRPRepository repository) {
        this.repository = repository;
    }


    public List<MRP> getAll(){
        List<MRP> bids = new ArrayList<>();
        for (Integer date : repository.getDistinctYear()) {
            bids.add(repository.findDistinctFirstByYearOrderByYearDesc(date));
        }
        return bids;
    }

    @Transactional
    public void updateData(HashMap<Integer, Integer> data){
        List<MRP> mrps = new ArrayList<>();
        for (Integer date : data.keySet().stream().toList()) {
            mrps.add(new MRP(date, data.get(date)));
        }
        repository.saveAll(mrps);
    }
}
