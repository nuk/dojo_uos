package br.unb.unbiquitous.dojo;

import br.unb.unbiquitous.ubiquitos.uos.context.ContextException;
import br.unb.unbiquitous.ubiquitos.uos.context.UOSApplicationContext;

public class UosStarter {
	public static void main(String[] args) throws ContextException {
		UOSApplicationContext uosApplicationContext = new UOSApplicationContext();
		uosApplicationContext.init("br/unb/unbiquitous/dojo/ubiquitos");
	}
}
