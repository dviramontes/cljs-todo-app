(ns todo.app.core
  (:require [reagent.dom :as rdom]
            [reagent.core :as r]
            [todo.app.db :as db]))

(defn todo-item [todo]
  [:li [:span (:title todo)]])

(defn save [title]
  (js/console.log "--save todo =>" title))

(defn todo-input [on-save]
  (let [value (r/atom "")]
   (fn []
     [:input
      {:type "text"
       :value @value
       :on-key-down
       #(case (.-which %)
              13 (on-save @value) ;; enter-key
              nil)
       :on-change
       #(reset! value (-> % .-target .-value))}])))

(defn todo-wrapper []
  (let [todos (vals @db/todos)]
   (fn []
     [:div#todo-wrapper
      [:header
       [:h1 "To-Do Items:"]]
      [:div#todos
       [todo-input save]
       (for [todo todos
             :let [id (:id todo)]]
          ^{:key (gensym id)}
          [todo-item todo])]])))

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
  (db/seed)
  (mount))
