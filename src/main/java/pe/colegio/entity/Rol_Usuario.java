package pe.colegio.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import pe.colegio.util.RoleType;

@Entity @Table(name="roles")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roleId")
public class Rol_Usuario implements Serializable {
	private static final long serialVersionUID=1L;

	public Rol_Usuario() { }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;
	
	@Column(unique = true, nullable = false)
	private String nombre = RoleType.APOD.name();
	
	@ManyToMany(mappedBy = "itemsRole", fetch = FetchType.EAGER)
	private Set<Usuario> itemsUser=new HashSet<>();

	public Rol_Usuario(String nombre) {
		super();
		this.nombre = nombre;
	}


	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Usuario> getItemsUser() {
		return itemsUser;
	}

	public void setItemsUser(Set<Usuario> itemsUser) {
		this.itemsUser = itemsUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}