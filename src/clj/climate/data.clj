(ns climate.data
  (:use cascalog.api
        cascalog.lzo
        climate.pail)
  (:import [climate.schema
            DataChunk FireTuple DoubleArray LongArray FireSeries
            ArrayValue TimeSeries DataValue ModisPixelLocation DataChunk]))

;; Fires parallel aggregator

(defn add-fires [a b]
  (FireTuple. (+ (.getTemp330 a)
                 (.getTemp330 b))
              (+ (.getConf50 a)
                 (.getConf50 b))
              (+ (.getBothPreds a)
                 (.getBothPreds b))
              (+ (.getCount a)
                 (.getCount b))))

(defparallelagg combine-fires
  :init-var    #'identity
  :combine-var #'add-fires)

(comment
  (let [src [["a" (FireTuple. 1 1 1 1)]
             ["b" (FireTuple. 1 1 1 1)]]]
    (??<- [?output]
          (src ?name ?fire)
          (combine-fires ?fire :> ?output))))

;; Show another way w/ predicate macros.

(defprotocol IMakeData
  (mk-data-value [x]))

(extend-protocol IMakeData
  LongArray   (mk-data-value [x] (DataValue/longs x))
  Long        (mk-data-value [x] (DataValue/longVal x))
  DoubleArray (mk-data-value [x] (DataValue/doubles x))
  Double      (mk-data-value [x] (DataValue/doubleVal x))
  FireTuple   (mk-data-value [x] (DataValue/fireVal x))
  TimeSeries  (mk-data-value [x] (DataValue/timeSeries x))
  FireSeries  (mk-data-value [x] (DataValue/fireSeries x)))

(defn mk-chunk
  [dataset t-res period location-prop data-value]
  (let [chunk (DataChunk. dataset location-prop data-value t-res)]
    (if period
      (doto chunk (.setPeriod period))
      chunk)))

(def test-src
  (into []
        (for [sample (range 5)
              line   (range 5)
              :let [loc (ModisPixelLocation. "1000" 1 1 sample line)]]
          [(mk-chunk "1000" "32" nil loc (mk-data-value 10.0))])))

(defn sink-to-lzo []
  (?- (hfs-lzo-thrift "/tmp/lzothrift" DataChunk
                      :sinkmode :replace)
      test-src))

;; mention job-conf.clj
(defn source-from-lzo []
  (??- (hfs-lzo-thrift "/tmp/lzothrift" DataChunk)))

(defn pail-append [item]
  (?pail- (data-chunk-tap "/tmp/chunks")
          [[item]]))

;; You can append data to the pail!

;; (mk-chunk "1000" "32" nil (ModisPixelLocation. "1000" 1 1 100 100)
;;           (mk-data-value 10.0))

(defn fill-pail []
  (?pail- (data-chunk-tap "/tmp/chunks")
          test-src))

(defn pull-from-pail [& colls]
  (let [src (apply data-chunk-tap "/tmp/chunks" colls)]
    (??<- [?path ?chunk]
          (src ?path ?chunk)
          (:distinct false))))

;; (pull-from-pail ["1000" "1000-32"])
