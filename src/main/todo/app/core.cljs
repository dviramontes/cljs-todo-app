(ns todo.app.core
  (:require [reagent.dom :as rdom]
            [reagent.core :as r]
            [clojure.string :as str]
            [todo.app.db :as db]))

(defn toggle [id]
      (swap! db/todos update-in [id :done] not))

(defn save [title]
  (let [id (swap! db/counter inc)]
    (swap! db/todos assoc id {:id id :title title :done false})))

(defn delete [id]
  (swap! db/todos dissoc id))

(defn handle-save [val on-save after-save]
  (let [val (-> val str str/trim)]
       (if-not (empty? val)
         (on-save val))
       (after-save)))


(defn todo-item [{:keys [done title id]}]
  [:li
   [:input
    {:type "checkbox"
     :checked done
     :on-change #(toggle id)} ]
   [:span title]
   [:button
    {:type "button"
     :on-click #(delete id)}
    "[x]"]])

(defn todo-input [on-save]
  (let [value (r/atom "")
        after-save (fn [] (reset! value ""))]
   (fn []
     [:input
      {:type "text"
       :value @value
       :on-key-down
       #(case (.-which %)
              13 (handle-save @value on-save after-save) ;; enter-key
              nil)
       :on-change
       #(reset! value (-> % .-target .-value))}])))

(defn todo-wrapper []
  [:div#todo-wrapper
   [:header
    [:h1 "To-Do Items:"]]
   [:div#todos
    [todo-input save]
    (for [todo (vals @db/todos)
          :let [id (:id todo)]]
         ^{:key (gensym id)}
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
  (db/seed)
  (mount))
