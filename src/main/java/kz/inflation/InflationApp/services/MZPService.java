package kz.inflation.InflationApp.services;

import kz.inflation.InflationApp.models.MRP;
import kz.inflation.InflationApp.models.MZP;
import kz.inflation.InflationApp.repositories.MZPRepository;
import kz.inflation.InflationApp.scripts.CreditMRPParser;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MZPService {
    private final MZPRepository mzpRepo;

    @Autowired
    public MZPService(MZPRepository mzpRepo) {
        this.mzpRepo = mzpRepo;
    }


    public List<MZP> getAll(){
        List<MZP> bids = new ArrayList<>();
        for (Integer date : mzpRepo.getDistinctYear()) {
            bids.add(mzpRepo.findDistinctFirstByYearOrderByYearDesc(date));
        }
        return bids;
    }

    @Transactional()
    public void updateData(HashMap<Integer, Integer> data) {
        List<MZP> mzps = new ArrayList<>();
        for (Integer date : data.keySet().stream().toList()) {
            mzps.add(new MZP(date, data.get(date)));
        }
        mzpRepo.saveAll(mzps);
    }
}
