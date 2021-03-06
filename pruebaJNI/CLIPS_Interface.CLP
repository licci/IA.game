(deftemplate node
	(slot idNumber (type INTEGER))
	(slot belongsTo (type INTEGER))
	(slot squares (type INTEGER))
	(slot circles (type INTEGER))
	(slot triangles (type INTEGER))
	(slot unitCreationType (type INTEGER))
	(slot unitCreationSpeed (type INTEGER))
	(slot special (type INTEGER))
	(multislot availableAdjacentNodes(type INTEGER))
)

(deftemplate graph
	(multislot nodes)
)

(defrule findNode
   (node (idNumber 0) (belongsTo ?belongsNumber))
=>
   (printout t "node 0 belongs to " ?belongsNumber crlf)
)
	