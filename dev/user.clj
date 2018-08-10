(ns user
  (:require [figwheel-sidecar.repl-api :refer [cljs-repl start-figwheel!]]))

(defn cljs []
  (start-figwheel!)
  (cljs-repl))
