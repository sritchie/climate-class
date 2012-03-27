(ns climate.problems.checkpoint
  (:use cascalog.api
        cascalog.playground
        [cascalog.checkpoint :only (workflow)])
  (:require [cascalog.ops :as c]))

;; ## Problem 1:
;;
;; This problem uses the `sentence` dataset in
;;cascalog.playground. Write a query that generates a distribution of
;;the count of words that have appeared n times in the
;;dataset. Tuples will look like the following:
;;
;; [<# of words> <# of appearances>]
;;
;; So a return value of [[93 1] [4 2]] would mean that 93 words
;;appeared once in the corpus and 4 words appeared twice.

;; ## Problem 2
;;
;; Rewrite this query to use the checkpointing macro to store
;;intermediate results.
;;
;; http://sritchie.github.com/2011/11/15/introducing-cascalogcontrib.html
