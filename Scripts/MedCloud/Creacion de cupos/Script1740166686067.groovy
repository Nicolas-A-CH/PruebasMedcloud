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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import utils.InicioSesionClass
import utils.SelectorCalendarioClass

import org.openqa.selenium.Keys as Keys

//Variables
String fechaCalendario = "5"
String intervaloTiempoCupos = "5"

//Apertura del navegador
WebUI.openBrowser('')

WebUI.maximizeWindow()

//Ingreso de la pagina
WebUI.navigateToUrl('https://medcloudpruebas.idl.com.co/medCloud/index.xhtml')

InicioSesionClass.inicioSesion()

// Llamado a la función reutilizable (cambiar el parámetro según necesidad)
SelectorCalendarioClass.selectDynamicDate(fechaCalendario)

//Esperar a que el boton de crear cupos para el doctor especificado este presente
WebUI.waitForElementPresent(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_sbado 22-feb.-2025_ui-button-icon-left_1ee6c9'), 
    5)

WebUI.waitForElementVisible(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_sbado 22-feb.-2025_ui-button-icon-left_1ee6c9'), 
    5)

WebUI.waitForElementClickable(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_sbado 22-feb.-2025_ui-button-icon-left_1ee6c9'), 
    5)

//Boton que abre formulario para la creacion de cupos
WebUI.enhancedClick(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_sbado 22-feb.-2025_ui-button-icon-left_1ee6c9'))

//Esperar que el formulario abra
WebUI.waitForElementPresent(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/td_COOMEVA MP'), 
    5)

WebUI.waitForElementVisible(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/td_COOMEVA MP'), 
    5)

//Formulario para la creacion de cupos
WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/td_COOMEVA MP'))

WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/label_Seleccione'))

//Esperar que los items del select esten disponibles
WebUI.waitForElementPresent(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_Tiempo'), 5)

WebUI.waitForElementVisible(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_Tiempo'), 5)

WebUI.waitForElementClickable(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_Tiempo'), 5)

WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_Tiempo'))

WebUI.setText(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/input_Rango(minutos)_tabviewaddCuposFormrango'), 
    intervaloTiempoCupos)

WebUI.delay(2)

WebUI.waitForElementClickable(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_Seleccione_ui-icon ui-icon-triangle-1-s ui-c_1'), 10)

WebUI.enhancedClick(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_Seleccione_ui-icon ui-icon-triangle-1-s ui-c'))

WebUI.delay(2)

//Esperar que los items del select esten disponibles
WebUI.waitForElementPresent(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_0800 a. m'), 5)

WebUI.waitForElementVisible(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_0800 a. m'), 5)

WebUI.waitForElementClickable(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_0800 a. m'), 
    5)

WebUI.enhancedClick(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_0800 a. m'))

WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_Seleccione_ui-icon ui-icon-triangle-1-s ui-c_1'))

//Esperar que los items del select esten disponibles
WebUI.waitForElementPresent(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_2000 p. m'), 5)

WebUI.waitForElementVisible(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_2000 p. m'), 5)

WebUI.waitForElementClickable(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_2000 p. m'), 
    5)

WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_2000 p. m'))

//Envio de formulario
WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_Aceptar'))