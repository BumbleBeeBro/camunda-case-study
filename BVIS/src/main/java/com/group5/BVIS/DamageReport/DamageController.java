package com.group5.BVIS.DamageReport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for a DamageReport. Manages DamageReports.
 */
@RestController
public class DamageController {
	
	//autwire necessary repositories
	@Autowired
	private DamageRepository damageRepository;
	
	/**
	 * Return all DamageReports in the database.
	 * @return list of DamageReports
	 */
	@GetMapping("/damage-reports")
    public List<DamageReport> damageReportFindAll() {
        return damageRepository.findAll();
	}
	
	/**
	 * Return the DamageReport with a specific ID.
	 * @param id, Long. ID of the requested DamageReport.
	 * @return return the billing object that was requested
	 */
	@GetMapping("/damage-reports/{id}")
    public DamageReport damageFindOne(@PathVariable("id") Long id) {
        return damageRepository.getOne(id);
	}
	
	/**
	 * Create a new DamageReport entry in the database.
	 * @param damageReport, DamageReport. DamageReport to be stored.
	 * @return Id of the stored DamageReport.
	 */
	@PostMapping("/damage-reports/create") 
	public Long createDamageReport(@RequestBody DamageReport damageReport) {
		return damageRepository.save(damageReport).getId();
	}
}