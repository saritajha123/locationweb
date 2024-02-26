package com.sarita.location.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sarita.location.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
	
	@Query(value = "select type, count(type) from location group by type", nativeQuery = true)
	public List<Object[]> findTypeAndTypeCount();



	


	
	
	

}
