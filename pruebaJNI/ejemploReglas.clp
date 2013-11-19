         CLIPS (Quicksilver Beta 3/26/08)
CLIPS> (defrule rule1 (hair s) (mammary s) (legs s) => (assert (mammal s)))
CLIPS> (defrule rule2 (mammal s) => (printout t "The animal is mammal" crlf))
CLIPS> (defrule rule3 (wings s) => (assert (bird s)))
CLIPS> (defrule rule4 (bird s) => (printout t "The animal is a bird" crlf))
CLIPS> 
