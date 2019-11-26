# DemoRepository For testing purpose
H2 in Memory DB has been used
http://localhost:8080/h2-console

# REST controller to create the following REST API endpoints :<br>
 HTTP Method	URI	Description<br>
  <b>1)GET /organisation/desecendants/{nodeId}	  - > List all descendants of a given node.<br></b>
  <b>2)PATCH	/organisation/desecendants/{nodeId} - > 	Update  parent of given node where nodeId = {:nodeId}.</b><br
  Sample Request Body For PATCH /Update    Eg: <br>
 {
 
    "node_id": "p",
    "parent_node": "c",   ---> with updated value ("foo")
    "root_node": "root",
    "height": 3

  }

