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

(defglobal ?*cost* = 999999)

(defrule attackFrom0To1
   (player (ID ?playerId))
   (node (idNumber 0) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 1) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "000101")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom0To2
   (player (ID ?playerId))
   (node (idNumber 0) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 2) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "000201")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom0To3
   (player (ID ?playerId))
   (node (idNumber 0) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 3) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "000301")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom1To0
   (player (ID ?playerId))
   (node (idNumber 1) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 0) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "010001")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom1To5
   (player (ID ?playerId))
   (node (idNumber 1) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 5) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "010501")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom2To4
   (player (ID ?playerId))
   (node (idNumber 2) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 4) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "020401")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom2To6
   (player (ID ?playerId))
   (node (idNumber 2) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 6) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "020601")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom3To5
   (player (ID ?playerId))
   (node (idNumber 3) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 5) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "030501")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom3To6
   (player (ID ?playerId))
   (node (idNumber 3) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 6) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "030601")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom4To1
   (player (ID ?playerId))
   (node (idNumber 4) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 1) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "040101")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom4To2
   (player (ID ?playerId))
   (node (idNumber 4) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 2) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "040201")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom4To8
   (player (ID ?playerId))
   (node (idNumber 4) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 8) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "040801")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom5To1
   (player (ID ?playerId))
   (node (idNumber 5) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 1) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "050101")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom5To3
   (player (ID ?playerId))
   (node (idNumber 5) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 3) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "050301")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom5To7
   (player (ID ?playerId))
   (node (idNumber 5) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 7) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "050701")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom5To8
   (player (ID ?playerId))
   (node (idNumber 5) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 8) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "050801")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom5To9
   (player (ID ?playerId))
   (node (idNumber 5) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 9) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "050901")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom6To2
   (player (ID ?playerId))
   (node (idNumber 6) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 2) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "060201")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom6To3
   (player (ID ?playerId))
   (node (idNumber 6) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 3) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "060301")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom6To8
   (player (ID ?playerId))
   (node (idNumber 6) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 8) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "060801")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom7To5
   (player (ID ?playerId))
   (node (idNumber 7) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 5) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "070501")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom7To10
   (player (ID ?playerId))
   (node (idNumber 7) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 10) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "071001")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom7To11
   (player (ID ?playerId))
   (node (idNumber 7) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 11) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "071101")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom8To4
   (player (ID ?playerId))
   (node (idNumber 8) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 4) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "080401")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom8To4
   (player (ID ?playerId))
   (node (idNumber 8) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 5) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "080501")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom8To6
   (player (ID ?playerId))
   (node (idNumber 8) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 6) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "080601")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom8To10
   (player (ID ?playerId))
   (node (idNumber 8) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 10) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "081001")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom8To12
   (player (ID ?playerId))
   (node (idNumber 8) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 12) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "081201")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom9To5
   (player (ID ?playerId))
   (node (idNumber 9) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 5) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "090501")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom9To11
   (player (ID ?playerId))
   (node (idNumber 9) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 11) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
  (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "091101")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom9To12
   (player (ID ?playerId))
   (node (idNumber 9) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 12) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "091201")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom10To7
   (player (ID ?playerId))
   (node (idNumber 10) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 7) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "100701")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom10To8
   (player (ID ?playerId))
   (node (idNumber 10) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 8) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
  (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "100801")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom10To13
   (player (ID ?playerId))
   (node (idNumber 10) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 13) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "101301")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom11To7
   (player (ID ?playerId))
   (node (idNumber 11) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 7) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "110701")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom11To9
   (player (ID ?playerId))
   (node (idNumber 11) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 9) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "110901")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom11To13
   (player (ID ?playerId))
   (node (idNumber 11) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 13) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
  (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "111301")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom12To8
   (player (ID ?playerId))
   (node (idNumber 12) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 8) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "120801")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom12To9
   (player (ID ?playerId))
   (node (idNumber 12) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 9) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "120901")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom12To13
   (player (ID ?playerId))
   (node (idNumber 12) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 13) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "121301")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom13To10
   (player (ID ?playerId))
   (node (idNumber 13) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 10) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "131001")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom13To11
   (player (ID ?playerId))
   (node (idNumber 13) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 11) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "131101")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
)

(defrule attackFrom13To12
   (player (ID ?playerId))
   (node (idNumber 13) (belongsTo ?sOwner) (squares ?sSq) (circles ?sCr) (triangles ?sTr))
   (node (idNumber 12) (belongsTo ?aOwner) (squares ?aSq) (circles ?aCr) (triangles ?aTr))
   (test (= ?sOwner ?playerId))
=>
   (bind ?actualPower (+ ?sSq (+ ?sCr ?sTr)))
   (bind ?actualCost (+ ?aSq (+ ?aCr ?aTr)))
   (bind ?actualCost (- ?actualPower ?actualCost))
   (if (> ?actualCost -1) then
   		(if (< ?actualCost ?*cost*) then
			(bind ?*global* "131201")
			(bind ?*squares* ?sSq) 
			(bind ?*circles* ?sCr) 
			(bind ?*triangles* ?sTr)
			(bind ?*cost* ?actualCost)
		)
	)
