package pe.edu.utp.alumnosws.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.utp.alumnosws.entity.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer>{
	List<Alumno> 	findByCodigoContaining(String codigo, Pageable page);
	Alumno findByCodigo(String codigo);
	Optional<Alumno> findOneByCodigo(String codigo);
}
