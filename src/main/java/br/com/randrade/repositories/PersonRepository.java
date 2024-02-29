/**
 * 
 */
package br.com.randrade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.randrade.model.Person;

/**
 * @author rafael.andrade
 */
public interface PersonRepository extends JpaRepository<Person, Long>{

}
