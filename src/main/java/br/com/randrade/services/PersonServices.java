/**
 * 
 */
package br.com.randrade.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.randrade.data.vo.v1.PersonVO;
import br.com.randrade.exceptions.ResourceNotFoundException;
import br.com.randrade.repositories.PersonRepository;

/**
 * 
 */
@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	public List<PersonVO> findAll() {
		logger.info("FindPersonVOl people!");
		
		return repository.findAll();
	}
	
	public PersonVO findById(Long id) {
		logger.info("Finding one person!");
		
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
	}
	
	public PersonVO createPerson(PersonVO person) {
		logger.info("Creating one person!");
		
		return repository.save(person);
	}
	
	public PersonVO updatePerson(PersonVO person) {
		logger.info("Updating one person!");
		
		var entity = repository.findById(person.getId())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		return repository.save(person);
				
	}
	
	public void deletePerson(Long id) {
		
		logger.info("Deleting one person!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		repository.delete(entity);
	}
	
}
