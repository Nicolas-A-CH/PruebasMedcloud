package utils

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class SelectorCalendarioClass {
//Elementos DOM
	
	TestObject ifremObj = findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/iframe')
	
	@Keyword
	def selectDynamicDate(String day) {
		int intentos = 3
	
		boolean exito = false
	
		while ((intentos > 0) && !(exito)) {
			try {
				WebUI.switchToFrame(ifremObj, 10)
	
				String xpathDate = "//table[@class='ui-datepicker-calendar']//a[text()='$day']"
	
				TestObject fecha = new TestObject("dia_$day").addProperty('xpath', com.kms.katalon.core.testobject.ConditionType.EQUALS,
					xpathDate)
	
				WebUI.waitForElementPresent(fecha, 5)
	
				WebUI.waitForElementClickable(fecha, 5)
	
				WebUI.enhancedClick(fecha)
	
				exito = true
			}
			catch (org.openqa.selenium.StaleElementReferenceException e) {
				intentos--
	
				if (intentos == 0) {
					throw e
				}
				
				WebUI.switchToDefaultContent()
	
				WebUI.delay(1)
			}
			finally {
				WebUI.switchToDefaultContent()
			}
		}
	}
}
