(ns climate.problems.kryo
  (:use cascalog.api
        carbonite.serializer)
  (:import [com.esotericsoftware.kryo.serialize StringSerializer]))

;; Helper functions for testing down below.

(def first-tuple
  (comp first ffirst))

(defn round-trip [obj]
  (first-tuple (??- [[obj]])))

;; ## Problem 1: Implement a Kryo serializer.
;;
;; Cascalog uses Kryo for serialization; Clojure's types are all
;; pre-registered, and can be round-tripped through Hadoop with no
;; issues.
;;
;; First, try round-tripping some primitive:

(defn round-trip-long []
  (round-trip 10))

;; Now a Clojure class:

(defn round-trip-ratio []
  (round-trip (/ 3 2)))

;; Both return just fine. What about a record type?

(defrecord LightSaber [style color])

;; Try round-tripping this type and think about what the error might
;; mean.
;;
;; What we need here is a custom serializer. Using the information at
;; Carbonite's README --
;; https://github.com/sritchie/carbonite/tree/develop -- take a crack
;; at implementing a LightSaber serializer.
