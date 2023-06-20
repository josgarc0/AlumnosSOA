package pe.edu.utp.alumnosws.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> cualquierMsgDispatcherServlet(ApplicationContext applicationContext){
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(servlet, "/ws/*");
	}
	@Bean
	public XsdSchema cualquierarticulosSchema() {
		return new SimpleXsdSchema(new ClassPathResource("alumno-detalle.xsd"));
	}

	@Bean(name = "alumnos")
	public DefaultWsdl11Definition defaultWsdl11Definition2(XsdSchema articulosSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("AlumnosPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://utp.edu.pe/alumnos");
		wsdl11Definition.setSchema(articulosSchema);
		return wsdl11Definition;
	}

	
	
}
