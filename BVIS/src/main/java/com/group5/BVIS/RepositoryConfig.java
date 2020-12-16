package com.group5.BVIS;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.group5.BVIS.Cars.Car;
import com.group5.BVIS.Contracts.Contract;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    
	/**
	 * method to configure the repositories that they also send Ids in rest calls.
	 */
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Car.class);
        config.exposeIdsFor(Contract.class);
    }
}