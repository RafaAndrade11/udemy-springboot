/**
 * 
 */
package br.com.randrade.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.randrade.data.vo.v1.PersonVO;
import br.com.randrade.data.vo.v2.PersonVOV2;
import br.com.randrade.exceptions.ResourceNotFoundException;
import br.com.randrade.mapper.DozerMapper;
import br.com.randrade.mapper.custom.PersonMapper;
import br.com.randrade.model.Person;
import br.com.randrade.repositories.PersonRepository;

/**
 * 
 */
@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper mapper;
	
	public List<PersonVO> findAll() {
		logger.info("FindPersonVOl people!");
		
		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}
	
	public PersonVO findById(Long id) {
		logger.info("Finding one person!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO createPerson(PersonVO person) {
		logger.info("Creating one person!");
		var entity =  DozerMapper.parseObject(person, Person.class);
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo; 
	}
	
	public PersonVOV2 createPersonV2(PersonVOV2 person) {
		logger.info("Creating one person V2!");
		var entity =  mapper.convertVoToEntity(person);
		
		var vo = mapper.convertEntityToVo(repository.save(entity));
		return vo;
	}
	
	public PersonVO updatePerson(PersonVO person) {
		logger.info("Updating one person!");
		
		var entity = repository.findById(person.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;		
	}
	
	public void deletePerson(Long id) {
		
		logger.info("Deleting one person!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		repository.delete(entity);
	}
}
