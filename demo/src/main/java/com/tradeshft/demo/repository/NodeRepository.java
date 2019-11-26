package com.tradeshft.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tradeshft.demo.entity.NodeEntity;
/**
 * Node Repository
 *
 * Created by Sangita Mukherjee
 */

public interface NodeRepository extends CrudRepository<NodeEntity,Long>{
	
	
	@Query("SELECT n FROM NodeEntity n where n.height >:height")
	public List<NodeEntity> findByLevel(@Param("height") Integer height);
	
	@Query("SELECT height FROM NodeEntity n where n.node_id= :node_id")
	public Integer findDepth(@Param("node_id") String node_id);
	
	@Query("SELECT n FROM NodeEntity n where n.node_id= :node_id")
	public NodeEntity findByNode_id(@Param("node_id") String node_id);
	
	/*
	 * SELECT * FROM TREE_STRUCTRE
		WHERE PARENT_NODE = 'f' //g,h
		UNION 
		SELECT * FROM TREE_STRUCTRE
		WHERE PARENT_NODE IN 
		( SELECT NODE_ID FROM TREE_STRUCTRE
		WHERE PARENT_NODE = 'f') 
	 
	*/
	/* fetches all children and grand children */
	@Query(value = "SELECT * FROM TREE_STRUCTRE" + 
			" WHERE PARENT_NODE = :parent_node" + 
			" UNION" + 
			" SELECT * FROM TREE_STRUCTRE" + 
			" WHERE PARENT_NODE IN" + 
			" ( SELECT NODE_ID FROM TREE_STRUCTRE" + 
			" WHERE PARENT_NODE = :parent_node)",nativeQuery = true)
	public List<NodeEntity> findAllChildrenNodeEntities(@Param("parent_node")
	 String parent_node);
	
/*Fetches all children of a given node */
	
	@Query("SELECT n FROM NodeEntity n where n.parent_node = :parent_node")
	public List<NodeEntity> findChildreNodeEntities(@Param("parent_node") String parent_node);
	
	/*Fetches all grand children of a given node */
	@Query("SELECT n FROM NodeEntity n WHERE n.parent_node IN (SELECT node_id from NodeEntity WHERE parent_node =:parent_node) ")
	public List<NodeEntity> findGrandChildreNodeEntities(@Param("parent_node") String parent_node);

	
	/*Option 3: Using Recursive CTE */
	/*@Query(value = "with recursive CHILDREN  as ("
	   + " SELECT * FROM TREE_STRUCTRE " 
	   + " WHERE PARENT_NODE = :parent_node"
	   + " UNION"
	   + " SELECT * from TREE_STRUCTRE t"
	   + " INNER JOIN CHILDREN c on (c.node_id = t.parent_node)"
	   + " ) select * from child",nativeQuery = true)*/
	
	

}
