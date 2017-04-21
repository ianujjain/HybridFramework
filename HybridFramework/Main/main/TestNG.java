package main;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;;

public class TestNG {

	@BeforeTest
	public void BeforeTest() {
		System.out.println("BeforeTest");
	}

	@BeforeMethod
	public void BeforeMethod() {
		System.out.println("BeforeMethod");
	}

	@Test
	public void Fun1() {
		System.out.println("Test-1");
	}

	@Test
	public void Fun2() {
		System.out.println("Test-2");
	}

}
