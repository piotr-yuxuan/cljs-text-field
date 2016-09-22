(ns cljs-text-field.text-field
  "Custom implementation of material UI text-field

Caret position should behave

state attribute `:clean?` comes from standard callemall Material UI library but isn't used yet"
  (:require [cljs-text-field.common :refer [curry-set-state]]
            [cljs-text-field.styles :refer [colours styles]]
            [re-frame.core :refer [subscribe dispatch]]
            [cljs-text-field.config :as config]))

(defn- placeholder-comp
  [{:keys [path id placeholder]
    :as   field-attrs}]
  (let [field-ref (select-keys field-attrs [:path :id])
        state (subscribe [:field-state/get field-ref])]
    (fn []
      (let [display (if (:has-value? @state)
                      :none
                      :block)]
        [:div {:style {:position    :absolute
                       :opacity     1
                       :font-size   16
                       :line-height "24px"
                       :text-align  :left
                       :display     display
                       :color       "rgba(0, 0, 0, 0.3)"
                       :transition  "all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms"
                       :bottom      10}}
         placeholder]))))

(defn- displayed-value-comp
  [{:keys [path id value->display]
    :as   field-attrs}
   {:keys [form-name db-path]
    :as   data-params}]
  (let [field-ref (select-keys field-attrs [:path :id])
        state (subscribe [:field-state/get field-ref])
        set-state (partial curry-set-state path id)
        db-value (subscribe [:form/field-value {:form-name form-name :db-path db-path}])]
    (fn []
      (let [display (if (and (not (:focused? @state))
                             (:has-value? @state))
                      :inline-block
                      :none)]
        [:div {:style    {:position    :absolute
                          :opacity     1
                          :font-size   16
                          :line-height "24px"
                          :text-align  :left
                          :width       "100%"
                          :display     display
                          :transition  "all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms"
                          :bottom      10}
               :on-click #(set-state {:focused? true})}
         (value->display @db-value)]))))

(defn- label-comp
  "Label can be a component, not necesssarily a string"
  [{:keys [path id label]
    :as   field-attrs}]
  (let [field-ref (select-keys field-attrs [:path :id])
        state (subscribe [:field-state/get field-ref])]
    (fn []
      [:label {:for   "undefined-HintText-FixedFloatingLabelText-44424"
               :style (merge (:label styles)
                             {:color (if (:focused? @state)
                                       (:green colours)
                                       (:grey-text colours))})}
       label])))

(defn- underline-comp
  [{:keys [path id]
    :as   field-attrs}]
  (let [state (subscribe [:field-state/get {:path path :id id}])]
    (fn []
      [:div {}
       [:hr {:style (:hr-grey styles)}]
       [:hr {:style (merge (:hr-focus styles)
                           {:transform (if (:focused? @state)
                                         "scaleX(1)"
                                         "scaleX(0)")})}]])))

(defn text-field
  "At least field-attrs should be provided
`value->display` will not be invoked when the value is nil
`input-> value` can be used to add constraint on user input"
  [{{:keys [path id input->value value->display label placeholder]
     :or   {input->value   (fn [current old] current)
            value->display (fn [value] (when value "This is display"))
            path           []
            id             (str "text-field-" (rand))}
     :as   field-attrs} :field-attrs
    {:keys [form-name db-path]
     :as   data-params} :data-params
    {:keys []
     :as   input-style} :input-style
    :as                 args
    :or                 {field-attrs {}
                         input-style {}}}]
  {:pre [(-> args nil? not)
         (-> field-attrs nil? not)]}
  (let [new-state {:focused?   false
                   :error-text nil
                   :has-value? false
                   :clean?     true}
        set-state (partial curry-set-state path id)
        field-state (subscribe [:field-state/get {:path path :id id}])
        db-value (subscribe [:form/field-value {:form-name form-name :db-path db-path}])]
    (reagent.core/create-class
      {:component-will-mount   #(dispatch [:field-state/set {:path  path
                                                             :id    id
                                                             :state (merge new-state
                                                                           {:has-value? (not (empty? @db-value))})}])
       :component-will-unmount #(when (not config/debug?) (dispatch [:field-state/clean {:path path :id id}]))
       :component-will-update  #(set-state {:has-value? (not (empty? @db-value))})
       :reagent-render         (fn [_]
                                 [:div {:style (:container styles)}
                                  [:div {:style (:child styles)}
                                   [label-comp field-attrs]
                                   [placeholder-comp field-attrs]
                                   [displayed-value-comp field-attrs data-params]
                                   [:input {:id        id
                                            :style     (merge (:input styles)
                                                              input-style
                                                              {:opacity (if (:focused? @field-state) 1 0)})
                                            :type      :text
                                            :value     @db-value
                                            :on-focus  #(set-state {:focused? true})
                                            :on-blur   #(set-state {:focused? false})
                                            :on-change #(dispatch [:form/set {:key   :some-data
                                                                              :value (-> % .-target .-value (input->value @db-value))}])}]
                                   [underline-comp {:path path :id id}]]])})))