(ns climate.wide-tap
  (:use cascalog.api
        climate.datastore))

;; ## Wide Tap Discussion

;; naming all fields.
(<- [?date ?spread]
    (stock-tap ?exchange ?stock-sym ?date ?open ?high ?low ?close ?volume ?adj)
    (- ?high ?low :> ?spread))

;; ignore certain vars
(<- [?date ?spread]
    (stock-tap _ _ ?date _ ?high ?low _ _ _)
    (- ?high ?low :> ?spread))

;; select fields
(let [src (select-fields stock-tap ["?high" "?low" "?date"])]
  (<- [?date ?spread]
      (src ?high ?low ?date)
      (- ?high ?low :> ?spread)))

;; Wide tap operator
(<- [?date ?spread]
    (stock-tap :#> 9 {4 ?high, 5 ?low, 2 ?date})
    (- ?high ?low :> ?spread))
