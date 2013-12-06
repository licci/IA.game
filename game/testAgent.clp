//This is a template on which you should build your knowledge agents
//You should create your agents on LOCAL COPY of this template!

slot ID (type INTEGER)

//datastructure holding data about a node
(deftemplate node
	(slot idNumber (type INTEGER))
	(slot belongsTo (type INTEGER))
	(slot squares (type INTEGER))
	(slot circles (type INTEGER))
	(slot triangles (type INTEGER))
	(slot unitCreationType (type INTEGER))
	(slot unitCreationSpeed (type INTEGER))
	(multislot availableAdjacentNodes(type INTEGER))
)

//"array" of all nodes
(deftemplate graph
	(multislot nodes)
)

//variable used to return selected action to game
(defglobal ?*global* = "") 

(defglobal ?*squares* = "")
(defglobal ?*circles* = "")
(defglobal ?*triangles* = "")

//print result
(defrule hello 
  => 
  (printout t "Action chosen" crlf)
  (bind ?*global* "000000") 
)
  
 //example rules
(defrule findNode
   (node (idNumber 0) (belongsTo ?belongsNumber))
=>
   (printout t "node 0 belongs to " ?belongsNumber crlf)
)
(defrule findNodee
   (node (idNumber 5) (belongsTo ?belongsNumber))
=>
   (printout t "node 5 belongs to " ?belongsNumber crlf)
)
(defrule findNodeee
   (node (idNumber 13) (belongsTo ?belongsNumber))
=>
   (printout t "node 13 belongs to " ?belongsNumber crlf)
)

(defrule currentAgent
   (ID ?number)
=>
   (printout t "Agent ID is " ?number crlf)
)
	