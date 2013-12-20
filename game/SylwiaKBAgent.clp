;This is a template on which you should build your knowledge agents
;You should create your agents on LOCAL COPY of this template!

(deftemplate player
	(slot ID (type INTEGER))
)

;datastructure holding data about a node
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

;"array" of all nodes
(deftemplate graph
	(multislot nodes)
)

;variable used to return selected action to game
(defglobal ?*global* = "") 
(defglobal ?*start* = "") 
(defglobal ?*target* = "") 

(defglobal ?*squares* = 0)
(defglobal ?*circles* = 0)
(defglobal ?*triangles* = 0)
(defglobal ?*value* = 0)
(defglobal ?*sum* = 0)
(defglobal ?*sq* = 0)
(defglobal ?*s* = 0)
(defglobal ?*cir* = 0)
(defglobal ?*c* = 0)
(defglobal ?*tri* = 0)
(defglobal ?*t* = 0)


;print result
;(defrule test
;(node (idNumber 0) (squares ?sq) (circles ?cir) (triangles ?tra))
;  => 
;  (printout t "Action chosen" crlf)
;
;   (bind ?*global* "000000") 
;  (bind ?*squares* ?sq) 
;   (bind ?*circles* (* ?cir 2)) 
;   (bind ?*triangles* (- ?tra 10)) 
;)
  
;example rules
(defrule currentAgent
	(declare (salience 10))
    (player (ID ?number))
=>
    (printout t "Agent ID is " ?number crlf)
)

(defrule nodes-belonging-to-player
    (declare (salience 4))
	(node (idNumber ?idNumber) (belongsTo ?belongsTo))
	(player (ID ?number))
	(test (= ?belongsTo ?number))
=>
	(assert (belongs-to ?number ?idNumber))
	(printout t "Node belonging to player " ?number ": " ?idNumber crlf)
)

(defrule find-available-nodes
    (declare (salience 3))
    (node (idNumber ?idNumber)
  	(availableAdjacentNodes ?n1 ?n2 ?n3 ?n4 ?n5)
  	(belongsTo ?belongsTo))
	(player (ID ?number))
	(test (= ?belongsTo ?number))
=>
	(assert (neighbor-of ?idNumber ?n1 ?n2 ?n3 ?n4 ?n5))
	(assert (is-neighbor ?idNumber ?n1))
	(assert (is-neighbor ?idNumber ?n2))
	(assert (is-neighbor ?idNumber ?n3))
	(assert (is-neighbor ?idNumber ?n4))
	(assert (is-neighbor ?idNumber ?n5))
	(printout t "Available from node " ?idNumber " are nodes " ?n1 ?n2 ?n3 ?n4 ?n5 crlf)
)

(defrule get-number-of-your-units
	(declare (salience 2))
    (node (idNumber ?idNumber)
  	(squares ?sq) (circles ?cir) (triangles ?tri))
  	(neighbor-of ?number $?neighbors)
	(test (= ?idNumber ?number))
=>
	(assert (has-units ?number ?sq ?cir ?tri))
	(printout t "Node " ?idNumber " has number of units: " ?sq " squares, " ?cir " circles, " ?tri " triangles" crlf)
)

(defrule get-number-of-enemy-units
	(declare (salience 1))
    (node (idNumber ?idNumber)
  	(squares ?sq) (circles ?cir) (triangles ?tri) (belongsTo ?belongsTo))
  	(neighbor-of ?number ?n1 ?n2 ?n3 ?n4 ?n5)
  	(player (ID ?playerId))
  	(test(not(= ?belongsTo ?playerId)))
	(test (or (= ?idNumber ?n1) (= ?idNumber ?n2) (= ?idNumber ?n3) (= ?idNumber ?n4) (= ?idNumber ?n5)))
=>
	(assert (enemy-has-units ?idNumber ?sq ?cir ?tri))
	(printout t "Enemy's node " ?idNumber " has number of units: " ?sq " squares, " ?cir " circles, " ?tri " triangles" crlf)
)

(defrule check-attack-pos
   	(has-units ?start ?sq ?cir ?tri)
   	(enemy-has-units ?target ?s ?c ?t)
   	(is-neighbor ?start ?target)
   	(test (>= (- ?sq ?s) 0))
   	(test (>= (- ?cir ?c) 0))
   	(test (>= (- ?tri ?t) 0))
=>
	(bind ?*value* (+ ?*value* 1))
	(assert (attack-possible ?start ?target ?sq ?s ?cir ?c ?tri ?t))
	(printout t "Attack " ?*value* " possible from node " ?start " to node " ?target " with advantage of units: " (- ?sq ?s) " squares, " (- ?cir ?c) " circles, " (- ?tri ?t) " triangles" crlf)
)

(defrule choose-attack
	(attack-possible ?start ?target ?sq ?s ?cir ?c ?tri ?t)
	(test(> (+ (- ?sq ?s) (- ?cir ?c) (- ?tri ?t)) ?*sum*))
=>
	(bind ?*sum* (+ (- ?sq ?s) (- ?cir ?c) (- ?tri ?t))) 
	(bind ?*start* ?start)
	(bind ?*target* ?target)
	(printout t "CHOSEN! From node " ?start " to node " ?target " with advantage of units: " (- ?sq ?s) " squares, " (- ?cir ?c) " circles, " (- ?tri ?t) " triangles" crlf)
	(bind ?*squares* (+ (integer(/ (- ?sq ?s) 1.7)) ?s)) 
    (bind ?*circles* (+ (integer(/ (- ?cir ?c) 1.7)) ?c)) 
    (bind ?*triangles* (+ (integer(/ (- ?tri ?t) 1.7)) ?t))
)

(defrule create-start-target
	(declare (salience -4))
	(chosen-one ?start ?target)
	(test(< ?start 10))
	(test(< ?target 10))
=>
	(bind ?*global* (str-cat "0" ?start "0" ?target "01"))
	(printout t ?*global* crlf)
)

(defrule create-start-target-2
	(declare (salience -4))
	(chosen-one ?start ?target)
	(test(> ?start 9))
	(test(> ?target 9))
=>
	(bind ?*global* (str-cat ?start ?target "01"))
	(printout t ?*global* crlf)
)

(defrule create-start-target-3
	(declare (salience -4))
	(chosen-one ?start ?target)
	(test(< ?start 10))
	(test(> ?target 9))
=>
	(bind ?*global* (str-cat "0" ?start ?target "01"))
	(printout t ?*global* crlf)
)

(defrule create-start-target-4
	(declare (salience -4))
	(chosen-one ?start ?target)
	(test(> ?start 9))
	(test(< ?target 10))
=>
	(bind ?*global* (str-cat ?start "0" ?target "01"))
	(printout t ?*global* crlf)
)

(defrule attack-chosen
	(declare (salience -3))
=>
	(printout t "DEFINATELY CHOSEN! From node " ?*start* " to node " ?*target* " with number of units: " ?*squares* " squares, " ?*circles* " circles, " ?*triangles* " triangles" crlf)
	(assert (chosen-one ?*start* ?*target*))
)
