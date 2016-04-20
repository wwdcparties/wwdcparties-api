(ns wwdcparties.render.nav
  (:require [hiccup.core :refer [html]]
            [wwdcparties.render.icons :as icons]))

(def header
  [:header {:role "header" }
   [:nav
    [:ul
     [:li.site-title [:a {:href "/"} "WWDC Parties " [:span "2016"]]]]]])
