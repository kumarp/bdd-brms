package com.rhc.brms.ref;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.drools.event.rule.AfterActivationFiredEvent;
import org.junit.Assert;
import org.junit.Test;

import com.rhc.brms.ref.domain.Application;
import com.rhc.brms.ref.domain.Customer;
import com.rhc.brms.ref.mortgageApplication.MortgageApplicationRequest;
import com.rhc.brms.ref.mortgageApplication.MortgageApplicationResponse;
import com.rhc.brms.ref.mortgageApplication.MortgageApplicationService;

public class MortgageTest {

	private static HashMap<String, List<AfterActivationFiredEvent>> firedActivations;
	private static MortgageApplicationService droolsExecService = new MortgageApplicationService();

	private final static Long CUSTOMER_ID_1 = Long.valueOf( 1 );
	private final static Long CUSTOMER_ID_2 = Long.valueOf( 2 );
	private final static Long CUSTOMER_ID_3 = Long.valueOf( 3 );

	private final static Long APPLICATION_ID_1 = Long.valueOf( 1 );
	private final static Long APPLICATION_ID_2 = Long.valueOf( 2 );
	private final static Long APPLICATION_ID_3 = Long.valueOf( 3 );

	@Test
	public void shouldApproveCustomer1and2DeniedCustomer3() {
		MortgageApplicationRequest request = new MortgageApplicationRequest( createApplications(), createCustomers() );

		MortgageApplicationResponse response = droolsExecService.executeAllRules( request );

		Assert.assertNotNull( response );

		Assert.assertEquals( 2, response.getApprovedApplications().size() );

		// TODO how do I do this cleaner?
		boolean containsCustomer1 = false;
		boolean containsCustomer2 = false;
		boolean containsCustomer3 = false;
		for ( Application application : response.getApprovedApplications() ) {
			if ( application.getCustomerId() == 1L ) {
				containsCustomer1 = true;
			} else if ( application.getCustomerId() == 2L ) {
				containsCustomer2 = true;
			}
		}
		Assert.assertTrue( containsCustomer1 );
		Assert.assertTrue( containsCustomer2 );
		Assert.assertFalse( containsCustomer3 );

		Assert.assertEquals( 1, response.getDeniedApplications().size() );

		containsCustomer1 = false;
		containsCustomer2 = false;
		containsCustomer3 = false;
		for ( Application application : response.getDeniedApplications() ) {
			if ( application.getCustomerId() == 3L ) {
				containsCustomer3 = true;
			}
		}
		Assert.assertFalse( containsCustomer1 );
		Assert.assertFalse( containsCustomer2 );
		Assert.assertTrue( containsCustomer3 );
	}

	private Set<Customer> createCustomers() {

		Set<Customer> customers = new HashSet<Customer>();
		customers.add( new Customer( "Dave", 20, 700, CUSTOMER_ID_1 ) );
		customers.add( new Customer( "Mike", 18, 750, CUSTOMER_ID_2 ) );
		customers.add( new Customer( "Bryan", 28, 450, CUSTOMER_ID_3 ) );

		return customers;
	}

	private Set<Application> createApplications() {

		Set<Application> applications = new HashSet<Application>();
		applications.add( new Application( new BigDecimal( 15000 ), CUSTOMER_ID_1, APPLICATION_ID_1 ) );
		applications.add( new Application( new BigDecimal( 75000 ), CUSTOMER_ID_3, APPLICATION_ID_2 ) );
		applications.add( new Application( new BigDecimal( 85000 ), CUSTOMER_ID_2, APPLICATION_ID_3 ) );

		return applications;
	}

}
