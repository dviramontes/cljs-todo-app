(ns todo.app.chart)

(defn bar [word _count y text-x text-y]
  (let [width (* _count 10)
        height 19]
   [:g.bar
    [:rect {:width width :height height :y y}]
    [:text {:x text-x :y text-y :dy ".35em"} (str _count " " word)]]))

(defn bar-chart [wc]
  [:figure
   [:figcaption "Word count of tasks"]
   [:svg.chart
    {:version 1.1
     :xmlns "http://www.w3.org/2000/svg"
     :xmlnsXlink "http://www.w3.org/1999/xlink"
     :width 300
     :height 150
     :aria-labelledby "title"
     :role "img"}
    [:title#title "A bar chart shows a word count of tasks"]
    (for [[i wc] (map-indexed vector wc)
          :let [[w c] wc
                y (* 20 i)
                text-x (+ 5 (* c 10))
                text-y (+ 9.5 y)]]
         ^{:key (gensym w)}
         [bar w c y text-x text-y])]])

(defn pie-chart [todos]
  (let [total (count todos)
        complete-count (count (filter true? todos))
        incomplete-count (count (filter false? todos))]
    [:figure
     [:figcaption "Complete vs. incomplete tasks"]
     [:svg.pie-svg
      {:width 150
       :height 150}
      [:circle.pie
       {:r 25 :cx 50 :cy 50}]]]))
