
//Structure for holding playerID
(deftemplate player
	(slot ID (type INTEGER))
)

//Datastructure holding data about a node
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

//Added methods for string concatenation
(defmethod + ((?a STRING) (?b STRING)) 
(str-cat ?a ?b))

//Variable used to return selected action to game
(defglobal ?*global* = "") 

//Usefull data to use with action selection
(defglobal ?*START* = "") 
(defglobal ?*STOP* = "") 
(defglobal ?*MOVE* = "00") 
(defglobal ?*ATTACK* = "01") 

//Usefull data to use with action selection
(defglobal ?*STARTINT* = 0) 
(defglobal ?*STOPINT* = 0) 
(defglobal ?*ACTIONINT* = 0) 

//Ammount of units in a node
(defglobal ?*squares* = 0)
(defglobal ?*circles* = 0)
(defglobal ?*triangles* = 0)

(defglobal ?*testing* = 0)
(defglobal ?*helpme* = 0)

//Stores ammount of my nodes
(defglobal ?*myNodes* = 0)

//Stores my base node number
(defglobal ?*HQ* = 0)

//Primitive function for testing attac sucess
(deffunction symulate(?sq ?cir ?tra ?esq ?ecir ?etra)
(printout t "I: " ?sq " " ?cir " "?tra " "   crlf)
(printout t "ENEMY: " ?esq " " ?ecir " "?etra " "   crlf)
(bind ?sum 0)
(bind ?sum (integer(+ ?sum ?sq)))
(bind ?sum (integer(+ ?sum ?cir)))
(bind ?sum (integer(+ ?sum ?tra)))
(printout t "SUM: " ?sum   crlf)

(bind ?esum 0)
(bind ?esum (integer(+ ?esum ?esq)))
(bind ?esum (integer(+ ?esum ?ecir)))
(bind ?esum (integer(+ ?esum ?etra)))
(printout t "ESUM: " ?esum   crlf)
(if(>= ?esum ?sum) then
(return 0)
)
(if(< ?esum ?sum) then
(return 1)
)
)


//Prints info about nodes and sets the number of player base
(defrule infoMyNodes
(declare (salience 3000))
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

//Assert my nodes
(defrule isNodeMine
(declare (salience 2000))
 (node (belongsTo ?idnum) (idNumber ?number)) (player (ID ?idnum))
 =>
 (assert (myNode ?number))
)

//Assert enemy nodes
(defrule isNodeEnemy
(declare (salience 1900))
 (player (ID ?idnum))(node (belongsTo ?owner&:(!= ?owner ?idnum)) (idNumber ?number)) 
 =>
 (assert (enemyNode ?number))
)

//Asserts connections
(defrule getconnections
(declare (salience 1800))
(myNode ?num)(node (idNumber ?num)(availableAdjacentNodes ?a ?b ?c ?d ?e ))
 =>
 (if (!= ?a -1) then (assert ( connection ?num ?a)))
 (if (!= ?b -1) then (assert ( connection ?num ?b)))
 (if (!= ?b -1) then (assert ( connection ?num ?c)))
 (if (!= ?d -1) then (assert ( connection ?num ?d)))
 (if (!= ?e -1) then (assert ( connection ?num ?e)))
)

//Assigns ammount of my nodes to ?*myNodes* global variable
(defrule countMyNodes
(declare (salience 1500))
	(player (ID ?idnum) )
	(node (belongsTo ?owner) (idNumber ?number))
	=>
			(if (= ?owner ?idnum ) then
				(bind ?*myNodes* (+ ?*myNodes* 1)) 
			) 
)

//Better my node printer
(defrule bprintMyNodes
(declare (salience 1400))
(myNode ?val)
=>
(printout t "My node is: " ?val crlf)
)

//Better enemy node printer
(defrule bprintEnemyNodes
(declare (salience 1300))
(enemyNode ?val)
=>
(printout t "Enemy node is: " ?val crlf)
)

(defrule initialTestingSet
(declare (salience 1250))
=>
(if (= ?*HQ* 0) then
(bind ?*testing* 0)
)
(if (= ?*HQ* 13) then
(bind ?*testing* 13)
)
)

//Attack selector
(defrule attacSet
(declare (salience 1200))
(enemyNode ?valEnemy) (myNode ?valMy)(connection ?a ?b)
(player (ID ?idnum))
(node  (belongsTo ?idnum)(idNumber ?anny) (squares ?sq) (circles ?cir) (triangles ?tra)) 
(node (belongsTo ?owner&:(!= ?owner ?idnum))(idNumber ?b) (squares ?esq) (circles ?ecir) (triangles ?etra))
=>
(if(= ?a ?valMy) then
	(if(= ?b ?valEnemy) then	
		(if (= ?*HQ* 0) then
			(if(< ?*testing* ?b) then
				(if(= (symulate ?sq ?cir ?tra ?esq ?ecir ?etra) 1) then
					(bind ?*helpme* 1)
					(printout t "DEBUG: " ?anny " " ?b crlf)
					(bind ?*testing* ?b)
					(printout t "Connection from: " ?a " to " ?b " exists" crlf)
					(bind ?*STARTINT* ?a)
					(printout t "Connection start: " ?*STARTINT* crlf)
					(bind ?*STOPINT* ?b)
					(printout t "Connection end: " ?*STOPINT* crlf)
					(bind ?*ACTIONINT* 1)
				)
			)
		)
		
		(if (= ?*HQ* 13) then
			(if(> ?*testing* ?b) then
				(if(= (symulate ?sq ?cir ?tra ?esq ?ecir ?etra) 1) then
					(bind ?*helpme* 1)
					(bind ?*testing* ?b)
					(printout t "Connection from: " ?a " to " ?b " exists" crlf)
					(bind ?*STARTINT* ?a)
					(printout t "Connection start: " ?*STARTINT* crlf)
					(bind ?*STOPINT* ?b)
					(printout t "Connection end: " ?*STOPINT* crlf)
					(bind ?*ACTIONINT* 1)
				)
			)
		)
	)
)
)

//If cannot attack move units
(defrule moveSet
(declare (salience 1000))
(myNode ?valMy) (myNode ?valMy2) (connection ?a ?b)
=>
(if(= ?*helpme* 0)then
	(if(!= ?valMy ?valMy2)then
		(if (= ?a ?valMy)then
			(if(= ?b ?valMy2)then
				(if (= ?*HQ* 0) then
					(if(< ?valMy ?valMy2)then
							(bind ?*STARTINT* ?valMy)
							(bind ?*STOPINT* ?valMy2)
							(bind ?*ACTIONINT* 0)
							(printout t "!!!!!!!!!!!! " ?valMy "  and  " ?valMy2 crlf)
							
					)
				)
				(if (= ?*HQ* 13) then
					(if(> ?valMy ?valMy2)then
							(bind ?*STARTINT* ?valMy)
							(bind ?*STOPINT* ?valMy2)
							(bind ?*ACTIONINT* 0)
							(printout t "!!!!!!!!!!!! " ?valMy "  and  " ?valMy2 crlf)
							
					)
				)
			)
		)
		
	)
)
)

//Big ugly way of seting the target
(defrule targetSelect
(declare (salience 70))
 => 
(printout t "FROM TARGET SELECTION: " ?*STARTINT* " and " ?*STOPINT* crlf)

(if (= ?*STARTINT* 0) then
(bind ?*START* (+ ?*START* "00"))
)
 
(if (= ?*STARTINT* 1) then
(bind ?*START* (+ ?*START* "01"))
)
(if (= ?*STARTINT* 2) then
(bind ?*START* (+ ?*START* "02"))
)
(if (= ?*STARTINT* 3) then
(bind ?*START* (+ ?*START* "03"))
)
(if (= ?*STARTINT* 4) then
(bind ?*START* (+ ?*START* "04"))
)
(if (= ?*STARTINT* 5) then
(bind ?*START* (+ ?*START* "05"))
)
(if (= ?*STARTINT* 6) then
(bind ?*START* (+ ?*START* "06"))
)
(if (= ?*STARTINT* 7) then
(bind ?*START* (+ ?*START* "07"))
)
(if (= ?*STARTINT* 8) then
(bind ?*START* (+ ?*START* "08"))
)
(if (= ?*STARTINT* 9) then
(bind ?*START* (+ ?*START* "09"))
)
(if (= ?*STARTINT* 10) then
(bind ?*START* (+ ?*START* "10"))
)
(if (= ?*STARTINT* 11) then
(bind ?*START* (+ ?*START* "11"))
)
(if (= ?*STARTINT* 12) then
(bind ?*START* (+ ?*START* "12"))
)
(if (= ?*STARTINT* 13) then
(bind ?*START* (+ ?*START* "13"))
)

(if (= ?*STOPINT* 0) then
(bind ?*STOP* (+ ?*STOP* "00"))
)
(if (= ?*STOPINT* 1) then
(bind ?*STOP* (+ ?*STOP* "01"))
)
(if (= ?*STOPINT* 2) then
(bind ?*STOP* (+ ?*STOP* "02"))
)
(if (= ?*STOPINT* 3) then
(bind ?*STOP* (+ ?*STOP* "03"))
)
(if (= ?*STOPINT* 4) then
(bind ?*STOP* (+ ?*STOP* "04"))
)
(if (= ?*STOPINT* 5) then
(bind ?*STOP* (+ ?*STOP* "05"))
)
(if (= ?*STOPINT* 6) then
(bind ?*STOP* (+ ?*STOP* "06"))
)
(if (= ?*STOPINT* 7) then
(bind ?*STOP* (+ ?*STOP* "07"))
)
(if (= ?*STOPINT* 8) then
(bind ?*STOP* (+ ?*STOP* "08"))
)
(if (= ?*STOPINT* 9) then
(bind ?*STOP* (+ ?*STOP* "09"))
)
(if (= ?*STOPINT* 10) then
(bind ?*STOP* (+ ?*STOP* "10"))
)
(if (= ?*STOPINT* 11) then
(bind ?*STOP* (+ ?*STOP* "11"))
)
(if (= ?*STOPINT* 12) then
(bind ?*STOP* (+ ?*STOP* "12"))
)
(if (= ?*STOPINT* 13) then
(bind ?*STOP* (+ ?*STOP* "13"))
)
)

//Performs a selected action
(defrule performAction
(declare (salience -1000))
(node (idNumber ?anny) (squares ?sq) (circles ?cir) (triangles ?tra))
  => 
	(if(= ?anny ?*STARTINT*) then
  (printout t "Action chosen!" crlf)
  (printout t "Start node: " ?anny crlf)
  (printout t "Start node: " ?*STARTINT* crlf)
  (bind ?*global* (+ ?*global* ?*START*)) 
  (bind ?*global* (+ ?*global* ?*STOP*)) 
  (if(= ?*ACTIONINT* 0) then
	(bind ?*global*(+ ?*global* ?*MOVE*))
  )
  (if (= ?*ACTIONINT* 1) then
	(bind ?*global*(+ ?*global* ?*ATTACK*))
  )
  (printout t "Command: " ?*global* crlf)
  (printout t "Start squares: " ?sq crlf)
  (bind ?*squares* ?sq) 
  (printout t "Start circles: " ?cir crlf)
  (bind ?*circles* ?cir)
  (printout t "Start triangles: " ?tra crlf)
  (bind ?*triangles* ?tra) 
  )
)