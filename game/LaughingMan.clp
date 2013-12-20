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
	(multislot availableAdjacentNodes)
)


//"array" of all nodes
(deftemplate graph
	(multislot nodes)
)

//variable used to return selected action to game
(defglobal ?*global* = "")

(defglobal ?*squares* = 0 )
(defglobal ?*circles* = 0)
(defglobal ?*triangles* = 0)

(defglobal ?*enemyId* = 0)
(defglobal ?*myNodes* = 0)
(defglobal ?*HQ* = 0)


//FUNCTIONS================================================================================================
(deffunction battleRusult (?myNode ?enemyNode ?sq ?cir ?tra ?esq ?ecir ?etra)
	
	(bind ?aSq ?sq)
	(bind ?aCir ?cir)
	(bind ?aTra ?tra)
	
	(bind ?dSq ?esq)
	(bind ?dCir ?ecir)
	(bind ?dTra ?etra)
	
	//battle result
	//1st
	//squares
	(if (> ?dSq 0 ) then
		(if (> ?aSq ?dSq) then
			(bind ?aSq (- ?aSq ?dSq))
			(bind ?dSq 0)
		else
			(bind ?dSq (- ?dSq ?aSq))
			(bind ?aSq 0)
		)
	)
	//circles
	(if (> ?dCir 0) then
		(if (> ?aCir ?dCir) then
			(bind ?aCir (- ?aCir ?dCir))
			(bind ?dCir 0)
		else
			(bind ?dCir (- ?dCir ?aCir))
			(bind ?aCir 0))					
	)		
	//triangles
	(if (> ?dTra 0) then
		(if (> ?aTra ?dTra) then
			(bind ?aTra (- ?aTra ?dTra))
			(bind ?dTra 0)
		else
			(bind ?dTra (- ?dTra ?aTra))
			(bind ?aTra 0)		
		)			
	)	
	//2nd
	//squares
	(if (> ?dTra 0) then
		(if (> ?aSq ?dTra) then
			(bind ?aSq (- ?aSq ?dTra))
			(bind ?dTra 0)
		else
			(bind ?dTra (- ?dTra ?aSq))
			(bind ?aSq 0)	
		)
	)
	//circles
	(if (> ?dSq 0) then
		(if (> ?aCir ?dSq) then
			(bind ?aCir (- ?aCir ?dSq))
			(bind ?dSq 0)
		else
			(bind ?dSq (- ?dSq ?aCir))
			(bind ?aCir 0)		
		)
	)		
	//triangles
	(if (> ?dCir 0) then
		(if (> ?aTra ?dCir) then
			(bind ?aTra (- ?aTra ?dCir))
			(bind ?dCir 0)
		else
			(bind ?dCir (- ?dCir ?aTra))
			(bind ?aTra 0)	
		)
	)
	//3rd
	//squares
	(if (> ?dCir 0) then
		(if (> ?aSq ?dCir) then
			(bind ?aSq (- ?aSq ?dCir))
			(bind ?dCir 0)
		else
			(bind ?dCir (- ?dCir ?aSq))
			(bind ?aSq 0)					
		)
	)
	//circles
	(if (> ?dTra 0) then
		(if (> ?aCir ?dTra) then
			(bind ?aCir (- ?aCir ?dTra))
			(bind ?dTra 0)
		else
			(bind ?dTra (- ?dTra ?aCir))
			(bind ?aCir 0)
		)
	)		
	//triangles
	(if (> ?dSq 0) then
		(if (> ?aTra ?dSq) then
			(bind ?aTra (- ?aTra ?dSq))
			(bind ?dSq 0)
		else
			(bind ?dSq (- ?dSq ?aTra))
			(bind ?aTra 0)	
		)
	)
	
	(if (> ?aSq 0) then 
		(assert (win ?myNode ?enemyNode))
	)
	(if (> ?aCir 0) then 
		(assert (win ?myNode ?enemyNode))
	)
	(if (> ?aTra 0) then 
		(assert (win ?myNode ?enemyNode))
	)
)

(deffunction optimal (?sq ?cir ?tra ?esq ?ecir ?etra)
	
	(bind ?allSq ?sq)
	(bind ?allCir ?cir)
	(bind ?allTra ?tra)
	
	(bind ?aSq ?sq)
	(bind ?aCir ?cir)
	(bind ?aTra ?tra)
	
	(bind ?dSq ?esq)
	(bind ?dCir ?ecir)
	(bind ?dTra ?etra)
	
    (bind ?best 0)
	(bind ?isItBetter 0)
	
	(bind ?optimalSq ?aSq)
	(bind ?optimalCir ?aCir)
	(bind ?optimalTra ?aTra)
	
	(bind ?curSq 0)
	(bind ?curTra 0)
	(bind ?curCir 0)

	(bind ?fcasSq ?aSq)
	(bind ?fcasTra ?aCir)
	(bind ?fcasCir ?aTra)
	
	(bind ?curcasSq 0)
	(bind ?curcasCir 0)
	(bind ?curcasTra 0)
	
	(bind ?ecasSq 0)
	(bind ?ecasCir 0)
	(bind ?ecasTra 0)
	
	(bind ?ecurcasSq 0)
	(bind ?ecurcasCir 0)
	(bind ?ecurcasTra 0)
	
	(bind ?squares 0)
	(bind ?circles 0)
	(bind ?triangles 0)
	
		(if (or (>= ?dSq 1) (>= ?dCir 1) (>= ?dTra 1)) then
			(loop-for-count (?triangles 0 ?aTra) do
				(loop-for-count (?circles 0 ?aCir) do
					(loop-for-count (?squares 0 ?aSq) do
						(bind ?curSq ?squares)
						(bind ?curCir ?circles)
						(bind ?curTra ?triangles)
						
						//battle result
						//1st
						//squares
						(if (> ?dSq 0 ) then
							(if (> ?aSq ?dSq) then
								(bind ?aSq (- ?aSq ?dSq))
								(bind ?dSq 0)
							else
								(bind ?dSq (- ?dSq ?aSq))
								(bind ?aSq 0)
							)
						)
						//circles
						(if (> ?dCir 0) then
							(if (> ?aCir ?dCir) then
								(bind ?aCir (- ?aCir ?dCir))
								(bind ?dCir 0)
							else
								(bind ?dCir (- ?dCir ?aCir))
								(bind ?aCir 0))					
						)		
						//triangles
						(if (> ?dTra 0) then
							(if (> ?aTra ?dTra) then
								(bind ?aTra (- ?aTra ?dTra))
								(bind ?dTra 0)
							else
								(bind ?dTra (- ?dTra ?aTra))
								(bind ?aTra 0)		
							)			
						)	
						//2nd
						//squares
						(if (> ?dTra 0) then
							(if (> ?aSq ?dTra) then
								(bind ?aSq (- ?aSq ?dTra))
								(bind ?dTra 0)
							else
								(bind ?dTra (- ?dTra ?aSq))
								(bind ?aSq 0)	
							)
						)
						//circles
						(if (> ?dSq 0) then
							(if (> ?aCir ?dSq) then
								(bind ?aCir (- ?aCir ?dSq))
								(bind ?dSq 0)
							else
								(bind ?dSq (- ?dSq ?aCir))
								(bind ?aCir 0)		
							)
						)		
						//triangles
						(if (> ?dCir 0) then
							(if (> ?aTra ?dCir) then
								(bind ?aTra (- ?aTra ?dCir))
								(bind ?dCir 0)
							else
								(bind ?dCir (- ?dCir ?aTra))
								(bind ?aTra 0)	
							)
						)
						//3rd
						//squares
						(if (> ?dCir 0) then
							(if (> ?aSq ?dCir) then
								(bind ?aSq (- ?aSq ?dCir))
								(bind ?dCir 0)
							else
								(bind ?dCir (- ?dCir ?aSq))
								(bind ?aSq 0)					
							)
						)
						//circles
						(if (> ?dTra 0) then
							(if (> ?aCir ?dTra) then
								(bind ?aCir (- ?aCir ?dTra))
								(bind ?dTra 0)
							else
								(bind ?dTra (- ?dTra ?aCir))
								(bind ?aCir 0)
							)
						)		
						//triangles
						(if (> ?dSq 0) then
							(if (> ?aTra ?dSq) then
								(bind ?aTra (- ?aTra ?dSq))
								(bind ?dSq 0)
							else
								(bind ?dSq (- ?dSq ?aTra))
								(bind ?aTra 0)	
							)
						)

						(bind ?curcasSq (- ?sq ?aSq ))
						(bind ?curcasCir (- ?cir ?aCir))
						(bind ?curcasTra (- ?tra ?aTra))
						
						(bind ?ecurcasSq (- ?esq ?dSq))
						(bind ?ecurcasCir (- ?ecir ?dCir))
						(bind ?ecurcasTra (- ?etra ?dTra))
						
						(if (> ?curSq 10) then
							(bind ?isItBetter (+ ?isItBetter 1))
						else
							(bind ?isItBetter (- ?isItBetter 1))
						)
						(if (> ?curCir 10) then
							(bind ?isItBetter (+ ?isItBetter 1))
						else
							(bind ?isItBetter (- ?isItBetter 1))
						)
						(if (> ?curTra 10) then
							(bind ?isItBetter (+ ?isItBetter 1))
						else
							(bind ?isItBetter (- ?isItBetter 1))
						)
						
						(if (< ?curcasSq ?fcasSq) then
							(bind ?isItBetter (+ ?isItBetter 1))
						else
							(bind ?isItBetter (- ?isItBetter 1))
						)
						(if (< ?curcasCir ?fcasCir) then
							(bind ?isItBetter (+ ?isItBetter 1))
						else
							(bind ?isItBetter (- ?isItBetter 1))
						)
						(if (< ?curcasTra ?fcasTra) then
							(bind ?isItBetter (+ ?isItBetter 1))
						else
							(bind ?isItBetter (- ?isItBetter 1))
						)
						
						(if (> ?ecurcasSq ?ecasSq) then
							(bind ?isItBetter (+ ?isItBetter 1))
						else
							(bind ?isItBetter (- ?isItBetter 1))
						)
						(if (> ?ecurcasCir ?ecasCir) then
							(bind ?isItBetter (+ ?isItBetter 1))
						else
							(bind ?isItBetter (- ?isItBetter 1))
						)
						(if (> ?ecurcasTra ?ecasTra) then
							(bind ?isItBetter (+ ?isItBetter 1))
						else
							(bind ?isItBetter (- ?isItBetter 1))
						)
						
						if(or (> ?aSq 0) (> ?aCir 0) (> ?aTra 0) then
							(bind ?isItBetter (+ ?isItBetter 4))
						else
							(bind ?isItBetter (- ?isItBetter 4))
						)
						
						(if(> ?isItBetter ?best) then
							(bind ?best ?isItBetter)
							(bind ?optimalSq ?curSq)
							(bind ?optimalCir ?curCir)
							(bind ?optimalTra ?curTra)
						)
						(bind ?isItBetter 0)
					)
				)	
			)
		else
			(if (and (= ?optimalSq 0)(= ?optimalCir 0)(= ?optimalTra 0))then
				(if( >= ?sq 1) then (bind ?optimalSq (integer (+ (* ?sq 0.3) 1)))
				else
					(if (>= ?cir 1) then (bind ?optimalCir (integer (+ (* ?cir 0.2) 1)))
					else
						(if (>= ?tra 1) then (bind ?optimalTra (integer (+ (* ?tra 0.1) 1))))
					)
				)
			)
		)
		
	(if (> ?optimalSq ?sq) then (bind ?optimalSq ?sq))
	(if (> ?optimalCir ?cir) then (bind ?optimalCir ?cir))
	(if (> ?optimalTra ?tra) then (bind ?optimaltra ?tra))
		
	(bind ?*squares* 	?optimalSq)
	(bind ?*circles* 	?optimalCir)
	(bind ?*triangles* 	?optimalTra)
)


(deffunction glue (?type ?id ?id2)
	(if (= ?type 1) then
		(if (and (< ?id 10) (< ?id2 10)) then
			(bind ?*global* (str-cat "0" ?id "0" ?id2 "01") )
		else
			(if (and (< ?id 10) (>= ?id2 10)) then
				(bind ?*global* (str-cat "0" ?id ?id2 "01") )
			else
				(if (and (>= ?id 10) (< ?id2 10)) then
					(bind ?*global* (str-cat ?id "0" ?id2 "01") )
				else
					(if (and (>= ?id 10) (>= ?id2 10)) then
					(bind ?*global* (str-cat ?id ?id2 "01") )
					)
				)
			)
		)
	else
		(if (and (< ?id 10) (< ?id2 10)) then
			(bind ?*global* (str-cat "0" ?id "0" ?id2 "00") )
		else
			(if (and (< ?id 10) (>= ?id2 10)) then
				(bind ?*global* (str-cat "0" ?id ?id2 "00") )
			else
				(if (and (>= ?id 10) (< ?id2 10)) then
					(bind ?*global* (str-cat ?id "0" ?id2 "00") )
				else
					(if (and (>= ?id 10) (>= ?id2 10)) then
					(bind ?*global* (str-cat ?id ?id2 "00") )
					)
				)
			)
		)
	)
)

//=======================================================================================================


//GENERAL RULES===================================================================================================
(defrule infoMyNodes
(declare (salience 998))
	(player (ID ?idnum))
	=>
		(if (< ?*myNodes* 2) then
			(printout t "I have " ?*myNodes* " node!" crlf)
		else (printout t "I have " ?*myNodes* " nodes!" crlf))
		
		(printout  t "Agent ID is " ?idnum crlf)
		
		(if (= ?idnum 1) then
			(bind ?*HQ* 0) 
			(bind ?*enemyId* 2)
		else 
			(bind ?*HQ* 13) 
			(bind ?*enemyId* 1)
		)

)

(defrule countMyNodes
(declare (salience 999))
	(node (belongsTo ?owner))(player (ID ?idnum))
	(test (= ?owner ?idnum))
	=>
		(bind ?*myNodes* (+ ?*myNodes* 1)) 
)

//which node are mine?
(defrule isNodeMine
(declare (salience 1000))
	(node (belongsTo ?idnum) (idNumber ?number)) (player (ID ?idnum))
	=>
	(assert (myNode ?number))
)

//which are not?
(defrule isItEnemyNode
(declare (salience 1000))
	(node (belongsTo ?owner) (idNumber ?number)) (player (ID ?idnum))
	(test (!= ?owner ?idnum))
	=>
	(assert (enemyNode ?number))
)

//are enough units in there?
(defrule enoughAttackSquares
(declare (salience 1000))
(myNode ?num)(node (idNumber ?num) (squares ?sq) (circles ?cir) (triangles ?tra))
(test (> ?sq 15))
=>
(assert (enoughAttack ?num))
)

(defrule enoughAttackCircles
(declare (salience 1000))
(myNode ?num)(node (idNumber ?num) (squares ?sq) (circles ?cir) (triangles ?tra))
(test (> ?cir 10))
=>
(assert (enoughAttack ?num))
)

(defrule enoughAttackTriangles
(declare (salience 1000))
(myNode ?num)(node (idNumber ?num) (squares ?sq) (circles ?cir) (triangles ?tra))
(test (> ?tra 5))
=>
(assert (enoughAttack ?num))
)

(defrule enoughMoveSquares
(declare (salience 1000))
(myNode ?num)(node (idNumber ?num) (squares ?sq) (circles ?cir) (triangles ?tra))
(test (> ?sq 15))
=>
(assert (enoughMove ?num))
)

(defrule enoughMoveCircles
(declare (salience 1000))
(myNode ?num)(node (idNumber ?num) (squares ?sq) (circles ?cir) (triangles ?tra))
(test (> ?cir 10))
=>
(assert (enoughMove ?num))
)

(defrule enoughMoveTriangles
(declare (salience 1000))
(myNode ?num)(node (idNumber ?num) (squares ?sq) (circles ?cir) (triangles ?tra))
(test (> ?tra 5))
=>
(assert (enoughMove ?num))
)
//what connections are ok?
(defrule connections
(declare (salience 1000))
(myNode ?num)(node (idNumber ?num)(availableAdjacentNodes ?a ?b ?c ?d ?e ))
	=>
	(if (!= ?a -1) then (assert ( connection ?num ?a)))
	(if (!= ?b -1) then (assert ( connection ?num ?b)))
	(if (!= ?b -1) then (assert ( connection ?num ?c)))
	(if (!= ?d -1) then (assert ( connection ?num ?d)))
	(if (!= ?e -1) then (assert ( connection ?num ?e)))
)

(defrule firstLine
(declare (salience 900))
(myNode 1)(myNode 2)(myNode 3)
=>
	(if (= ?*HQ* 0) then 
		(assert (firstLine owned))
	)
)

(defrule firstLine1
(declare (salience 900))
(myNode 10)(myNode 11)(myNode 12)
=>
	(if (= ?*HQ* 13) then 
		(assert (firstLine owned))	
	)
)

(defrule secondLine
(declare (salience 900))
(myNode 4)(myNode 5)(myNode 6)
=>
	(if (= ?*HQ* 0) then 
		(assert (secondLine owned))
	)
)
(defrule secondLine2
(declare (salience 900))
(myNode 7)(myNode 8)(myNode 9)
=>
	(if (= ?*HQ* 13) then 
		(assert (secondLine owned))
	)
)

(defrule thirdLine
(declare (salience 900))
(myNode 7)(myNode 8)(myNode 9)
=>
	(if (= ?*HQ* 0) then 
		(assert (thirdLine owned))
	)
)
(defrule thirdLine2
(declare (salience 900))
(myNode 4)(myNode 5)(myNode 6)
=>

	(if (= ?*HQ* 13) then 
		(assert (thirdLine owned))
	)
)

(defrule enemyHQinRange
(myNode ?myId)(enemyNode ?eId)(connection ?myId ?eId) 
=>
(if (= ?*HQ* 0) then 
	(if (= ?eId 13) then
		(assert (enemyHQ ?myId ?eId))
	)
) 
(if (= ?*HQ* 13) then 
	(if (= ?eId 0) then
		(assert (enemyHQ ?myId ?eId))
	)
) 

)

(defrule strongAttack
(myNode ?myId)(enemyNode ?eId)(enoughAttack ?myId)(connection ?myId ?eId) 
=>
	(if (= ?*HQ* 0) then 
		(if (< ?eId 7) then 
			(assert (strongAttack ?myId ?eId))
		)
	)

	(if (= ?*HQ* 13) then 
		(if (> ?eId 6) then 
			(assert (strongAttack ?myId ?eId))
		)
	)
)

(defrule canIwin
(declare (salience 800))
(myNode ?myId)(enemyNode ?eId)(enoughAttack ?myId)(connection ?myId ?eId) 
(node (idNumber ?myId) (squares ?sq)(circles ?cir)(triangles ?tra))
(node (idNumber ?eId) (squares ?esq)(circles ?ecir)(triangles ?etra))
=>
(battleRusult ?myId ?eId ?sq ?cir ?tra ?esq ?ecir ?etra)
)

(defrule moveForward
(myNode ?myId)(myNode ?myId2)(enoughMove ?myId)(connection ?myId ?myId2)
=>
(if (= ?*HQ* 0) then 
	(if ( < ?myId ?myId2) then
		(assert (moveForward ?myId ?myId2))
	)
)
(if (= ?*HQ* 13) then 
	(if ( > ?myId ?myId2) then
		(assert (moveForward ?myId ?myId2))
	)
)
)
//=======================================================================================================

//ATTACK RULE===========================================================================================
//bidirectional
(defrule OptimalAttack
(declare (salience 50))
(myNode ?myId)(enemyNode ?eId)(enoughAttack ?myId)(connection ?myId ?eId) (win ?myId ?eId)
(node (idNumber ?myId) (squares ?sq)(circles ?cir)(triangles ?tra))
(node (idNumber ?eId) (squares ?esq)(circles ?ecir)(triangles ?etra))
=>
;(printout t "Optimal attack from " ?myId " to " ?eId crlf)

;(printout t "available units " ?sq " " ?cir " " ?tra crlf)

(glue 1 ?myId ?eId)
(optimal ?sq ?cir ?tra ?esq ?ecir ?etra)
)

(defrule StrongAttack
(declare (salience 45))
(myNode ?myId)(enemyNode ?eId)(enoughAttack ?myId)(connection ?myId ?eId) (win ?myId ?eId) (strongAttack ?myId ?eId)
(node (idNumber ?myId) (squares ?sq)(circles ?cir)(triangles ?tra))
(node (idNumber ?eId) (squares ?esq)(circles ?ecir)(triangles ?etra))
=>
;(printout t "Strong attack from " ?myId " to " ?eId crlf)

;(printout t "available units " ?sq " " ?cir " " ?tra crlf)

(glue 1 ?myId ?eId)
(optimal ?sq ?cir ?tra ?esq ?ecir ?etra)
(bind ?*squares* (integer (* ?sq 0.4)))
(bind ?*circles* (integer (* ?cir 0.4)))
(bind ?*triangles* (integer (* ?tra 0.4)))
)

(defrule AllOutAttack
(declare (salience 1))
(myNode ?myId)(enemyNode ?eId)(enoughAttack ?myId)(connection ?myId ?eId) (win ?myId ?eId) (enemyHQ ?myId ?eId)
(node (idNumber ?myId) (squares ?sq)(circles ?cir)(triangles ?tra))
(node (idNumber ?eId) (squares ?esq)(circles ?ecir)(triangles ?etra))
=>
;(printout t "All out attack from " ?myId " to " ?eId crlf)

;(printout t "available units " ?sq " " ?cir " " ?tra crlf)

(glue 1 ?myId ?eId)
(bind ?*squares* ?sq )
(bind ?*circles* ?cir)
(bind ?*triangles* ?tra)
)
//=============================================================================================

//MOVE RULE====================================================================================
//bidirectional
(defrule move
(declare (salience 100))
(myNode ?myId)(myNode ?myId2)(enoughMove ?myId)(connection ?myId ?myId2) (moveForward ?myId ?myId2)
(node (idNumber ?myId) (squares ?sq)(circles ?cir)(triangles ?tra))
(node (idNumber ?myId2) (squares ?sq2)(circles ?cir2)(triangles ?tra2))
=>
;(printout t "Move from " ?myId " to " ?myId2 crlf)

;(printout t "available units " ?sq " " ?cir " " ?tra crlf)

(glue 0 ?myId ?myId2)

(bind ?*squares* ?sq )
(bind ?*circles* ?cir)
(bind ?*triangles* ?tra)

)
//=============================================================================================




















