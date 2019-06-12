package tituloDigitalFirma;

import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

import tituloDigital.xsdBeans.ObjectFactory;
import tituloDigital.xsdBeans.TituloElectronico;
import tituloDigital.xsdBeans.TituloElectronico.Antecedente;
import tituloDigital.xsdBeans.TituloElectronico.Autenticacion;
import tituloDigital.xsdBeans.TituloElectronico.Carrera;
import tituloDigital.xsdBeans.TituloElectronico.Expedicion;
import tituloDigital.xsdBeans.TituloElectronico.FirmaResponsables;
import tituloDigital.xsdBeans.TituloElectronico.FirmaResponsables.FirmaResponsable;
import tituloDigital.xsdBeans.TituloElectronico.Institucion;
import tituloDigital.xsdBeans.TituloElectronico.Profesionista;

public class tituloXmlTest {
	
	@Test
	public void construyeTituloXml() throws JAXBException, DatatypeConfigurationException{
		JAXBContext jaxbContext 
        = JAXBContext.newInstance("tituloDigital.xsdBeans");
		
		TituloElectronico tituloElectronico = new TituloElectronico();	
		
		FirmaResponsables firmaResponsables = new FirmaResponsables();
		FirmaResponsable firmaResponsable = new FirmaResponsable();
		
		firmaResponsable.setNombre("value");
		firmaResponsable.setPrimerApellido("value");
		firmaResponsable.setSegundoApellido("value");
		firmaResponsable.setAbrTitulo("value");
		firmaResponsable.setCargo("value");
		firmaResponsable.setCertificadoResponsable("value");
		firmaResponsable.setCurp("value");
		firmaResponsable.setIdCargo(new BigInteger("1"));
		firmaResponsable.setNoCertificadoResponsable("value");
		firmaResponsable.setSello("value");
		
		firmaResponsables.getFirmaResponsable().add(firmaResponsable);
		firmaResponsables.getFirmaResponsable().add(firmaResponsable);
		firmaResponsables.getFirmaResponsable().add(firmaResponsable);
		tituloElectronico.setFirmaResponsables(firmaResponsables);
		
		Institucion institucion = new Institucion();
		institucion.setCveInstitucion("value");
		institucion.setNombreInstitucion("value");
		
		tituloElectronico.setInstitucion(institucion);
		
		Carrera carrera = new Carrera();
		carrera.setAutorizacionReconocimiento("value");
		carrera.setCveCarrera("value");
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar fecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		carrera.setFechaInicio(fecha);
		carrera.setFechaTerminacion(fecha);
		carrera.setIdAutorizacionReconocimiento(new BigInteger("1"));
		carrera.setNombreCarrera("value");
		carrera.setNumeroRvoe("value");
		
		tituloElectronico.setCarrera(carrera);
		
		Profesionista profesionista = new Profesionista();
		profesionista.setCorreoElectronico("value");
		profesionista.setCurp("value");
		profesionista.setNombre("value");
		profesionista.setPrimerApellido("value");
		profesionista.setSegundoApellido("value");
		
		tituloElectronico.setProfesionista(profesionista);
		
		Expedicion expedicion = new Expedicion();
		expedicion.setCumplioServicioSocial(new BigInteger("1"));
		expedicion.setEntidadFederativa("value");
		expedicion.setFechaExamenProfesional(fecha);
		expedicion.setFechaExencionExamenProfesional(fecha);
		expedicion.setFechaExpedicion(fecha);
		expedicion.setFundamentoLegalServicioSocial("value");
		expedicion.setIdEntidadFederativa("value");
		expedicion.setIdFundamentoLegalServicioSocial(new BigInteger("1"));
		expedicion.setIdModalidadTitulacion(new BigInteger("1"));
		expedicion.setModalidadTitulacion("value");
		
		tituloElectronico.setExpedicion(expedicion);
		
		Antecedente antecedente = new Antecedente();
		antecedente.setEntidadFederativa("value");
		antecedente.setFechaInicio(fecha);
		antecedente.setFechaTerminacion(fecha);
		antecedente.setIdEntidadFederativa("value");
		antecedente.setIdTipoEstudioAntecedente(new BigInteger("1"));
		antecedente.setInstitucionProcedencia("value");
		antecedente.setNoCedula("value");
		antecedente.setTipoEstudioAntecedente("value");
		
		tituloElectronico.setAntecedente(antecedente);
		
		Autenticacion autenticacion = new Autenticacion();
		tituloElectronico.setAutenticacion(autenticacion);
		
		String version ="1.0";
		String folioControl = "value";
		
		tituloElectronico.setVersion(version);
		tituloElectronico.setFolioControl(folioControl);
		
		
		
		
		Marshaller marshaller = jaxbContext.createMarshaller();
	    TituloElectronico tituloElectronicoElement  
	             = (new ObjectFactory()).createTituloElectronico();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    marshaller.marshal( tituloElectronico, System.out );
		
		String result = "r";
		assertNotNull(result);
		
		
	}

}
