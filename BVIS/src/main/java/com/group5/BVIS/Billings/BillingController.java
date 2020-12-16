package com.group5.BVIS.Billings;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.Billings.Billing;
import com.group5.BVIS.RestModels.ReceiveInsuranceReimbursement;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for the billing WS. Manages bills.
 * 
 *
 */
@RestController
public class BillingController {
	
	//autowire needed repositories, and the camunda runtime service
	@Autowired
	private BillingRepository billingRepository;
	
	@Autowired
	private RuntimeService runtimeService;
	
	final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
    	
	//ControllerMethods
	
	/**
	 * return all customer billings in the database
	 * @return list of billings
	 */
	@GetMapping("/billings")
    public List<Billing> billingFindAll() {
        return billingRepository.findAll();
	}
	
	/**
	 * return the billing with a specific Id
	 * @param id, id of the bill wanted
	 * @return return the billing object that was requested
	 */
	@GetMapping("/billings/{id}")
    public Billing billingFindOne(@PathVariable("id") Long id) {
        return billingRepository.getOne(id);
	}
	
	/**
	 * method to return a bill as a txt file. could be changed to a pdf in the future
	 * @param id, id of the bill wanted
	 * @param response, the response given back, initialized here
	 */
	@GetMapping("/billings/{id}/file")
    public void billingProvideFile(@PathVariable("id") Long id, HttpServletResponse response) {
		
		Billing billing = billingRepository.getOne(id);
		
		try {
			LOGGER.info("trying to create a txt billing file");
			response.setContentType("text/plain");
			
			response.setHeader("Content-Disposition","attachment;filename=billing" + billing.getContract().getId() + ".txt");
			
			 ServletOutputStream out = response.getOutputStream();
			 
			 out.write(billing.toString().getBytes("UTF-8"));
			 out.flush();
			 out.close();
		} catch (Exception e) {
			LOGGER.info("failed to create the txt file" + e.toString());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/**
	 * Deprecated: The insurance reimbursement is now sent with the overall damage assessment by Capitol.
	 * Access point for capitol to send the insurance reimbursement money, the money is saved in the process instance.
	 * @param reimbursement object as specified in the interface document (in package restModels)
	 * @return return status code 200 if everything worked, and the process instance was found or 404 and a text if the process was not found.
	 */
	@Deprecated
	@SuppressWarnings("rawtypes")
	@PostMapping("/billings/receive-insurance-reimbursement")
	public ResponseEntity receiveInsuranceReimbursement(@RequestBody ReceiveInsuranceReimbursement reimbursement) {
		try {
			
			//correlate the message received (InsuranceReimburcement) with the processInstance corresponding to the customer_id as they are the same thing in 
			//the BVIS case.
			//for some reason is the result is empty and cant be used, to access the processinstance.
			MessageCorrelationResult result = runtimeService.createMessageCorrelation("InsuranceReimburcement")
					.processInstanceId(reimbursement.getCustomer_id())
					.setVariable("insuranceMoney", reimbursement.getMoney())
					.correlateWithResult();
			
			LOGGER.info("correlated message with: " + reimbursement.getCustomer_id());
			
			return ResponseEntity.status(HttpStatus.OK).build();
			
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new String("failed to find the corresponding customer"));
		}
	}
	
	/**
	 * create a new database entry
	 * @param billing the billing object to be saved
	 * @return returns the database id of the object.
	 */
	@PostMapping("/billings/create")
	public Long billingCreate(@RequestBody Billing billing) {
		
		return billingRepository.save(billing).getId();
	}
	
	/**
	 * Delete a billing, currently only used for tests.
	 * @param id the id of the bill to delete.
	 */
	@DeleteMapping("/billings/{id}")
    public void billingDeleteOne(@PathVariable("id") Long id) {
        billingRepository.deleteById(id);
	}
}