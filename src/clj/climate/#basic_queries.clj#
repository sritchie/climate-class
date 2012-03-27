(ns climate.basic-queries
  (:use cascalog.api
        cascalog.playground)
  (:require [cascalog.ops :as c]))

;; Here are some basic queries to get you all started. These make use
;; of Cascalog's playground dataset, located in the Cascalog codebase
;; at `cascalog.playground`.
;;
;; This first query returns all people from the age dataset under 30:

(def people-under-30
  (<- [?person]
      (age ?person ?age) ;; <-- generator
      (< ?age 30)))      ;; <-- filter

;; This query returns the same, this time pairing users with age:

(def under-30-with-age
  (<- [?person ?age]
      (age ?person ?age) ;; <-- generator
      (< ?age 30)))      ;; <-- filter

;; The second predicate introduces a constraint that ?n must equal its
;; square. This is only true for 0 and 1:

(def square-equals-self
  (<- [?n]
      (integer ?n)      ;; <-- generator
      (* ?n ?n :> ?n))) ;; <-- operation

;; This query constrains ?n to equal its own cube:

(def cubed-equals-self
  (<- [?n]
      (integer ?n)         ;; <-- generator
      (* ?n ?n ?n :> ?n))) ;; <-- operation

;; This query returns all users following someone younger.

(def follows-younger
  (<- [?person1 ?person2]
      (age ?person1 ?age1)        ;; <-- generator
      (follows ?person1 ?person2) ;; <-- generator
      (age ?person2 ?age2)        ;; <-- generator
      (< ?age2 ?age1)))           ;; <-- filter

;; This query makes use of an aggregator, and returns the count of all
;; users under 30.

(def people-under-30-count
  (<- [?count]
      (age _ ?age)       ;; <-- generator
      (< ?age 30)        ;; <-- operation
      (c/count ?count))) ;; <-- aggregator

;; ## Aggregation Implementations

(defn always-one [] 1)

(defparallelagg our-count
  "Parallel \"count\" aggregator. Converts tuple in its input group
  into a 1, adds all 1s together in parallel."
  :init-var    #'always-one
  :combine-var #'+)

(defparallelagg our-sum
  "Parallel \"sum\" aggregator, meant to act on numbers. Adds all
  numbers in its group together."
  :init-var    #'identity
  :combine-var #'+)

;; aggregate-op implementation of distinct-count.

(defaggregateop distinct-count*
  ([] [nil 0])
  ([[prev count] val]
     [val (if (= prev val)
            count
            (inc count))])
  ([[recent count]] [count]))

(def distinct-count
  (<- [!val :> !count]
      (:sort !val)
      (distinct-count* !num :> !sum)))

;; ### Defbufferop discussion
;;
;; The following points are a bit subtle, and involve a behavior of
;; debufferop that's similar to defmapcatop.

(def test-src
  [[1 2]
   [1 3]
   [1 4]
   [2 1]])

(defbufferop tuples->str-1
  "Returns a sequence of string tuple fields."
  [tuple-seq]
  (map str tuple-seq))

(defn exec-to-str []
  (?<- (stdout)
       [?x ?str]
       (test-src ?x ?y)
       (tuples->str-1 ?y :> ?str)))

;; RESULTS
;; -----------------------
;; 1	(2)
;; 1	(3)
;; 1	(4)
;; 2	(1)
;; -----------------------

(defbufferop tuples->str-2
  "Returns a single tuple with one field that contains a sequence."
  [tuple-seq]
  [[(map str tuple-seq)]])

(defn exec-to-str2 []
  (?<- (stdout)
       [?x ?str]
       (test-src ?x ?y)
       (tuples->str-2 ?y :> ?str)))

;; RESULTS
;; -----------------------
;; 1	("(2)" "(3)" "(4)")
;; 2	("(1)")
;; -----------------------

(def test-tap
  [["a" 1] 
   ["b" 2]
   ["a" 3]])

;; defbufferop's meant to return something sort of like
;; defmapcatop. 

(defbufferop dosum [tuples]
  [(reduce + (map first tuples))])

(defn show-sum []
  (?<- (stdout)
       [?a ?sum]
       (test-tap ?a ?b)
       (dosum ?b :> ?sum)))

;; RESULTS
;; -----------------------
;; a	4
;; b	2
;; -----------------------

(defn run-distincter []
  (let [src [[1 2] [2 2] [1 3] [3 2]]]
    (?<- (stdout)
         [?count]
         (src ?x ?y)
         (c/count-distinct ?x :> ?count)
         (:sort ?x))))

;; RESULTS
;; -----------------------
;; 3
;; -----------------------

;; ## Back to our aggregators

(def our-avg
  (<- [?x :> ?avg]
      (our-sum ?x :> ?sum)
      (our-count ?count)
      (/ ?sum ?count :> ?avg)))

;; Using our average.

(defn our-avg
  (let [src [[1] [2] [1]]]
    (?<- (stdout)
         [?avg]
         (src ?x)
         (our-avg ?x :> ?avg))))

;; Custom aggregator for fires objects, represented here with Clojure
;; maps.

(def fire-src
  [["a" {:count 1
         :above-conf 1
         :above-temp 1
         :both-preds 1}]

   ["a" {:count 3
         :above-conf 1
         :above-temp 0
         :both-preds 0}]])

(defn combine-fires* [m1 m2]
  (merge-with + m1 m2))

(defparallelagg combine-fires
  :init-var    #'identity
  :combine-var #'combine-fires*)

(?<- (stdout)
     [?letter ?combined]
     (fire-src ?letter ?fire)
     (combine-fires ?fire :> ?combined))

(defparallelagg our-max
  :init-var    #'identity
  :combine-var #'max)

(defparallelagg our-min
  :init-var    #'identity
  :combine-var #'min)

(def people-under-30-count
  (<- [?count]
      (age _ ?age)       ;; <-- generator
      (< ?age 30)        ;; <-- operation
      (our-count ?count))) ;; <-- aggregator

(def follows-count
  (<- [?person ?count]
      (follows ?person _) ;; <-- generator
      (c/count ?count)))  ;; <-- aggregator

;; Word Counting

(defmapcatop split [sentence]
  (.split sentence "\\s+"))

(def wordcount-query
  (<- [?word ?count]
      (sentence ?sentence)       ;; <-- generator
      (split ?sentence :> ?word) ;; <-- operation
      (c/count ?count)))         ;; <-- aggregator

(def a-follows-b
  (let [many-follows (<- [?person]
                         (follows ?person _) ;; <-- generator
                         (c/count ?count)    ;; <-- aggregator
                         (> ?count 2))]      ;; <-- filter
    (<- [?person1 ?person2]
        (many-follows ?person1)        ;; <-- generator
        (many-follows ?person2)        ;; <-- generator
        (follows ?person1 ?person2)))) ;; <-- generator

(def inner-join
  (<- [?person ?age ?gender]
      (age ?person ?age)         ;; <-- generator
      (gender ?person ?gender))) ;; <-- generator

;; very similar to outer-join:

(def outer-join
  (<- [?person !!age !!gender]
      (age ?person !!age)         ;; <-- generator
      (gender ?person !!gender))) ;; <-- generator

;; Aggregation examples with the Stock dataset.

(defn bundle [& xs]
  [(into [] xs)])

(def stock-src
  [["AA" 1]
   ["AA" 2]
   ["AA" 1]
   ["BB" 5]
   ["BB" 4]])

(<- [?avg]
    (stock-src ?stock-sym ?price)
    (c/avg ?price :> ?avg))

;; RESULTS
;; -----------------------
;; 2.6
;; -----------------------

(<- [?stock-sym ?avg]
    (stock-src ?stock-sym ?price)
    (c/avg ?price :> ?avg))

;; RESULTS
;; -----------------------
;; AA	1.3333333333333333
;; BB	4.5
;; -----------------------

(<- [?stock-sym ?avg ?max]
    (stock-src ?stock-sym ?price)
    (c/avg ?price :> ?avg)
    (c/max ?price :> ?max))

;; RESULTS
;; -----------------------
;; AA	1.3333333333333333	2
;; BB	4.5	5
;; -----------------------

(<- [?stock-sym ?price ?avg]
    (stock-src ?stock-sym ?price)
    (c/avg ?price :> ?avg))

;; RESULTS
;; -----------------------
;; AA	1	1.0
;; AA	2	2.0
;; BB	4	4.0
;; BB	5	5.0
;; -----------------------

(defn run-stock-query []
  (let [avg-q (<- [?stock-sym ?avg]
                  (stock-src ?stock-sym ?price)
                  (c/avg ?price :> ?avg))
        [[max-avg]] (??<- [?max-avg]
                          (avg-q ?stock-sym ?avg)
                          (c/max ?avg :> ?max-avg))]
    (?<- (stdout)
         [?stock-sym]
         (avg-q ?stock-sym max-avg)
         (:distinct false))))

;; RESULTS
;; -----------------------
;; BB
;; -----------------------
