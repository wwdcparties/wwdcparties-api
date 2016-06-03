(ns wwdcparties.render.filters
  (:require [hiccup.core :refer [html]]
            [wwdcparties.render.icons :as icons]))

(defn filter-button [id icon name]
  (list [:input.js-filter {:type "checkbox" :id id :data-filter id
                           :role "checkbox" :aria-checked "false"
                           :onchange "applyFilters()"}]
        [:label.filter__label {:for id} [:div.filter__icon icon] name]))

(def filter-buttons
  (list [:div#filters
         [:div.filter-options
          (filter-button "alcohol" icons/booze "Alcohol")
          (filter-button "food" icons/food "Food")
          (filter-button "meetup" icons/chat "Meetup")
          (filter-button "presentation" icons/presentation "Presentation")
          (filter-button "all-ages" icons/all-ages "All Ages")
          (filter-button "outdoors" icons/tree "Outdoors")]
        [:label#label--matches.label " matched events"]]))
