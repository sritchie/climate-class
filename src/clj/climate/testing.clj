(ns climate.testing
  (:use cascalog.api
        [hadoop-util.core :only (path)]))

;; ## Directory Management
;;
;; These functions provide assistance for directory navigation for
;; testing. One compromise we make here is the binding of
;; `dev-resources-subdir`; This is defined as `:dev-resources-path` in
;; `project.clj`, but reading either of these would force a dependency
;; on pallet or cake. We've included a test to make sure that `dev`
;; exists on any deployment of the given project; a failing test means
;; that this directory should be created.

;; ## Test Dataset Helpers

(def dev-resources-subdir "dev")

(defn get-current-directory
  "Returns the current project directory."
  []
  (.getCanonicalPath (java.io.File. ".")))

(defn project-path
  "Accepts a sub-path within the current project structure and returns
  a fully qualified system path. For example:

    (project-path \"/dev/file.txt\")
    ;=> \"/home/sritchie/myproject/dev/file.txt\""
  ([] (project-path ""))
  ([sub-path]
     (str (path (get-current-directory) sub-path))))

(defn dev-path
  ([] (project-path dev-resources-subdir))
  ([sub-path]
     (project-path (str (path dev-resources-subdir sub-path)))))
