package com.tradeshft.demo.resource;

import java.util.List;

public class Node {
	
	private String parent;
	private List<Node> children;
	
	
	public Node() {
		super();
	}


	public Node(String parent, List<Node> children) {
		super();
		this.parent = parent;
		this.children = children;
	}


	public String getParent() {
		return parent;
	}


	public void setParent(String parent) {
		this.parent = parent;
	}


	public List<Node> getChildren() {
		return children;
	}


	public void setChildren(List<Node> children) {
		this.children = children;
	}
	

}
