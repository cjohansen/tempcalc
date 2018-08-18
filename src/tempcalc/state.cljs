(ns tempcalc.state
  (:require [tempcalc.calc :as calc]))

(defonce store (atom {}))

(def default-state
  {:temperature 25
   :items [{:amount 1000 :temperature 21}
           {:amount 750}]})

(defmulti exec-action (fn [action] action))

(defmethod exec-action :assoc-in-state [_ state k v]
  (assoc-in state k v))

(defmethod exec-action :add-item [_ state]
  (assoc state :items (conj (vec (calc/solve (:temperature state) (:items state))) {:amount 0})))

(defmethod exec-action :remove-item [_ state idx]
  (update state :items #(vec (concat (subvec % 0 idx) (subvec % (inc idx))))))

(defn publish [actions & args]
  (reset! store (reduce (fn [state [action & action-args]]
                          (apply exec-action action state (concat action-args args)))
                        @store actions)))
