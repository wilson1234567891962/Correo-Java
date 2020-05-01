package com.co.principal;

import org.apache.log4j.Logger;

import com.co.utilities.Utilities;

public class Principal {

	final static Logger logger = Logger.getLogger(Principal.class);

	public static void main(String args[]) {
		try {
			Utilities utilities = new Utilities();
			utilities.getStore();
			utilities.openFolder();
			utilities.readFolder();
			utilities.closeResources();
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
