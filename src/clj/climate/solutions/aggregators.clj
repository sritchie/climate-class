(ns climate.solutions.aggregators
  (:use [cascalog api playground]
        [clojure.string :only (join)])
  (:require [clojure.string :as s]))

;; Distinct count:

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
      (distinct-count* !val :> !count)))
