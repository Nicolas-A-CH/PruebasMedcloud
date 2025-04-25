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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.WebElement as WebElement

// Método para encontrar y seleccionar la cita por hora
def seleccionarCitaPorHora(String horaInicio, String horaFin) {
    // Cambiar el contexto al iframe
    TestObject iframe = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/iframe')
    WebUI.switchToFrame(iframe, 10)
    
    // XPath dinámico que busca la hora exacta en el <td> y el <span>
    String xpath = "//tr[contains(@class, 'ui-node-level-2') and .//td[1][contains(., '${horaInicio}') and .//span[contains(@id, 'divhora') and contains(., '-${horaFin}')]]]"
    
    // Crear TestObject dinámico
    TestObject filaCupo = new TestObject("filaCupo").addProperty("xpath", ConditionType.EQUALS, xpath)
    
    // Esperar a que la fila esté presente (máximo 10 segundos)
    WebUI.waitForElementPresent(filaCupo, 10)
    
    // Verificar que el XPath funcione en Katalon
    List<WebElement> elementos = WebUiCommonHelper.findWebElements(filaCupo, 10)
    println "Elementos encontrados: " + elementos.size()
    
    // Interactuar con la fila encontrada
    WebUI.click(filaCupo)
    WebUI.rightClick(filaCupo)
    
    // Volver al contexto principal si es necesario
    WebUI.switchToDefaultContent()
}

//apertura de navegacion
WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl('https://medcloudpruebas.idl.com.co/medCloud/index.xhtml')

//Inicio de sesion
WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input_Ver.9.9.5.19022025_ingresoFormfield_user'), 
    'niarevalo')

WebUI.setEncryptedText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input_Usuario_ingresoFormfield_password'), 
    'iKK2QhFB4Lt3r+B0vfLvEw==')

WebUI.click(findTestObject('null'))

//Navegación a la agenda médica
WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/div_Agendas Expandidas                     _5ffbf0_1'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_NICOLAS AREVALO'))

WebUI.waitForElementPresent(findTestObject('null'), 
    0)

WebUI.delay(3)

// Seleccionar la cita por hora
seleccionarCitaPorHora("5:00 p. m.", "5:30 p. m.") // Formato exacto del texto

//WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_var valor                               _39e786'))
//WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/td_310 p. m.-320 p. m. BOGOTA              _843fff'))
//WebUI.rightClick(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/td_310 p. m.-320 p. m. BOGOTA              _843fff'))
WebUI.delay(2)
WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Agendar'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/div_NICOLAS AREVALO'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/label_Seleccione'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_CIRUGIA PLASTICA-PRIMERA VEZ (120min)'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Aceptar'))

//Esperar a que la ventana del formulario cargue
WebUI.waitForElementPresent(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/label_Cedula Ciudadania'), 
    5)

WebUI.waitForElementClickable(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/label_Cedula Ciudadania'), 15)

//Ingreso de datos del paciente en formulario
WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/label_Cedula Ciudadania'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_Cedula Ciudadania'))

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewnroI_3c9080'), 
    '1072364048')

WebUI.sendKeys(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewnroI_3c9080'), 
    Keys.chord(Keys.TAB))

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewprim_676a58'), 
    'luis')

WebUI.sendKeys(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewprim_676a58'), 
    Keys.chord(Keys.TAB))

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input_Segundo Nombre_PacienteCrearFormPacie_702494'), 
    'nicolas')

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewprim_d7f304'), 
    'arevalo')

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input_Segundo Apellido_PacienteCrearFormPac_2061f7'), 
    'chiquiza')

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewcelular'), 
    '3134541985')

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/label_Seleccione uno'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_Masculino'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span__ui-button-icon-left ui-icon ui-icon-calendar'))

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewfech_a2cb1e'), 
    '2004-01-29')

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Seleccione uno_ui-icon ui-icon-triangl_fd1878'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_SANITAS'))

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewemai_4f6d20'), 
    'pruebas@gmail.com')

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewinEps'), 
    'famisanal')

WebUI.check(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Acepta Tratamiento De Datos_ui-chkbox-_5009df'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Datos Complementarios'))

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewdireccion'), 
    'calle 25 #70-05')

WebUI.selectOptionByValue(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/select_Seleccione unoCasadoDivorciadoMenorO_abc012'), 
    'class co.idl.medicalcloud.entities.EstadoCivil@6', true)

WebUI.selectOptionByValue(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/select_Seleccione unoAdicionalBeneficiarioC_c24d6b'), 
    'class co.idl.medicalcloud.entities.TipoAfiliado@2', true)

WebUI.selectOptionByValue(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/select_Seleccione unoContributivoDesplazado_13c85f'), 
    'class co.idl.medicalcloud.entities.TipoUsuario@1', true)

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/label_Seleccione uno_1'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_Contributivo Cotizante'))

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewacudiente'), 
    'adriana')

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewtele_792a81'), 
    '3233205930')

WebUI.selectOptionByValue(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/select_Seleccione unoConyugueHermano(a)Hijo_8cc99f'), 
    'class co.idl.medicalcloud.entities.Parentesco@6', true)

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewresponsable'), 
    'yo mismo')

WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewtelR_048270'), 
    '3233205930')

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Otros Datos'))

//Envio de formulario
WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Guardar'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Esta seguro de continuar con la creaci_4d5d74'))

WebUI.waitForElementPresent(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/div_Cita Agendada con xitoCdigo Cita465346F_3bb5d0'), 
    5)

WebUI.waitForElementClickable(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/div_Cita Agendada con xitoCdigo Cita465346F_3bb5d0'), 
    15)

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/button_Aceptar'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/button_Evolucin historica'))

WebUI.closeBrowser()

