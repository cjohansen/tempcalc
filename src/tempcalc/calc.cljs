(ns tempcalc.calc)

(defn- sum [xs]
  (reduce + 0 xs))

(defn- amount [xs]
  (sum (map :amount xs)))

(defn- temp-points [{:keys [temperature amount]}]
  (* temperature amount))

(defn solve [target-temp xs]
  (let [total-amount (amount xs)
        amount-to-calc (amount (filter (comp not :temperature) xs))
        locked-points (->> xs (filter :temperature) (map temp-points) sum)
        points-to-distribute (- (* total-amount target-temp) locked-points)
        temp (/ points-to-distribute amount-to-calc)]
    (map #(assoc % :temperature (get % :temperature temp)) xs)))

#_(solve 26 [{:amount 425 :temperature 27.7}
           {:amount 750 :temperature 24}
           {:amount 605}])
