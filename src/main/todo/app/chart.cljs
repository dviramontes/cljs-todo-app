(ns todo.app.chart)

(defn circumference [r]
  (* 2 js/Math.PI r))

(defn calc-percentage [num total radius]
  (let [percent (* 100 (/ num total))
        circ (circumference radius)
        calc (/ (* percent circ) 100)]
   (str "calc(" calc ")" " " circ)))

(defn bar [word _count y text-x text-y]
  (let [width (* _count 10)
        height 19]
   [:g.bar
    [:rect {:width width :height height :y y}]
    [:text {:x text-x :y text-y :dy ".35em"} (str _count " " word)]]))

(defn bar-chart [wc]
  [:figure
   [:figcaption "Word count of tasks"]
   [:svg#bar-chart
    [:title#title "A bar chart shows a word count of tasks"]
    (let [wc (sort-by val > wc)
          wc (map-indexed vector wc)]
     (for [[i wc] wc
           :let [[w c] wc
                 y (* 20 i)
                 text-x (+ 5 (* c 10))
                 text-y (+ 9.5 y)]]
          ^{:key (gensym w)}
          [bar w c y text-x text-y]))]])

(defn pie-chart [todos]
  (let [r 5
        total (count todos)
        complete-count (count (filter true? todos))]
    [:figure
     [:figcaption
      [:span.blue "Complete"]
      [:span " vs. "]
      [:span.tomato "incomplete"]
      [:span " tasks"]]
     [:svg#pie-chart
      {:viewBox "0 0 20 20"}
      [:circle
       {:r 10
        :cx 10
        :cy 10
        :fill "tomato"}]
      [:circle
       {:r r
        :cx 10
        :cy 10
        :fill "transparent"
        :stroke "blue"
        :strokeWidth 10
        :strokeDasharray
        (calc-percentage complete-count total r)}]]]))
