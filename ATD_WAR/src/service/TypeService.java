package service;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import agents.HostAgentLocal;
import dto.TypeDTO;

@Stateless
@LocalBean
public class TypeService {

	@EJB
	private HostAgentLocal host;
	
	public Set<TypeDTO> getAllTypes() {
		Set<TypeDTO> ret = new HashSet<>();
		
		host.getAllTypes().forEach(at -> {
			ret.add(new TypeDTO(at));
		});
		
		return ret;
	}
}
