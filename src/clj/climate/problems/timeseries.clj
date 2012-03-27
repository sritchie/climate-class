(ns climate.problems.timeseries
  (:use cascalog.api
        climate.datastore))

;; ## Timeseries Generation
;;
;; ### Exercise 1:
;; 
;; The `timeseries-src` dataset in `climate.datastore` contains
;; 3-tuples of the form [region, time-period, value].
;;
;; Write a query (or series of queries) that accepts this source and
;; produces a single timeseries for each region. Represent a
;;timeseries as a map that looks like the following:

(def example-timeseries
  {:start-period 10
   :vals [0 1 2 3 2 1]})

;; ### Exercise 2:
;;
;; Write the other side of this timeseries function; that is, consume
;; a source of timeseries and emit 3-tuples of the form [region,
;; time-period, value].
;;
;; ### Exercise 3:
;;
;; Write a function that accepts a query, a "time" variable and a
;;"value" variable and returns a NEW query with that value replaced by
;;a timeseries.

(defn series-query
  [q time-field val-field]
  ,,,)
