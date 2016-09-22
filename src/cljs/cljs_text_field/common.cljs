(ns cljs-text-field.common
  (:require [re-frame.core :refer [dispatch]]))

(defn curry-set-state
  [path id partial-state]
  (dispatch [:field-state/set {:path  path
                               :id    id
                               :state partial-state}]))