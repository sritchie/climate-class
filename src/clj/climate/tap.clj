(ns climate.tap
  (:use [cascalog.api :only (hfs-tap)]
        [cascalog.io :only (get-bytes)]
        [clojure.string :only (join)]
        [climate.testing :only (dev-path)])
  (:require [cascalog.workflow :as w])
  (:import [climate WholeFile]
           [cascading.tuple Fields]))

(defn whole-file
  "Custom scheme for dealing with entire files."
  [field-names]
  (WholeFile. (w/fields field-names)))

(defn hfs-wholefile
  "Subquery to return distinct files in the supplied directory. Files
  will be returned as 2-tuples, formatted as `<filename, file>` The
  filename is a text object, while the entire, unchopped file is
  encoded as a Hadoop `BytesWritable` object."
  [path & opts]
  (let [scheme (-> (:outfields (apply array-map opts) Fields/ALL)
                   (whole-file))]
    (apply hfs-tap scheme path opts)))
