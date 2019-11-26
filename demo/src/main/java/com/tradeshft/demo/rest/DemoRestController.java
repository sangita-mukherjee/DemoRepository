package com.tradeshft.demo.rest;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tradeshft.demo.entity.NodeEntity;
import com.tradeshft.demo.repository.NodeRepository;
import com.tradeshft.demo.service.NodeService;


@RestController
@RequestMapping("/organisation/desecendants")
public class DemoRestController {
	Logger logger = LoggerFactory.getLogger(DemoRestController.class);

	@Autowired
	private NodeRepository noderepo;

	@Autowired
	private NodeService service;
	/* Aproach1 */

	/**
	 * Fetches all descendant of a given node.
	 * 
	 * @param nodeId
	 * @return Map of descendant nodes
	 * 
	 */
	@RequestMapping(path = "/{nodeId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Map<String, NodeEntity>> getAllDescendant(@PathVariable(value = "nodeId") String nodeId)
			throws NoSuchElementException {
		
		Map<String, NodeEntity> descendants = service.collectchild(nodeId);

		if (!descendants.isEmpty()) {
			
			/*return new Resource<>(descendants,
				    linkTo(methodOn(DemoRestController.class).one(nodeId)).withSelfRel(),
				    linkTo(methodOn(DemoRestController.class).all()).withRel("descendants"));
*/
			return new ResponseEntity<>(descendants, HttpStatus.OK);

		} else {
			logger.info("No descendants found for "+nodeId);
			throw new NoSuchElementException("No children found for the given parent:" + nodeId);
		}

	}

	/**
	 * Update parent of given node Id of a Tree Like Structure
	 * 
	 * @param nodeId
	 * @return 
	 * @return OK.
	 */
	@RequestMapping(path = "/{nodeId}", method = RequestMethod.PATCH,
			consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Map<String, List<NodeEntity>>> UpdateParent(@PathVariable(value = "nodeId") String nodeId,
				@RequestBody NodeEntity node) {

		Map<String,List<NodeEntity>> updatedNodes = service.updateParent(nodeId,node);
		
		return new ResponseEntity<>(updatedNodes, HttpStatus.CREATED);
	}

	/* Aproch 2 with SQL */
	/**
	 * Fetches all descendant of a given node.
	 * 
	 * @param nodeId
	 * @return Map of descendant nodes
	 * 
	 */
	@RequestMapping(path = "/all/{nodeId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<NodeEntity>> getAllChildNode(@PathVariable String nodeId) throws NoSuchElementException {
		List<NodeEntity> descendants = noderepo.findAllChildrenNodeEntities(nodeId);

		if (!descendants.isEmpty()) {
			return new ResponseEntity<>(descendants, HttpStatus.OK);
		}

		else {
			System.out.println("No descendants found "+nodeId);
			throw new NoSuchElementException("No children found for the given parent:" + nodeId);
		}

	}

	/**
	 * Exception handler if NoSuchElementException is thrown in this Controller
	 * 
	 * @param ex
	 * @return Error message String.
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public String return400(NoSuchElementException ex) {

		return ex.getMessage();

	}
}
