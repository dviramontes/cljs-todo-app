(ns ^:dev/once todo.app.db
  (:require [reagent.core :as r]))

(def init-todos
  [{:id 0 :title "dishes" :done true}
   {:id 1 :title "exercise" :done false}
   {:id 2 :title "laundry" :done true}])

(def todos
  (r/atom (sorted-map)))

(def counter
  (r/atom 0))

(defn seed []
  (doseq [todo init-todos
    :let [id (swap! counter inc)]]
    (swap! todos assoc id todo)))
