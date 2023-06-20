package pe.edu.utp.alumnosws.entity;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="alumnos")
@EntityListeners(AuditingEntityListener.class)
public class Alumno {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	@Column(name="codigo",unique =  true, nullable=false, length=10)
	private String codigo;
	@Column(name="nombre",unique =  true, nullable=false, length=100)
	private String nombre;
	@Column(name="carrera",nullable = false, length=100)
	private String carrera;
	@Column(name="direccion",nullable = false, length=100)
	private String direccion;
	@Column(name="created_at",nullable=false,updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;
	@Column(name="update_at",nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

}
