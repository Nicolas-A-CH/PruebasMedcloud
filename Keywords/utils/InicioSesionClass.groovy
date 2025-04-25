package utils
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.exception.StepFailedException
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

class InicioSesionClass {

	//Declaracion de elementos
	static TestObject nombreUsuarioObj = findTestObject('Object Repository/inicio sesion/Page_MedCloud IDL/input_Ver.9.9.5.25022025_ingresoFormfield_user')
	static TestObject passwordUsuarioObj = findTestObject('Object Repository/inicio sesion/Page_MedCloud IDL/input_Usuario_ingresoFormfield_password')
	static TestObject btnIngresarObj = findTestObject('Object Repository/inicio sesion/Page_MedCloud IDL/button_Aceptar')
	static TestObject ventanaFlotanteObj = findTestObject('Object Repository/inicio sesion/Page_MedCloud IDL/div_invalidar Sessionya existe una sesin previa con este usuario')
	static TestObject btnConfirmarIngresoYCerrarSesionAnteriorObj = findTestObject('Object Repository/inicio sesion/Page_MedCloud IDL/button_Aceptar')
	static TestObject checkbobRecordarUsuarioObj = findTestObject('Object Repository/inicio sesion/Page_MedCloud IDL/span_Contrasea_ui-chkbox-icon ui-icon ui-icon-blank ui-c')
	static TestObject liDoctorSeleccionado = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_NICOLAS AREVALO')
	static TestObject spinnerLogin = findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga')

	@Keyword
	static void inicioSesion(){

		try {
			//Esperar que el login este presente
			if (!WebUI.waitForElementPresent(nombreUsuarioObj, 5, FailureHandling.OPTIONAL)) {
				throw new Exception("El campo de nombre de usuario no está presente.")
			}
			WebUI.waitForElementVisible(nombreUsuarioObj, 20)
			WebUI.waitForElementClickable(nombreUsuarioObj, 5)

			//Ingresar
			WebUI.setText(nombreUsuarioObj, 'niarevalo', FailureHandling.STOP_ON_FAILURE)

			WebUI.setEncryptedText(passwordUsuarioObj, 'iKK2QhFB4Lt3r+B0vfLvEw==', FailureHandling.STOP_ON_FAILURE)

			WebUI.click(findTestObject('Object Repository/inicio sesion/Page_MedCloud IDL/span_Iniciar sesin'))

			if (WebUI.verifyElementPresent(ventanaFlotanteObj, 4, FailureHandling.OPTIONAL)) {

				WebUI.waitForElementVisible(ventanaFlotanteObj, 20)
				WebUI.waitForElementPresent(btnConfirmarIngresoYCerrarSesionAnteriorObj, 5)
				WebUI.waitForElementClickable(btnConfirmarIngresoYCerrarSesionAnteriorObj, 5)
				WebUI.click(btnConfirmarIngresoYCerrarSesionAnteriorObj)
			}


			//Navegación a la agenda médica
			WebUI.waitForElementPresent(liDoctorSeleccionado, 5)
			WebUI.waitForElementVisible(liDoctorSeleccionado, 20)
			WebUI.waitForElementClickable(liDoctorSeleccionado, 5)

			WebUI.click(liDoctorSeleccionado)

			WebUI.waitForElementNotVisible(spinnerLogin, 50)
		} catch (Exception e) {
			println("Error inesperado durante el inicio de sesión: " + e.getMessage())
			throw e
		}
	}
}