(require '[clojure.string :as s])

{"io.serializations" "backtype.hadoop.ThriftSerialization"
 "mapred.map.output.compression.codec" "com.hadoop.compression.lzo.LzoCodec"
 "io.compression.codec.lzo.class" "com.hadoop.compression.lzo.LzoCodec"
 "io.compression.codecs"
 (s/join "," ["org.apache.hadoop.io.compress.GzipCodec"
              "org.apache.hadoop.io.compress.DefaultCodec"
              "org.apache.hadoop.io.compress.BZip2Codec"
              "com.hadoop.compression.lzo.LzoCodec"
              "com.hadoop.compression.lzo.LzopCodec"])}
