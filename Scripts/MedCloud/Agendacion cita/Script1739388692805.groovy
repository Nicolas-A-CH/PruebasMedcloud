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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://medcloudpruebas.idl.com.co/medCloud/index.xhtml')

WebUI.maximizeWindow()

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/input_Ver.9.9.5.12022025_ingresoFormfield_user'))

WebUI.setText(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/input_Ver.9.9.5.12022025_ingresoFormfield_user'), 
    'niarevalo')

WebUI.setEncryptedText(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/input_Usuario_ingresoFormfield_password'), 
    'iKK2QhFB4Lt3r+B0vfLvEw==')

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/span_Iniciar sesin'))

WebUI.rightClick(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/div_Agendas Expandidas'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/span_NICOLAS AREVALO_ui-chkbox-icon ui-icon_8d450e'))

WebUI.scrollToElement(findTestObject('creacion cita/Page_MedCloud IDL/span_mircoles 12-feb.-2025_ui-button-icon-l_9a5267'), 
    10)

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/span_mircoles 12-feb.-2025_ui-button-icon-l_9a5267'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/label_COOMEVA MP'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/label_Seleccione'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/li_Tiempo'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/label_Seleccione_1'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/li_1430 p. m'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/label_Seleccione_1_2'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/li_1500 p. m'))

WebUI.click(findTestObject('Object Repository/creacion cita/Page_MedCloud IDL/span_Aceptar'))

