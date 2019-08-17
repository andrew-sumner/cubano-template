package org.concordion.template.specification.google;

import org.concordion.template.framework.CubanoTemplateFixture;

public class SearchForConcordionFixture extends CubanoTemplateFixture {

    public String google(String term, String site) {
		return workflow()
				.openSearch()
				.searchFor(term)
				.getSearchResult(site);
	}
}
