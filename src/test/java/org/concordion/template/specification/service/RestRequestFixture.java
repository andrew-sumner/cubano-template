package org.concordion.template.specification.service;

import java.io.IOException;

import org.concordion.template.framework.CubanoTemplateFixture;

public class RestRequestFixture extends CubanoTemplateFixture {

	public boolean callService() throws IOException {
		return workflow()
				.restExample()
				.getIPAddress() != null;
	}
}
