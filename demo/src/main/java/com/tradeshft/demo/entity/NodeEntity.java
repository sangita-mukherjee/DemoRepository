package com.tradeshft.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TREE_STRUCTRE")
public class NodeEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8562818916006296100L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String node_id;
	
	private String parent_node;
	private String root_node;

	private Integer height;
	
	public NodeEntity() {
		super();
	}
	
	
	public NodeEntity(String node_id, String parent_node, String root_node, Integer height) {
		//super();
		this.node_id = node_id;
		this.parent_node = parent_node;
		this.root_node = root_node;
		this.height = height;
		//children = new ArrayList<NodeEntity>(); 
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNode_id() {
		return node_id;
	}
	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
	public String getParent_node() {
		return parent_node;
	}
	public void setParent_node(String parent_node) {
		this.parent_node = parent_node;
	}
	public String getRoot_node() {
		return root_node;
	}
	public void setRoot_node(String root_node) {
		this.root_node = root_node;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	
}
