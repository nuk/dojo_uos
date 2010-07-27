package br.unb.unbiquitous.dojo;

import org.json.JSONException;
import org.json.JSONObject;

import br.unb.unbiquitous.ubiquitos.uos.context.UOSApplicationContext;
import br.unb.unbiquitous.ubiquitos.uos.context.UOSMessageContext;
import br.unb.unbiquitous.ubiquitos.uos.driverManager.UosDriver;
import br.unb.unbiquitous.ubiquitos.uos.messageEngine.dataType.UpDriver;
import br.unb.unbiquitous.ubiquitos.uos.messageEngine.dataType.UpService.ParameterType;
import br.unb.unbiquitous.ubiquitos.uos.messageEngine.messages.ServiceCall;
import br.unb.unbiquitous.ubiquitos.uos.messageEngine.messages.ServiceResponse;

public class UserDriver implements UosDriver{

	private JSONObject user;

	@Override
	public UpDriver getDriver() {
		UpDriver driver = new UpDriver("br.unb.unbiquitous.dojo.UserDriver");
		
		driver
			.addService("retrieveUserInfo")
				.addParameter("specificField", ParameterType.OPTIONAL);
		return driver;
	}

	@Override
	public void init(UOSApplicationContext arg0, String arg1) {
		try {
			user = new JSONObject();
			
			user.put("name", "Bruce Wayne");
			user.put("codename", "Batman");
			user.put("vehicle", "Batmobile");
			user.put("location","Solitude Fortress");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tearDown() {}
	
	public void retrieveUserInfo(ServiceCall serviceCall, 
			ServiceResponse serviceResponse, UOSMessageContext messageContext) {
		
		JSONObject returned_user = user;
		
		String specificField = serviceCall.getParameter("specificField");
		if (specificField != null){
			try {
				returned_user = new JSONObject();
				returned_user.put(specificField, user.get(specificField));
			} catch (JSONException e) {
				throw new RuntimeException("Json Error",e);
			}
		}
		
		serviceResponse.addParameter("user", returned_user.toString());
		
	}

}
