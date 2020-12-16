package com.group5.BVIS.Contracts;

import java.util.List;

import com.group5.BVIS.Contracts.Contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for the Contract object. Manages Contracts.
 */
@RestController
public class ContractController {
	
	//autowire necessary repository.
	@Autowired
	private ContractRepository contractRepository;
	
	/**
	 * Return all Contracts in the database.
	 * @return list of Contracts.
	 */
	@GetMapping("/contracts")
    public List<Contract> contractFindAll() {
        return contractRepository.findAll();
	}
	
	/**
	 * Returns the Contract with a specific ID.
	 * @param id, Long. ID of selected Contract.
	 * @return requested Contract object.
	 */
	@GetMapping("/contracts/{id}")
    public Contract contractFindOne(@PathVariable("id") Long id) {
        return contractRepository.getOne(id);
	}
	
	/**
	 * Create a new entry in the database.
	 * @param contract, Contract. Contract object to be stored.
	 * @return id, Long. ID of the stored Contract.
	 */
	@PostMapping("/contracts/create")
	public Long contractCreate(@RequestBody Contract contract) {
		
		return contractRepository.save(contract).getId();
	}
	
	/**
	 * Delete an entry from the database.
	 * @param id, Long. Id of the Contract object to be deleted.
	 */
	@DeleteMapping("/contracts/{id}")
    public void contractDeleteOne(@PathVariable("id") Long id) {
        contractRepository.deleteById(id);
	}
}