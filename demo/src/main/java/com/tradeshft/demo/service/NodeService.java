package com.tradeshft.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeshft.demo.entity.NodeEntity;
import com.tradeshft.demo.repository.NodeRepository;
/**
 * Node Service
 *
 * Created by Sangita Mukherjee
 */
@Service
public class NodeService {
	private static Logger logger = LoggerFactory.getLogger(NodeService.class);

	@Autowired
	private NodeRepository noderepo;	
	private  Map<String, NodeEntity> childmap = new HashMap<>();
	private int depth;
	
	
	/**
	 * collect all descendant of a given node.
	 * 
	 * @param nodeId
	 * @return Map of descendant nodes
	
	 */

public Map<String,NodeEntity> collectchild(String nodeId) {
	
	int ht = noderepo.findDepth(nodeId);

	List<NodeEntity> masterlist =noderepo.findByLevel(ht);
	logger.info
		("Level >>>>"+ht+">>>>Size of the master list >>>"
				+masterlist.size()
				+">>>nodeId>>>>>"+nodeId);
	
	/*collecting immediate children */	
	if(!masterlist.isEmpty()) {
		childmap =
			masterlist.stream()
			.filter((p) -> ht+1 == (p.getHeight()) && nodeId.equals(p.getParent_node()))
			.collect(Collectors.toMap(x -> x.getNode_id(), x -> x));
	
	
		masterlist.removeIf(condition -> ht+1 == condition.getHeight());
	}
	/* Increasing depth for fetching grand-children*/
	depth = ht+2 ;
		
	while(!masterlist.isEmpty() && !childmap.isEmpty()) {
		
		masterlist
	   .parallelStream()
	   .filter(i -> depth == i.getHeight())
	   .filter(item -> childmap.containsKey(item.getParent_node()))
	   .forEach(e ->childmap.put(e.getNode_id(), e));
		
		masterlist.removeIf(x -> x.getHeight() == depth);
		depth = depth+1;
	  
		}
		masterlist.clear();
		
		return childmap;
	
	}


public Map<String, List<NodeEntity>> updateParent(String nodeId,NodeEntity node) {
	
	int ht = node.getHeight();
	String new_parent_node_id =node.getParent_node();//New node from  API
	
	String existing_parent_id = noderepo.findByNode_id(nodeId)
			.getParent_node();//From DB

	logger.info("new_parent_node_id >>>>"+new_parent_node_id+
			">>>>existing_parent_id>>>"+existing_parent_id+
			">>>>>depth>>>>>>>"+ht+">>>>");	
	
	List<NodeEntity> masterlist = noderepo.findByLevel(ht-1);
	logger.info("Size of the master list inside updateParent() :"
	+masterlist.size()); 
	
	
	masterlist.forEach(i -> logger.info(i.getNode_id()));
	 Map<String,List<NodeEntity>> siblingMap = 
    		masterlist.parallelStream()
    		.filter(p -> p.getParent_node().equals(existing_parent_id))
    		.collect(Collectors.groupingBy(NodeEntity::getParent_node));
	 
	 logger.info("Size of the sibling Map list >>>"+siblingMap.size());
	
    if(siblingMap.containsKey(existing_parent_id)) {
    	// update the parent for  all siblings
    	siblingMap
        .get(existing_parent_id).forEach(sibling -> 
        			{sibling.setParent_node(node.getParent_node());
        				noderepo.save(sibling);
        			}); 	
    }
    	//update parent
    	NodeEntity parentNode =noderepo.findByNode_id(existing_parent_id);
    	parentNode.setNode_id(node.getParent_node());
    	noderepo.save(parentNode);
    	List<NodeEntity> parent = new ArrayList<NodeEntity>();
    	parent.add(parentNode);
    	siblingMap.put(new_parent_node_id,parent);
   
 
    return siblingMap;
}
	


}

