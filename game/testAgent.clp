//This is a template on which you should build your knowledge agents
//You should create your agents on LOCAL COPY of this template!

(deftemplate player
	(slot ID (type INTEGER))
)

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


(defglobal ?*squares* = 0)
(defglobal ?*circles* = 0)
(defglobal ?*triangles* = 0)

(defglobal ?*myNodes* = 0)

(defglobal ?*HQ* = 0)

(defrule infoMyNodes
(declare (salience 1000))
	(player (ID ?idnum))
	=>
		(if (< ?*myNodes* 2) then
			(printout t "I have " ?*myNodes* " node!" crlf)
		else (printout t "I have " ?*myNodes* " nodes!" crlf))
		
		(printout  t "Agent ID is " ?idnum crlf)
		
		(if (= ?idnum 1) then
			(bind ?*HQ* 0) 
		else (bind ?*HQ* 13) )
		(printout t "Agent HQ is node " ?*HQ* crlf)
)

(defrule countMyNodes
(declare (salience 1000))
	(node (belongsTo ?owner) (idNumber ?number))
	=>
			(if (= ?owner 1) then
				(bind ?*myNodes* (+ ?*myNodes* 1)) 
			) 
)

//print result
(defrule test
(node (idNumber 0) (squares ?sq) (circles ?cir) (triangles ?tra))
  => 
  (printout t "Action chosen" crlf)

  (bind ?*global* "000000") 
   (bind ?*squares* ?sq) 
   (bind ?*circles* (* ?cir 2)) 
   (bind ?*triangles* (- ?tra 10)) 
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
	