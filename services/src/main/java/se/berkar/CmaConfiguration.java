package se.berkar;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
@Lock(LockType.READ)
public class CmaConfiguration extends AbstractSystemPropertiesConfiguration {

	public final static String PREFIX = "cma";

	public static final ApplicationProperty GROUP_SIZE = new ApplicationProperty("group_size", "20");

	public static final ApplicationProperty START_INTERVAL_IN_MINUTES = new ApplicationProperty("interval-in-min", Integer.toString(5));

	@Override
	public String getPrefix() {
		return PREFIX;
	}

	public String getStatisticsPeriod() {
		return getValue(GROUP_SIZE);
	}

	public void setStatisticsPeriod(String theValue) {
		setValue(GROUP_SIZE, theValue);
	}

	public Integer getStatisticsTimeoutInMs() {
		return Integer.valueOf(getValue(START_INTERVAL_IN_MINUTES));
	}

	public void setStatisticsTimeoutInMs(Integer theValue) {
		setValue(START_INTERVAL_IN_MINUTES, theValue.toString());
	}

}
