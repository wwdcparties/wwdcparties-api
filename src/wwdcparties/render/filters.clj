(ns wwdcparties.render.filters
  (:require [hiccup.core :refer [html]]
            [wwdcparties.render.icons :as icons]))

(def filter-buttons
  (list [:div#filters.button-group
   [:h2 "Filter events"]
   [:div.filters
    [:input {:type "checkbox" :id "alcohol" :class "js-filter" :data-filter ".alcohol" :role "checkbox" :aria-checked "false"}]
    [:label {:for "alcohol"} icons/booze "Alcohol"]

    [:input {:type "checkbox" :id "food" :class "js-filter" :data-filter ".food" :role "checkbox" :aria-checked "false"}]
    [:label {:for "food"} icons/food "Food"]

    [:input {:type "checkbox" :id "meetup" :class "js-filter" :data-filter ".meetup" :role "checkbox" :aria-checked "false"}]
    [:label {:for "meetup"} icons/chat "Meetup"]

    [:input {:type "checkbox" :id "presentation" :class "js-filter" :data-filter ".presentation" :role "checkbox" :aria-checked "false"}]
    [:label {:for "presentation"} icons/presentation "Presentation"]

    [:input {:type "checkbox" :id "all-ages" :class "js-filter" :data-filter ".all-ages" :role "checkbox" :aria-checked "false"}]
    [:label {:for "all-ages"} icons/all-ages "All Ages"]

    [:input {:type "checkbox" :id "outdoors" :class "js-filter" :data-filter ".outdoors" :role "checkbox" :aria-checked "false"}]
    [:label {:for "outdoors"} icons/tree "Outdoors"]]]))
