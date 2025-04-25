import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.text.SimpleDateFormat

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import groovy.transform.Field
import internal.GlobalVariable
import utils.InicioSesionClass
import utils.SelecccionarCuposClass
import utils.SelectorCalendarioClass

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys as Keys

//Intancia de class
@Field SelecccionarCuposClass seleccionarCupos = new SelecccionarCuposClass()

//variables
String horaInicial = "7:20"
String horaFinal = "9:20"

def confirmarCita(String horaInicio, String horaFin) {
	// Selección de cupo específico
	seleccionarCupos.seleccionarCupoCitaPrevia(horaInicio, horaFin)
	
	int intentos = 3, intentos2 = 3
	boolean exito = false, exito2 = false
	
	while (intentos > 0 && !exito) {
		
		try {
			
			//posiciona el mouse en el elemento
			WebUI.focus(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_Cambiar estados'))
			
			WebUI.waitForElementVisible(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_En sala'), 20)
			
			WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_En sala'))
			
			WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 50)
			
			exito = true
			
		} catch (Exception e) {
			//Reducir los intentos
			intentos--
			
            WebUI.comment("Error al seleccionar el estado 'cancelar'")
            if (intentos == 0) {
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time)
                WebUI.comment("No se pudo seleccionar el link tras varios intentos. Error: " + e.getMessage())
                throw new Exception("No se pudo seleccionar el cupo ${horaInicio}-${horaFin} tras múltiples intentos.")
            }
            WebUI.delay(2)
		}
	}
	
	seleccionarCupos.seleccionarHistoriaClinica(horaInicio, horaFin)
	
	TestObject iframe = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/iframe')
	
	WebUI.switchToFrame(iframe, 10)
	def elementoDiagnostico = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/td_Diagnstico'), 10)
	
	((JavascriptExecutor) DriverFactory.getWebDriver()).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", elementoDiagnostico)
	
	WebUI.switchToDefaultContent()
	
	WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/td_Diagnstico'))
	
	WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 300)
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/span_Aplica'), 20)
	
	while (intentos2 > 0 && !exito2) {
		
		try {
			
			WebUI.switchToFrame(iframe, 10)
			def btnAplica = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/span_Aplica'), 10)
			
			((JavascriptExecutor) DriverFactory.getWebDriver()).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", btnAplica)
			
			WebUI.switchToDefaultContent()
			
			WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/span_Aplica'))
			
			WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/select-tipoDiagnostico'))
			
			if (!WebUI.waitForElementVisible(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/li_Confirmado nuevo-Tipo diagnostico'), 50)) {
				
				throw new Exception("El elemento 'li_confirmado nuevo' no es visible.")
			}
			
			WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/li_Confirmado nuevo-Tipo diagnostico'))
			
			//WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/select-via ingreso servicio salud'))
			
			//if (!WebUI.waitForElementVisible(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/li_02-Derivado de consulta externa - via ingreso servicio salud'), 50)) {
				
				//throw new Exception("El elemento 'li_derivado de consulta externa' no es visible.")
			//}
			
			//WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/li_02-Derivado de consulta externa - via ingreso servicio salud'))
			
			WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/select-finalidad servicio'))
			
			if (!WebUI.waitForElementVisible(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/li_15-FinalidadServicio-DIAGNOSTICO'), 50)) {
				
				throw new Exception("El elemento 'li_15-FinalidadServicio-DIAGNOSTICO' no es visible.")
			}
			
			WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/li_15-FinalidadServicio-DIAGNOSTICO'))
			
			WebUI.setText(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/input_Buscar_historiaPacienteFormtabViewdia_957322'), 'encefa')
			
			WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 50)
			
			WebUI.delay(2)
			
			WebUI.check(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/checkboxDiagnostico'))
			
			exito2 = true
			
		} catch (Exception e) {
			//Reducir los intentos
			intentos--
			
			WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/span_Aplica'))
			
            WebUI.comment("Error al seleccionar el estado 'cancelar'")
            if (intentos == 0) {
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time)
                WebUI.comment("No se pudo completar el formulario: " + e.getMessage())
				throw e
            }
            WebUI.delay(2)
		}
	}
	
	WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 50)
	
	WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/span_Motivo de consulta'))
	
	WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 50)
	
	// Paso 1: Cambiar al iframe principal
	TestObject iframePrincipal = findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/iframe')
	TestObject iframeEditor = findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud/iframe_textoEnriquecido')
	
	WebUI.switchToFrame(iframePrincipal, 10)
	
	// Paso 2: Identificar y cambiar al iframe del editor de texto enriquecido
	
	WebUI.waitForElementPresent(iframeEditor, 5)
	WebUI.switchToFrame(iframeEditor, 10)
	
	// Ejecutar JavaScript directamente en el editor
	WebUI.executeJavaScript('document.body.innerHTML = "lo que sea";', null)
	
	// Paso 4: Volver al contexto principal (necesitas salir de ambos iframes)
	WebUI.switchToDefaultContent() // Sale del iframeEditor
	WebUI.switchToDefaultContent() // Sale del iframePrincipal
	
	WebUI.enhancedClick(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud/span_Cerrar'))
	
	WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 300)
	
	int intentos3 = 3
	for (int i = 0; i < intentos3; i++) {
		try {
			WebUI.waitForElementVisible(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/button_Salir'), 300)
			
			WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/button_Salir'))
			break
		} catch (Exception e) {
			if (i == intentos3 - 1) throw e
			WebUI.delay(1)
		}
	}
	
	WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 300)
	
}

//apertura de navegacion
WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl('https://medcloudpruebas.idl.com.co/medCloud/index.xhtml')

//Inicio de sesion
InicioSesionClass.inicioSesion()

SelectorCalendarioClass.selectDynamicDate("8")

WebUI.delay(3)

confirmarCita(horaInicial, horaFinal)