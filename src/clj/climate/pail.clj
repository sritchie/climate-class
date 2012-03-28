(ns climate.pail
  (:use cascalog.api
        [cascalog.io :only (with-fs-tmp)])
  (:import [backtype.cascading.tap PailTap PailTap$PailTapOptions]
           [backtype.hadoop.pail Pail]
           [java.util List]
           [climate.schema DataChunk]))

;; ## Pail Data Structures

(defn hv->tilestring
  "Returns a 0-padded tilestring of format `HHHVVV`, for the supplied
  MODIS h and v coordinates. For example:

     (tilestring 8 6)
     ;=> \"008006\""
  [mod-h mod-v]
  (format "%03d%03d" mod-h mod-v))

(gen-class :name climate.pail.DataChunkPailStructure
           :extends climate.tap.ThriftPailStructure
           :prefix "pail-")

(defn pail-getType [this] DataChunk)

(defn pail-createThriftObject [this] (DataChunk.))

(defn pail-getTarget [this ^DataChunk d]
  (let [location   (.getPixelLocation d)
        tilestring (hv->tilestring (.getTileH location)
                                   (.getTileV location))
        res (format "%s-%s"
                    (.getResolution location)
                    (.getTemporalRes d))]
    [(.getDataset d) res tilestring]))

(defn pail-isValidTarget [this dirs]
  (boolean (#{3 4} (count dirs))))

;; ## Pail Taps
;;
;; makeSpec:
;;https://github.com/nathanmarz/dfs-datastores-cascading/blob/master/src/jvm/backtype/cascading/tap/PailTap.java#L38
;;
;; PailTapOptions:
;; https://github.com/nathanmarz/dfs-datastores-cascading/blob/master/src/jvm/backtype/cascading/tap/PailTap.java#L56
;;
;; PailTap:
;; https://github.com/nathanmarz/dfs-datastores-cascading/blob/master/src/jvm/backtype/cascading/tap/PailTap.java#L182

(defn- pail-tap
  [path colls structure]
  (let [seqs (into-array List colls)
        spec (PailTap/makeSpec nil structure)
        opts (PailTap$PailTapOptions. spec "!datachunk" seqs nil)]
    (PailTap. path opts)))

(defn pail-structure []
  (climate.pail.DataChunkPailStructure.))
 
(defn data-chunk-tap [path & colls]
  (pail-tap path colls (pail-structure)))

(defn ?pail-*
  "Executes the supplied query into the pail located at the supplied
  path, consolidating when finished."
  [tap pail-path query]
  (let [pail (Pail/create pail-path (pail-structure) false)]
    (with-fs-tmp [_ tmp]
      (?- (tap tmp) query)
      (.absorb pail (Pail. tmp)))))

(defmacro ?pail-
  "Executes the supplied query into the pail located at the supplied
  path, consolidating when finished."
  [[tap path] query]
  (list `?pail-* tap path query))

(defn to-pail
  "Executes the supplied `query` into the pail at `pail-path`. This
  pail must make use of the `SplitDataChunkPailStructure`."
  [pail-path query]
  (?pail- (data-chunk-tap pail-path)
          query))

(defmain consolidate [pail-path]
  (.consolidate (Pail. pail-path)))

(defmain absorb [from-pail to-pail]
  (.absorb (Pail. to-pail) (Pail. from-pail)))
