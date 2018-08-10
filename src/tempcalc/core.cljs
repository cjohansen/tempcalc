(ns tempcalc.core
  (:require [cljs.reader :refer [read-string]]
            [tempcalc.homeless :as h]
            [tempcalc.prep :as prep]
            [tempcalc.state :as state]
            [tempcalc.view :as view]))

(enable-console-print!)

(defn cached-state []
  (try
    (some-> (.getItem js/localStorage "app-state") read-string)
    (catch js/Error e
      (.removeItem js/localStorage "app-state")
      nil)))

(defn cache [state]
  (.setItem js/localStorage "app-state" (pr-str state)))

(defn run []
  (add-watch state/store ::render #(view/render (prep/prepare-view-data %4)))
  (let [calm-cache (h/throttle (h/debounce cache 1000) 5000)]
    (add-watch state/store ::cache #(calm-cache %4)))
  (if-let [cached (cached-state)]
    (reset! state/store cached)
    (reset! state/store state/default-state)))
