(ns cljs-text-field.views
  (:require [cljs-text-field.text-field :as custom]
            [re-frame.core :refer [subscribe dispatch]]
            [cljs-react-material-ui.reagent :as mui]))

(def french-display
  #(-> %
       js/parseFloat
       (.toLocaleString "fr-FR"
                        (clj->js {:style    :currency
                                  :currency :EUR}))))

(def fancy-display
  #(str "\uD83D\uDE4C "
        (-> %
            js/parseFloat
            (.toLocaleString "zh-Hans-CN-u-nu-hanidec"
                             (clj->js {:style    "currency"
                                       :currency "JPY"})))))

(defn main-panel []
  (let [name (subscribe [:name])
        db (subscribe [:app-db])]
    #(dispatch [:form/set {:key   :some-data
                           :value "1,123.345"}])
    (fn []
      [:div
       [:p {} "Custom text-field state"]
       [:ul {} (map (fn [[k v]] [:li {:key (str k)} [:span {:style {:font-weight :bold}} (str k)] " " (str v)])
                    (-> @db :field-states ::main-field))]
       [:div "Hello from " @name]

       [custom/text-field {:field-attrs {:id             ::main-field
                                         :value->display french-display
                                         :input->value   (fn [new current] (if (re-matches #"[0-9.\ ]*" new)
                                                                             new
                                                                             current))
                                         :label          "Fixed floating label"
                                         :placeholder    "Text for placeholder"}
                           :data-params {:form-name :foobar
                                         :db-path   :some-data}
                           :input-style {}}]
       [:p {} "db-value: " (-> @db :form :foobar :values :some-data)]
       [mui/text-field {:id                :old-field
                        :floatingLabelText "Force db-value"
                        :on-change         #(dispatch [:form/set {:key   :some-data
                                                                  :value (-> % .-target .-value)}])}]])))
