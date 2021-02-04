(ns todo.app.core
  (:require [reagent.dom :as rdom]
            [reagent.core :as r]
            [clojure.string :as str]
            [todo.app.db :as db]
            [todo.app.chart :refer [bar-chart pie-chart]]))

(defn toggle [id]
  (swap! db/todos update-in [id :done] not))

(defn add [title]
  (let [id (swap! db/counter inc)]
    (swap! db/todos assoc id {:id id :title title :done false})))

(defn delete [id]
  (swap! db/todos dissoc id))

(defn save [id title]
  (swap! db/todos assoc-in [id :title] title))

(defn handle-save [val on-save after-save]
  (let [val (-> val str str/trim)]
       (when-not (empty? val)
         (on-save val))
       (after-save)))

(defn handle-stop [val on-stop]
  (reset! val "")
  (when on-stop (on-stop)))

(defn todo-input [{:keys [title on-save on-stop]}]
  (let [value (r/atom title)
        after-save #(reset! value "")]
   (fn [{:keys [id class placeholder]}]
     [:input
      {:type "text"
       :id    id
       :class class
       :value @value
       :placeholder placeholder
       :on-key-down
       #(case (.-which %)
              13 (handle-save @value on-save after-save) ;; enter-key
              27 (handle-stop value on-stop)             ;; escape-key
              nil)
       :on-change
       #(reset! value (-> % .-target .-value))}])))

(def todo-edit
  (with-meta todo-input
     {:component-did-mount #(.focus (rdom/dom-node %))}))

(defn todo-item []
  (let [editing (r/atom false)]
   (fn [{:keys [done title id]}]
     [:li.todo-item
      [:label
       {:on-double-click #(reset! editing true)}
       [:input
        {:type "checkbox"
         :checked done
         :on-change #(toggle id)}]
       [:span title]]
      [:span
       {:class "btn delete-button"
        :on-click #(delete id)}
       [:i.fa.fa-trash]]
      (when @editing
        [todo-edit
         {:class "edit"
          :title title
          :on-save #(save id %)
          :on-stop #(reset! editing false)}])])))

(defn todo-wrapper []
  (let [t (r/atom false)]
   (fn [todos]
     [:div#todo-wrapper
      [:header#todo-header
       [:h1 "To-Do Items:"]
       [:button
        {:class "btn add-button"
         :type "button"
         :on-click #(swap! t not)}
        [:i.fa.fa-plus-circle] " Add Item"]]
      [:div#todos
       (when @t
         [todo-input
          {:placeholder "enter a new todo"
           :on-save add
           :class "todo-input"}])
       (for [todo todos
             :let [id (:id todo)]]
            ^{:key (gensym id)}
            [todo-item todo])]])))

(defn chart-wrapper [{:keys [wc done]}]
  [:div#chart-wrapper
   [:div.chart
    [pie-chart done]]
   [:div.chart
    [bar-chart wc]]])

(defn root []
  (let [todos (vals @db/todos)
        words (map :title todos)
        done (map :done todos)
        wc (frequencies words)]
   [:div#root
    [chart-wrapper
     {:wc wc
      :done done}]
    [todo-wrapper todos]]))

(defn ^:dev/after-load mount []
  (rdom/render
   [root]
   (js/document.getElementById "root")))

(defn ^:export main []
  (db/seed)
  (mount))
