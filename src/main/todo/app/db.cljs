(ns ^:dev/once todo.app.db
  (:require [reagent.core :as r]))

(def db 
  (r/atom 
   {:todos ["laundry" "dishes" "exercise"]}))