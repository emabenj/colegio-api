package pe.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.colegio.entity.Administrador;

public interface AdministradorRep extends JpaRepository<Administrador, Integer>{

}
