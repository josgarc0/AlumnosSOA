package pe.edu.utp.alumnosws.soap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;

import jakarta.servlet.http.HttpServletRequest;
import pe.edu.utp.alumnosws.GetAllAlumnosRequest;
import pe.edu.utp.alumnosws.GetAllAlumnosResponse;
import pe.edu.utp.alumnosws.GetAlumnoRequest;
import pe.edu.utp.alumnosws.GetAlumnoResponse;
import pe.edu.utp.alumnosws.UpdAlumnoRequest;
import pe.edu.utp.alumnosws.UpdAlumnoResponse;
import pe.edu.utp.alumnosws.entity.Alumno;
import pe.edu.utp.alumnosws.service.AlumnoService;
import pe.edu.utp.alumnosws.util.JwtUtil;
import pe.edu.utp.alumnosws.AddAlumnoRequest;
import pe.edu.utp.alumnosws.AddAlumnoResponse;
import pe.edu.utp.alumnosws.AlumnoDetalle;
import pe.edu.utp.alumnosws.DelAlumnoRequest;
import pe.edu.utp.alumnosws.DelAlumnoResponse;
import pe.edu.utp.alumnosws.ServiceStatus;

@Endpoint
public class AlumnoEndpoint {
	@Autowired 
	private AlumnoService service;
	
	@Autowired
	private JwtUtil jwtUtils;
	
	private static final String AUTH_HEADER = "Authorization";
	
	@PayloadRoot(namespace = "http://utp.edu.pe/alumnosws", localPart="GetAllAlumnosRequest")
	@ResponsePayload
	public GetAllAlumnosResponse findAll (@RequestPayload GetAllAlumnosRequest request) throws Exception {
		String auth = getHttpHeaderValue("authorization");
		String auth2;
		if ( auth == null){
			auth2 = "";
		}
		else {			
			auth2 = auth.substring(7);
		}
		System.out.println(auth2);
		jwtUtils.verify(auth2);
		GetAllAlumnosResponse response = new GetAllAlumnosResponse();
		Pageable page = PageRequest.of(request.getOffset(),request.getLimit());
		List<Alumno> alumnos;
		if(request.getTexto()==null) {
			alumnos = service.findAll(page);
		}else {
			alumnos = service.findByCodigo(request.getTexto(),page);
		}
		
		List<AlumnoDetalle> alumnosResponse=new ArrayList<>();
		for (int i = 0; i < alumnos.size(); i++) {
			AlumnoDetalle ob = new AlumnoDetalle();
			BeanUtils.copyProperties(alumnos.get(i),  ob);
			alumnosResponse.add(ob);
		}
		response.getAlumnoDetalle().addAll(alumnosResponse);
		return response;
		
	}
	protected HttpServletRequest getHttpServletRequest() {
	    TransportContext ctx = TransportContextHolder.getTransportContext();
	    return ( null != ctx ) ? ((HttpServletConnection ) ctx.getConnection()).getHttpServletRequest() : null;
	}
	protected String getHttpHeaderValue( final String headerName ) {
	    HttpServletRequest httpServletRequest = getHttpServletRequest();
	    return ( null != httpServletRequest ) ? httpServletRequest.getHeader( headerName ) : null;
	}
	@PayloadRoot(namespace = "http://utp.edu.pe/alumnosws", localPart="GetAlumnoRequest")
	@ResponsePayload
	public GetAlumnoResponse findById (@RequestPayload GetAlumnoRequest request) throws Exception {
		 
		ServiceStatus serviceStatus = new ServiceStatus();
		String auth = getHttpHeaderValue("authorization");
		String auth2;
		if ( auth == null){
			auth2 = "";
		}
		else {			
			auth2 = auth.substring(7);
		}
		System.out.println(auth2);
		jwtUtils.verify(auth2);
		GetAlumnoResponse response = new GetAlumnoResponse();
		Alumno entity = service.findById(request.getId());
		AlumnoDetalle alumno = new AlumnoDetalle();
		
		if(entity != null) {
			alumno.setId(entity.getId());
			alumno.setNombre(entity.getNombre());
			alumno.setCodigo(entity.getCodigo());
			alumno.setDireccion(entity.getDireccion());
			response.setAlumnoDetalle(alumno);
		}else {
			 serviceStatus.setStatuscode("CONFLICT");
			 serviceStatus.setMessage("Content Not Exist");
			 response.setServiceStatus(serviceStatus);
			 
		}
		return response;
		
	}
	
	@PayloadRoot(namespace = "http://utp.edu.pe/alumnosws", localPart="AddAlumnoRequest")
	@ResponsePayload
	public AddAlumnoResponse create (@RequestPayload AddAlumnoRequest request) {
		ServiceStatus serviceStatus = new ServiceStatus();
		AddAlumnoResponse response = new AddAlumnoResponse();
		Alumno entity = new Alumno();
		entity.setNombre(request.getNombre());
		entity.setCodigo(request.getCodigo());
		entity.setCarrera(request.getCarrera());
		entity.setDireccion(request.getDireccion());	
		entity=service.save(entity);
		if(entity != null) {
			 AlumnoDetalle alumno = new AlumnoDetalle();
			 BeanUtils.copyProperties(entity, alumno);
			 response.setAlumnoDetalle(alumno);
			 serviceStatus.setStatuscode("SUCCESS");
			 serviceStatus.setMessage("Content Added Successfully");
			 response.setServiceStatus(serviceStatus);
		}else {
			 serviceStatus.setStatuscode("CONFLICT");
			 serviceStatus.setMessage("Content Already Available");
			 response.setServiceStatus(serviceStatus);
			 
		}
		return response;
		
	}
	@PayloadRoot(namespace = "http://utp.edu.pe/alumnosws", localPart="DelAlumnoRequest")
	@ResponsePayload
	public DelAlumnoResponse eliminar (@RequestPayload DelAlumnoRequest request) {
		ServiceStatus serviceStatus = new ServiceStatus();
		DelAlumnoResponse response = new DelAlumnoResponse();
		boolean resp=service.delete(request.getId());
		if(resp) {
			 serviceStatus.setStatuscode("SUCCESS");
			 serviceStatus.setMessage("Content Deleted Successfully");
			 response.setServiceStatus(serviceStatus);
		}else {
			 serviceStatus.setStatuscode("CONFLICT");
			 serviceStatus.setMessage("Content Not Deleted");
			 response.setServiceStatus(serviceStatus);
			 
		}
		return response;
		
	}

	@PayloadRoot(namespace = "http://utp.edu.pe/alumnosws", localPart="UpdAlumnoRequest")
	@ResponsePayload
	public UpdAlumnoResponse update (@RequestPayload UpdAlumnoRequest request) {
		ServiceStatus serviceStatus = new ServiceStatus();
		UpdAlumnoResponse response = new UpdAlumnoResponse();
		Alumno entity = service.findById(request.getId());
		entity.setNombre(request.getNombre());
		entity.setCodigo(request.getCodigo());
		entity.setCarrera(request.getCarrera());
		entity.setDireccion(request.getDireccion());
		entity=service.update(entity);
		if(entity != null) {
			 AlumnoDetalle alumno = new AlumnoDetalle();
			 BeanUtils.copyProperties(entity, alumno);
			 response.setAlumnoDetalle(alumno);
			 serviceStatus.setStatuscode("SUCCESS");
			 serviceStatus.setMessage("Content Updated Successfully");
			 response.setServiceStatus(serviceStatus);
		}else {
			 serviceStatus.setStatuscode("CONFLICT");
			 serviceStatus.setMessage("Content Not Updated");
			 response.setServiceStatus(serviceStatus);
			 
		}
		return response;
		
	}

}
