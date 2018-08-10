(ns tempcalc.templates)

(def white-bread
  {:target-temp 25
   :friction-factor 1
   :ingredients [{:description "Bread flour"
                  :amount 1000
                  :temperature 21}
                 {:description "Water"
                  :amount 750}
                 {:description "Salt"
                  :amount 22
                  :temperature 21}
                 {:description "Yeast"
                  :amount 3
                  :temperature 4}]})

(def whole-wheat-bread
  {:target-temp 25
   :friction-factor 1
   :ingredients [{:description "Whole wheat bread"
                  :amount 750
                  :temperature 21}
                 {:description "Bread flour"
                  :amount 250
                  :temperature 21}
                 {:description "Water"
                  :amount 750}
                 {:description "Salt"
                  :amount 22
                  :temperature 21}
                 {:description "Yeast"
                  :amount 3
                  :temperature 4}]})

;; TODO: Solve for temps - find amounts

(def strike-water
  {:target-temp 68
   :ingredients [{:description "Boiling water"
                  :amount 15000
                  :temperature 100}
                 {:description "Tap water"
                  :amount 10000
                  :temperature 11}]})
