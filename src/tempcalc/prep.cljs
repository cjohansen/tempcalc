(ns tempcalc.prep
  (:require [tempcalc.calc :as calc]))

(defn prepare-view-data [state]
  (let [calculated (into [] (calc/solve (:temperature state) (:items state)))]
    (-> state
        (update :items
                #(map-indexed (fn [idx x]
                                (cond-> x
                                  (not (:temperature x)) (merge {:temperature (get-in calculated [idx :temperature])
                                                                 :calculated? true})
                                  (= idx 0) (assoc :tease? true)
                                  :true (assoc :actions {:remove [[:remove-item idx]]
                                                         :set-amount [[:assoc-in-state [:items idx :amount]]]
                                                         :set-temp [[:assoc-in-state [:items idx :temperature]]]})
                                  :true (update :temperature (fn [t] (.toFixed t 1)))))
                              %))
        (assoc :actions {:add-item [[:add-item]]
                         :set-temp [[:assoc-in-state [:temperature]]]}))))
