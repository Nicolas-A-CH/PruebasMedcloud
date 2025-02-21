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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import java.text.SimpleDateFormat
import java.util.Calendar


// Apertura del navegador
WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl('https://medcloudpruebas.idl.com.co/medCloud/index.xhtml')

// Aplicar zoom del 80% utilizando JavaScript
WebUI.executeJavaScript('document.body.style.zoom=\'80%\'', null)

WebUI.setText(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/input_Ver.9.9.5.12022025_ingresoFormfield_user'), 
    'niarevalo')

WebUI.setEncryptedText(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/input_Usuario_ingresoFormfield_password'), 
    'iKK2QhFB4Lt3r+B0vfLvEw==')

// Simular la tecla Enter en el campo de contraseña
WebUI.sendKeys(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/input_Usuario_ingresoFormfield_password'), 
    Keys.chord(Keys.ENTER))

//Seleccionar el dococtor
WebUI.enhancedClick(findTestObject('creacion cita/Page_MedCloud IDL/span_NICOLAS AREVALO_ui-chkbox-icon ui-icon_8d450e'))

//Seleccionar el icono para agregar la cita
WebUI.enhancedClick(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/span_jueves 13-feb.-2025_ui-button-icon-lef_7ab219'))

// Esperar hasta que el formulario esté visible antes de interactuar con él (ajusta el tiempo máximo en segundos)
WebUI.waitForElementVisible(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/span_Seleccione_ui-icon ui-icon-triangle-1-s ui-c'), 10)

//Formulario para agendar cita
// Restablecer el zoom al 100%
WebUI.executeJavaScript("document.body.style.zoom='100%'", null)

WebUI.enhancedClick(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/span_Seleccione_ui-icon ui-icon-triangle-1-s ui-c'))

// Esperar hasta que el elemento `li_Tiempo` sea visible
WebUI.waitForElementVisible(findTestObject('creacion cita/Page_MedCloud IDL/li_Tiempo'), 10)

WebUI.delay(2)

WebUI.click(findTestObject('creacion cita/Page_MedCloud IDL/li_Tiempo'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/span_Seleccione_ui-icon ui-icon-triangle-1-s ui-c_1'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/li_1000 a. m'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/span_Seleccione_ui-icon ui-icon-triangle-1-s ui-c_1_2'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/li_1030 a. m'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/span_Aceptar_1'))

