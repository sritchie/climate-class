(ns climate.solutions.checkpoint
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

(defmapcatop split [sentence]
  (.split sentence "\\s+"))

(def wordcount-distribution-query
  (let [wc-query (<- [?word ?count]
                     (sentence ?sentence)
                     (split ?sentence :> ?word)
                     (c/count ?count))]
    (<- [?count ?appears-count]
        (wc-query ?word ?appears-count)
        (c/count ?count))))

;; ## Problem 2
;;
;; Rewrite this query to use the checkpointing macro to store
;;intermediate results.
;;
;; http://sritchie.github.com/2011/11/15/introducing-cascalogcontrib.html

(defn distribution-workflow []
  (workflow ["/tmp/checkpoint"]
            wordcounts ([:tmp-dirs wordcount-path]
                          (prn "Starting step")
                          (?<- (hfs-seqfile wordcount-path)
                               [?word ?count]
                               (sentence ?sentence)
                               (split ?sentence :> ?word)
                               (c/count ?count)))

            final-step ([]
                          (prn "Starting step 2!")
                          (Thread/sleep 2000)
                          (let [src (hfs-seqfile wordcount-path)]
                            (?<- (stdout)
                                 [?count ?appears-count]
                                 (src ?word ?appears-count)
                                 (c/count ?count))))))
