import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import groovy.json.StringEscapeUtils
import groovy.transform.Field
import internal.GlobalVariable
import utils.InicioSesionClass
import utils.SelectorCalendarioClass

import org.openqa.selenium.Keys as Keys

//Intancia de class
InicioSesionClass inicioSesion = new InicioSesionClass()
SelectorCalendarioClass seleccionCalendario = new SelectorCalendarioClass()

def horaInicio(String hora) {
	TestObject iframe = findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/iframe')
	
	WebUI.switchToFrame(iframe, 10)
	
	String xpathHora = "//div[@id='tabview:addCuposForm:horaInicio_panel']//li[contains(@data-label, '${hora}')]"
    
    TestObject horaObj = new TestObject()
    horaObj.addProperty("xpath", ConditionType.EQUALS, xpathHora)
    
    WebUI.enhancedClick(horaObj)
	
	WebUI.switchToDefaultContent()
	
}

def horaFin(String horaFin) {
	TestObject iframe = findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/iframe')
	
	WebUI.switchToFrame(iframe, 10)
	
	String xpathHora = "//div[@id='tabview:addCuposForm:horaFinal_panel']//li[contains(@data-label, '${horaFin}')]"
    
    TestObject horaObjfin = new TestObject()
	
    horaObjfin.addProperty("xpath", ConditionType.EQUALS, xpathHora)
    
	WebUI.waitForElementClickable(horaObjfin, 60)
	
	WebUI.scrollToElement(horaObjfin, 30)
	
    WebUI.enhancedClick(horaObjfin)
	
	WebUI.switchToDefaultContent()
	
}

def formulario() {
	
	int intentos =3
	boolean exito = false
	
	while (intentos > 0 && !exito) {
	
		try {
			
			WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_Seleccione_ui-icon ui-icon-triangle-1-s ui-c'))
			
			WebUI.waitForElementVisible(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_Tiempo'), 40)
			
			WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_Tiempo'))
			
			WebUI.setText(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/input_Rango(minutos)_tabviewaddCuposFormrango'),
				'5')
			
			WebUI.sendKeys(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/input_Rango(minutos)_tabviewaddCuposFormrango'),
				Keys.chord(Keys.TAB))
			
			WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 30)
			
			WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_Seleccione_ui-icon ui-icon-triangle-1-s ui-c_1'))
			
			WebUI.waitForElementVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/li_Seleccionar'), 60)			
			
			horaInicio("07:00 a. m.")
			
			WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_Seleccione_ui-icon ui-icon-triangle-1-s ui-c_1_2'))
			
			WebUI.waitForElementVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/li_Seleccionar_horaFinal'), 60)
			
			horaFin("14:00 p. m.")
			
			WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/button_Aceptar'))
			
			WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 60)
			
			exito = true
			
			
		} catch (Exception e) {
			
			intentos--
			
			if (intentos == 0) {
				e.printStackTrace()
			}
		}
	}
}

WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl('https://medcloudpruebas.idl.com.co/medCloud/index.xhtml')

inicioSesion.inicioSesion()

seleccionCalendario.selectDynamicDate("20")

WebUI.enhancedClick(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/button_agregareliminar cupos del dia'))

WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 30)

formulario()

WebUI.closeBrowser()