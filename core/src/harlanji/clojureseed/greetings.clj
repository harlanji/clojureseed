(ns harlanji.clojureseed.greetings)

; conclusion: clojure-stack does not work as a package name, but clojure_stack does. dashes in fn names are OK.

; last thing to check out be dash/underscore collisions.


;
;
;
;
; (defn main []
;   (println "Hai! hello:" (c/hello-again))
;   0)
;
; > Hai! hello: HERRO underscore!
;
;
;`(c/hello_again)` gives the same result.
;

(defn hello [] "Hello hello!")

(defn hello-again [] "HERRO dash!")


(defn hello_again [] "HERRO underscore!")

(def hellop "Hello hello!")

(def hellop-again "HERRO!")