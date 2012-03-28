(ns climate.basic-queries
  (:use cascalog.api
        cascalog.playground)
  (:require [cascalog.ops :as c]))


;; Here are some basic queries to get you all started. These make use
;; of Cascalog's playground dataset, located in the Cascalog codebase
;; at `cascalog.playground`.
;;
;; This first query returns all people from the age dataset under 30:



;; generator

;; [
;;  ["mike" 24]
;;  ]

;; (defn square [x]
;;   (* x x))

;; ;; (<operation> :< input-vars :> output-vars)


;; (def src
;;   [[1 2]
;;    [3 4]
;;    [5 nil]
;;    [nil 6]])

;; ;; <-  -- makes a query
;; ;; ?-  -- execs a query

;; (defn another-query [path]
;;   (let [src (hfs-textline path)]
;;     (<- [?txt]
;;         (src ?txt))))

;; (defn mk-query
;;   "Accepts a source that produces a single field with a line of text,
;;   returns 2-tuples of the form ?word, ?count."
;;   [src]
;;   {:pre [(= 1 (num-fields src))]}
;;   (<- [?word ?count]
;;       (src ?txt)
;;       ;;
;;       (split ?txt :> ?word)
;;       (c/count ?count)
;;       ;;

;;       (wordcount ?txt :> ?word ?count)
      
;;       ))

;; (q-apply (c/comp #'dec #'square #'inc)
;;          (select-fields generator ["?c" "?b" "?a"]))

;; (def composed
;;   (comp inc dec square))

;; (defn comp [f1 f2]
;;   (fn [& args]
;;     (f2 (apply f1 args))))

;; (defn mk-query [thrift-op]
;;   (let [op (c/comp #'recreate-thrift
;;                    thrift-op
;;                    #'unpack-thrift)]
;;     (<- [?new-sequence]
;;         (src ?thrift-sequence)
;;         (var-op ?thrift-sequence :> ?new-sequence))))

;; (c/comp #'halve
;;         (c/comp #'dec #'square #'inc))

;; (defn square [x] (* x x))

;; (def square
;;   (fn [x] (* x x)))

;; (??- some-query)


;; (io/with-fs-tmp [_ staging-path]
;;   (?- (hfs-seqfile staging-path)
;;       my-query)

;;   (?- (hfs-seqfile final-path)
;;       (hfs-seqfile staging-path)))

;; (require '[cascalog.io :as io])

;; (comment
;;   (let [base-path "/output/"]
;;     (loop [iter   0
;;            tap (hfs-seqfile (str base-path iter)
;;                             :sinkmode :replace)
;;            params [1 2 3]]
;;       (?- tap some-query)
;;       (let [subquery (c/first-n tap 200
;;                                 :sort ["?beta"]
;;                                 :reverse true)
;;             result-tuples (??- subquery)]
;;         (if (converged? result-tuples)
;;           tap
;;           (recur (inc iter)
;;                  (hfs-seqfile (str base-path (inc iter))
;;                               :sinkmode :replace)
;;                  (new-params params result-tuples)))))))

;; (def inc-square
;;   (<- [?x :> ?y]
;;       (inc ?x :> ?z)
;;       (square ?z :> ?y)))

;; (let [op (c/comp #'dec #'square #'inc)]
;;   (<- [?result]
;;       (src ?x ?y ?z)
;;       (op ?x ?y ?z :> ?result)))

;; ;; produce a new subquery

;; (<- [?x :> ?result]
;;     (inc ?x :> ?y)
;;     (square ?y :> ?z)
;;     (dec ?z :> ?result))

;; (let [op (c/comp #'square #'inc)]
;;   (<- [?result]
;;       (integer ?x)
;;       (inc ?x :> ?y)
;;       (square ?y :> ?result)))

;; (def wordcount
;;   (<- [?txt :> ?word ?count]
;;       (split ?txt :> ?word)
;;       (c/count ?count)))

;; (let [src (-> (hfs-seqfile "some-path")
;;               (select-fields ["?txt"])
;;               (mk-query))])

;; (let [src (select-fields src ["?txt"])]
;;   (mk-query src))

;; (fact
;;   (mk-query :path) => (produces [["abe" 2]])
;;   (provided
;;     (another-query :path) => [["abe abe"]]    ))

;; (let [src (hfs-textline "a")
;;       src-fn (fn [] (hfs-textline "a"))]
;;   (?- (hfs-seqfile "/tmp1")
;;       (mk-query1 (src-fn))

;;       (hfs-seqfile "/tmp2")
;;       (mk-query2 (src-fn))

;;       (hfs-seqfile "/tmp3")
;;       (mk-query3 (src-fn))))
;; (def query
;;   (<- [?a ?b]
;;       (src ?a ?b)))

;; (def people-under-30
;;   (<- [?person]
;;       (age ?person ?age) ;; <-- generator
;;       (< ?age 30)))      ;; <-- filter



;; ;; This query returns the same, this time pairing users with age:

;; (def under-30-with-age
;;   (<- [?person ?age]
;;       (age ?person ?age) ;; <-- generator
;;       (< ?age 30)))      ;; <-- filter

;; ;; The second predicate introduces a constraint that ?n must equal its
;; ;; square. This is only true for 0 and 1:

(require '[cascalog.vars :as v])

(defn square-equals-self [equal-val]
  (let [var     (v/gen-nullable-var)
        invars  ["?n" "?n"]
        outvars ["?n"]]
    (<- [?n]
        (integer :> ?n)
        (* :< ?n ?n :> ?n)
        (* :<< invars :>> outvars))))

(defn square-equals-self [equal-val]
  (construct ["?n"]
             [[integer "?n"]
              [#'* "?n" "?n" :> equal-val]]))

;; ;; This query constrains ?n to equal its own cube:

;; (def cubed-equals-self
;;   (<- [?n]
;;       (integer ?n)         ;; <-- generator
;;       (* ?n ?n ?n :> ?n))) ;; <-- operation

;; ;; This query returns all users following someone younger.

;; (def follows-younger
;;   (<- [?person1 ?person2]
;;       (age ?person1 ?age1)        ;; <-- generator
;;       (follows ?person1 ?person2) ;; <-- generator
;;       (age ?person2 ?age2)        ;; <-- generator
;;       (< ?age2 ?age1)))           ;; <-- filter

;; ;; This query makes use of an aggregator, and returns the count of all
;; ;; users under 30.

;; (def people-under-30-count
;;   (<- [?count]
;;       (age _ ?age)       ;; <-- generator
;;       (< ?age 30)        ;; <-- operation
;;       (c/count ?count))) ;; <-- aggregator

;; ;; ## Aggregation Implementations

;; (defn always-one [] 1)

;; (defparallelagg our-count
;;   "Parallel \"count\" aggregator. Converts tuple in its input group
;;   into a 1, adds all 1s together in parallel."
;;   :init-var    #'always-one
;;   :combine-var #'+)

;; (defparallelagg our-sum
;;   "Parallel \"sum\" aggregator, meant to act on numbers. Adds all
;;   numbers in its group together."
;;   :init-var    #'identity
;;   :combine-var #'+)

;; ;; aggregate-op implementation of distinct-count.

;; (defaggregateop distinct-count*
;;   ([] [nil 0])
;;   ([[prev count] val]
;;      [val (if (= prev val)
;;             count
;;             (inc count))])
;;   ([[recent count]] [count]))

;; (def distinct-count
;;   (<- [!val :> !count]
;;       (:sort !val)
;;       (distinct-count* !num :> !sum)))

;; ;; ### Defbufferop discussion
;; ;;
;; ;; The following points are a bit subtle, and involve a behavior of
;; ;; debufferop that's similar to defmapcatop.

;; (def test-src
;;   [[1 2]
;;    [1 3]
;;    [1 4]
;;    [2 1]])

;; (defbufferop tuples->str-1
;;   "Returns a sequence of string tuple fields."
;;   [tuple-seq]
;;   (map str tuple-seq))

;; (defn exec-to-str []
;;   (?<- (stdout)
;;        [?x ?str]
;;        (test-src ?x ?y)
;;        (tuples->str-1 ?y :> ?str)))

;; ;; RESULTS
;; ;; -----------------------
;; ;; 1	(2)
;; ;; 1	(3)
;; ;; 1	(4)
;; ;; 2	(1)
;; ;; -----------------------

;; (defbufferop tuples->str-2
;;   "Returns a single tuple with one field that contains a sequence."
;;   [tuple-seq]
;;   [[(map str tuple-seq)]])

;; (defn exec-to-str2 []
;;   (?<- (stdout)
;;        [?x ?str]
;;        (test-src ?x ?y)
;;        (tuples->str-2 ?y :> ?str)))

;; ;; RESULTS
;; ;; -----------------------
;; ;; 1	("(2)" "(3)" "(4)")
;; ;; 2	("(1)")
;; ;; -----------------------

;; (def test-tap
;;   [["a" 1] 
;;    ["b" 2]
;;    ["a" 3]])

;; ;; defbufferop's meant to return something sort of like
;; ;; defmapcatop. 

;; (defbufferop dosum [tuples]
;;   [(reduce + (map first tuples))])

;; (defn show-sum []
;;   (?<- (stdout)
;;        [?a ?sum]
;;        (test-tap ?a ?b)
;;        (dosum ?b :> ?sum)))

;; ;; RESULTS
;; ;; -----------------------
;; ;; a	4
;; ;; b	2
;; ;; -----------------------

;; (defn run-distincter []
;;   (let [src [[1 2] [2 2] [1 3] [3 2]]]
;;     (?<- (stdout)
;;          [?count]
;;          (src ?x ?y)
;;          (c/count-distinct ?x :> ?count)
;;          (:sort ?x))))

;; ;; RESULTS
;; ;; -----------------------
;; ;; 3
;; ;; -----------------------

;; ;; ## Back to our aggregators

;; (def our-avg
;;   (<- [?x :> ?avg]
;;       (our-sum ?x :> ?sum)
;;       (our-count ?count)
;;       (/ ?sum ?count :> ?avg)))

;; ;; Using our average.

;; (defn our-avg
;;   (let [src [[1] [2] [1]]]
;;     (?<- (stdout)
;;          [?avg]
;;          (src ?x)
;;          (our-avg ?x :> ?avg))))

;; ;; Custom aggregator for fires objects, represented here with Clojure
;; ;; maps.

;; (def fire-src
;;   [["a" {:count 1
;;          :above-conf 1
;;          :above-temp 1
;;          :both-preds 1}]

;;    ["a" {:count 3
;;          :above-conf 1
;;          :above-temp 0
;;          :both-preds 0}]])

;; (defn combine-fires* [m1 m2]
;;   (merge-with + m1 m2))

;; (defparallelagg combine-fires
;;   :init-var    #'identity
;;   :combine-var #'combine-fires*)

;; (?<- (stdout)
;;      [?letter ?combined]
;;      (fire-src ?letter ?fire)
;;      (combine-fires ?fire :> ?combined))

;; (defparallelagg our-max
;;   :init-var    #'identity
;;   :combine-var #'max)

;; (defparallelagg our-min
;;   :init-var    #'identity
;;   :combine-var #'min)

;; (def people-under-30-count
;;   (<- [?count]
;;       (age _ ?age)       ;; <-- generator
;;       (< ?age 30)        ;; <-- operation
;;       (our-count ?count))) ;; <-- aggregator

;; (def follows-count
;;   (<- [?person ?count]
;;       (follows ?person _) ;; <-- generator
;;       (c/count ?count)))  ;; <-- aggregator

;; ;; Word Counting

;; (defmapcatop split [sentence]
;;   (.split sentence "\\s+"))

;; (def wordcount-query
;;   (<- [?word ?count]
;;       (sentence ?sentence)       ;; <-- generator
;;       (split ?sentence :> ?word) ;; <-- operation
;;       (c/count ?count)))         ;; <-- aggregator

;; (def a-follows-b
;;   (let [many-follows (<- [?person]
;;                          (follows ?person _) ;; <-- generator
;;                          (c/count ?count)    ;; <-- aggregator
;;                          (> ?count 2))]      ;; <-- filter
;;     (<- [?person1 ?person2]
;;         (many-follows ?person1)        ;; <-- generator
;;         (many-follows ?person2)        ;; <-- generator
;;         (follows ?person1 ?person2)))) ;; <-- generator

;; (def inner-join
;;   (<- [?person ?age ?gender]
;;       (age ?person ?age)         ;; <-- generator
;;       (gender ?person ?gender))) ;; <-- generator

;; ;; very similar to outer-join:

(def inner-join
  (<- [?person ?age ?gender]
      (age ?person ?age)         ;; <-- generator
      (gender ?person ?gender))) ;; <-- generator

(defn default [gender]
  (or gender "male"))

(def outer-join
  (<- [?person !!age]
      (age ?person !!age)
      (gender ?person !!gender)))

(def src
  [[1 2]
   [3 nil]
   [4 5]])

(??<- [?a !b]
      (src ?a !b))

;; ;; Aggregation examples with the Stock dataset.

;; (defn bundle [& xs]
;;   [(into [] xs)])

;; (def stock-src
;;   [["AA" 1]
;;    ["AA" 2]
;;    ["AA" 1]
;;    ["BB" 5]
;;    ["BB" 4]])

;; (<- [?avg]
;;     (stock-src ?stock-sym ?price)
;;     (c/avg ?price :> ?avg))

;; ;; RESULTS
;; ;; -----------------------
;; ;; 2.6
;; ;; -----------------------

;; (<- [?stock-sym ?avg]
;;     (stock-src ?stock-sym ?price)
;;     (c/avg ?price :> ?avg))

;; ;; RESULTS
;; ;; -----------------------
;; ;; AA	1.3333333333333333
;; ;; BB	4.5
;; ;; -----------------------

;; (<- [?stock-sym ?avg ?max]
;;     (stock-src ?stock-sym ?price)
;;     (c/avg ?price :> ?avg)
;;     (c/max ?price :> ?max))

;; ;; RESULTS
;; ;; -----------------------
;; ;; AA	1.3333333333333333	2
;; ;; BB	4.5	5
;; ;; -----------------------

;; (<- [?stock-sym ?price ?avg]
;;     (stock-src ?stock-sym ?price)
;;     (c/avg ?price :> ?avg))

;; ;; RESULTS
;; ;; -----------------------
;; ;; AA	1	1.0
;; ;; AA	2	2.0
;; ;; BB	4	4.0
;; ;; BB	5	5.0
;; ;; -----------------------

;; (defn run-stock-query []
;;   (let [avg-q (<- [?stock-sym ?avg]
;;                   (stock-src ?stock-sym ?price)
;;                   (c/avg ?price :> ?avg))
;;         [[max-avg]] (??<- [?max-avg]
;;                           (avg-q ?stock-sym ?avg)
;;                           (c/max ?avg :> ?max-avg))]
;;     (?<- (stdout)
;;          [?stock-sym]
;;          (avg-q ?stock-sym max-avg)
;;          (:distinct false))))

;; ;; RESULTS
;; ;; -----------------------
;; ;; BB
;; ;; -----------------------



(my-aggregator 1 ) => return defaggregateop

(defaggregateop my-aggregator
  {:params [a b c]
   :prepare true}
  [flow-process]
  (let [conn  (open-database-connection! a)
        state (atom {})]
    (aggregator 
     (init [] ,,return initial state,,,)
     (aggregate [state field1 field2]
                ,,returns next state)
     (complete [state]
               ,,final return value,,,)
     )))

(defmapop my-aggregator2
  {:params [a b c]}
  [a b c]
  ,,,return value,,,)

(defaggregateop my-aggregator
  ([] [])
  ([state field1 field2])
  ([state] ))
