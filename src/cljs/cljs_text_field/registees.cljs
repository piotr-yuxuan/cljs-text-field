(ns cljs-text-field.registees
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [reg-sub reg-event-db]]
            [cljs-text-field.db :as db]))

(def base-field-state-path
  [:field-states])

(defn- resolve-field-state-path
  [path id]
  (-> base-field-state-path
      (into (or path []))
      (conj id)))


(reg-event-db
  :initialize-db
  (fn [_ _]
    db/default-db))

(reg-event-db
  :field-state/set
  (fn [db [_ {:keys [path id state]}]]
    (let [old-state (get-in db (resolve-field-state-path path id))]
      (assoc-in db
                (resolve-field-state-path path id)
                (merge old-state
                       state)))))

(reg-event-db
  :field-state/clean
  (fn [db [_ {:keys [path id]}]]
    (let [old (get-in db (into base-field-state-path path))
          new (dissoc old id)]
      (assoc-in db (into base-field-state-path path) new))))

(reg-sub
  :field-state/get
  (fn [db [_ {:keys [path id]}]]
    (get-in db (resolve-field-state-path path id))))

(reg-sub
  :name
  (fn [db]
    (:name db)))

(reg-sub
  :app-db
  (fn [db]
    db))

(reg-event-db
  :form/set
  (fn [db [_ {:keys [key value]}]]
    (assoc-in db [:form :foobar :values key] value)))

(reg-sub
  ;; "Tiny POC smell: db-path is not a path. Fuck off."
  :form/field-value
  (fn [db [_ {:keys [form-name db-path]}]]
    (get-in db [:form form-name :values db-path])))