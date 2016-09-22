(ns cljs-text-field.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [cljs-react-material-ui.reagent :refer [mui-theme-provider]]
            [cljs-react-material-ui.core :refer [color get-mui-theme]]
            [cljs-text-field.registees]
            [cljs-text-field.views :as views]
            [cljs-text-field.config :as config]
            [cljs-text-field.state-inspector :refer [state-inspector]]))

(def theme
  (get-mui-theme {:palette {:text-color           (color :dark-black)
                            :primary3-color       (color :grey-400)
                            :accent1-color        (color :light-blue-500)
                            :accent2-color        (color :grey-100)
                            :accent3-color        (color :grey-500)
                            :accent4-color        "rgba(96, 200, 248, 0.7)"
                            :accent-veil-color    "rgba(79, 195, 247, 0.1)"
                            :accent-border-color  (color :light-blue-500)
                            :alternate-text-color (color :white)
                            :border-color         (color :grey-300)}
                  :toolbar {:background-color (color :grey-100)
                            :color            (color :black)
                            :icon-color       (color :black)
                            :padding          "15px"}
                  :spacing {:desktop-gutter 24}}))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (reagent/render (fn []
                    [mui-theme-provider
                     {:mui-theme theme}
                     [:div
                      [state-inspector]
                      [views/main-panel]]])
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
