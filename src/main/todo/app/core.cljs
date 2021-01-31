(ns todo.app.core
  (:require [reagent.dom :as rdom]
            [todo.app.db :refer [db]]))

(defn todo-item [title]
  [:li [:span title]])

(defn todo-wrapper []
  [:div#todo-wrapper
   [:header
    [:h1 "To-Do Items:"]]
   [:div#todos
    (for [todo (:todos @db)]
      ^{:key (gensym todo)}
      [todo-item todo])]])

(defn chart-wrapper []
  [:div#chart-wrapper
   [:div.chart
    [:h1 "Complete vs. incomplete tasks"]]
   [:div.chart
    [:h1 "Word count of tasks"]]
   [:div]])

(defn root []
  [:div#root
   [chart-wrapper]
   [todo-wrapper]])

(defn ^:dev/after-load mount []
  (rdom/render
   [root]
   (js/document.getElementById "root")))

(defn ^:export main []
  (mount))
