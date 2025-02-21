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

// Función principal para selección de fecha
def selectDynamicDate(String day) {
    // Cambiar al iframe del calendario
    WebUI.switchToFrame(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/iframe'), 10)
    
    try {
        // Crear XPath dinámico
        String xpathDate = "//table[@class='ui-datepicker-calendar']//a[text()='${day}']"
        
        // Construir objeto dinámico
        TestObject fecha = new TestObject("dia_${day}").addProperty(
            'xpath', 
            com.kms.katalon.core.testobject.ConditionType.EQUALS, 
            xpathDate
        )
        
        // Esperar y hacer clic
        WebUI.waitForElementPresent(fecha, 10)
        WebUI.waitForElementClickable(fecha, 10)
        WebUI.enhancedClick(fecha)
    } finally {
        // Volver al contexto principal
        WebUI.switchToDefaultContent()
    }
}
// Configuración del día deseado (cambia este valor según necesites)
String targetDay = "23"

//Apertura del navegador
WebUI.openBrowser('')

WebUI.maximizeWindow()

//Ingreso de la pagina
WebUI.navigateToUrl('https://medcloudpruebas.idl.com.co/medCloud/index.xhtml')

//Ingreso de credenciales en el login
WebUI.setText(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/input_Ver.9.9.5.19022025_ingresoFormfield_user'), 
    'niarevalo')

WebUI.setEncryptedText(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/input_Usuario_ingresoFormfield_password'), 
    'iKK2QhFB4Lt3r+B0vfLvEw==')

WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_Iniciar sesin'))

//Esperar que el listado de doctores este disponible
WebUI.waitForPageLoad(5)

//Seleccionar doctor para la agendacion de cupos
WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_NICOLAS AREVALO'))

//Elemento donde esta el calendario
//WebUI.click(findTestObject('Crear cupos para citas/Page_MedCloud IDL/div_AnteriorSiguienteFebrero2025LMXJVSD1234_d3f339'))
// Llamado a la función reutilizable (cambiar el parámetro según necesidad)
selectDynamicDate('23')

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
WebUI.waitForElementPresent(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/td_COOMEVA MP'), 5)
WebUI.waitForElementVisible(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/td_COOMEVA MP'), 5)

//Formulario para la creacion de cupos
WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/td_COOMEVA MP'))

WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/label_Seleccione'))

//Esperar que los items del select esten disponibles
WebUI.waitForElementPresent(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_Tiempo'), 5)
WebUI.waitForElementVisible(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_Tiempo'), 5)
WebUI.waitForElementClickable(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_Tiempo'), 5)

WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_Tiempo'))

WebUI.setText(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/input_Rango(minutos)_tabviewaddCuposFormrango'), 
    '30')

WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_Seleccione_ui-icon ui-icon-triangle-1-s ui-c'))

//Esperar que los items del select esten disponibles
WebUI.waitForElementPresent(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_0800 a. m'), 5)
WebUI.waitForElementVisible(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_0800 a. m'), 5)
WebUI.waitForElementClickable(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_0800 a. m'), 5)

WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_0800 a. m'))

WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_Seleccione_ui-icon ui-icon-triangle-1-s ui-c_1'))

//Esperar que los items del select esten disponibles
WebUI.waitForElementPresent(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_2000 p. m'), 5)
WebUI.waitForElementVisible(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_2000 p. m'), 5)
WebUI.waitForElementClickable(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_2000 p. m'), 5)

WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/li_2000 p. m'))

//Envio de formulario
WebUI.click(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/span_Aceptar'))

