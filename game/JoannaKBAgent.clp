;------------------------------BOTH PLAYERS------------------------------

(deftemplate player
	(slot ID (type INTEGER))
)

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

(deftemplate myNodesId
        (multislot idNumber)
)

;------------------------------PLAYER ID=1------------------------------

(deftemplate MyLargestNode
	(slot idNumber (type INTEGER))
	(slot belongsTo (type INTEGER))
	(slot squares (type INTEGER))
	(slot circles (type INTEGER))
	(slot triangles (type INTEGER))
	(slot unitCreationType (type INTEGER))
	(slot unitCreationSpeed (type INTEGER))
	(multislot availableAdjacentNodes(type INTEGER))
)

(deftemplate MySecondLargestNode
	(slot idNumber (type INTEGER))
	(slot belongsTo (type INTEGER))
	(slot squares (type INTEGER))
	(slot circles (type INTEGER))
	(slot triangles (type INTEGER))
	(slot unitCreationType (type INTEGER))
	(slot unitCreationSpeed (type INTEGER))
	(multislot availableAdjacentNodes(type INTEGER))
)

(defglobal ?*1stLargestNodeId* = 13)
(defglobal ?*2ndLargestNodeId* = 20)

(defglobal ?*1stLargestNodeId-1stLargestNeighboor* = -2)
(defglobal ?*1stLargestNodeId-2ndLargestNeighboor* = -2)
(defglobal ?*2ndLargestNodeId-1stLargestNeighboor* = -2)
(defglobal ?*2ndLargestNodeId-2ndLargestNeighboor* = -2)

;units of the 1st largest node
(defglobal ?*I-1Squares* = 0)
(defglobal ?*I-1Circles* = 0)
(defglobal ?*I-1Triangles* = 0)

;units of the 1st largest node 1st largest neighbour
(defglobal ?*I-1:1Squares* = 0)
(defglobal ?*I-1:1Circles* = 0)
(defglobal ?*I-1:1Triangles* = 0)

;units of the 1st largest node 2nd largest neighbour
(defglobal ?*I-1:2Squares* = 0)
(defglobal ?*I-1:2Circles* = 0)
(defglobal ?*I-1:2Triangles* = 0)

;units of the 2nd largest node
(defglobal ?*I-2Squares* = 0)
(defglobal ?*I-2Circles* = 0)
(defglobal ?*I-2Triangles* = 0)

;units of the 2nd largest node 1st largest neighbour
(defglobal ?*I-2:1Squares* = 0)
(defglobal ?*I-2:1Circles* = 0)
(defglobal ?*I-2:1Triangles* = 0)

;units of the 2nd largest node 2nd largest neighbour
(defglobal ?*I-2:2Squares* = 0)
(defglobal ?*I-2:2Circles* = 0)
(defglobal ?*I-2:2Triangles* = 0)

;bilans per unit of (1st largest node - 1st largest neighbour)
(defglobal ?*I-1:1BilansSquares* = 0)
(defglobal ?*I-1:1BilansCircles* = 0)
(defglobal ?*I-1:1BilansTriangles* = 0)

;bilans of (1st largest node - 1st largest neighbour)
(defglobal ?*I-1:1Difference* = 0)

;bilans per unit of (1st largest node - 2nd largest neighbour)
(defglobal ?*I-1:2BilansSquares* = 0)
(defglobal ?*I-1:2BilansCircles* = 0)
(defglobal ?*I-1:2BilansTriangles* = 0)

;bilans of (1st largest node - 2nd largest neighbour)
(defglobal ?*I-1:2Difference* = 0)

;bilans per unit of (2nd largest node - 1st largest neighbour)
(defglobal ?*I-2:1BilansSquares* = 0)
(defglobal ?*I-2:1BilansCircles* = 0)
(defglobal ?*I-2:1BilansTriangles* = 0)

;bilans of (2nd largest node - 1st largest neighbour)
(defglobal ?*I-2:1Difference* = 0)

;bilans per unit of (2nd largest node - 2nd largest neighbour)
(defglobal ?*I-2:2BilansSquares* = 0)
(defglobal ?*I-2:2BilansCircles* = 0)
(defglobal ?*I-2:2BilansTriangles* = 0)

;bilans of (2nd largest node - 2nd largest neighbour)
(defglobal ?*I-2:2Difference* = 0)


;------------------------------PLAYER ID=2------------------------------

(deftemplate MySmallestNode
	(slot idNumber (type INTEGER))
	(slot belongsTo (type INTEGER))
	(slot squares (type INTEGER))
	(slot circles (type INTEGER))
	(slot triangles (type INTEGER))
	(slot unitCreationType (type INTEGER))
	(slot unitCreationSpeed (type INTEGER))
	(multislot availableAdjacentNodes(type INTEGER))
)

(deftemplate MySecondSmallestNode
	(slot idNumber (type INTEGER))
	(slot belongsTo (type INTEGER))
	(slot squares (type INTEGER))
	(slot circles (type INTEGER))
	(slot triangles (type INTEGER))
	(slot unitCreationType (type INTEGER))
	(slot unitCreationSpeed (type INTEGER))
	(multislot availableAdjacentNodes(type INTEGER))
)

(defglobal ?*1stSmallestNodeId* = 0)
(defglobal ?*2ndSmallestNodeId* = -20)

(defglobal ?*1stSmallestNodeId-1stSmallestNeighboor* = 14)
(defglobal ?*1stSmallestNodeId-2ndSmallestNeighboor* = 14)
(defglobal ?*2ndSmallestNodeId-1stSmallestNeighboor* = 14)
(defglobal ?*2ndSmallestNodeId-2ndSmallestNeighboor* = 14)

;units of the 1st smallest node
(defglobal ?*II-1Squares* = 0)
(defglobal ?*II-1Circles* = 0)
(defglobal ?*II-1Triangles* = 0)

;units of the 1st smallest node 1st smallest neighbour
(defglobal ?*II-1:1Squares* = 0)
(defglobal ?*II-1:1Circles* = 0)
(defglobal ?*II-1:1Triangles* = 0)

;units of the 1st smallest node 2nd smallest neighbour
(defglobal ?*II-1:2Squares* = 0)
(defglobal ?*II-1:2Circles* = 0)
(defglobal ?*II-1:2Triangles* = 0)

;units of the 2nd smallest node
(defglobal ?*II-2Squares* = 0)
(defglobal ?*II-2Circles* = 0)
(defglobal ?*II-2Triangles* = 0)

;units of the 2nd smallest node 1st smallest neighbour
(defglobal ?*II-2:1Squares* = 0)
(defglobal ?*II-2:1Circles* = 0)
(defglobal ?*II-2:1Triangles* = 0)

;units of the 2nd smallest node 2nd smallest neighbour
(defglobal ?*II-2:2Squares* = 0)
(defglobal ?*II-2:2Circles* = 0)
(defglobal ?*II-2:2Triangles* = 0)

;bilans per unit of (1st smallest node - 1st smallest neighbour)
(defglobal ?*II-1:1BilansSquares* = 0)
(defglobal ?*II-1:1BilansCircles* = 0)
(defglobal ?*II-1:1BilansTriangles* = 0)

;bilans of (1st smallest node - 1st smallest neighbour)
(defglobal ?*II-1:1Difference* = 0)

;bilans per unit of (1st smallest node - 2nd smallest neighbour)
(defglobal ?*II-1:2BilansSquares* = 0)
(defglobal ?*II-1:2BilansCircles* = 0)
(defglobal ?*II-1:2BilansTriangles* = 0)

;bilans of (1st smallest node - 2nd smallest neighbour)
(defglobal ?*II-1:2Difference* = 0)

;bilans per unit of (2nd smallest node - 1st smallest neighbour)
(defglobal ?*II-2:1BilansSquares* = 0)
(defglobal ?*II-2:1BilansCircles* = 0)
(defglobal ?*II-2:1BilansTriangles* = 0)

;bilans of (2nd smallest node - 1st smallest neighbour)
(defglobal ?*II-2:1Difference* = 0)

;bilans per unit of (2nd smallest node - 2nd smallest neighbour)
(defglobal ?*II-2:2BilansSquares* = 0)
(defglobal ?*II-2:2BilansCircles* = 0)
(defglobal ?*II-2:2BilansTriangles* = 0)

;bilans of (2nd smallest node - 2nd smallest neighbour)
(defglobal ?*II-2:2Difference* = 0)


;------------------------------BOTH PLAYERS------------------------------

;variable used to return selected action to game
(defglobal ?*global* = "") 

(defglobal ?*squares* = 0)
(defglobal ?*circles* = 0)
(defglobal ?*triangles* = 0)

(defglobal ?*start* = "")
(defglobal ?*target* = "")
(defglobal ?*type* = "")

(defglobal ?*startNum* = -1)
(defglobal ?*targetNum* = -1)
(defglobal ?*typeNum* = -1)

(defglobal ?*targetNode* = 0)
(defglobal ?*HQ* = 0)

;(defglobal ?*myNodes* = 0)

;******************************************* RULES *******************************************

;------------------------------BOTH PLAYERS------------------------------
(defrule MyInfo
(declare (salience 10000))
	(player (ID ?idnum))
	=>
		(printout  t "Player ID = " ?idnum crlf)
		(if (= ?idnum 1) then (bind ?*HQ* 0) 
		else (bind ?*HQ* 13) 
		)
		(printout t "Base = " ?*HQ* crlf)
)

;------------------------------PLAYER ID=1------------------------------
;setting gole node
(defrule targetNode_13
(declare (salience 9999))
    (player (ID 1)) => (bind ?*targetNode* 13)
    (printout t "Goal node - " ?*targetNode* crlf)
)

;------------------------------PLAYER ID=2------------------------------
;setting gole node
(defrule targetNode_0
(declare (salience 9999))
	(player (ID 2)) => (bind ?*targetNode* 0)
    (printout t "Goal node - " ?*targetNode* crlf)
)

;------------------------------BOTH PLAYERS------------------------------
;saving all node's IDs that belong to me
(defrule MyNodes
(declare (salience 9998))
	(node (belongsTo ?owner) (idNumber ?number))
	(player (ID ?idnum))
	=>
	    (if (= ?owner ?idnum) then
		;(printout t "My node - " ?number  crlf)
		(assert (myNodesId (idNumber ?number)))
		) 
)

;------------------------------PLAYER ID=1------------------------------
;1
;takes my largest node Id
(defrule MyLargestNode
(declare (salience 9997))
    (player (ID ?idnum))
    (myNodesId (idNumber ?number))
    (not (myNodesId (idNumber ?biggest&: (> ?biggest ?number))))
    =>
        (bind ?*1stLargestNodeId* ?number)
        (if (= ?idnum 1) then
            (printout t "My largest node = " ?number crlf)
        )
)

;------------------------------PLAYER ID=2------------------------------
;1
;takes my smallest node Id
(defrule MySmallestNode
(declare (salience 9997))
    (player (ID ?idnum))
    (myNodesId (idNumber ?number))
    (not (myNodesId (idNumber ?smallest&: (< ?smallest ?number))))
    =>
        (bind ?*1stSmallestNodeId* ?number)
        (if (= ?idnum 2) then
            (printout t "My smallest node = " ?number crlf)
        )
)    

;------------------------------PLAYER ID=1------------------------------
;2
;takes my second largest node Id
(defrule MySecondLargestNode
(declare (salience 9996)) ;zmienic na 9996
    (player (ID ?idnum))
    (myNodesId (idNumber ?number2))
            =>
                (if (not(= ?number2 ?*1stLargestNodeId*))then
                    (bind ?*2ndLargestNodeId* ?number2)
                )
                (if (= ?idnum 1) then
                    (if (not (= ?*2ndLargestNodeId* 20)) then
                       (printout t "My second largest node = " ?*2ndLargestNodeId* crlf)
                    )    
                )
    )  
   
;------------------------------PLAYER ID=2------------------------------
;2
;takes my second smallest node Id
(defrule MySecondSmallestNode
(declare (salience 9996)) ;zmienic na 9996
    (player (ID ?idnum))
    (myNodesId (idNumber ?number2))
            =>
                (if (not(= ?number2 ?*1stSmallestNodeId*))then
                    (bind ?*2ndSmallestNodeId* ?number2)
                )
                (if (= ?idnum 2) then
                    (if (not (= ?*2ndSmallestNodeId* -20)) then
                        (printout t "My second smallest node = " ?*2ndSmallestNodeId* crlf)
                    )    
                )
    )     

;------------------------------PLAYER ID=1------------------------------
;1
;displays neighbours for my largest node
(defrule NeighboursOfMyLargestNode
(declare (salience 9995))
    (player (ID ?idnum))
    (node (idNumber ?number)(availableAdjacentNodes $?available))
	=>
        (if (= ?number ?*1stLargestNodeId*) then
            (if (= ?idnum 1) then
            (printout t "Neighbours of my biggest node - " $?available  crlf)
            )
        )   
)

;------------------------------PLAYER ID=2------------------------------
;1
;displays neighbours for my smallest node
(defrule NeighboursOfMySmallestNode
(declare (salience 9995))
    (player (ID ?idnum))
    (node (idNumber ?number)(availableAdjacentNodes $?available))
	=>
        (if (= ?number ?*1stSmallestNodeId*) then
            (if (= ?idnum 2) then
                (printout t "Neighbours of my smallest node - " $?available  crlf)
            )
        )   
)

;------------------------------PLAYER ID=1------------------------------
;2
;displays neighbours for my second largest node
(defrule NeighboursOfMySecondLargestNode
(declare (salience 9994))
    (player (ID ?idnum))
    (node (idNumber ?number)(availableAdjacentNodes $?available))
	=>
        (if (= ?number ?*2ndLargestNodeId*) then
            (if (= ?idnum 1) then
            (printout t "Neighbours of my second biggest node - " $?available  crlf)
            )
        )   
)

;------------------------------PLAYER ID=2------------------------------
;2
;displays neighbours for my second smallest node
(defrule NeighboursOfMySecondSmallestNode
(declare (salience 9994))
    (player (ID ?idnum))
    (node (idNumber ?number)(availableAdjacentNodes $?available))
	=>
        (if (= ?number ?*2ndSmallestNodeId*) then
            (if (= ?idnum 2) then
                (printout t "Neighbours of my second smallest node - " $?available  crlf)
            )
        )   
)

;------------------------------PLAYER ID=1------------------------------
;1
;takes node with largest ID and saves it in MyLargestNode template
(defrule TakeMyLargestNode
(declare (salience 9993))
    (node (idNumber ?a) (belongsTo ?c) (squares ?d) (circles ?e) (triangles ?f) (unitCreationType ?g) (unitCreationSpeed ?h) (availableAdjacentNodes $?i))
    =>
        (if (= ?a ?*1stLargestNodeId*) then
			(assert (MyLargestNode (idNumber ?*1stLargestNodeId*) (belongsTo ?c) (squares ?d) (circles ?e) (triangles ?f) (unitCreationType ?g) (unitCreationSpeed ?h) (availableAdjacentNodes $?i)))
        )
)

;------------------------------PLAYER ID=2------------------------------
;1
;takes node with smallest ID and saves it in MyLargestNode template
(defrule TakeMySmallestNode
(declare (salience 9993))
    (node (idNumber ?a) (belongsTo ?c) (squares ?d) (circles ?e) (triangles ?f) (unitCreationType ?g) (unitCreationSpeed ?h) (availableAdjacentNodes $?i))
    =>
        (if (= ?a ?*1stSmallestNodeId*) then
			(assert (MySmallestNode (idNumber ?a) (belongsTo ?c) (squares ?d) (circles ?e) (triangles ?f) (unitCreationType ?g) (unitCreationSpeed ?h) (availableAdjacentNodes $?i)))
        )
)

;------------------------------PLAYER ID=1------------------------------
;2
;takes node with second largest ID and saves it in MySecondLargestNode template
(defrule TakeMySecondLargestNode
(declare (salience 9992))
    (node (idNumber ?a) (belongsTo ?c) (squares ?d) (circles ?e) (triangles ?f) (unitCreationType ?g) (unitCreationSpeed ?h) (availableAdjacentNodes $?i))
    =>
        (if (= ?a ?*2ndLargestNodeId*) then
			(assert (MySecondLargestNode (idNumber ?*2ndLargestNodeId*) (belongsTo ?c) (squares ?d) (circles ?e) (triangles ?f) (unitCreationType ?g) (unitCreationSpeed ?h) (availableAdjacentNodes $?i)))
        )
)

;------------------------------PLAYER ID=2------------------------------
;2
;takes node with second smallest ID and saves it in MySecondSmallestNode template
(defrule TakeMySecondSmallestNode
(declare (salience 9992))
    (node (idNumber ?a) (belongsTo ?c) (squares ?d) (circles ?e) (triangles ?f) (unitCreationType ?g) (unitCreationSpeed ?h) (availableAdjacentNodes $?i))
    =>
        (if (= ?a ?*2ndSmallestNodeId*) then
			(assert (MySecondSmallestNode (idNumber ?a) (belongsTo ?c) (squares ?d) (circles ?e) (triangles ?f) (unitCreationType ?g) (unitCreationSpeed ?h) (availableAdjacentNodes $?i)))
        )
)

;------------------------------PLAYER ID=1------------------------------
;1-1
;takes neighbour with largest ID for my largest Node
(defrule TakeLargestNeighbourForMyLargestNode
(declare (salience 9991))
    (player (ID ?idnum))
    (MyLargestNode (availableAdjacentNodes ?a ?b ?c ?d ?e))
    =>
        (if (not(= ?a -1)) then
            (if (> ?a ?*1stLargestNodeId-1stLargestNeighboor*) then
				(bind ?*1stLargestNodeId-1stLargestNeighboor* ?a)
            )
        )
        (if (not(= ?b -1)) then
            (if (> ?b ?*1stLargestNodeId-1stLargestNeighboor*) then
				(bind ?*1stLargestNodeId-1stLargestNeighboor* ?b)
            )
        )
        (if (not(= ?c -1)) then
            (if (> ?c ?*1stLargestNodeId-1stLargestNeighboor*) then
				(bind ?*1stLargestNodeId-1stLargestNeighboor* ?c)
            )
        )
        (if (not(= ?d -1)) then
            (if (> ?d ?*1stLargestNodeId-1stLargestNeighboor*) then
				(bind ?*1stLargestNodeId-1stLargestNeighboor* ?d)
            )
        )
        (if (not(= ?e -1)) then
            (if (> ?e ?*1stLargestNodeId-1stLargestNeighboor*) then
				(bind ?*1stLargestNodeId-1stLargestNeighboor* ?e)
            )
        )

        (if (= ?idnum 1) then
            (if (= ?*1stLargestNodeId-1stLargestNeighboor* -2) then
                (printout t "My largest node doesn't have any neighbour." crlf)
            else
                (printout t "Largest neighbour for my largest node - " ?*1stLargestNodeId-1stLargestNeighboor*  crlf)
            )
        )     
)

;------------------------------PLAYER ID=2------------------------------
;1-1
;takes neighbour with smallest ID for my smallest Node
(defrule TakeSmallestNeighbourForMySmallestNode
(declare (salience 9991))
    (player (ID ?idnum))
    (MySmallestNode (availableAdjacentNodes ?a ?b ?c ?d ?e))
    =>
        (if (not(= ?a -1)) then
            (if (< ?a ?*1stSmallestNodeId-1stSmallestNeighboor*) then
				(bind ?*1stSmallestNodeId-1stSmallestNeighboor* ?a)
            )
        )
        (if (not(= ?b -1)) then
            (if (< ?b ?*1stSmallestNodeId-1stSmallestNeighboor*) then
				(bind ?*1stSmallestNodeId-1stSmallestNeighboor* ?b)
            )
        )
        (if (not(= ?c -1)) then
            (if (< ?c ?*1stSmallestNodeId-1stSmallestNeighboor*) then
				(bind ?*1stSmallestNodeId-1stSmallestNeighboor* ?c)
            )
        )
        (if (not(= ?d -1)) then
            (if (< ?d ?*1stSmallestNodeId-1stSmallestNeighboor*) then
				(bind ?*1stSmallestNodeId-1stSmallestNeighboor* ?d)
            )
        )
        (if (not(= ?e -1)) then
            (if (< ?e ?*1stSmallestNodeId-1stSmallestNeighboor*) then
				(bind ?*1stSmallestNodeId-1stSmallestNeighboor* ?e)
            )
        )

        (if (= ?idnum 2) then
            (if (= ?*1stSmallestNodeId-1stSmallestNeighboor* 14) then
                (printout t "My smallest node doesn't have any neighbour." crlf)
            else
                (printout t "Smallest neighbour for my smallest node - " ?*1stSmallestNodeId-1stSmallestNeighboor*  crlf)
            )  
        )   
)

;------------------------------PLAYER ID=1------------------------------
;2-1
;takes neighbour with largest ID for my second largest Node
(defrule TakeLargestNeighbourForMySecondLargestNode
(declare (salience 9990))
    (player (ID ?idnum))
    (MySecondLargestNode (availableAdjacentNodes ?a ?b ?c ?d ?e))
    =>
        (if (not(= ?a -1)) then
            (if (> ?a ?*2ndLargestNodeId-1stLargestNeighboor*) then
				(bind ?*2ndLargestNodeId-1stLargestNeighboor* ?a)
            )
        )
        (if (not(= ?b -1)) then
            (if (> ?b ?*2ndLargestNodeId-1stLargestNeighboor*) then
				(bind ?*2ndLargestNodeId-1stLargestNeighboor* ?b)
            )
        )
        (if (not(= ?c -1)) then
            (if (> ?c ?*2ndLargestNodeId-1stLargestNeighboor*) then
				(bind ?*2ndLargestNodeId-1stLargestNeighboor* ?c)
            )
        )
        (if (not(= ?d -1)) then
            (if (> ?d ?*2ndLargestNodeId-1stLargestNeighboor*) then
				(bind ?*2ndLargestNodeId-1stLargestNeighboor* ?d)
            )
        )
        (if (not(= ?e -1)) then
            (if (> ?e ?*2ndLargestNodeId-1stLargestNeighboor*) then
				(bind ?*2ndLargestNodeId-1stLargestNeighboor* ?e)
            )
        )

        (if (= ?idnum 1) then
            (if (= ?*2ndLargestNodeId-1stLargestNeighboor* -2) then
                (printout t "My second largest node doesn't have any neighbour." crlf)
            else
                (printout t "Largest neighbour for my second largest node - " ?*2ndLargestNodeId-1stLargestNeighboor*  crlf)
            )
        )     
)

;------------------------------PLAYER ID=2------------------------------
;2-1
;takes neighbour with smallest ID for my second smallest Node
(defrule TakeSmallestNeighbourForMySecondSmallestNode
(declare (salience 9990))
    (player (ID ?idnum))
    (MySecondSmallestNode (availableAdjacentNodes ?a ?b ?c ?d ?e))
    =>
        (if (not(= ?a -1)) then
            (if (< ?a ?*2ndSmallestNodeId-1stSmallestNeighboor*) then
				(bind ?*2ndSmallestNodeId-1stSmallestNeighboor* ?a)
            )
        )
        (if (not(= ?b -1)) then
            (if (< ?b ?*2ndSmallestNodeId-1stSmallestNeighboor*) then
				(bind ?*2ndSmallestNodeId-1stSmallestNeighboor* ?b)
            )
        )
        (if (not(= ?c -1)) then
            (if (< ?c ?*2ndSmallestNodeId-1stSmallestNeighboor*) then
				(bind ?*2ndSmallestNodeId-1stSmallestNeighboor* ?c)
            )
        )
        (if (not(= ?d -1)) then
            (if (< ?d ?*2ndSmallestNodeId-1stSmallestNeighboor*) then
				(bind ?*2ndSmallestNodeId-1stSmallestNeighboor* ?d)
            )
        )
        (if (not(= ?e -1)) then
            (if (< ?e ?*2ndSmallestNodeId-1stSmallestNeighboor*) then
				(bind ?*2ndSmallestNodeId-1stSmallestNeighboor* ?e)
            )
        )

        (if (= ?idnum 2) then
            (if (= ?*2ndSmallestNodeId-1stSmallestNeighboor* 14) then
                (printout t "My second smallest node doesn't have any neighbour." crlf)
            else
                (printout t "Smallest neighbour for my second smallest node - " ?*2ndSmallestNodeId-1stSmallestNeighboor*  crlf)
            )  
        )   
)

;------------------------------PLAYER ID=1------------------------------
;1-2
;takes neighbour with second largest ID for my largest Node
(defrule TakeSecondLargestNeighbourForMyLargestNode
(declare (salience 9989));zmienic priorytet
    (player (ID ?idnum))
    (MyLargestNode (availableAdjacentNodes ?a ?b ?c ?d ?e))
    =>
        (if (not(= ?a -1)) then
            (if (not(= ?a ?*1stLargestNodeId-1stLargestNeighboor*)) then
				(if (> ?a ?*1stLargestNodeId-2ndLargestNeighboor*) then
					(bind ?*1stLargestNodeId-2ndLargestNeighboor* ?a)
            ))
        )
        (if (not(= ?b -1)) then
            (if (not(= ?b ?*1stLargestNodeId-1stLargestNeighboor*)) then
				(if (> ?b ?*1stLargestNodeId-2ndLargestNeighboor*) then
					(bind ?*1stLargestNodeId-2ndLargestNeighboor* ?b)
            ))
        )
        (if (not(= ?c -1)) then
            (if (not(= ?c ?*1stLargestNodeId-1stLargestNeighboor*)) then
				(if (> ?c ?*1stLargestNodeId-2ndLargestNeighboor*) then
					(bind ?*1stLargestNodeId-2ndLargestNeighboor* ?c)
            ))
        )
        (if (not(= ?d -1)) then
            (if (not(= ?d ?*1stLargestNodeId-1stLargestNeighboor*)) then
				(if (> ?d ?*1stLargestNodeId-2ndLargestNeighboor*) then
					(bind ?*1stLargestNodeId-2ndLargestNeighboor* ?d)
            ))
        )
        (if (not(= ?e -1)) then
            (if (not(= ?e ?*1stLargestNodeId-1stLargestNeighboor*)) then
				(if (> ?e ?*1stLargestNodeId-2ndLargestNeighboor*) then
					(bind ?*1stLargestNodeId-2ndLargestNeighboor* ?e)
            ))
        )

        (if (= ?idnum 1) then
            (if (= ?*1stLargestNodeId-2ndLargestNeighboor* -2) then
                (printout t "My largest node doesn't have two neighbours." crlf)
            else
                (printout t "Second largest neighbour for my largest node - " ?*1stLargestNodeId-2ndLargestNeighboor*  crlf)
            )
        )     
)

;------------------------------PLAYER ID=2------------------------------
;1-2
;takes neighbour with second smallest ID for my smallest Node
(defrule TakeSecondSmallestNeighbourForMySmallestNode
(declare (salience 9989));zmienic priorytet
    (player (ID ?idnum))
    (MySmallestNode (availableAdjacentNodes ?a ?b ?c ?d ?e))
    =>
        (if (not(= ?a -1)) then
            (if (not(= ?a ?*1stSmallestNodeId-1stSmallestNeighboor*)) then
				(if (< ?a ?*1stSmallestNodeId-2ndSmallestNeighboor*) then
					(bind ?*1stSmallestNodeId-2ndSmallestNeighboor* ?a)
            ))
        )
        (if (not(= ?b -1)) then
            (if (not(= ?b ?*1stSmallestNodeId-1stSmallestNeighboor*)) then
				(if (< ?b ?*1stSmallestNodeId-2ndSmallestNeighboor*) then
					(bind ?*1stSmallestNodeId-2ndSmallestNeighboor* ?b)
            ))
        )
        (if (not(= ?c -1)) then
            (if (not(= ?c ?*1stSmallestNodeId-1stSmallestNeighboor*)) then
				(if (< ?c ?*1stSmallestNodeId-2ndSmallestNeighboor*) then
					(bind ?*1stSmallestNodeId-2ndSmallestNeighboor* ?c)
            ))
        )
        (if (not(= ?d -1)) then
            (if (not(= ?d ?*1stSmallestNodeId-1stSmallestNeighboor*)) then
				(if (< ?d ?*1stSmallestNodeId-2ndSmallestNeighboor*) then
					(bind ?*1stSmallestNodeId-2ndSmallestNeighboor* ?d)
            ))
        )
        (if (not(= ?e -1)) then
            (if (not(= ?e ?*1stSmallestNodeId-1stSmallestNeighboor*)) then
				(if (< ?e ?*1stSmallestNodeId-2ndSmallestNeighboor*) then
					(bind ?*1stSmallestNodeId-2ndSmallestNeighboor* ?e)
            ))
        )

        (if (= ?idnum 2) then
            (if (= ?*1stSmallestNodeId-2ndSmallestNeighboor* 14) then
                (printout t "My smallest node doesn't have two neighbours." crlf)
            else
                (printout t "Second smallest neighbour for my smallest node - " ?*1stSmallestNodeId-2ndSmallestNeighboor*  crlf)
            )
        )     
)

;------------------------------PLAYER ID=1------------------------------
;2-2
;takes neighbour with second largest ID for my second largest Node
(defrule TakeSecondLargestNeighbourForMySecondLargestNode
(declare (salience 9988));zmienic priorytet
    (player (ID ?idnum))
    (MySecondLargestNode (availableAdjacentNodes ?a ?b ?c ?d ?e))
    =>
        (if (not(= ?a -1)) then
            (if (not(= ?a ?*2ndLargestNodeId-1stLargestNeighboor*)) then
				(if (> ?a ?*2ndLargestNodeId-2ndLargestNeighboor*) then
					(bind ?*2ndLargestNodeId-2ndLargestNeighboor* ?a)
            ))
        )
        (if (not(= ?b -1)) then
            (if (not(= ?b ?*2ndLargestNodeId-1stLargestNeighboor*)) then
				(if (> ?b ?*2ndLargestNodeId-2ndLargestNeighboor*) then
					(bind ?*2ndLargestNodeId-2ndLargestNeighboor* ?b)
            ))
        )
        (if (not(= ?c -1)) then
            (if (not(= ?c ?*2ndLargestNodeId-1stLargestNeighboor*)) then
				(if (> ?c ?*2ndLargestNodeId-2ndLargestNeighboor*) then
					(bind ?*2ndLargestNodeId-2ndLargestNeighboor* ?c)
            ))
        )
        (if (not(= ?d -1)) then
            (if (not(= ?d ?*2ndLargestNodeId-1stLargestNeighboor*)) then
				(if (> ?d ?*2ndLargestNodeId-2ndLargestNeighboor*) then
					(bind ?*2ndLargestNodeId-2ndLargestNeighboor* ?d)
            ))
        )
        (if (not(= ?e -1)) then
            (if (not(= ?e ?*2ndLargestNodeId-1stLargestNeighboor*)) then
				(if (> ?e ?*2ndLargestNodeId-2ndLargestNeighboor*) then
					(bind ?*2ndLargestNodeId-2ndLargestNeighboor* ?e)
            ))
        )

        (if (= ?idnum 1) then
            (if (= ?*2ndLargestNodeId-2ndLargestNeighboor* -2) then
                (printout t "Second largest neighbour for my second largest node" crlf)
            else
                (printout t "My second biggest neighbour for second biggest - " ?*2ndLargestNodeId-2ndLargestNeighboor*  crlf)
            )
        )     
)

;------------------------------PLAYER ID=2------------------------------
;2-2
;takes neighbour with second smallest ID for my second smallest Node
(defrule TakeSecondSmallestNeighbourForMySecondSmallestNode
(declare (salience 9988));zmienic priorytet
    (player (ID ?idnum))
    (MySecondSmallestNode (availableAdjacentNodes ?a ?b ?c ?d ?e))
    =>
        (if (not(= ?a -1)) then
            (if (not(= ?a ?*2ndSmallestNodeId-1stSmallestNeighboor*)) then
				(if (< ?a ?*2ndSmallestNodeId-2ndSmallestNeighboor*) then
					(bind ?*2ndSmallestNodeId-2ndSmallestNeighboor* ?a)
            ))
        )
        (if (not(= ?b -1)) then
            (if (not(= ?b ?*2ndSmallestNodeId-1stSmallestNeighboor*)) then
				(if (< ?b ?*2ndSmallestNodeId-2ndSmallestNeighboor*) then
					(bind ?*2ndSmallestNodeId-2ndSmallestNeighboor* ?b)
            ))
        )
        (if (not(= ?c -1)) then
            (if (not(= ?c ?*2ndSmallestNodeId-1stSmallestNeighboor*)) then
				(if (< ?c ?*2ndSmallestNodeId-2ndSmallestNeighboor*) then
					(bind ?*2ndSmallestNodeId-2ndSmallestNeighboor* ?c)
            ))
        )
        (if (not(= ?d -1)) then
            (if (not(= ?d ?*2ndSmallestNodeId-1stSmallestNeighboor*)) then
				(if (< ?d ?*2ndSmallestNodeId-2ndSmallestNeighboor*) then
					(bind ?*2ndSmallestNodeId-2ndSmallestNeighboor* ?d)
            ))
        )
        (if (not(= ?e -1)) then
            (if (not(= ?e ?*2ndSmallestNodeId-1stSmallestNeighboor*)) then
				(if (< ?e ?*2ndSmallestNodeId-2ndSmallestNeighboor*) then
					(bind ?*2ndSmallestNodeId-2ndSmallestNeighboor* ?e)
            ))
        )

        (if (= ?idnum 2) then
            (if (= ?*1stSmallestNodeId-2ndSmallestNeighboor* 14) then
                (printout t "Second smallest neighbour for my second smallest node" crlf)
            else
                (printout t "My second smallest neighbour for second smallest node - " ?*2ndSmallestNodeId-2ndSmallestNeighboor*  crlf)
            )
        )     
)

;------------------------------PLAYER ID=1------------------------------
;1
;counts units for my largest node
(defrule CountUnitsForMyLargestNode
(declare (salience 9987))
    (player (ID ?idnum))
    (MyLargestNode (squares ?sq)(circles ?ci)(triangles ?tr))
    =>
        (bind ?*I-1Squares* ?sq)
        (bind ?*I-1Circles* ?ci)
        (bind ?*I-1Triangles* ?tr)

        (if (= ?idnum 1) then
            (printout t "I-1 squares - " ?*I-1Squares*  crlf)
            (printout t "I-1 circles - " ?*I-1Circles*  crlf)
            (printout t "I-1 triangles - " ?*I-1Triangles*  crlf)
        )
)

;------------------------------PLAYER ID=2------------------------------
;1
;counts units for my smallest node
(defrule CountUnitsForMySmallestNode
(declare (salience 9987))
    (player (ID ?idnum))
    (MySmallestNode (squares ?sq)(circles ?ci)(triangles ?tr))
    =>
        (bind ?*II-1Squares* ?sq)
        (bind ?*II-1Circles* ?ci)
        (bind ?*II-1Triangles* ?tr)

        (if (= ?idnum 2) then
            (printout t "II-1 squares - " ?*II-1Squares*  crlf)
            (printout t "II-1 circles - " ?*II-1Circles*  crlf)
            (printout t "II-1 triangles - " ?*II-1Triangles*  crlf)
        )
)

;------------------------------PLAYER ID=1------------------------------
;2
;counts units for my second largest node
(defrule CountUnitsForMySecondLargestNode
(declare (salience 9986))
    (player (ID ?idnum))
    (MySecondLargestNode (squares ?sq)(circles ?ci)(triangles ?tr))
    =>
        (bind ?*I-2Squares* ?sq)
        (bind ?*I-2Circles* ?ci)
        (bind ?*I-2Triangles* ?tr)

        (if (= ?idnum 1) then
            (printout t "I-2 squares - " ?*I-2Squares*  crlf)
            (printout t "I-2 circles - " ?*I-2Circles*  crlf)
            (printout t "I-2 triangles - " ?*I-2Triangles*  crlf)
        )
)

;------------------------------PLAYER ID=2------------------------------
;2
;counts units for my second smallest node
(defrule CountUnitsForMySecondSmallestNode
(declare (salience 9986))
    (player (ID ?idnum))
    (MySecondSmallestNode (squares ?sq)(circles ?ci)(triangles ?tr))
    =>
        (bind ?*II-2Squares* ?sq)
        (bind ?*II-2Circles* ?ci)
        (bind ?*II-2Triangles* ?tr)

        (if (= ?idnum 2) then
            (printout t "II-2 squares - " ?*II-2Squares*  crlf)
            (printout t "II-2 circles - " ?*II-2Circles*  crlf)
            (printout t "II-2 triangles - " ?*II-2Triangles*  crlf)
        )
)

;------------------------------PLAYER ID=1------------------------------
;1-1
;counts units for my largest node's largest neighbour
(defrule CountUnitsForMyLargestNodeLargestNeighbour
(declare (salience 9985))
    (player (ID ?idnum))
    (node (idNumber ?id) (squares ?sq)(circles ?ci)(triangles ?tr))
    =>
        (if (= ?id ?*1stLargestNodeId-1stLargestNeighboor*) then
            (bind ?*I-1:1Squares* ?sq)
            (bind ?*I-1:1Circles* ?ci)
            (bind ?*I-1:1Triangles* ?tr)

            (if (= ?idnum 1) then
                (printout t "I-1:1 squares - " ?*I-1:1Squares*  crlf)
                (printout t "I-1:1 circles - " ?*I-1:1Circles*  crlf)
                (printout t "I-1:1 triangles - " ?*I-1:1Triangles*  crlf)
            )
        )
)

;------------------------------PLAYER ID=2------------------------------
;1-1
;counts units for my smallest node's smallest neighbour
(defrule CountUnitsForMySmallestNodeSmallestNeighbour
(declare (salience 9985))
    (player (ID ?idnum))
    (node (idNumber ?id) (squares ?sq)(circles ?ci)(triangles ?tr))
    =>
        (if (= ?id ?*1stSmallestNodeId-1stSmallestNeighboor*) then
            (bind ?*II-1:1Squares* ?sq)
            (bind ?*II-1:1Circles* ?ci)
            (bind ?*II-1:1Triangles* ?tr)

            (if (= ?idnum 2) then
                (printout t "II-1:1 squares - " ?*II-1:1Squares*  crlf)
                (printout t "II-1:1 circles - " ?*II-1:1Circles*  crlf)
                (printout t "II-1:1 triangles - " ?*II-1:1Triangles*  crlf)
            )
        )
)

;------------------------------PLAYER ID=1------------------------------
;1-2
;counts units for my largest node's second largest neighbour
(defrule CountUnitsForMyLargestNodeSecondLargestNeighbour
(declare (salience 9984))
    (player (ID ?idnum))
    (node (idNumber ?id) (squares ?sq)(circles ?ci)(triangles ?tr))
    =>
        (if (= ?id ?*1stLargestNodeId-2ndLargestNeighboor*) then
            (bind ?*I-1:2Squares* ?sq)
            (bind ?*I-1:2Circles* ?ci)
            (bind ?*I-1:2Triangles* ?tr)

            (if (= ?idnum 1) then
                (printout t "I-1:2 squares - " ?*I-1:2Squares*  crlf)
                (printout t "I-1:2 circles - " ?*I-1:2Circles*  crlf)
                (printout t "I-1:2 triangles - " ?*I-1:2Triangles*  crlf)
            )
        )
)

;------------------------------PLAYER ID=2------------------------------
;1-2
;counts units for my smallest node's second smallest neighbour
(defrule CountUnitsForMySmallestNodeSecondSmallestNeighbour
(declare (salience 9984))
    (player (ID ?idnum))
    (node (idNumber ?id) (squares ?sq)(circles ?ci)(triangles ?tr))
    =>
        (if (= ?id ?*1stSmallestNodeId-2ndSmallestNeighboor*) then
            (bind ?*II-1:2Squares* ?sq)
            (bind ?*II-1:2Circles* ?ci)
            (bind ?*II-1:2Triangles* ?tr)

            (if (= ?idnum 2) then
                (printout t "II-1:2 squares - " ?*II-1:2Squares*  crlf)
                (printout t "II-1:2 circles - " ?*II-1:2Circles*  crlf)
                (printout t "II-1:2 triangles - " ?*II-1:2Triangles*  crlf)
            )
        )
)

;------------------------------PLAYER ID=1------------------------------
;2-1
;counts units for my second largest node's largest neighbour
(defrule CountUnitsForMySecondLargestNodeLargestNeighbour
(declare (salience 9983))
    (player (ID ?idnum))
    (node (idNumber ?id) (squares ?sq)(circles ?ci)(triangles ?tr))
    =>
        (if (= ?id ?*2ndLargestNodeId-1stLargestNeighboor*) then
            (bind ?*I-2:1Squares* ?sq)
            (bind ?*I-2:1Circles* ?ci)
            (bind ?*I-2:1Triangles* ?tr)

            (if (= ?idnum 1) then
                (printout t "I-2:1 squares - " ?*I-2:1Squares*  crlf)
                (printout t "I-2:1 circles - " ?*I-2:1Circles*  crlf)
                (printout t "I-2:1 triangles - " ?*I-2:1Triangles*  crlf)
            )
        )
)

;------------------------------PLAYER ID=2------------------------------
;2-1
;counts units for my second smallest node's smallest neighbour
(defrule CountUnitsForMySmallestNodeSmallestNeighbour
(declare (salience 9983))
    (player (ID ?idnum))
    (node (idNumber ?id) (squares ?sq)(circles ?ci)(triangles ?tr))
    =>
        (if (= ?id ?*2ndSmallestNodeId-1stSmallestNeighboor*) then
            (bind ?*II-2:1Squares* ?sq)
            (bind ?*II-2:1Circles* ?ci)
            (bind ?*II-2:1Triangles* ?tr)

            (if (= ?idnum 2) then
                (printout t "II-2:1 squares - " ?*II-2:1Squares*  crlf)
                (printout t "II-2:1 circles - " ?*II-2:1Circles*  crlf)
                (printout t "II-2:1 triangles - " ?*II-2:1Triangles*  crlf)
            )
        )
)

;------------------------------PLAYER ID=1------------------------------
;2-2
;counts units for my second largest node's second largest neighbour
(defrule CountUnitsForMySecondLargestNodeSecondLargestNeighbour
(declare (salience 9982))
    (player (ID ?idnum))
    (node (idNumber ?id) (squares ?sq)(circles ?ci)(triangles ?tr))
    =>
        (if (= ?id ?*2ndLargestNodeId-2ndLargestNeighboor*) then
            (bind ?*I-2:2Squares* ?sq)
            (bind ?*I-2:2Circles* ?ci)
            (bind ?*I-2:2Triangles* ?tr)

            (if (= ?idnum 1) then
                (printout t "I-2:2 squares - " ?*I-2:2Squares*  crlf)
                (printout t "I-2:2 circles - " ?*I-2:2Circles*  crlf)
                (printout t "I-2:2 triangles - " ?*I-2:2Triangles*  crlf)
            )
        )
)

;------------------------------PLAYER ID=2------------------------------
;2-2
;counts units for my second smallest node's second smallest neighbour
(defrule CountUnitsForMySmallestNodeSmallestNeighbour
(declare (salience 9982))
    (player (ID ?idnum))
    (node (idNumber ?id) (squares ?sq)(circles ?ci)(triangles ?tr))
    =>
        (if (= ?id ?*2ndSmallestNodeId-2ndSmallestNeighboor*) then
            (bind ?*II-2:2Squares* ?sq)
            (bind ?*II-2:2Circles* ?ci)
            (bind ?*II-2:2Triangles* ?tr)

            (if (= ?idnum 2) then
                (printout t "II-2:2 squares - " ?*II-2:2Squares*  crlf)
                (printout t "II-2:2 circles - " ?*II-2:2Circles*  crlf)
                (printout t "II-2:2 triangles - " ?*II-2:2Triangles*  crlf)
            )
        )
)

;------------------------------PLAYER ID=1------------------------------
;1-1
;bilans per unit of (1st largest node - 1st largest neighbour)
(defrule I-1:1unitBilans
(declare (salience 9981))
    (player (ID ?idnum))
	=>
		(bind ?*I-1:1BilansSquares* (- ?*I-1Squares* ?*I-1:1Squares*))
		(bind ?*I-1:1BilansCircles* (- ?*I-1Circles* ?*I-1:1Circles*))
		(bind ?*I-1:1BilansTriangles* (- ?*I-1Triangles* ?*I-1:1Triangles*))

		(if (= ?idnum 1) then
			(printout t "I-1:1 squares bilans - " ?*I-1:1BilansSquares*  crlf)
			(printout t "I-1:1 circles bilans - " ?*I-1:1BilansCircles*  crlf)
			(printout t "I-1:1 triangles bilans - " ?*I-1:1BilansTriangles*  crlf)
		)
)

;------------------------------PLAYER ID=2------------------------------
;1-1
;bilans per unit of (1st smallest node - 1st smallest neighbour)
(defrule II-1:1unitBilans
(declare (salience 9981))
    (player (ID ?idnum))
	=>
		(bind ?*II-1:1BilansSquares* (- ?*II-1Squares* ?*II-1:1Squares*))
		(bind ?*II-1:1BilansCircles* (- ?*II-1Circles* ?*II-1:1Circles*))
		(bind ?*II-1:1BilansTriangles* (- ?*II-1Triangles* ?*II-1:1Triangles*))

		(if (= ?idnum 2) then
			(printout t "II-1:1 squares bilans - " ?*II-1:1BilansSquares*  crlf)
			(printout t "II-1:1 circles bilans - " ?*II-1:1BilansCircles*  crlf)
			(printout t "II-1:1 triangles bilans - " ?*II-1:1BilansTriangles*  crlf)
		)
)

;------------------------------PLAYER ID=1------------------------------
;1-2
;bilans per unit of (1st largest node - 2nd largest neighbour)
(defrule I-1:2unitBilans
(declare (salience 9980))
    (player (ID ?idnum))
	=>
		(bind ?*I-1:2BilansSquares* (- ?*I-1Squares* ?*I-1:2Squares*))
		(bind ?*I-1:2BilansCircles* (- ?*I-1Circles* ?*I-1:2Circles*))
		(bind ?*I-1:2BilansTriangles* (- ?*I-1Triangles* ?*I-1:2Triangles*))

		(if (= ?idnum 1) then
			(printout t "I-1:2 squares bilans - " ?*I-1:2BilansSquares*  crlf)
			(printout t "I-1:2 circles bilans - " ?*I-1:2BilansCircles*  crlf)
			(printout t "I-1:2 triangles bilans - " ?*I-1:2BilansTriangles*  crlf)
		)
)

;------------------------------PLAYER ID=2------------------------------
;1-2
;bilans per unit of (1st smallest node - 2nd smallest neighbour)
(defrule II-1:2unitBilans
(declare (salience 9980))
    (player (ID ?idnum))
	=>
		(bind ?*II-1:2BilansSquares* (- ?*II-1Squares* ?*II-1:2Squares*))
		(bind ?*II-1:2BilansCircles* (- ?*II-1Circles* ?*II-1:2Circles*))
		(bind ?*II-1:2BilansTriangles* (- ?*II-1Triangles* ?*II-1:2Triangles*))

		(if (= ?idnum 2) then
			(printout t "II-1:2 squares bilans - " ?*II-1:2BilansSquares*  crlf)
			(printout t "II-1:2 circles bilans - " ?*II-1:2BilansCircles*  crlf)
			(printout t "II-1:2 triangles bilans - " ?*II-1:2BilansTriangles*  crlf)
		)
)

;------------------------------PLAYER ID=1------------------------------
;2-1
;bilans per unit of (2nd largest node - 1st largest neighbour)
(defrule I-2:1unitBilans
(declare (salience 9979))
    (player (ID ?idnum))
	=>
		(bind ?*I-2:1BilansSquares* (- ?*I-2Squares* ?*I-2:1Squares*))
		(bind ?*I-2:1BilansCircles* (- ?*I-2Circles* ?*I-2:1Circles*))
		(bind ?*I-2:1BilansTriangles* (- ?*I-2Triangles* ?*I-2:1Triangles*))

		(if (= ?idnum 1) then
			(printout t "I-2:1 squares bilans - " ?*I-2:1BilansSquares*  crlf)
			(printout t "I-2:1 circles bilans - " ?*I-2:1BilansCircles*  crlf)
			(printout t "I-2:1 triangles bilans - " ?*I-2:1BilansTriangles*  crlf)
		)
)

;------------------------------PLAYER ID=2------------------------------
;2-1
;bilans per unit of (2nd smallest node - 1st smallest neighbour)
(defrule II-2:1unitBilans
(declare (salience 9979))
    (player (ID ?idnum))
	=>
		(bind ?*II-2:1BilansSquares* (- ?*II-2Squares* ?*II-2:1Squares*))
		(bind ?*II-2:1BilansCircles* (- ?*II-2Circles* ?*II-2:1Circles*))
		(bind ?*II-2:1BilansTriangles* (- ?*II-2Triangles* ?*II-2:1Triangles*))

		(if (= ?idnum 2) then
			(printout t "II-2:1 squares bilans - " ?*II-2:1BilansSquares*  crlf)
			(printout t "II-2:1 circles bilans - " ?*II-2:1BilansCircles*  crlf)
			(printout t "II-2:1 triangles bilans - " ?*II-2:1BilansTriangles*  crlf)
		)
)

;------------------------------PLAYER ID=1------------------------------
;2-2
;;bilans per unit of (2nd largest node - 2nd largest neighbour)
(defrule I-2:2unitBilans
(declare (salience 9978))
    (player (ID ?idnum))
	=>
		(bind ?*I-2:2BilansSquares* (- ?*I-2Squares* ?*I-2:2Squares*))
		(bind ?*I-2:2BilansCircles* (- ?*I-2Circles* ?*I-2:2Circles*))
		(bind ?*I-2:2BilansTriangles* (- ?*I-2Triangles* ?*I-2:2Triangles*))

		(if (= ?idnum 1) then
			(printout t "I-2:2 squares bilans - " ?*I-2:2BilansSquares*  crlf)
			(printout t "I-2:2 circles bilans - " ?*I-2:2BilansCircles*  crlf)
			(printout t "I-2:2 triangles bilans - " ?*I-2:2BilansTriangles*  crlf)
		)	
)

;------------------------------PLAYER ID=2------------------------------
;2-2
;bilans per unit of (2nd smallest node - 2nd smallest neighbour)
(defrule II-2:2unitBilans
(declare (salience 9978))
    (player (ID ?idnum))
	=>
		(bind ?*II-2:2BilansSquares* (- ?*II-2Squares* ?*II-2:2Squares*))
		(bind ?*II-2:2BilansCircles* (- ?*II-2Circles* ?*II-2:2Circles*))
		(bind ?*II-2:2BilansTriangles* (- ?*II-2Triangles* ?*II-2:2Triangles*))

		(if (= ?idnum 2) then
			(printout t "II-2:2 squares bilans - " ?*II-2:2BilansSquares*  crlf)
			(printout t "II-2:2 circles bilans - " ?*II-2:2BilansCircles*  crlf)
			(printout t "II-2:2 triangles bilans - " ?*II-2:2BilansTriangles*  crlf)
		)
)

;------------------------------PLAYER ID=1------------------------------
;1-1
;bilans of (1st largest node - 1st largest neighbour)
(defrule I-1:1DifferenceValue
(declare (salience 9977))
    (player (ID ?idnum))
	=>
		(bind ?*I-1:1Difference* (+ ?*I-1:1BilansSquares* ?*I-1:1BilansCircles* ?*I-1:1BilansTriangles*))

		(if (= ?idnum 1) then
			(printout t "I-1:1 difference - " ?*I-1:1Difference*  crlf)
		)
)

;------------------------------PLAYER ID=2------------------------------
;1-1
;bilans of (1st smallest node - 1st smallest neighbour)
(defrule II-1:1DifferenceValue
(declare (salience 9977))
    (player (ID ?idnum))
	=>
		(bind ?*II-1:1Difference* (+ ?*II-1:1BilansSquares* ?*II-1:1BilansCircles* ?*II-1:1BilansTriangles*))

		(if (= ?idnum 2) then
			(printout t "II-1:1 difference - " ?*II-1:1Difference*  crlf)
		)
)

;------------------------------PLAYER ID=1------------------------------
;1-2
;bilans of (1st largest node - 2nd largest neighbour)
(defrule I-1:2DifferenceValue
(declare (salience 9976))
    (player (ID ?idnum))
	=>
		(bind ?*I-1:2Difference* (+ ?*I-1:2BilansSquares* ?*I-1:2BilansCircles* ?*I-1:2BilansTriangles*))

		(if (= ?idnum 1) then
			(printout t "I-1:2 difference - " ?*I-1:2Difference*  crlf)
		)
)

;------------------------------PLAYER ID=2------------------------------
;1-2
;bilans of (1st smallest node - 2nd smallest neighbour)
(defrule II-1:2DifferenceValue
(declare (salience 9976))
    (player (ID ?idnum))
	=>
		(bind ?*II-1:2Difference* (+ ?*II-1:2BilansSquares* ?*II-1:2BilansCircles* ?*II-1:2BilansTriangles*))

		(if (= ?idnum 2) then
			(printout t "II-1:2 difference - " ?*II-1:2Difference*  crlf)
		)
)

;------------------------------PLAYER ID=1------------------------------
;2-1
;bilans of (2nd largest node - 1st largest neighbour)
(defrule I-2:1DifferenceValue
(declare (salience 9975))
    (player (ID ?idnum))
	=>
		(bind ?*I-2:1Difference* (+ ?*I-2:1BilansSquares* ?*I-2:1BilansCircles* ?*I-2:1BilansTriangles*))

		(if (= ?idnum 1) then
			(printout t "I-2:1 difference - " ?*I-2:1Difference*  crlf)
		)
)

;------------------------------PLAYER ID=2------------------------------
;2-1
;bilans of (2nd smallest node - 1st smallest neighbour)
(defrule II-2:1DifferenceValue
(declare (salience 9975))
    (player (ID ?idnum))
	=>
		(bind ?*II-2:1Difference* (+ ?*II-2:1BilansSquares* ?*II-2:1BilansCircles* ?*II-2:1BilansTriangles*))

		(if (= ?idnum 2) then
			(printout t "II-2:1 difference - " ?*II-2:1Difference*  crlf)
		)
)

;------------------------------PLAYER ID=1------------------------------
;2-2
;bilans of (2nd largest node - 2nd largest neighbour)
(defrule I-2:2DifferenceValue
(declare (salience 9974))
    (player (ID ?idnum))
	=>
		(bind ?*I-2:2Difference* (+ ?*I-2:2BilansSquares* ?*I-2:2BilansCircles* ?*I-2:2BilansTriangles*))

		(if (= ?idnum 1) then
			(printout t "I-2:2 difference - " ?*I-2:2Difference*  crlf)
		)
)

;------------------------------PLAYER ID=2------------------------------
;2-2
;bilans of (2nd smallest node - 2nd smallest neighbour)
(defrule II-2:2DifferenceValue
(declare (salience 9974))
    (player (ID ?idnum))
	=>
		(bind ?*II-2:2Difference* (+ ?*II-2:2BilansSquares* ?*II-2:2BilansCircles* ?*II-2:2BilansTriangles*))

		(if (= ?idnum 2) then
			(printout t "II-2:2 difference - " ?*II-2:2Difference*  crlf)
		)
)

;------------------------------BOTH PLAYERS------------------------------
;setting start & target node and units for the action
(defrule choosePath
(declare (salience 9973))
	(player (ID ?idnum))
	=>
		(if (= ?idnum 1) then
			(if (> ?*I-1:1Difference* 0) then (bind ?*startNum* ?*1stLargestNodeId*)(bind ?*targetNum* ?*1stLargestNodeId-1stLargestNeighboor*)(bind ?*squares* ?*I-1Squares*)(bind ?*circles* ?*I-1Circles*)(bind ?*triangles* ?*I-1Triangles*) 
			else
			(if (> ?*I-1:2Difference* 0) then (bind ?*startNum* ?*1stLargestNodeId*)(bind ?*targetNum* ?*1stLargestNodeId-2ndLargestNeighboor*)(bind ?*squares* ?*I-1Squares*)(bind ?*circles* ?*I-1Circles*)(bind ?*triangles* ?*I-1Triangles*) 
			else
			(if (> ?*I-2:1Difference* 0) then (bind ?*startNum* ?*2ndLargestNodeId*)(bind ?*targetNum* ?*2ndLargestNodeId-1stLargestNeighboor*)(bind ?*squares* ?*I-2Squares*)(bind ?*circles* ?*I-2Circles*)(bind ?*triangles* ?*I-2Triangles*) 
			else
			(if (> ?*I-2:2Difference* 0) then (bind ?*startNum* ?*2ndLargestNodeId*)(bind ?*targetNum* ?*2ndLargestNodeId-2ndLargestNeighboor*)(bind ?*squares* ?*I-2Squares*)(bind ?*circles* ?*I-2Circles*)(bind ?*triangles* ?*I-2Triangles*) 
			))))
		else
			(if (> ?*II-1:1Difference* 0) then (bind ?*startNum* ?*1stSmallestNodeId*)(bind ?*targetNum* ?*1stSmallestNodeId-1stSmallestNeighboor*)(bind ?*squares* ?*II-1Squares*)(bind ?*circles* ?*II-1Circles*)(bind ?*triangles* ?*II-1Triangles*) 
			else
			(if (> ?*II-1:2Difference* 0) then (bind ?*startNum* ?*1stSmallestNodeId*)(bind ?*targetNum* ?*1stSmallestNodeId-2ndSmallestNeighboor*)(bind ?*squares* ?*II-1Squares*)(bind ?*circles* ?*II-1Circles*)(bind ?*triangles* ?*II-1Triangles*) 
			else
			(if (> ?*II-2:1Difference* 0) then (bind ?*startNum* ?*2ndSmallestNodeId*)(bind ?*targetNum* ?*2ndSmallestNodeId-1stSmallestNeighboor*)(bind ?*squares* ?*II-1Squares*)(bind ?*circles* ?*II-1Circles*)(bind ?*triangles* ?*II-1Triangles*) 
			else
			(if (> ?*II-2:2Difference* 0) then (bind ?*startNum* ?*2ndSmallestNodeId*)(bind ?*targetNum* ?*2ndSmallestNodeId-2ndSmallestNeighboor*)(bind ?*squares* ?*II-2Squares*)(bind ?*circles* ?*II-2Circles*)(bind ?*triangles* ?*II-2Triangles*) 
			))))
		)
		(printout t "start node: " ?*startNum* crlf)
		(printout t "target node: " ?*targetNum* crlf)
)

;setting string value for chosen start node
(defrule startString
(declare (salience 9972))
	=>
        (if (= ?*startNum* 0) then (bind ?*start* "00"))
        (if (= ?*startNum* 1) then (bind ?*start* "01"))
        (if (= ?*startNum* 2) then (bind ?*start* "02"))
        (if (= ?*startNum* 3) then (bind ?*start* "03"))
        (if (= ?*startNum* 4) then (bind ?*start* "04"))
        (if (= ?*startNum* 5) then (bind ?*start* "05"))
        (if (= ?*startNum* 6) then (bind ?*start* "06"))
        (if (= ?*startNum* 7) then (bind ?*start* "07"))
        (if (= ?*startNum* 8) then (bind ?*start* "08"))
        (if (= ?*startNum* 9) then (bind ?*start* "09"))
        (if (= ?*startNum* 10) then (bind ?*start* "10"))
        (if (= ?*startNum* 11) then (bind ?*start* "11"))
        (if (= ?*startNum* 12) then (bind ?*start* "12"))
        (if (= ?*startNum* 13) then (bind ?*start* "13"))
)

;setting string value for chosen target node
(defrule targetString
(declare (salience 9971))
	(myNodesId (idNumber ?number))
	=>
        (if (= ?*targetNum* 0) then (bind ?*target* "00"))
        (if (= ?*targetNum* 1) then (bind ?*target* "01"))
        (if (= ?*targetNum* 2) then (bind ?*target* "02"))
        (if (= ?*targetNum* 3) then (bind ?*target* "03"))
        (if (= ?*targetNum* 4) then (bind ?*target* "04"))
        (if (= ?*targetNum* 5) then (bind ?*target* "05"))
        (if (= ?*targetNum* 6) then (bind ?*target* "06"))
        (if (= ?*targetNum* 7) then (bind ?*target* "07"))
        (if (= ?*targetNum* 8) then (bind ?*target* "08"))
        (if (= ?*targetNum* 9) then (bind ?*target* "09"))
        (if (= ?*targetNum* 10) then (bind ?*target* "10"))
        (if (= ?*targetNum* 11) then (bind ?*target* "11"))
        (if (= ?*targetNum* 12) then (bind ?*target* "12"))
        (if (= ?*targetNum* 13) then (bind ?*target* "13"))

        (if (= ?*targetNum* ?number ) then (printout t "I cannot attack myself!" crlf)
            (bind ?*typeNum* 0)
        )
)

;constructing final variable
(defrule finalAction
(declare (salience 9970))
	(player (ID ?idnum))
	=>
		(if (= ?*typeNum* 0) then (bind ?*type* "00")
		else (bind ?*type* "01")
		)
    
		(if (= ?idnum 1) then
          (bind ?*global* (str-cat ?*start* ?*target* ?*type*))
          (printout t "Action chosen: " ?*global* crlf)
		else
          (bind ?*global* (str-cat ?*start* ?*target* ?*type*))
          (printout t "Action chosen: " ?*global* crlf)
		)      
)