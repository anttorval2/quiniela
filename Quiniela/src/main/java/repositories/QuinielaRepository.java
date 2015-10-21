package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Partido;
import domain.Quiniela;

@Repository
public interface QuinielaRepository extends JpaRepository<Quiniela, Integer> {

    @Query("select p from Partido p where p.quiniela.id=?1")
	Collection<Partido> findPartidos(int id);

    @Query("select q from Quiniela q where q.administrator.id=?1 and q.user.id=null")
	Collection<Quiniela> findMyQuinielas(int id);

    @Query("select q from Quiniela q where q.user.id=?1")
	Collection<Quiniela> findQuinielasForUser(int id);

    @Query("select q from Quiniela q where q.jornada=?1")
	Collection<Quiniela> findQuinielasByJornada(String jornada);




}

