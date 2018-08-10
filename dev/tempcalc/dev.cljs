(ns tempcalc.dev
  (:require [cljs.spec.alpha :as s]
            [cljs.spec.test.alpha :as stest]
            [expound.alpha :as expound]
            [tempcalc.core :as tc]))

(defn run
  []
  (s/check-asserts true)
  (set! s/*explain-out* expound/printer)
  (stest/instrument)
  (tc/run))

(defonce init (run))
