(ns tempcalc.ui.button
  (:require [dumdom.core :refer [defcomponent]]
            [dumdom.dom :as d]
            [tempcalc.state :refer [publish]]))

(def ^:private button-styles
  {:outline "none"
   :display "inline-block"
   :textAlign "center"
   :whiteSpace "nowrap"
   :position "relative"
   :cursor "pointer"
   :fontSize "20px"
   :fontWeight "bold"
   :textDecoration "none"})

(defcomponent RoundButton [{:keys [action text color]}]
  (d/button {:style (merge button-styles
                           {:borderRadius "50%"
                            :padding "0 0 2px"
                            :marginTop 11
                            :width "30px"
                            :height "30px"
                            :background "#fff"
                            :color (or color "#0c0")
                            :border (str "2px solid " (or color "#0c0"))})
             :onClick #(publish action)}
    text))

(defcomponent Button [{:keys [action text]}]
  (d/button {:style (merge button-styles
                           {:borderRadius "4px"
                            :width "100%"
                            :minHeight "52px"
                            :fontSize "20px"
                            :fontWeight "bold"
                            :textDecoration "none"
                            :borderTop 0
                            :borderLeft 0
                            :borderRight 0
                            :borderBottom "4px solid #ccc"
                            :background "#f0f0f0"})
             :onClick #(publish action)}
    text))
