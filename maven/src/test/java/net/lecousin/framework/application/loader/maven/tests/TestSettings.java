package net.lecousin.framework.application.loader.maven.tests;

import java.io.InputStream;
import java.util.List;

import net.lecousin.framework.application.libraries.LibraryManagementException;
import net.lecousin.framework.application.loader.maven.MavenSettings;
import net.lecousin.framework.core.test.LCCoreAbstractTest;

import org.junit.Assert;
import org.junit.Test;

public class TestSettings extends LCCoreAbstractTest {

	@Test
	public void testSettings() throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream("test-maven/settings.xml");
		MavenSettings settings = MavenSettings.load(in);
		in.close();
		Assert.assertEquals("/test/maven/local/repo", settings.getLocalRepository());
		List<String> profiles = settings.getActiveProfiles();
		Assert.assertEquals(0, profiles.size());
	}
	
	@Test
	public void testEmptySettings() throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream("test-maven/settings.empty.xml");
		MavenSettings settings = MavenSettings.load(in);
		in.close();
		Assert.assertNull(settings.getLocalRepository());
	}
	
	@Test(expected = LibraryManagementException.class)
	public void testInvalidSettings() throws Exception {
		try (InputStream in = getClass().getClassLoader().getResourceAsStream("test-maven/settings.invalid.xml")) {
			MavenSettings.load(in);
		}
	}

	@Test
	public void testSettingsActiveProfile() throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream("test-maven/settings.active_profile.xml");
		MavenSettings settings = MavenSettings.load(in);
		in.close();
		Assert.assertEquals("/test/maven/local/repo", settings.getLocalRepository());
		List<String> profiles = settings.getActiveProfiles();
		Assert.assertEquals(1, profiles.size());
		Assert.assertEquals("test", profiles.get(0));
	}
	
}
