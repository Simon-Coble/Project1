package com.room3.test;

import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;

import com.revature.dao.AccountDaoImpl;
import com.revature.service.AccountService;

public class DBTests {
	@Before
	public void setup() {
	
		aserv = new AccountService();
		mockDao = mock(AccountDaoImpl.class);
		
		// set the dao that the Account Service relies on == to mockDao
		aserv.adao = mockDao; // adao is the property of AccountService that's = the DaoImpl that the service relies on
		
	}
	
	@After // after every unit test 
	public void teardown() {
		
		aserv = null;
		mockDao = null;
		
	}
}
