(ns tempcalc.ui.swipe-reveal
  (:require [dumdom.core :as dc :refer [defcomponent]]
            [dumdom.dom :as d]))

(defn- touch-x [e]
  (some-> e .-changedTouches (aget 0) .-screenX))

(defn- parse-num [v]
  (let [num (js/parseInt v 10)]
    (if (js/isNaN num)
      0
      num)))

(defn- set-left [el pos]
  (set! (.-left (.-style el)) (str pos "px")))

(defn- animate-left-pos [el pos & [duration]]
  (let [duration (or duration 150)]
    (set! (.-transition (.-style el)) (str "left " duration "ms"))
    (js/setTimeout #(set-left el pos))
    (js/setTimeout #(set! (.-transition (.-style el)) "") duration)))

(defcomponent SwipeReveal
  :on-mount (fn [node {:keys [tease?]}]
              (let [swipee (some-> node .-firstChild .-nextSibling)
                    hidden-width (some-> node .-firstChild .-clientWidth)
                    pos (atom {:x 0 :left 0})
                    touchstart #(reset! pos {:x (touch-x %)
                                             :left (parse-num (.-left (.-style swipee)))})
                    touchmove (fn [e]
                                (.preventDefault e)
                                (let [left (touch-x e)
                                      left-pos (+ (:left @pos) (- left (:x @pos)))]
                                  (set-left swipee (js/Math.min left-pos 0))))
                    touchend (fn [e]
                               (let [diff (- (parse-num (.-left (.-style swipee))) (:left @pos))]
                                 (animate-left-pos
                                  swipee
                                  (if (and (< diff 0)
                                           (< (/ hidden-width 4) (js/Math.abs diff)))
                                    (- hidden-width)
                                    0))))]
                (.addEventListener node "touchstart" touchstart false)
                (.addEventListener node "mousedown" touchstart false)
                (.addEventListener node "touchmove" touchmove false)
                (.addEventListener node "mousemove" touchmove false)
                (.addEventListener node "touchend" touchend false)
                (.addEventListener node "mouseup" touchend false)
                (when tease?
                  (js/setTimeout #(animate-left-pos swipee (- (* hidden-width 0.75)) 500) 500)
                  (js/setTimeout #(animate-left-pos swipee 0 500) 1500))))
  [{:keys [swipee hidden]}]
  (d/div {:style {:position "relative"}}
    (d/div {:style {:position "absolute"
                    :right 0
                    :top 0}}
      hidden)
    (d/div {:style {:position "relative"
                    :left 0}}
      swipee)))
