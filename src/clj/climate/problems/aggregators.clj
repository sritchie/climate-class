(ns climate.problems.aggregators
  (:use [cascalog api playground]
        [clojure.string :only (join)])
  (:require [clojure.string :as s]
            [cascalog.ops :as c]))

;; ### Challenge 1
;;
;; Using the `follows` data source from `cascalog.playground`,
;;generate a sequence of 2-tuples of the form
;;
;; ["username", "follower1,follower2,follower3"]
;;
;; The second field should be a comma-separated string listing each of
;; the user's followers.
;;
;; Try to write your aggregation all three ways:
;;
;; * defbufferop
;; * defaggregateop
;; * defparallelagg

;; ### Challenge 2
;;
;; This problem uses the `dirty-follower-counts` dataset in
;; cascalog.playground. The goal is to figure out the user's follower
;; count by choosing the most recent value out of all values.
;;
;; Implement a query that returns a sequence of tuples of the form:
;;
;; ["username" "follower-count"]
;;
;; Implement the aggregation using defaggregateop and defparallelagg.

;; ### Challenge 3:
;;
;; Implement "distinct-count". Distinct-count will take some value and
;; return a count of the set of all items that pass through. Example
;; usage:

(comment
  (let [src [["tim" "a"]
             ["tim" "a"]
             ["tim" "b"]
             ["sam" "a"]
             ["sam" "b"]]]
    (?<- (stdout)
         [?name ?count]
         (src ?name ?val)
         (distinct-count ?val :> ?count))))

;; RESULTS
;; -----------------------
;; sam	2
;; tim	2
;; -----------------------
