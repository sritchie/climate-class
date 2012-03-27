(ns climate.solutions.kryo
  (:use cascalog.api
        carbonite.serializer)
  (:import [com.esotericsoftware.kryo.serialize StringSerializer]))

;; Helper functions for testing down below.

(def first-tuple
  (comp first ffirst))

(defn round-trip [obj]
  (first-tuple (??- [[obj]])))

(defrecord LightSaber [style color])

;; The saber doesn't fare so well:

(defn round-trip-saber []
  (round-trip (LightSaber. "hello" "world")))

;; First attempt uses Kryo's primitives directly:

(gen-class
 :name climate.solutions.kryo.Saber1Serializer
 :prefix saber1-
 :extends com.esotericsoftware.kryo.Serializer)

(defn saber1-writeObjectData [_ buffer saber]
  (StringSerializer/put buffer (pr-str (:style saber)))
  (StringSerializer/put buffer (pr-str (:color saber))))

(defn saber1-readObjectData [_ buffer type]
  (LightSaber. (read-string (StringSerializer/get buffer))
               (read-string (StringSerializer/get buffer))))

(defn round-trip-saber1 []
  (with-job-conf
    {"cascading.kryo.registrations"
     "climate.solutions.kryo.LightSaber,climate.solutions.kryo.Saber1Serializer"}
    (round-trip (LightSaber. "hello" "world"))))

;; 2nd attempt uses helpers from Carbonite:

(gen-class
 :name climate.solutions.kryo.Saber2Serializer
 :prefix saber2-
 :extends com.esotericsoftware.kryo.Serializer)

(defn saber2-writeObjectData [_ buffer saber]
  (clj-print buffer (:style saber))
  (clj-print buffer (:color saber)))

(defn saber2-readObjectData [_ buffer type]
  (LightSaber. (clj-read buffer)
               (clj-read buffer)))

(defn round-trip-saber2 []
  (with-job-conf
    {"cascading.kryo.registrations"
     "climate.solutions.kryo.LightSaber,climate.solutions.kryo.Saber2Serializer"}
    (round-trip (LightSaber. "hello" "world"))))

;; Third uses Kryo again. What's interesting about this final solution?

(gen-class
 :name climate.solutions.kryo.Saber3Serializer
 :prefix saber3-
 :extends com.esotericsoftware.kryo.Serializer)

(defn saber3-writeObjectData [_ buffer obj]
  (let [obj-str (binding [*print-dup* true]
                  (pr-str obj))]
    (StringSerializer/put buffer obj)))

(defn saber3-readObjectData [_ buffer type]
  (read-string (StringSerializer/get buffer)))

(defn round-trip-saber3 []
  (with-job-conf
    {"cascading.kryo.registrations"
     "climate.solutions.kryo.LightSaber,climate.solutions.kryo.Saber2Serializer"}
    (round-trip (LightSaber. "hello" "world"))))

;; Hierarchy registrations:

(defn final-round-trip []
  (with-job-conf
    {"cascading.kryo.registrations" "climate.solutions.kryo.LightSaber"
     "cascading.kryo.hierarchy.registrations"
     "clojure.lang.IRecord,carbonite.PrintDupSerializer"}
    (round-trip (LightSaber. "hello" "world"))))
