(ns cljs-text-field.state-inspector
  "When you have eliminated the impossible, whatever remains, however improbable, must be the truth"
  (:require [datafrisk.core :refer [DataFriskShellVisibleButton Root styles]]
            [re-frame.core :refer [subscribe]]
            [reagent.core :as r]
            [cljs-react-material-ui.core :as mui]
            [cljs-react-material-ui.icons :as ic]))

(def z-index-above-material-ui 1501)

(def btn-styles {:position "fixed"
                 :top      4
                 :right    4
                 :z-index  (+ 1 z-index-above-material-ui)})

(def container-styles {:padding         "10px 10px 70px 10px"
                       :box-sizing      "border-box"
                       :overflow-y      "scroll"
                       :box-shadow      "rgba(0, 0, 0, 1) 0px 3px 10px"
                       :position        "fixed"
                       :right            0
                       :bottom          0
                       :top             0
                       :width           "40%"
                       :height          "100%"
                       :max-height      "100%"
                       :transition      "all 0.3s ease-out"
                       :z-index         z-index-above-material-ui
                       :backgroundColor "#f6f8fb"})

(defn state-inspector []
  "Based on datafrisk lib, inspect whole app-db value"
  (let [app-db (subscribe [:app-db])
        expand-by-default (reduce #(assoc-in %1 [:data-frisk %2 :expanded-paths] #{[]})
                                  {}
                                  (range (count @app-db)))
        state-atom (r/atom expand-by-default)]
    (fn []
      (let [data-frisk (:data-frisk @state-atom)
            visible? (:visible? data-frisk)]
        [:div
         [mui/floating-action-button
          {:style     btn-styles
           :mini      true
           :secondary true
           :on-click  (fn [_] (swap! state-atom assoc-in [:data-frisk :visible?] (not visible?)))}
          (if visible?
            (ic/action-visibility-off)
            (ic/action-visibility))]

         (if visible?
           [:div {:style container-styles}
            [Root @app-db 1 state-atom]])]))))
