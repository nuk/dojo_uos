package br.unb.unbiquitous.dojo;


import java.util.List;

import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.unb.unbiquitous.ubiquitos.uos.context.UOSApplicationContext;
import br.unb.unbiquitous.ubiquitos.uos.driverManager.DriverData;
import br.unb.unbiquitous.ubiquitos.uos.messageEngine.messages.ServiceCall;
import br.unb.unbiquitous.ubiquitos.uos.messageEngine.messages.ServiceResponse;

public class UserDriverTest {

	private UOSApplicationContext uosApplicationContext;

	@Before
	public void setUp() throws Exception {
		uosApplicationContext = new UOSApplicationContext();
		uosApplicationContext.init("br/unb/unbiquitous/dojo/ubiquitos_test");
	}

	@Test
	public void should_list_my_driver() throws Exception {
		List<DriverData> listDrivers = uosApplicationContext.getDriverManager().listDrivers();
		
		Assert.assertNotNull(listDrivers);
		Assert.assertEquals(1, listDrivers.size());
		Assert.assertEquals("My_user_driver", listDrivers.get(0).getInstanceID());
	}
	
	@Test
	public void should_return_all_user_data() throws Exception {
		ServiceResponse response = uosApplicationContext.getAdaptabilityEngine()
		.callService(null, "retrieveUserInfo","br.unb.unbiquitous.dojo.UserDriver", null, null, null);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getResponseData());
		String string_user = response.getResponseData().get("user");
		Assert.assertNotNull(string_user);
		JSONObject user = new JSONObject(string_user);
		
		Assert.assertEquals("Bruce Wayne",user.opt("name"));
		Assert.assertEquals("Batman",user.opt("codename"));
		Assert.assertEquals("Batmobile",user.opt("vehicle"));
		Assert.assertEquals("Solitude Fortress",user.opt("location"));
	}
	
	@Test
	public void should_return_only_specifyied_data() throws Exception {
		
		ServiceCall serviceCall = new ServiceCall("br.unb.unbiquitous.dojo.UserDriver","retrieveUserInfo"); 
		serviceCall.addParameter("specificField", "codename");
		
		ServiceResponse response = uosApplicationContext.getAdaptabilityEngine().callService(null,serviceCall);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getResponseData());
		String string_user = response.getResponseData().get("user");
		Assert.assertNotNull(string_user);
		JSONObject user = new JSONObject(string_user);
		
		Assert.assertNull(user.opt("name"));
		Assert.assertEquals("Batman",user.opt("codename"));
		Assert.assertNull(user.opt("vehicle"));
		Assert.assertNull(user.opt("location"));
	}
	
	@After
	public void tearDown() throws Exception {
		uosApplicationContext.tearDown();
	}

}
