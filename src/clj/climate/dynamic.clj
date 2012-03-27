(ns climate.dynamic
  (:use [cascalog api playground]))

;; ## Dynamically creating functions:

(defmapop double-val [val]
  (* 2 val))

(defmapop double-and-triple [val]
  [(* 2 val) (* 3 val)])

(deffilterop multiple-of-7 [val]
  (= 0 (mod val 7)))

(defn double-function [val]
  (* 2 val))

(deffilterop big-age1 [val]
  (> val 30))

(defn big-age2 [val]
  (> val 30))

;; The difference between defn and *ops:

(defn age-filter [afilter]
  (<- [?person ?age]
      (age ?person ?age)
      (afilter ?age)
      (:distinct false)))

(defn big-age1-query []
  (age-filter big-age1))

(defn big-age2-query []
  ;; only works with var
  (age-filter #'big-age2))
