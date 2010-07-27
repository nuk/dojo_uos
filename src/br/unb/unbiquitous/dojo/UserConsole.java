package br.unb.unbiquitous.dojo;

import org.json.JSONObject;

import br.unb.unbiquitous.ubiquitos.uos.context.UOSApplicationContext;
import br.unb.unbiquitous.ubiquitos.uos.context.UosApplicationLauncher;
import br.unb.unbiquitous.ubiquitos.uos.messageEngine.messages.ServiceResponse;

public class UserConsole implements UosApplicationLauncher{

	@Override
	public void start(UOSApplicationContext appCtx) {
		try {
			ServiceResponse response = appCtx.getAdaptabilityEngine()
				.callService(null, "retrieveUserInfo","br.unb.unbiquitous.dojo.UserDriver", null, null, null);
			
			JSONObject user = new JSONObject(response.getResponseData().get("user"));
			
			System.out.println("=====================================================");
			System.out.println("Name:"+user.get("name")+"\n");
			System.out.println("Codename:"+user.get("codename")+"\n");
			System.out.println("Last used vehicle:"+user.get("vehicle")+"\n");
			System.out.println("Current location:"+user.get("location")+"\n");
			System.out.println("=====================================================");
		} catch (Exception e) {
			System.out.println("Not possible to retrieve data.");
		}
	}

	@Override
	public void stop() throws Exception {}

}
