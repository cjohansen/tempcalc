(ns tempcalc.homeless
  (:require [cljs.core.async :refer [<! alts! chan put! timeout]])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

(defn debounce
  "Creates a new function that will call function `f` some `ms` milliseconds
  after the last invocation, discarding arguments of all invocations in that
  timeframe but the last. "
  [f ms]
  (let [c (chan)]
    (go-loop [args (<! c)]
      (let [[value port] (alts! [c (timeout ms)])]
        (if (= port c)
          (recur value)
          (do ;; or timed out
            (apply f args)
            (recur (<! c))))))
    (fn [& args]
      (put! c (or args [])))))

(defn throttle
  "Creates a new function that will call function `f` only once every `ms`
   milliseconds. The very first invocation is done immediately. After that,
   all subsequent calls but the last are discarded. The last call is then
   performed once `ms` has passed."
  [f ms]
  (let [args-ch (chan)]
    (go-loop [args (<! args-ch)
              hold-ch nil]
      (when (and args (not hold-ch))
        (apply f args)
        (recur nil (timeout ms)))
      (let [[value port] (alts! (if hold-ch
                                  [args-ch hold-ch]
                                  [args-ch]))]
        (if (= port args-ch)
          (recur value hold-ch) ;; new arguments
          (recur args nil)))) ;; timed out
    (fn [& args]
      (put! args-ch (or args [])))))
