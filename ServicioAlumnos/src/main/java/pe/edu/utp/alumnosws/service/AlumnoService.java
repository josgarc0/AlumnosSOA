package pe.edu.utp.alumnosws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import pe.edu.utp.alumnosws.entity.Alumno;
import pe.edu.utp.alumnosws.repository.AlumnoRepository;

@Service
@Slf4j
public class AlumnoService {
	@Autowired
	private AlumnoRepository repository;
	
	@Transactional(readOnly = true)
	public List<Alumno> findAll(Pageable page) {
		try {
			return repository.findAll(page).toList();
		}catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
    @Transactional(readOnly = true)
	public List<Alumno> findByCodigo(String codigo, Pageable page) {
		try {
			return repository.findByCodigoContaining(codigo,page);
		}catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
    
    @Transactional(readOnly = true)
	public Alumno findById(int id){
		try {
			Alumno registro = repository.findById(id).orElseThrow();
			return registro;
		}catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
    
    
    @Transactional
	public Alumno save(Alumno alumno) {
		try {
			if(repository.findByCodigo(alumno.getCodigo())!=null) {
				return null;
			}
			Alumno nuevo=repository.save(alumno);
			return nuevo;
		}catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
		
	}
    
    @Transactional
	public boolean delete(int id) {
		try {
			Alumno alumno=repository.findById(id).orElseThrow();
			repository.delete(alumno);
			return true;
		}catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		
	}

    @Transactional
	public Alumno update(Alumno alumno) {
		try {
			Alumno registro = repository.findById(alumno.getId()).orElseThrow();
			Alumno registroD = repository.findByCodigo(alumno.getCodigo()); 
			if (registroD!=null && alumno.getId()!=registroD.getId()) {
				return null;
			}
			registro.setCodigo(alumno.getCodigo());
			registro.setNombre(alumno.getNombre());
			registro.setCarrera(alumno.getCarrera());
			registro.setDireccion(alumno.getDireccion());
			repository.save(registro);
			return registro;
		}catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
