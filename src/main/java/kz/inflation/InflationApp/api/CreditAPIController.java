package kz.inflation.InflationApp.api;

import kz.inflation.InflationApp.models.MRP;
import kz.inflation.InflationApp.models.MZP;
import kz.inflation.InflationApp.models.RefinancingBids;
import kz.inflation.InflationApp.services.MRPService;
import kz.inflation.InflationApp.services.MZPService;
import kz.inflation.InflationApp.services.RefinancingBidsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bids")
public class CreditAPIController {
    private final MZPService mzpService;
    private final MRPService mrpService;
    private final RefinancingBidsService refinancingBidsService;

    @Autowired
    public CreditAPIController(MZPService mzpService, MRPService mrpService, RefinancingBidsService refinancingBidsService) {
        this.mzpService = mzpService;
        this.mrpService = mrpService;
        this.refinancingBidsService = refinancingBidsService;
    }


    @GetMapping("/mzp")
    public List<MZP> getMzp(){
        return mzpService.getAll();
    }

    @GetMapping("/mrp")
    public List<MRP> getMrp(){
        return mrpService.getAll();
    }

    @GetMapping("/refinancing-bids")
    public List<RefinancingBids> getRefinancingBids(){
        return refinancingBidsService.getAll();
    }
}
