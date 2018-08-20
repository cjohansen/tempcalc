(ns tempcalc.view
  (:require [dumdom.core :as dc :refer [defcomponent]]
            [dumdom.dom :as d]
            [tempcalc.state :refer [publish]]
            [tempcalc.ui.button :as button]
            [tempcalc.ui.swipe-reveal :refer [SwipeReveal]]))

(defcomponent Header []
  (d/div {:className "bd vs-s"
          :style {:background "#f0f0f0"}}
    (d/p {} "Temperature calculator")))

(defcomponent Label [attrs & content]
  (apply d/label
         (merge attrs
                {:style {:lineHeight "52px"}})
         content))

(defn get-number-value [input]
  (let [n (js/parseFloat (.. input -target -value))]
    (if (js/isNaN n)
      nil
      n)))

(defcomponent NumberInput [{:keys [width value fontWeight color on-input on-change disabled?]}]
  (d/input
   {:type "number"
    :value value
    :disabled (when disabled? "disabled")
    :onInput (when on-input
               #(on-input (get-number-value %)))
    :onBlur (when on-change
              #(on-change (get-number-value %)))
    :style {:padding "16px 20px"
            :width (or width "100%")
            :fontSize "16px"
            :fontWeight (or fontWeight "400")
            :background "#fff"
            :borderRadius "2px"
            :boxShadow "none"
            :outline "none"
            :borderLeft "none"
            :borderRight "none"
            :borderBottom "none"
            :borderTop (str "2px solid " (or color "#b5d1e0"))
            :color "#000"
            :MozAppearance "none"
            :WebkitAppearance "none"
            :appearance "none"}}))

(defcomponent Row [& cols]
  (d/div {:className "mod grid"}
    (map #(d/div {:className "col"} %) cols)))

(defcomponent Item [{:keys [amount temperature calculated? actions]}]
  (let [row (Row
             (d/div {:style {:marginRight 20}} (NumberInput {:value amount
                                                             :on-input (partial publish (:set-amount actions))}))
             (d/div {:className "grid"}
               (NumberInput (merge {:value temperature
                                    :on-input (partial publish (:set-temp actions))}
                                   (when calculated?
                                     {:color "#495b63"
                                      :fontWeight "bold"
                                      :disabled? true})))))
        button (d/div {:className "mrm"}
                 (button/SquareButton {:text "Delete"
                                       :action (:remove actions)
                                       :color "#c00"}))]
    (if calculated?
      row
      (SwipeReveal {:swipee row :hidden button}))))

(defcomponent App [state]
  (d/div {}
    (Header)
    (d/div {:className "vs-s"}
      (Row
       (Label {:htmlFor "temperature"}
              "Target temp")
       (NumberInput {:value (:temperature state)
                     :on-input (partial publish (-> state :actions :set-temp))}))
      (Row "Amount" "Temp")
      (map Item (:items state))
      (d/div {:className "mod"}
        (button/Button {:action (-> state :actions :add-item)
                        :text "Add item"})))))

(defn render [state]
  (dc/render (App state) (js/document.getElementById "app")))
