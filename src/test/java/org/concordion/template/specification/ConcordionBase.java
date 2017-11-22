package org.concordion.template.specification;

import org.concordion.api.ConcordionResources;
import org.concordion.api.extension.Extension;
import org.concordion.api.extension.Extensions;
import org.concordion.cubano.config.Config;
import org.concordion.cubano.config.ProxyConfig;
import org.concordion.cubano.driver.concordion.EnvironmentExtension;
import org.concordion.ext.LoggingFormatterExtension;
import org.concordion.ext.StoryboardLogListener;
import org.concordion.ext.TimestampFormatterExtension;
import org.concordion.ext.runtotals.RunTotalsExtension;
import org.concordion.template.AppConfig;

/**
 * Sets up any Concordion extensions or other items that must be shared between index and test fixtures.
 * 
 * NOTE: Test can be run from a Fixture or an Index, any global (@...Suite) methods must be in this class 
 * to ensure the are executed from whichever class initiates the test run.
 */
@ConcordionResources("/customConcordion.css")
@Extensions({ TimestampFormatterExtension.class, RunTotalsExtension.class })
public abstract class ConcordionBase extends org.concordion.cubano.framework.ConcordionBase {

    // TODO Is the position of this causing the logging of the final screenshot to come to late in HelloWorldFixture?
    @Extension
    private final LoggingFormatterExtension loggerExtension = new LoggingFormatterExtension()
            .registerListener(new StoryboardLogListener(getStoryboard()));

    @Extension
    private final EnvironmentExtension footer = new EnvironmentExtension()
            .withRerunTest("01 - ProcessAndRules-RunSelectedTest")
            .withRerunParameter("token", "ALLOW")
            .withRerunParameter("environment", Config.getInstance().getEnvironment())
            .withRerunParameter("TEST_CLASSNAME", this.getClass().getName().replace(ConcordionBase.class.getPackage().getName() + ".", ""))
            .withRerunParameter("SVN_TAG", EnvironmentExtension.getSubversionUrl())
            .withEnvironment(Config.getInstance().getEnvironment().toUpperCase())
            .withURL(AppConfig.getInstance().getBaseUrl());

    static {
        ProxyConfig proxyConfig = Config.getInstance().getProxyConfig();
        AppConfig config = AppConfig.getInstance();
        config.logSettings();

        // // Set the proxy rules for all rest requests made during the test run
        // HttpEasy.withDefaults()
        // .allowAllHosts()
        // .trustAllCertificates()
        // .baseUrl(config.getBaseUrl());
        //
        // if (proxyConfig.isProxyRequired()) {
        // HttpEasy.withDefaults()
        // .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyConfig.getProxyHost(), proxyConfig.getProxyPort())))
        // .bypassProxyForLocalAddresses(true);
        //
        // if (!proxyConfig.getProxyUsername().isEmpty() && !proxyConfig.getProxyPassword().isEmpty()) {
        // HttpEasy.withDefaults().proxyAuth(proxyConfig.getProxyUsername(), proxyConfig.getProxyPassword());
        // }
        // }
    }

}
