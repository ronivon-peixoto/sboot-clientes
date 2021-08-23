package io.builders.sbootclientes.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import io.builders.sbootclientes.entity.Cliente;

public class ClienteSpecificationsBuilder {
	
	private final List<SearchCriteria> params;

    public ClienteSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public ClienteSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Cliente> build() {
        if (params.isEmpty()) {
            return null;
        }
        
        Specification<Cliente> result = new ClienteSpecification(params.get(0));
     
        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
              ? Specification.where(result).or(new ClienteSpecification(params.get(i))) 
              : Specification.where(result).and(new ClienteSpecification(params.get(i)));
        }
    
        return result;
    }

}
