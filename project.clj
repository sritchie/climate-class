(defproject climate-clj "1.0.0"
  :description "Project for the Climate Corporation's Cascalog Course."
  :repositories {"conjars" "http://conjars.org/repo/"}
  :exclusions [backtype/thriftjava]
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :dev-resources-path "dev"
  :aot [climate.pail
        climate.problems.kryo
        climate.solutions.kryo]
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [cascalog "1.9.0-wip8"]
                 [cascalog-lzo "0.1.0-wip7"]
                 [cascalog-checkpoint "0.1.1"]
                 [elephantdb "0.2.0-wip1" :exclusions [org.slf4j/slf4j-api]]
                 [cascalog-elephantdb "0.3.1"]
                 [backtype/dfs-datastores "1.1.3-SNAPSHOT"]
                 [backtype/dfs-datastores-cascading "1.1.3"]
                 [org.apache.thrift/libthrift "0.6.1"
                  :exclusions [org.slf4j/slf4j-log4j12]]
                 [backtype/cascading-thrift "0.2.0"
                  :exclusions [cascading/cascading-core
                               cascading/cascading-hadoop]]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [midje-cascalog "0.4.0"]])
