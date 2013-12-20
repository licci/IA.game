
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
(defglobal ?*global* = "000000") 

(defglobal ?*stringtest* = "boo")
(defglobal ?*startNod* = 0)
(defglobal ?*targetNod* = 0)
(defglobal ?*attackmove* = 0)
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



   

  
 


 //example rules
(defglobal ?*aa* = 0)
(defglobal ?*bb* = 0)
(defglobal ?*cc* = 0)
(defglobal ?*dd* = 0)
(defglobal ?*ee* = 0)
(defglobal ?*attack* = 0)
(defglobal ?*tempsq* = 0)
(defglobal ?*tempcir* = 0)
(defglobal ?*temptra* = 0)
(defglobal ?*possibleattack* = 0)
//////////////////////////////////////Variables for fight
(defglobal ?*Ssq* = 0)
(defglobal ?*Scir* = 0)
(defglobal ?*Stra* = 0)
(defglobal ?*Ssq2* = 0)
(defglobal ?*Scir2* = 0)
(defglobal ?*Stra2* = 0)
(defglobal ?*HQ1* = 0)
(defglobal ?*HQ2* = 13)
////////////////////////////////////////////// Functions /////////////////////////////////////////////////////////

(deffunction createOrder (?start ?target ?action)
  (if (> 10 ?start) then (bind ?start (str-cat "0" ?start))) 
  (if (> 10 ?target) then (bind ?target (str-cat "0" ?target))) 
(bind ?*global* (str-cat ?start ?target ?action)) 
)


(deffunction minAttack (?defenders)

(+ ?defenders 2)

)

(deffunction sumUnits (?a ?b ?c)

(+ ?a (+ ?b ?c))

)

(deffunction differenceUnits (?a ?b)
(- ?a ?b)
)

(deffunction checkSucces(?sq ?cir ?tra ?sq2 ?cir2 ?tra2)

  (bind ?*Ssq* ?sq)
  (bind ?*Scir* ?cir)
  (bind ?*Stra* ?tra)
  (bind ?*Ssq2* ?sq2)
  (bind ?*Scir2* ?cir2)
  (bind ?*Stra2* ?tra2)
  (printout t "Test attack: Attacker" ?*Ssq* " " ?*Scir* " " ?*Stra* " Defenders: "  ?*Ssq2* " " ?*Scir2* " " ?*Stra2* crlf )
  //////////// I Attack PHASE /////////
 (if (> ?*Ssq* ?*Ssq2*) then (bind ?*Ssq* (- ?*Ssq* ?*Ssq2*)) (bind ?*Ssq2* 0) else (bind ?*Ssq2* (- ?*Ssq2* ?*Ssq*)) (bind ?*Ssq* 0))
 (if (> ?*Scir* ?*Scir2*) then (bind ?*Scir* (- ?*Scir* ?*Scir2*)) (bind ?*Scir2* 0) else (bind ?*Scir2* (- ?*Scir2* ?*Scir*)) (bind ?*Scir* 0))
(if (> ?*Stra* ?*Stra2*) then (bind ?*Stra* (- ?*Stra* ?*Stra2*)) (bind ?*Stra2* 0) else (bind ?*Stra2* (- ?*Stra2* ?*Stra*)) (bind ?*Stra* 0))
(printout t "Test attack after I phase!: Attacker: " ?*Ssq* " " ?*Scir* " " ?*Stra* " Defenders: "  ?*Ssq2* " " ?*Scir2* " " ?*Stra2* crlf)
 //////////// II Attack PHASE/////////
 (if (> ?*Stra2* 0) then 
 (if (> ?*Ssq* (*  0.5 ?*Stra2*)) then (bind ?*Ssq* (- ?*Ssq* (* ?*Stra2* 0.5))) (bind ?*Stra2* 0) else (bind ?*Stra2* (- ?*Stra2* (* ?*Ssq* 2)))  (bind ?*Ssq* 0))
 )
 
 
(if (> ?*Ssq2* 0) then 
 (if (> ?*Scir* (*  0.5 ?*Ssq2*)) then (bind ?*Scir* (- ?*Scir* (* ?*Ssq2* 0.5))) (bind ?*Ssq2* 0) else (bind ?*Ssq2* (- ?*Ssq2* (* ?*Scir* 2)))  (bind ?*Scir* 0))
 )
 
 
 (if (> ?*Scir2* 0) then 
 (if (> ?*Stra* (*  0.5 ?*Scir2*)) then (bind ?*Stra* (- ?*Stra* (* ?*Scir2* 0.5))) (bind ?*Scir2* 0) else (bind ?*Scir2* (- ?*Scir2* (* ?*Stra* 2)))  (bind ?*Stra* 0))
 )
 
 (printout t "Test Attack phase after II phase!: Attacker: " ?*Ssq* " " ?*Scir* " " ?*Stra* " Defenders: "  ?*Ssq2* " " ?*Scir2* " " ?*Stra2* crlf)
 
 
 //////////////////////  III Attack PHASE //////
 
 (if (> ?*Scir2* 0) then 
 (if (> ?*Ssq* (* 2 ?*Scir2*)) then (bind ?*Ssq* (- ?*Ssq* (* ?*Scir2* 2))) (bind ?*Scir2* 0) else (bind ?*Scir2* (- ?*Scir2* (* ?*Ssq* 0.5))) (bind ?*Ssq* 0))
 )
 
 
  (if (> ?*Stra2* 0) then 
 (if (> ?*Scir* (* 2 ?*Stra2*)) then (bind ?*Scir* (- ?*Scir* (* ?*Stra2* 2))) (bind ?*Stra2* 0) else (bind ?*Stra2* (- ?*Stra2* (* ?*Scir* 0.5))) (bind ?*Scir* 0))
 )
 (if (> ?*Ssq2* 0) then 
 (if (> ?*Stra* (* 2 ?*Ssq2*)) then (bind ?*Stra* (- ?*Stra* (* ?*Ssq2* 2))) (bind ?*Ssq2* 0) else (bind ?*Ssq2* (- ?*Ssq2* (* ?*Stra* 0.5))) (bind ?*Stra* 0))
 )
 (printout t "Test attack after III phase!: Attacker: " ?*Ssq* " " ?*Scir* " " ?*Stra* " Defenders: "  ?*Ssq2* " " ?*Scir2* " " ?*Stra2* crlf)
 
 
 
 (if (> (sumUnits ?*Ssq* ?*Scir* ?*Stra*) (sumUnits ?*Ssq2* ?*Scir2* ?*Stra2*) ) then (+ 0 1) else (+ 0 0))
 
)

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////    RULES           ////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 (defrule worthMoving
  (player (ID ?id))
  (node (belongsTo ?id) (idNumber ?number)(squares ?sq) (circles ?cir) (triangles ?tra)(availableAdjacentNodes ?a ?b ?c ?d ?e))
  =>
   (if (> ?sq 10) then (assert (canmove ?number)))
   (if (> ?cir 10) then (assert (canmove ?number)))
   (if (> ?tra 10) then (assert (canmove ?number)))
  
 )


 (defrule checkConnection
(declare (salience 1000))
   (player (ID ?id))
 (node (belongsTo ?id) (idNumber ?number)(squares ?sq) (circles ?cir) (triangles ?tra)(availableAdjacentNodes ?a ?b ?c ?d ?e))
 =>
 
 
         (if (!= ?a -1) then (assert (connection ?number ?a)))
       (if (!= ?b -1) then (assert (connection ?number ?b)))
       (if (!= ?c -1) then (assert (connection ?number ?c)))
       (if (!= ?d -1) then (assert (connection ?number ?d)))
       (if (!= ?e -1) then (assert (connection ?number ?e)))
 )


 (defrule printConnection
  (connection ?a ?b)
  (player (ID ?id))

 (node (belongsTo ?id) (idNumber ?a)(squares ?sq) (circles ?cir) (triangles ?tra)) 
 (node (belongsTo ?id2) (idNumber ?b)(squares ?sq2) (circles ?cir2) (triangles ?tra2)) 
 =>

  (printout t "From node " ?a " we have got connection to " ?b "  "crlf)

  (if (!= ?id ?id2) then (printout t "Id" ?id " iddrugi" ?id2 "  ")
  (if (> (sumUnits ?sq ?cir ?tra)(+ 10 (sumUnits ?sq2 ?cir2 ?tra2))) then (assert (attackPossible ?a ?b)) else 
  
  
  (bind ?*tempsq* 0)
  (bind ?*tempcir* 0)
  (bind ?*temptra* 0)
  (if(> (differenceUnits ?sq2 ?sq) 0) then (bind ?*tempsq* (differenceUnits ?sq2 ?sq)))
  (if(> (differenceUnits ?cir2 ?cir) 0) then (bind ?*tempcir* (differenceUnits ?cir2 ?cir)))
  (if(> (differenceUnits ?tra2 ?tra) 0) then (bind ?*temptra* (differenceUnits ?tra2 ?tra)))
  
  (assert (attackImpossible ?a ?*tempsq* ?*tempcir* (bind ?*temptra* 0)))
  
  
  
  
  ))
 
 )
 

 (defrule needSUPPORT
 (attackImpossible ?a)
 => (printout t "Support need node : " ?a crlf)
 
 )
 

 
 (defrule givingSupport
 (declare (salience 800))
 (attackImpossible ?number2 ?sq2 ?cir2 ?tra2)
 (canmove ?number) (connection ?number ?number2) (not  (attackPossible ?a ?b))
 (node (belongsTo ?id) (idNumber ?number)(squares ?sq) (circles ?cir) (triangles ?tra))
 (node (belongsTo ?id) (idNumber ?number2))
 =>
   (createOrder ?number ?number2 "00") 
            
			(printout t "Support needed: " ?sq2 " " ?cir2 " " ?tra2 crlf)
			(printout t "Units of supporter : " ?sq " " ?cir " " ?tra crlf)
			( if (> ?sq (+ ?sq2 5)) then (bind ?*squares* (+ ?sq2 5)) else (bind ?*squares* ?sq) )
			( if (> ?cir (+ ?cir2 5)) then (bind ?*circles* (+ ?cir2 5)) else (bind ?*circles* ?cir) )
			( if (> ?tra (+ ?tra2 5)) then (bind ?*triangles* (+ ?tra2 5)) else (bind ?*triangles* ?tra) )
			
     
 )
 
////////////////////////////////////////////////////// If any Attack is possible, and will end up with success, then attack!////////////////////////
 (defrule attackNode
 (declare (salience 1))
 (attackPossible ?a ?b)
 (node (idNumber ?a)(squares ?sq) (circles ?cir) (triangles ?tra))
 (node (idNumber ?b)(squares ?sq2) (circles ?cir2) (triangles ?tra2))
 =>
 (if (> (checkSucces ?sq ?cir ?tra ?sq2 ?cir2 ?tra2) 0) then  (printout t "Attack will be succesfull!"  crlf) else (printout t "Atak will not be succesfull"  crlf) )
 
 (bind ?*possibleattack* 0)
 
 (if (> ?sq (+ ?sq2 2)) then (if (> ?cir (+ ?cir2 2)) then (if (> ?tra (+ ?tra2 2)) then 
 (createOrder ?a ?b "01")
 (bind ?*squares* (minAttack ?sq2))
 (bind ?*circles* (minAttack ?cir2))
 (bind ?*triangles* (minAttack ?tra2))
 (bind ?*possibleattack* 1)
 )))
 
 (if (= ?*possibleattack* 0) then (if (> (checkSucces ?sq ?cir ?tra ?sq2 ?cir2 ?tra2) 0) then 
  (createOrder ?a ?b "01")
  (bind ?*squares* ?sq)
  (bind ?*circles* ?cir)
  (bind ?*triangles* ?tra)
 ))
			
            
 )
 
 
 ///////////////////////////////////////////////////// Need to defend the  HQ! //////////////
 
 (defrule defendingHQ
 (player (ID ?id))
  (node (belongsTo ?id)(idNumber ?num)(squares ?sq) (circles ?cir) (triangles ?tra))
 (node (belongsTo ?id2)(idNumber ?num2)) 
 =>
 (if (= ?id 1) then
	(if (= ?id2 2) then 
	(if (= ?num2 1) then (createOrder ?num ?num2 "01")(bind ?*squares* ?sq)(bind ?*circles* ?cir)(bind ?*triangles* ?tra))
	(if (= ?num2 2) then (createOrder ?num ?num2 "01")(bind ?*squares* ?sq)(bind ?*circles* ?cir)(bind ?*triangles* ?tra))
	(if (= ?num2 3) then (createOrder ?num ?num2 "01")(bind ?*squares* ?sq)(bind ?*circles* ?cir)(bind ?*triangles* ?tra))
 ))
 
 (if (= ?id 2) then
	(if (= ?id2 1) then 
	(if (= ?num2 10) then (createOrder ?num ?num2 "01")(bind ?*squares* ?sq)(bind ?*circles* ?cir)(bind ?*triangles* ?tra))
	(if (= ?num2 11) then (createOrder ?num ?num2 "01")(bind ?*squares* ?sq)(bind ?*circles* ?cir)(bind ?*triangles* ?tra))
	(if (= ?num2 12) then (createOrder ?num ?num2 "01")(bind ?*squares* ?sq)(bind ?*circles* ?cir)(bind ?*triangles* ?tra))
 ))
 )
 
 
  (defrule attackingHQ
 (player (ID ?id))
  (node (belongsTo ?id)(idNumber ?num)(squares ?sq) (circles ?cir) (triangles ?tra))
 (node (belongsTo ?id2)(idNumber ?num2)) 
 =>
 (if (= ?id 1) then
	(if (= ?id2 2) then 
	(if (= ?num 10) then (createOrder ?num ?*HQ2* "01")(bind ?*squares* ?sq)(bind ?*circles* ?cir)(bind ?*triangles* ?tra))
	(if (= ?num 11) then (createOrder ?num ?*HQ2* "01")(bind ?*squares* ?sq)(bind ?*circles* ?cir)(bind ?*triangles* ?tra))
	(if (= ?num 12) then (createOrder ?num ?*HQ2* "01")(bind ?*squares* ?sq)(bind ?*circles* ?cir)(bind ?*triangles* ?tra))
 ))
 
 (if (= ?id 2) then
	(if (= ?id2 1) then 
	(if (= ?num 1) then (createOrder ?num ?*HQ1*  "01")(bind ?*squares* ?sq)(bind ?*circles* ?cir)(bind ?*triangles* ?tra))
	(if (= ?num 2) then (createOrder ?num ?*HQ1* "01")(bind ?*squares* ?sq)(bind ?*circles* ?cir)(bind ?*triangles* ?tra))
	(if (= ?num 3) then (createOrder ?num ?*HQ1* "01")(bind ?*squares* ?sq)(bind ?*circles* ?cir)(bind ?*triangles* ?tra))
 ))
 )